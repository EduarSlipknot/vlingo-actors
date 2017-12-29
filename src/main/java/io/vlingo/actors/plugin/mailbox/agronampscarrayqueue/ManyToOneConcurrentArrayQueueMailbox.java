// Copyright © 2012-2017 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors.plugin.mailbox.agronampscarrayqueue;

import io.vlingo.actors.Dispatcher;
import io.vlingo.actors.Mailbox;
import io.vlingo.actors.Message;
import uk.co.real_logic.agrona.concurrent.ManyToOneConcurrentArrayQueue;

public class ManyToOneConcurrentArrayQueueMailbox implements Mailbox {
  private final Dispatcher dispatcher;
  private final ManyToOneConcurrentArrayQueue<Message> queue;
  private final int totalSendRetries;

  @Override
  public void close() {
    dispatcher.close();
    queue.clear();
  }

  @Override
  public boolean isDelivering() {
    throw new UnsupportedOperationException("ManyToOneConcurrentArrayQueueMailbox does not support this operation.");
  }

  @Override
  public boolean delivering(final boolean flag) {
    throw new UnsupportedOperationException("ManyToOneConcurrentArrayQueueMailbox does not support this operation.");
  }

  @Override
  public void run() {
    throw new UnsupportedOperationException("ManyToOneConcurrentArrayQueueMailbox does not support this operation.");
  }

  @Override
  public void send(final Message message) {
    for (int tries = 0; tries < totalSendRetries; ++tries) {
      if (queue.offer(message)) {
        break;
      } else {
        delay();
      }
    }
  }

  @Override
  public final Message receive() {
    return queue.poll();
  }

  protected ManyToOneConcurrentArrayQueueMailbox(final Dispatcher dispatcher, final int mailboxSize, final int totalSendRetries) {
    this.dispatcher = dispatcher;
    this.queue = new ManyToOneConcurrentArrayQueue<>(mailboxSize);
    this.totalSendRetries = totalSendRetries;
  }

  private void delay() {
    // TODO: support configurable delay strategy
    try {
      Thread.sleep(2); // sleep for 2 milliseconds
    } catch (InterruptedException ignored) {
    }
  }
}
