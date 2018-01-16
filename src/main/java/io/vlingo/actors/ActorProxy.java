// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import java.lang.reflect.Constructor;
import static io.vlingo.actors.proxy.ProxyNaming.fullyQualifiedClassnameFor;

import io.vlingo.actors.proxy.ProxyClassLoader;
import io.vlingo.actors.proxy.ProxyCompiler;
import io.vlingo.actors.proxy.ProxyCompiler.Input;
import io.vlingo.actors.proxy.ProxyGenerator;
import io.vlingo.actors.proxy.ProxyGenerator.Result;

public final class ActorProxy {
  private static final ProxyClassLoader classLoader = new ProxyClassLoader(ClassLoader.getSystemClassLoader());
  private static final ProxyCompiler proxyCompiler = new ProxyCompiler();
  
  public static <T> T createFor(final Class<T> protocol, final Actor actor, final Mailbox mailbox) {
    final String proxyClassname = fullyQualifiedClassnameFor(protocol);
    
    try {
      return tryCreate(protocol, actor, mailbox, proxyClassname);
    } catch (Exception e) {
      return tryGenerateCreate(protocol, actor, mailbox, proxyClassname);
    }
  }

  public static Object[] createFor(final Class<?>[] protocols, final Actor actor, final Mailbox mailbox) {
    final Object[] proxies = new Object[protocols.length];
    
    for (int idx = 0; idx < protocols.length; ++idx) {
      proxies[idx] = createFor(protocols[idx], actor, mailbox);
    }

    return proxies;
  }

  @SuppressWarnings("unchecked")
  private static <T> T tryCreate(
          final Class<T> protocol,
          final Actor actor,
          final Mailbox mailbox,
          final String targetClassname)
  throws Exception {
    final Class<?> proxyClass = Class.forName(targetClassname, true, classLoader);
    return (T) tryCreateWithProxyClass(proxyClass, actor, mailbox);
  }
  
  @SuppressWarnings("unchecked")
  private static <T> T tryCreateWithProxyClass(final Class<T> proxyClass, final Actor actor, final Mailbox mailbox) throws Exception {
    Constructor<?> ctor = proxyClass.getConstructor(new Class<?>[] {Actor.class, Mailbox.class});
    return (T) ctor.newInstance(actor, mailbox);
  }

  private static <T> T tryGenerateCreate(
          final Class<T> protocol,
          final Actor actor,
          final Mailbox mailbox,
          final String targetClassname) {
    try (final ProxyGenerator generator = ProxyGenerator.forMain(true)) {
      return tryGenerateCreate(protocol, actor, mailbox, generator, targetClassname);
    } catch (Exception emain) {
      try (final ProxyGenerator generator = ProxyGenerator.forTest(true)) {
        return tryGenerateCreate(protocol, actor, mailbox, generator, targetClassname);
      } catch (Exception etest) {
        throw new IllegalArgumentException("Actor proxy " + protocol.getName() + " not created for main or test: " + etest.getMessage(), etest);
      }
    }
  }

  private static <T> T tryGenerateCreate(
          final Class<T> protocol,
          final Actor actor,
          final Mailbox mailbox,
          final ProxyGenerator generator,
          final String targetClassname) {
    try {
      final Result result = generator.generateFor(protocol.getName());
      final Input input = new Input(protocol, targetClassname, result.source, result.sourceFile, classLoader, generator.type(), true);
      final Class<T> proxyClass = proxyCompiler.compile(input);
      return tryCreateWithProxyClass(proxyClass, actor, mailbox);
    } catch (Exception e) {
      throw new IllegalArgumentException("Actor proxy " + protocol.getName() + " not created because: " + e.getMessage(), e);
    }
  }
}
