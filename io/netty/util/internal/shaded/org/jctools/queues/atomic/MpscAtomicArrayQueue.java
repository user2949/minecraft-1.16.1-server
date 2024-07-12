package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import io.netty.util.internal.shaded.org.jctools.util.PortableJvmInfo;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MpscAtomicArrayQueue<E> extends MpscAtomicArrayQueueL3Pad<E> {
	public MpscAtomicArrayQueue(int capacity) {
		super(capacity);
	}

	public boolean offerIfBelowThreshold(E e, int threshold) {
		if (null == e) {
			throw new NullPointerException();
		} else {
			int mask = this.mask;
			long capacity = (long)(mask + 1);
			long producerLimit = this.lvProducerLimit();

			long pIndex;
			do {
				pIndex = this.lvProducerIndex();
				long available = producerLimit - pIndex;
				long size = capacity - available;
				if (size >= (long)threshold) {
					long cIndex = this.lvConsumerIndex();
					size = pIndex - cIndex;
					if (size >= (long)threshold) {
						return false;
					}

					producerLimit = cIndex + capacity;
					this.soProducerLimit(producerLimit);
				}
			} while (!this.casProducerIndex(pIndex, pIndex + 1L));

			int offset = this.calcElementOffset(pIndex, mask);
			soElement(this.buffer, offset, e);
			return true;
		}
	}

	@Override
	public boolean offer(E e) {
		if (null == e) {
			throw new NullPointerException();
		} else {
			int mask = this.mask;
			long producerLimit = this.lvProducerLimit();

			long pIndex;
			do {
				pIndex = this.lvProducerIndex();
				if (pIndex >= producerLimit) {
					long cIndex = this.lvConsumerIndex();
					producerLimit = cIndex + (long)mask + 1L;
					if (pIndex >= producerLimit) {
						return false;
					}

					this.soProducerLimit(producerLimit);
				}
			} while (!this.casProducerIndex(pIndex, pIndex + 1L));

			int offset = this.calcElementOffset(pIndex, mask);
			soElement(this.buffer, offset, e);
			return true;
		}
	}

	public final int failFastOffer(E e) {
		if (null == e) {
			throw new NullPointerException();
		} else {
			int mask = this.mask;
			long capacity = (long)(mask + 1);
			long pIndex = this.lvProducerIndex();
			long producerLimit = this.lvProducerLimit();
			if (pIndex >= producerLimit) {
				long cIndex = this.lvConsumerIndex();
				producerLimit = cIndex + capacity;
				if (pIndex >= producerLimit) {
					return 1;
				}

				this.soProducerLimit(producerLimit);
			}

			if (!this.casProducerIndex(pIndex, pIndex + 1L)) {
				return -1;
			} else {
				int offset = this.calcElementOffset(pIndex, mask);
				soElement(this.buffer, offset, e);
				return 0;
			}
		}
	}

	@Override
	public E poll() {
		long cIndex = this.lpConsumerIndex();
		int offset = this.calcElementOffset(cIndex);
		AtomicReferenceArray<E> buffer = this.buffer;
		E e = lvElement(buffer, offset);
		if (null == e) {
			if (cIndex == this.lvProducerIndex()) {
				return null;
			}

			do {
				e = lvElement(buffer, offset);
			} while (e == null);
		}

		spElement(buffer, offset, null);
		this.soConsumerIndex(cIndex + 1L);
		return e;
	}

	@Override
	public E peek() {
		AtomicReferenceArray<E> buffer = this.buffer;
		long cIndex = this.lpConsumerIndex();
		int offset = this.calcElementOffset(cIndex);
		E e = lvElement(buffer, offset);
		if (null == e) {
			if (cIndex == this.lvProducerIndex()) {
				return null;
			}

			do {
				e = lvElement(buffer, offset);
			} while (e == null);
		}

		return e;
	}

	@Override
	public boolean relaxedOffer(E e) {
		return this.offer(e);
	}

	@Override
	public E relaxedPoll() {
		AtomicReferenceArray<E> buffer = this.buffer;
		long cIndex = this.lpConsumerIndex();
		int offset = this.calcElementOffset(cIndex);
		E e = lvElement(buffer, offset);
		if (null == e) {
			return null;
		} else {
			spElement(buffer, offset, null);
			this.soConsumerIndex(cIndex + 1L);
			return e;
		}
	}

	@Override
	public E relaxedPeek() {
		AtomicReferenceArray<E> buffer = this.buffer;
		int mask = this.mask;
		long cIndex = this.lpConsumerIndex();
		return lvElement(buffer, this.calcElementOffset(cIndex, mask));
	}

	@Override
	public int drain(Consumer<E> c) {
		return this.drain(c, this.capacity());
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
	public int drain(Consumer<E> c, int limit) {
		AtomicReferenceArray<E> buffer = this.buffer;
		int mask = this.mask;
		long cIndex = this.lpConsumerIndex();

		for (int i = 0; i < limit; i++) {
			long index = cIndex + (long)i;
			int offset = this.calcElementOffset(index, mask);
			E e = lvElement(buffer, offset);
			if (null == e) {
				return i;
			}

			spElement(buffer, offset, null);
			this.soConsumerIndex(index + 1L);
			c.accept(e);
		}

		return limit;
	}

	@Override
	public int fill(Supplier<E> s, int limit) {
		int mask = this.mask;
		long capacity = (long)(mask + 1);
		long producerLimit = this.lvProducerLimit();
		int actualLimit = 0;

		long pIndex;
		do {
			pIndex = this.lvProducerIndex();
			long available = producerLimit - pIndex;
			if (available <= 0L) {
				long cIndex = this.lvConsumerIndex();
				producerLimit = cIndex + capacity;
				available = producerLimit - pIndex;
				if (available <= 0L) {
					return 0;
				}

				this.soProducerLimit(producerLimit);
			}

			actualLimit = Math.min((int)available, limit);
		} while (!this.casProducerIndex(pIndex, pIndex + (long)actualLimit));

		AtomicReferenceArray<E> buffer = this.buffer;

		for (int i = 0; i < actualLimit; i++) {
			int offset = this.calcElementOffset(pIndex + (long)i, mask);
			soElement(buffer, offset, s.get());
		}

		return actualLimit;
	}

	@Override
	public void drain(Consumer<E> c, WaitStrategy w, ExitCondition exit) {
		AtomicReferenceArray<E> buffer = this.buffer;
		int mask = this.mask;
		long cIndex = this.lpConsumerIndex();
		int counter = 0;

		while (exit.keepRunning()) {
			for (int i = 0; i < 4096; i++) {
				int offset = this.calcElementOffset(cIndex, mask);
				E e = lvElement(buffer, offset);
				if (null == e) {
					counter = w.idle(counter);
				} else {
					cIndex++;
					counter = 0;
					spElement(buffer, offset, null);
					this.soConsumerIndex(cIndex);
					c.accept(e);
				}
			}
		}
	}

	@Override
	public void fill(Supplier<E> s, WaitStrategy w, ExitCondition exit) {
		int idleCounter = 0;

		while (exit.keepRunning()) {
			if (this.fill(s, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
				idleCounter = w.idle(idleCounter);
			} else {
				idleCounter = 0;
			}
		}
	}

	@Deprecated
	public int weakOffer(E e) {
		return this.failFastOffer(e);
	}
}
