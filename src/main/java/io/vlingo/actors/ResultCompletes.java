// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import java.util.function.Consumer;
import java.util.function.Function;

public class ResultCompletes implements Completes<Object> {
  Completes<Object> clientCompletes;
  Object outcome = null;
  boolean outcomeSet = false;

  @Override
  public Completes<Object> after(final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> after(final long timeout, final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> after(final Object failedOutcomeValue, final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> after(final long timeout, final Object failedOutcomeValue, final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> andThen(final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> atLast(Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> otherwise(final Function<Object,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> exception(final Function<Exception,Object> function) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> consumeAfter(final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> consumeAfter(final long timeout, final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> consumeAfter(final Object failedOutcomeValue, final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> consumeAfter(final long timeout, final Object failedOutcomeValue, final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> andThenConsume(final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> atLastConsume(final Consumer<Object> consumer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object await() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object await(final long timeout) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isCompleted() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasFailed() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void failed() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasOutcome() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object outcome() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> repeat() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Completes<Object> with(final Object outcome) {
    this.outcomeSet = true;
    this.outcome = outcome;
    return this;
  }

  void reset(final Completes<Object> clientCompletes) {
    this.clientCompletes = clientCompletes;
    this.outcome = null;
    this.outcomeSet = false;
  }
}
