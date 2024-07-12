package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
import java.util.Iterator;

public abstract class BaseMpscLinkedArrayQueue<E>
	extends BaseMpscLinkedArrayQueueColdProducerFields<E>
	implements MessagePassingQueue<E>,
	QueueProgressIndicators {
	private static final Object JUMP = new Object();
	private static final int CONTINUE_TO_P_INDEX_CAS = 0;
	private static final int RETRY = 1;
	private static final int QUEUE_FULL = 2;
	private static final int QUEUE_RESIZE = 3;

	public BaseMpscLinkedArrayQueue(int initialCapacity) {
		RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
		int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
		long mask = (long)(p2capacity - 1 << 1);
		E[] buffer = (E[])CircularArrayOffsetCalculator.allocate(p2capacity + 1);
		this.producerBuffer = buffer;
		this.producerMask = mask;
		this.consumerBuffer = buffer;
		this.consumerMask = mask;
		this.soProducerLimit(mask);
	}

	public final Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final int size() {
		long after = this.lvConsumerIndex();

		long before;
		long currentProducerIndex;
		do {
			before = after;
			currentProducerIndex = this.lvProducerIndex();
			after = this.lvConsumerIndex();
		} while (before != after);

		long size = currentProducerIndex - after >> 1;
		return size > 2147483647L ? Integer.MAX_VALUE : (int)size;
	}

	@Override
	public final boolean isEmpty() {
		return this.lvConsumerIndex() == this.lvProducerIndex();
	}

	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public boolean offer(E e) {
		if (null == e) {
			throw new NullPointerException();
		} else {
			while (true) {
				long producerLimit = this.lvProducerLimit();
				long pIndex = this.lvProducerIndex();
				if ((pIndex & 1L) != 1L) {
					long mask = this.producerMask;
					E[] buffer = this.producerBuffer;
					if (producerLimit <= pIndex) {
						int result = this.offerSlowPath(mask, pIndex, producerLimit);
						switch (result) {
							case 0:
							default:
								break;
							case 1:
								continue;
							case 2:
								return false;
							case 3:
								this.resize(mask, buffer, pIndex, e);
								return true;
						}
					}

					if (this.casProducerIndex(pIndex, pIndex + 2L)) {
						producerLimit = LinkedArrayQueueUtil.modifiedCalcElementOffset(pIndex, mask);
						UnsafeRefArrayAccess.soElement(buffer, producerLimit, e);
						return true;
					}
				}
			}
		}
	}

	@Override
	public E poll() {
		E[] buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
		if (e == null) {
			if (index == this.lvProducerIndex()) {
				return null;
			}

			do {
				e = UnsafeRefArrayAccess.lvElement(buffer, offset);
			} while (e == null);
		}

		if (e == JUMP) {
			E[] nextBuffer = this.getNextBuffer(buffer, mask);
			return this.newBufferPoll(nextBuffer, index);
		} else {
			UnsafeRefArrayAccess.soElement(buffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return (E)e;
		}
	}

	@Override
	public E peek() {
		E[] buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
		if (e == null && index != this.lvProducerIndex()) {
			do {
				e = UnsafeRefArrayAccess.lvElement(buffer, offset);
			} while (e == null);
		}

		return (E)(e == JUMP ? this.newBufferPeek(this.getNextBuffer(buffer, mask), index) : e);
	}

	private int offerSlowPath(long mask, long pIndex, long producerLimit) {
		long cIndex = this.lvConsumerIndex();
		long bufferCapacity = this.getCurrentBufferCapacity(mask);
		if (cIndex + bufferCapacity > pIndex) {
			return !this.casProducerLimit(producerLimit, cIndex + bufferCapacity) ? 1 : 0;
		} else if (this.availableInQueue(pIndex, cIndex) <= 0L) {
			return 2;
		} else {
			return this.casProducerIndex(pIndex, pIndex + 1L) ? 3 : 1;
		}
	}

	protected abstract long availableInQueue(long long1, long long2);

	private E[] getNextBuffer(E[] buffer, long mask) {
		long offset = this.nextArrayOffset(mask);
		E[] nextBuffer = (E[])((Object[])UnsafeRefArrayAccess.lvElement(buffer, offset));
		UnsafeRefArrayAccess.soElement(buffer, offset, null);
		return nextBuffer;
	}

	private long nextArrayOffset(long mask) {
		return LinkedArrayQueueUtil.modifiedCalcElementOffset(mask + 2L, Long.MAX_VALUE);
	}

	private E newBufferPoll(E[] nextBuffer, long index) {
		long offset = this.newBufferAndOffset(nextBuffer, index);
		E n = UnsafeRefArrayAccess.lvElement(nextBuffer, offset);
		if (n == null) {
			throw new IllegalStateException("new buffer must have at least one element");
		} else {
			UnsafeRefArrayAccess.soElement(nextBuffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return n;
		}
	}

	private E newBufferPeek(E[] nextBuffer, long index) {
		long offset = this.newBufferAndOffset(nextBuffer, index);
		E n = UnsafeRefArrayAccess.lvElement(nextBuffer, offset);
		if (null == n) {
			throw new IllegalStateException("new buffer must have at least one element");
		} else {
			return n;
		}
	}

	private long newBufferAndOffset(E[] nextBuffer, long index) {
		this.consumerBuffer = nextBuffer;
		this.consumerMask = (long)(LinkedArrayQueueUtil.length(nextBuffer) - 2 << 1);
		return LinkedArrayQueueUtil.modifiedCalcElementOffset(index, this.consumerMask);
	}

	@Override
	public long currentProducerIndex() {
		return this.lvProducerIndex() / 2L;
	}

	@Override
	public long currentConsumerIndex() {
		return this.lvConsumerIndex() / 2L;
	}

	@Override
	public abstract int capacity();

	@Override
	public boolean relaxedOffer(E e) {
		return this.offer(e);
	}

	@Override
	public E relaxedPoll() {
		E[] buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
		if (e == null) {
			return null;
		} else if (e == JUMP) {
			E[] nextBuffer = this.getNextBuffer(buffer, mask);
			return this.newBufferPoll(nextBuffer, index);
		} else {
			UnsafeRefArrayAccess.soElement(buffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return (E)e;
		}
	}

	@Override
	public E relaxedPeek() {
		E[] buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
		return (E)(e == JUMP ? this.newBufferPeek(this.getNextBuffer(buffer, mask), index) : e);
	}

	@Override
	public int fill(Supplier<E> s) {
		long result = 0L;
		int capacity = this.capacity();

		do {
			int filled = this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
			if (filled == 0) {
				return (int)result;
			}

			result += (long)filled;
		} while (result <= (long)capacity);

		return (int)result;
	}

	@Override
	public int fill(Supplier<E> s, int batchSize) {
		while (true) {
			long producerLimit = this.lvProducerLimit();
			long pIndex = this.lvProducerIndex();
			if ((pIndex & 1L) != 1L) {
				long mask = this.producerMask;
				E[] buffer = this.producerBuffer;
				long batchIndex = Math.min(producerLimit, pIndex + (long)(2 * batchSize));
				if (pIndex >= producerLimit || producerLimit < batchIndex) {
					int result = this.offerSlowPath(mask, pIndex, producerLimit);
					switch (result) {
						case 0:
						case 1:
							continue;
						case 2:
							return 0;
						case 3:
							this.resize(mask, buffer, pIndex, s.get());
							return 1;
					}
				}

				if (this.casProducerIndex(pIndex, batchIndex)) {
					int claimedSlots = (int)((batchIndex - pIndex) / 2L);

					for (int var14 = 0; var14 < claimedSlots; var14++) {
						long offset = LinkedArrayQueueUtil.modifiedCalcElementOffset(pIndex + (long)(2 * var14), mask);
						UnsafeRefArrayAccess.soElement(buffer, offset, s.get());
					}

					return claimedSlots;
				}
			}
		}
	}

	@Override
	public void fill(Supplier<E> s, WaitStrategy w, ExitCondition exit) {
		while (exit.keepRunning()) {
			if (this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
				int idleCounter = 0;

				while (exit.keepRunning() && this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
					idleCounter = w.idle(idleCounter);
				}
			}
		}
	}

	@Override
	public int drain(Consumer<E> c) {
		return this.drain(c, this.capacity());
	}

	@Override
	public int drain(Consumer<E> c, int limit) {
		int i;
		E m;
		for (i = 0; i < limit && (m = this.relaxedPoll()) != null; i++) {
			c.accept(m);
		}

		return i;
	}

	@Override
	public void drain(Consumer<E> c, WaitStrategy w, ExitCondition exit) {
		int idleCounter = 0;

		while (exit.keepRunning()) {
			E e = this.relaxedPoll();
			if (e == null) {
				idleCounter = w.idle(idleCounter);
			} else {
				idleCounter = 0;
				c.accept(e);
			}
		}
	}

	private void resize(long oldMask, E[] oldBuffer, long pIndex, E e) {
		int newBufferLength = this.getNextBufferSize(oldBuffer);
		E[] newBuffer = (E[])CircularArrayOffsetCalculator.allocate(newBufferLength);
		this.producerBuffer = newBuffer;
		int newMask = newBufferLength - 2 << 1;
		this.producerMask = (long)newMask;
		long offsetInOld = LinkedArrayQueueUtil.modifiedCalcElementOffset(pIndex, oldMask);
		long offsetInNew = LinkedArrayQueueUtil.modifiedCalcElementOffset(pIndex, (long)newMask);
		UnsafeRefArrayAccess.soElement(newBuffer, offsetInNew, e);
		UnsafeRefArrayAccess.soElement(oldBuffer, this.nextArrayOffset(oldMask), (E)newBuffer);
		long cIndex = this.lvConsumerIndex();
		long availableInQueue = this.availableInQueue(pIndex, cIndex);
		RangeUtil.checkPositive(availableInQueue, "availableInQueue");
		this.soProducerLimit(pIndex + Math.min((long)newMask, availableInQueue));
		this.soProducerIndex(pIndex + 2L);
		UnsafeRefArrayAccess.soElement(oldBuffer, offsetInOld, JUMP);
	}

	protected abstract int getNextBufferSize(E[] arr);

	protected abstract long getCurrentBufferCapacity(long long1);
}
