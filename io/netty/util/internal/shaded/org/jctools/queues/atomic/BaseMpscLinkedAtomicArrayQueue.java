package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import io.netty.util.internal.shaded.org.jctools.util.Pow2;
import io.netty.util.internal.shaded.org.jctools.util.RangeUtil;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public abstract class BaseMpscLinkedAtomicArrayQueue<E>
	extends BaseMpscLinkedAtomicArrayQueueColdProducerFields<E>
	implements MessagePassingQueue<E>,
	QueueProgressIndicators {
	private static final Object JUMP = new Object();

	public BaseMpscLinkedAtomicArrayQueue(int initialCapacity) {
		RangeUtil.checkGreaterThanOrEqual(initialCapacity, 2, "initialCapacity");
		int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
		long mask = (long)(p2capacity - 1 << 1);
		AtomicReferenceArray<E> buffer = LinkedAtomicArrayQueueUtil.allocate(p2capacity + 1);
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
					AtomicReferenceArray<E> buffer = this.producerBuffer;
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
						int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(pIndex, mask);
						LinkedAtomicArrayQueueUtil.soElement(buffer, offset, e);
						return true;
					}
				}
			}
		}
	}

	@Override
	public E poll() {
		AtomicReferenceArray<E> buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
		if (e == null) {
			if (index == this.lvProducerIndex()) {
				return null;
			}

			do {
				e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
			} while (e == null);
		}

		if (e == JUMP) {
			AtomicReferenceArray<E> nextBuffer = this.getNextBuffer(buffer, mask);
			return this.newBufferPoll(nextBuffer, index);
		} else {
			LinkedAtomicArrayQueueUtil.soElement(buffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return (E)e;
		}
	}

	@Override
	public E peek() {
		AtomicReferenceArray<E> buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
		if (e == null && index != this.lvProducerIndex()) {
			while ((e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset)) == null) {
			}
		}

		return (E)(e == JUMP ? this.newBufferPeek(this.getNextBuffer(buffer, mask), index) : e);
	}

	private int offerSlowPath(long mask, long pIndex, long producerLimit) {
		long cIndex = this.lvConsumerIndex();
		long bufferCapacity = this.getCurrentBufferCapacity(mask);
		int result = 0;
		if (cIndex + bufferCapacity > pIndex) {
			if (!this.casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
				result = 1;
			}
		} else if (this.availableInQueue(pIndex, cIndex) <= 0L) {
			result = 2;
		} else if (this.casProducerIndex(pIndex, pIndex + 1L)) {
			result = 3;
		} else {
			result = 1;
		}

		return result;
	}

	protected abstract long availableInQueue(long long1, long long2);

	private AtomicReferenceArray<E> getNextBuffer(AtomicReferenceArray<E> buffer, long mask) {
		int offset = this.nextArrayOffset(mask);
		AtomicReferenceArray<E> nextBuffer = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
		LinkedAtomicArrayQueueUtil.soElement(buffer, offset, null);
		return nextBuffer;
	}

	private int nextArrayOffset(long mask) {
		return LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(mask + 2L, Long.MAX_VALUE);
	}

	private E newBufferPoll(AtomicReferenceArray<E> nextBuffer, long index) {
		int offset = this.newBufferAndOffset(nextBuffer, index);
		E n = LinkedAtomicArrayQueueUtil.lvElement(nextBuffer, offset);
		if (n == null) {
			throw new IllegalStateException("new buffer must have at least one element");
		} else {
			LinkedAtomicArrayQueueUtil.soElement(nextBuffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return n;
		}
	}

	private E newBufferPeek(AtomicReferenceArray<E> nextBuffer, long index) {
		int offset = this.newBufferAndOffset(nextBuffer, index);
		E n = LinkedAtomicArrayQueueUtil.lvElement(nextBuffer, offset);
		if (null == n) {
			throw new IllegalStateException("new buffer must have at least one element");
		} else {
			return n;
		}
	}

	private int newBufferAndOffset(AtomicReferenceArray<E> nextBuffer, long index) {
		this.consumerBuffer = nextBuffer;
		this.consumerMask = (long)(LinkedAtomicArrayQueueUtil.length(nextBuffer) - 2 << 1);
		return LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, this.consumerMask);
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
		AtomicReferenceArray<E> buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
		if (e == null) {
			return null;
		} else if (e == JUMP) {
			AtomicReferenceArray<E> nextBuffer = this.getNextBuffer(buffer, mask);
			return this.newBufferPoll(nextBuffer, index);
		} else {
			LinkedAtomicArrayQueueUtil.soElement(buffer, offset, null);
			this.soConsumerIndex(index + 2L);
			return (E)e;
		}
	}

	@Override
	public E relaxedPeek() {
		AtomicReferenceArray<E> buffer = this.consumerBuffer;
		long index = this.consumerIndex;
		long mask = this.consumerMask;
		int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(index, mask);
		Object e = LinkedAtomicArrayQueueUtil.lvElement(buffer, offset);
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
				AtomicReferenceArray<E> buffer = this.producerBuffer;
				long batchIndex = Math.min(producerLimit, pIndex + (long)(2 * batchSize));
				if (pIndex == producerLimit || producerLimit < batchIndex) {
					int result = this.offerSlowPath(mask, pIndex, producerLimit);
					switch (result) {
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
					boolean var14 = false;

					for (int var15 = 0; var15 < claimedSlots; var15++) {
						int offset = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(pIndex + (long)(2 * var15), mask);
						LinkedAtomicArrayQueueUtil.soElement(buffer, offset, s.get());
					}

					return claimedSlots;
				}
			}
		}
	}

	@Override
	public void fill(Supplier<E> s, WaitStrategy w, ExitCondition exit) {
		while (exit.keepRunning()) {
			while (this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) != 0 && exit.keepRunning()) {
			}

			int idleCounter = 0;

			while (exit.keepRunning() && this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
				idleCounter = w.idle(idleCounter);
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

	private void resize(long oldMask, AtomicReferenceArray<E> oldBuffer, long pIndex, E e) {
		int newBufferLength = this.getNextBufferSize(oldBuffer);
		AtomicReferenceArray<E> newBuffer = LinkedAtomicArrayQueueUtil.allocate(newBufferLength);
		this.producerBuffer = newBuffer;
		int newMask = newBufferLength - 2 << 1;
		this.producerMask = (long)newMask;
		int offsetInOld = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(pIndex, oldMask);
		int offsetInNew = LinkedAtomicArrayQueueUtil.modifiedCalcElementOffset(pIndex, (long)newMask);
		LinkedAtomicArrayQueueUtil.soElement(newBuffer, offsetInNew, e);
		LinkedAtomicArrayQueueUtil.soElement(oldBuffer, this.nextArrayOffset(oldMask), newBuffer);
		long cIndex = this.lvConsumerIndex();
		long availableInQueue = this.availableInQueue(pIndex, cIndex);
		RangeUtil.checkPositive(availableInQueue, "availableInQueue");
		this.soProducerLimit(pIndex + Math.min((long)newMask, availableInQueue));
		this.soProducerIndex(pIndex + 2L);
		LinkedAtomicArrayQueueUtil.soElement(oldBuffer, offsetInOld, JUMP);
	}

	protected abstract int getNextBufferSize(AtomicReferenceArray<E> atomicReferenceArray);

	protected abstract long getCurrentBufferCapacity(long long1);
}
