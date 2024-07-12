package org.apache.logging.log4j.core.async;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.jctools.queues.MpscArrayQueue;
import org.jctools.queues.MessagePassingQueue.Consumer;

@Plugin(
	name = "JCToolsBlockingQueue",
	category = "Core",
	elementType = "BlockingQueueFactory"
)
public class JCToolsBlockingQueueFactory<E> implements BlockingQueueFactory<E> {
	private final JCToolsBlockingQueueFactory.WaitStrategy waitStrategy;

	private JCToolsBlockingQueueFactory(JCToolsBlockingQueueFactory.WaitStrategy waitStrategy) {
		this.waitStrategy = waitStrategy;
	}

	@Override
	public BlockingQueue<E> create(int capacity) {
		return new JCToolsBlockingQueueFactory.MpscBlockingQueue<>(capacity, this.waitStrategy);
	}

	@PluginFactory
	public static <E> JCToolsBlockingQueueFactory<E> createFactory(
		@PluginAttribute(value = "WaitStrategy",defaultString = "PARK") JCToolsBlockingQueueFactory.WaitStrategy waitStrategy
	) {
		return new JCToolsBlockingQueueFactory<>(waitStrategy);
	}

	private interface Idle {
		int idle(int integer);
	}

	private static final class MpscBlockingQueue<E> extends MpscArrayQueue<E> implements BlockingQueue<E> {
		private final JCToolsBlockingQueueFactory.WaitStrategy waitStrategy;

		MpscBlockingQueue(int capacity, JCToolsBlockingQueueFactory.WaitStrategy waitStrategy) {
			super(capacity);
			this.waitStrategy = waitStrategy;
		}

		public int drainTo(Collection<? super E> c) {
			return this.drainTo(c, this.capacity());
		}

		public int drainTo(Collection<? super E> c, int maxElements) {
			return this.drain(new Consumer<E>() {
				public void accept(E e) {
					c.add(e);
				}
			}, maxElements);
		}

		public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
			int idleCounter = 0;
			long timeoutNanos = System.nanoTime() + unit.toNanos(timeout);

			while (!this.offer(e)) {
				if (System.nanoTime() - timeoutNanos > 0L) {
					return false;
				}

				idleCounter = this.waitStrategy.idle(idleCounter);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}

			return true;
		}

		public E poll(long timeout, TimeUnit unit) throws InterruptedException {
			int idleCounter = 0;
			long timeoutNanos = System.nanoTime() + unit.toNanos(timeout);

			do {
				E result = (E)this.poll();
				if (result != null) {
					return result;
				}

				if (System.nanoTime() - timeoutNanos > 0L) {
					return null;
				}

				idleCounter = this.waitStrategy.idle(idleCounter);
			} while (!Thread.interrupted());

			throw new InterruptedException();
		}

		public void put(E e) throws InterruptedException {
			int idleCounter = 0;

			while (!this.offer(e)) {
				idleCounter = this.waitStrategy.idle(idleCounter);
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
			}
		}

		public boolean offer(E e) {
			return this.offerIfBelowThreshold(e, this.capacity() - 32);
		}

		public int remainingCapacity() {
			return this.capacity() - this.size();
		}

		public E take() throws InterruptedException {
			int idleCounter = 100;

			do {
				E result = (E)this.relaxedPoll();
				if (result != null) {
					return result;
				}

				idleCounter = this.waitStrategy.idle(idleCounter);
			} while (!Thread.interrupted());

			throw new InterruptedException();
		}
	}

	public static enum WaitStrategy {
		SPIN(new JCToolsBlockingQueueFactory.Idle() {
			@Override
			public int idle(int idleCounter) {
				return idleCounter + 1;
			}
		}),
		YIELD(new JCToolsBlockingQueueFactory.Idle() {
			@Override
			public int idle(int idleCounter) {
				Thread.yield();
				return idleCounter + 1;
			}
		}),
		PARK(new JCToolsBlockingQueueFactory.Idle() {
			@Override
			public int idle(int idleCounter) {
				LockSupport.parkNanos(1L);
				return idleCounter + 1;
			}
		}),
		PROGRESSIVE(new JCToolsBlockingQueueFactory.Idle() {
			@Override
			public int idle(int idleCounter) {
				if (idleCounter > 200) {
					LockSupport.parkNanos(1L);
				} else if (idleCounter > 100) {
					Thread.yield();
				}

				return idleCounter + 1;
			}
		});

		private final JCToolsBlockingQueueFactory.Idle idle;

		private int idle(int idleCounter) {
			return this.idle.idle(idleCounter);
		}

		private WaitStrategy(JCToolsBlockingQueueFactory.Idle idle) {
			this.idle = idle;
		}
	}
}
