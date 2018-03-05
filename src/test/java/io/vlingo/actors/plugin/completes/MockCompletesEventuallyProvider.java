// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors.plugin.completes;

import io.vlingo.actors.Completes;
import io.vlingo.actors.CompletesEventually;
import io.vlingo.actors.CompletesEventuallyProvider;
import io.vlingo.actors.MockCompletes;
import io.vlingo.actors.Stage;

public class MockCompletesEventuallyProvider implements CompletesEventuallyProvider {
  public static int initializeUsing;
  public static int provideCompletesForCount;
  
  public MockCompletesEventually completesEventually;
  public MockCompletes<?> completes;
  
  @Override
  public void close() { }

  @Override
  public CompletesEventually completesEventually() {
    return completesEventually;
  }

  @Override
  public void initializeUsing(final Stage stage) {
    completesEventually = new MockCompletesEventually();
    ++initializeUsing;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Completes<T> provideCompletesFor(final Completes<T> clientCompletes) {
    if (completes == null) {
      completes = new MockCompletes<T>();
    }
    ++provideCompletesForCount;
    return (Completes<T>) completes;
  }
}
