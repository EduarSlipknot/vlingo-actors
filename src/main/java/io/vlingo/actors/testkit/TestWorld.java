// Copyright © 2012-2017 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors.testkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vlingo.actors.Address;
import io.vlingo.actors.Configuration;
import io.vlingo.actors.Definition;
import io.vlingo.actors.MailboxProvider;
import io.vlingo.actors.Message;
import io.vlingo.actors.Stage;
import io.vlingo.actors.World;
import io.vlingo.actors.plugin.mailbox.testkit.TestMailboxPlugin;

public class TestWorld implements AutoCloseable {
  private static final Map<Integer, List<Message>> actorMessages = new HashMap<>();
  
  private final MailboxProvider mailboxProvider;
  private final World world;

  public static List<Message> allMessagesFor(final Address address) {
    final List<Message> all = actorMessages.get(address.id());
    
    return all == null ? new ArrayList<>() : all;
  }

  public static TestWorld start(final String name) {
    return new TestWorld(name);
  }

  public static TestWorld start(final String name, final Configuration configuration) {
        return new TestWorld(name, configuration);
    }

  public static void track(final Message message) {
    final int id = message.actor.address().id();
    final List<Message> messages = actorMessages.computeIfAbsent(id, k -> new ArrayList<>());
    messages.add(message);
  }

  public <T> TestActor<T> actorFor(final Definition definition, final Class<T> protocol) {
    if (world.isTerminated()) {
      throw new IllegalStateException("vlingo/actors: TestWorld has stopped.");
    }

    return world.stage().testActorFor(definition, protocol);
  }

  public Stage stage() {
    return world.stage();
  }

  public Stage stageNamed(final String name) {
    return world.stageNamed(name);
  }

  public boolean isTerminated() {
    return world.isTerminated();
  }

  public void terminate() {
    world.terminate();
  }

  public World world() {
    return world;
  }

  protected MailboxProvider mailboxProvider() {
    return this.mailboxProvider;
  }

  public void clearTrackedMessages() {
    actorMessages.clear();
  }

  private TestWorld(final String name) {
      this(name, new Configuration());
  }

  private TestWorld(final String name, final Configuration configuration) {
    this.world = World.start(name, configuration);
    this.mailboxProvider = new TestMailboxPlugin(this.world);
  }

  @Override
  public void close() throws Exception {
    if (!isTerminated()) {
      terminate();
    }
  }
}
