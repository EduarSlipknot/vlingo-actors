// Copyright 2012-2017 For Comprehension, Inc.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorldTest extends ActorsTest {
  private World world;
  
  @Test
  public void testStartWorld() throws Exception {
    assertNotNull(world.configuration());
    assertNotNull(world.deadLetters());
    assertEquals("test", world.name());
    assertNotNull(world.scheduler());
    assertNotNull(world.stage());
    assertEquals(world, world.stage().world());
    assertFalse(world.isTerminated());
    assertNotNull(world.findDefaultMailboxName());
    assertEquals("queueMailbox", world.findDefaultMailboxName());
    assertNotNull(world.assignMailbox("queueMailbox", 10));
    assertNotNull(world.defaultParent());
    assertNotNull(world.privateRoot());
    assertNotNull(world.publicRoot());
  }
  
  @Test
  public void testWorldActorFor() throws Exception {
    final Simple simple = world.actorFor(Definition.has(SimpleActor.class, Definition.NoParameters), Simple.class);
    
    simple.simpleSay();
    
    pause();
    
    assertTrue(SimpleActor.invoked);
  }

  @Before
  public void setUp() {
    world = World.start("test");
  }
  
  @After
  public void tearDown() throws Exception {
    world.terminate();
    
    assertTrue(world.stage().isStopped());
    assertTrue(world.isTerminated());
  }
  
  public static interface Simple {
    void simpleSay();
  }
  
  public static class SimpleActor extends Actor implements Simple {
    public static boolean invoked;
    
    @Override
    public void simpleSay() {
      invoked = true;
    }
  }
}
