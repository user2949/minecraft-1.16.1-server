package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Dispatcher {
	static Dispatcher perThreadDispatchQueue() {
		return new Dispatcher.PerThreadQueuedDispatcher();
	}

	static Dispatcher legacyAsync() {
		return new Dispatcher.LegacyAsyncDispatcher();
	}

	static Dispatcher immediate() {
		return Dispatcher.ImmediateDispatcher.INSTANCE;
	}

	abstract void dispatch(Object object, Iterator<Subscriber> iterator);

	private static final class ImmediateDispatcher extends Dispatcher {
		private static final Dispatcher.ImmediateDispatcher INSTANCE = new Dispatcher.ImmediateDispatcher();

		@Override
		void dispatch(Object event, Iterator<Subscriber> subscribers) {
			Preconditions.checkNotNull(event);

			while (subscribers.hasNext()) {
				((Subscriber)subscribers.next()).dispatchEvent(event);
			}
		}
	}

	private static final class LegacyAsyncDispatcher extends Dispatcher {
		private final ConcurrentLinkedQueue<Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber> queue = Queues.newConcurrentLinkedQueue();

		private LegacyAsyncDispatcher() {
		}

		@Override
		void dispatch(Object event, Iterator<Subscriber> subscribers) {
			Preconditions.checkNotNull(event);

			while (subscribers.hasNext()) {
				this.queue.add(new Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber(event, (Subscriber)subscribers.next()));
			}

			Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber e;
			while ((e = (Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber)this.queue.poll()) != null) {
				e.subscriber.dispatchEvent(e.event);
			}
		}

		private static final class EventWithSubscriber {
			private final Object event;
			private final Subscriber subscriber;

			private EventWithSubscriber(Object event, Subscriber subscriber) {
				this.event = event;
				this.subscriber = subscriber;
			}
		}
	}

	private static final class PerThreadQueuedDispatcher extends Dispatcher {
		private final ThreadLocal<Queue<Dispatcher.PerThreadQueuedDispatcher.Event>> queue = new ThreadLocal<Queue<Dispatcher.PerThreadQueuedDispatcher.Event>>() {
			protected Queue<Dispatcher.PerThreadQueuedDispatcher.Event> initialValue() {
				return Queues.<Dispatcher.PerThreadQueuedDispatcher.Event>newArrayDeque();
			}
		};
		private final ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>() {
			protected Boolean initialValue() {
				return false;
			}
		};

		private PerThreadQueuedDispatcher() {
		}

		@Override
		void dispatch(Object event, Iterator<Subscriber> subscribers) {
			Preconditions.checkNotNull(event);
			Preconditions.checkNotNull(subscribers);
			Queue<Dispatcher.PerThreadQueuedDispatcher.Event> queueForThread = (Queue<Dispatcher.PerThreadQueuedDispatcher.Event>)this.queue.get();
			queueForThread.offer(new Dispatcher.PerThreadQueuedDispatcher.Event(event, subscribers));
			if (!(Boolean)this.dispatching.get()) {
				this.dispatching.set(true);

				Dispatcher.PerThreadQueuedDispatcher.Event nextEvent;
				try {
					while ((nextEvent = (Dispatcher.PerThreadQueuedDispatcher.Event)queueForThread.poll()) != null) {
						while (nextEvent.subscribers.hasNext()) {
							((Subscriber)nextEvent.subscribers.next()).dispatchEvent(nextEvent.event);
						}
					}
				} finally {
					this.dispatching.remove();
					this.queue.remove();
				}
			}
		}

		private static final class Event {
			private final Object event;
			private final Iterator<Subscriber> subscribers;

			private Event(Object event, Iterator<Subscriber> subscribers) {
				this.event = event;
				this.subscribers = subscribers;
			}
		}
	}
}
