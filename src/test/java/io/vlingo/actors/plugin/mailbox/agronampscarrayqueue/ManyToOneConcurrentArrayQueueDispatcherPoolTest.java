// Copyright © 2012-2017 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors.plugin.mailbox.agronampscarrayqueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ManyToOneConcurrentArrayQueueDispatcherPoolTest {

  @Test
  public void testPoolMemberAssignment() throws Exception {
    final Map<ManyToOneConcurrentArrayQueueDispatcher, Integer> counts = new HashMap<>();
    
    final ManyToOneConcurrentArrayQueueDispatcherPool pool =
            new ManyToOneConcurrentArrayQueueDispatcherPool(4, 1.0f, 64, 2,5, 10);
    
    for (int idx = -100_000; idx < 100_000; ++idx) {
      final ManyToOneConcurrentArrayQueueDispatcher dispatcher = pool.assignFor(idx);
      
      Integer count = counts.get(dispatcher);
      
      if (count == null) {
        count = 0;
      }
      
      count = count + 1;
      counts.put(dispatcher, count);
    }
    
    assertEquals(4, counts.size());
    
    final Set<Integer> equalCounts = new HashSet<>(counts.values());
    
    assertEquals(1, equalCounts.size());
    
    for (final int count : counts.values()) {
      assertNotEquals(-1, count);
      assertNotEquals(0, count);
      assertNotEquals(1, count);
      assertFalse(equalCounts.add(count));
    }
  }
}
