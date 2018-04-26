// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.vlingo.actors.plugin.mailbox.testkit.TestMailbox;

public class StageTest {
  private World world;
  
  @Test
  public void testActorForDefinitionAndProtocol() throws Exception {
    final Definition definition = Definition.has(TestInterfaceActor.class, Definition.NoParameters);

    final NoProtocol test = world.stage().actorFor(definition, NoProtocol.class);
    
    assertNotNull(test);
    assertNotNull(TestInterfaceActor.instance.get());
    assertEquals(world.defaultParent(), TestInterfaceActor.instance.get().parent());
  }
  
  @Test
  public void testActorForAll() throws Exception {
    world.actorFor(Definition.has(ParentInterfaceActor.class, Definition.NoParameters), NoProtocol.class);
    
    final Definition definition =
            Definition.has(
                    TestInterfaceActor.class,
                    Definition.NoParameters,
                    ParentInterfaceActor.parent.get(),
                    TestMailbox.Name,
                    "test-actor");

    final NoProtocol test = world.stage().actorFor(definition, NoProtocol.class);
    
    assertNotNull(test);
    assertNotNull(TestInterfaceActor.instance.get());
  }

  @Before
  public void setUp() {
    world = World.start("test");
  }
  
  @After
  public void tearDown() throws Exception {
    world.terminate();
  }

  public static class ParentInterfaceActor extends Actor implements NoProtocol {
    public static ThreadLocal<ParentInterfaceActor> parent = new ThreadLocal<>();
    
    public ParentInterfaceActor() { parent.set(this); }
  }

  public static class TestInterfaceActor extends Actor implements NoProtocol {
    public static ThreadLocal<TestInterfaceActor> instance = new ThreadLocal<>();
    
    public TestInterfaceActor() {
      instance.set(this);
    }
  }
}
