// Copyright © 2012-2017 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Directory {
  private final Map<Address, Actor>[] maps;

  protected Directory() {
    this.maps = build();
  }

  protected int count() {
    int count = 0;
    for (final Map<Address, Actor> map : maps) {
      count += map.size();
    }
    return count;
  }

  protected void dump() {
    for (final Map<Address, Actor> map : maps) {
      for (final Actor actor : map.values()) {
        final Address address = actor.address();
        final Address parent = actor.__internal__Environment() == null ? new Address(0, "NONE") : actor.__internal__Environment().address;
        System.out.println("DIR: DUMP: ACTOR: " + address + " PARENT: " + parent);
      }
    }
  }

  protected boolean isRegistered(final Address address) {
    return this.maps[mapIndex(address)].containsKey(address);
  }

  protected void register(final Address address, final Actor actor) {
    if (isRegistered(address)) {
      throw new IllegalArgumentException("The actor address is already registered: " + address);
    }
    this.maps[mapIndex(address)].put(address, actor);
  }

  protected Actor remove(final Address address) {
    return this.maps[mapIndex(address)].remove(address);
  }

  @SuppressWarnings("unchecked")
  private Map<Address, Actor>[] build() {
    final Map<Address, Actor>[] tempMaps = new ConcurrentHashMap[20];

    for (int idx = 0; idx < tempMaps.length; ++idx) {
      tempMaps[idx] = new ConcurrentHashMap<Address, Actor>(16, 0.75f, 16);  // TODO: base this on scheduler/dispatcher
    }

    return tempMaps;
  }

  private int mapIndex(final Address address) {
    return Math.abs(address.hashCode() % maps.length);
  }
}
