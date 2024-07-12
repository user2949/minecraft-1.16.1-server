package io.netty.buffer;

import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCountedByteBuf extends AbstractByteBuf {
	private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(
		AbstractReferenceCountedByteBuf.class, "refCnt"
	);
	private volatile int refCnt;

	protected AbstractReferenceCountedByteBuf(int maxCapacity) {
		super(maxCapacity);
		refCntUpdater.set(this, 1);
	}

	@Override
	public int refCnt() {
		return this.refCnt;
	}

	protected final void setRefCnt(int refCnt) {
		refCntUpdater.set(this, refCnt);
	}

	@Override
	public ByteBuf retain() {
		return this.retain0(1);
	}

	@Override
	public ByteBuf retain(int increment) {
		return this.retain0(ObjectUtil.checkPositive(increment, "increment"));
	}

	private ByteBuf retain0(int increment) {
		int oldRef = refCntUpdater.getAndAdd(this, increment);
		if (oldRef > 0 && oldRef + increment >= oldRef) {
			return this;
		} else {
			refCntUpdater.getAndAdd(this, -increment);
			throw new IllegalReferenceCountException(oldRef, increment);
		}
	}

	@Override
	public ByteBuf touch() {
		return this;
	}

	@Override
	public ByteBuf touch(Object hint) {
		return this;
	}

	@Override
	public boolean release() {
		return this.release0(1);
	}

	@Override
	public boolean release(int decrement) {
		return this.release0(ObjectUtil.checkPositive(decrement, "decrement"));
	}

	private boolean release0(int decrement) {
		int oldRef = refCntUpdater.getAndAdd(this, -decrement);
		if (oldRef == decrement) {
			this.deallocate();
			return true;
		} else if (oldRef >= decrement && oldRef - decrement <= oldRef) {
			return false;
		} else {
			refCntUpdater.getAndAdd(this, decrement);
			throw new IllegalReferenceCountException(oldRef, -decrement);
		}
	}

	protected abstract void deallocate();
}
