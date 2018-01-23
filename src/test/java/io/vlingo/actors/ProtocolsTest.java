// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.vlingo.actors.testkit.TestActor;

public class ProtocolsTest extends ActorsTest {

  @Test
  public void testTwoProtocols() {
    final Protocols protocols =
            testWorld.actorFor(
                    Definition.has(TwoProtocolsActor.class, Definition.NoParameters),
                    new Class<?>[] { P1.class, P2.class });
    
    final Protocols.Two<TestActor<P1>, TestActor<P2>> two = Protocols.two(protocols);
    
    two.p1().actor().do1();
    assertEquals(1, TwoProtocolsActor.do1Count);
    
    two.p2().actor().do2();
    two.p2().actor().do2();
    assertEquals(2, TwoProtocolsActor.do2Count);
  }

  @Test
  public void testThreeProtocols() {
    final Protocols protocols =
            testWorld.actorFor(
                    Definition.has(ThreeProtocolsActor.class, Definition.NoParameters),
                    new Class<?>[] { P1.class, P2.class, P3.class });
    
    final Protocols.Three<TestActor<P1>, TestActor<P2>, TestActor<P3>> three = Protocols.three(protocols);
    
    three.p1().actor().do1();
    assertEquals(1, ThreeProtocolsActor.do1Count);
    
    three.p2().actor().do2();
    three.p2().actor().do2();
    assertEquals(2, ThreeProtocolsActor.do2Count);
    
    three.p3().actor().do3();
    three.p3().actor().do3();
    three.p3().actor().do3();
    assertEquals(3, ThreeProtocolsActor.do3Count);
  }

  @Test
  public void testFourProtocols() {
    final Protocols protocols =
            testWorld.actorFor(
                    Definition.has(FourProtocolsActor.class, Definition.NoParameters),
                    new Class<?>[] { P1.class, P2.class, P3.class, P4.class });
    
    final Protocols.Four<TestActor<P1>, TestActor<P2>, TestActor<P3>, TestActor<P4>> four = Protocols.four(protocols);
    
    four.p1().actor().do1();
    assertEquals(1, FourProtocolsActor.do1Count);
    
    four.p2().actor().do2();
    four.p2().actor().do2();
    assertEquals(2, FourProtocolsActor.do2Count);
    
    four.p3().actor().do3();
    four.p3().actor().do3();
    four.p3().actor().do3();
    assertEquals(3, FourProtocolsActor.do3Count);
    
    four.p4().actor().do4();
    four.p4().actor().do4();
    four.p4().actor().do4();
    four.p4().actor().do4();
    assertEquals(4, FourProtocolsActor.do4Count);
  }

  @Test
  public void testFiveProtocols() {
    final Protocols protocols =
            testWorld.actorFor(
                    Definition.has(FiveProtocolsActor.class, Definition.NoParameters),
                    new Class<?>[] { P1.class, P2.class, P3.class, P4.class, P5.class });
    
    final Protocols.Five<TestActor<P1>, TestActor<P2>, TestActor<P3>, TestActor<P4>, TestActor<P5>> five = Protocols.five(protocols);
    
    five.p1().actor().do1();
    assertEquals(1, FiveProtocolsActor.do1Count);
    
    five.p2().actor().do2();
    five.p2().actor().do2();
    assertEquals(2, FiveProtocolsActor.do2Count);
    
    five.p3().actor().do3();
    five.p3().actor().do3();
    five.p3().actor().do3();
    assertEquals(3, FiveProtocolsActor.do3Count);
    
    five.p4().actor().do4();
    five.p4().actor().do4();
    five.p4().actor().do4();
    five.p4().actor().do4();
    assertEquals(4, FiveProtocolsActor.do4Count);
    
    five.p5().actor().do5();
    five.p5().actor().do5();
    five.p5().actor().do5();
    five.p5().actor().do5();
    five.p5().actor().do5();
    assertEquals(5, FiveProtocolsActor.do5Count);
  }

  public static interface P1 {
    void do1();
  }

  public static interface P2 {
    void do2();
  }

  public static interface P3 {
    void do3();
  }

  public static interface P4 {
    void do4();
  }

  public static interface P5 {
    void do5();
  }

  public static class TwoProtocolsActor extends Actor implements P1, P2 {
    public static int do1Count;
    public static int do2Count;
    
    @Override
    public void do1() {
      ++do1Count;
    }

    @Override
    public void do2() {
      ++do2Count;
    }
  }

  public static class ThreeProtocolsActor extends Actor implements P1, P2, P3 {
    public static int do1Count;
    public static int do2Count;
    public static int do3Count;
    
    @Override
    public void do1() {
      ++do1Count;
    }

    @Override
    public void do2() {
      ++do2Count;
    }

    @Override
    public void do3() {
      ++do3Count;
    }
  }

  public static class FourProtocolsActor extends Actor implements P1, P2, P3, P4 {
    public static int do1Count;
    public static int do2Count;
    public static int do3Count;
    public static int do4Count;
    
    @Override
    public void do1() {
      ++do1Count;
    }

    @Override
    public void do2() {
      ++do2Count;
    }

    @Override
    public void do3() {
      ++do3Count;
    }

    @Override
    public void do4() {
      ++do4Count;
    }
  }

  public static class FiveProtocolsActor extends Actor implements P1, P2, P3, P4, P5 {
    public static int do1Count;
    public static int do2Count;
    public static int do3Count;
    public static int do4Count;
    public static int do5Count;
    
    @Override
    public void do1() {
      ++do1Count;
    }

    @Override
    public void do2() {
      ++do2Count;
    }

    @Override
    public void do3() {
      ++do3Count;
    }

    @Override
    public void do4() {
      ++do4Count;
    }

    @Override
    public void do5() {
      ++do5Count;
    }
  }
}
