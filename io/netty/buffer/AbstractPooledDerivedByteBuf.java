package io.netty.buffer;

import io.netty.util.ReferenceCounted;
import io.netty.util.Recycler.Handle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractPooledDerivedByteBuf extends AbstractReferenceCountedByteBuf {
	private final Handle<AbstractPooledDerivedByteBuf> recyclerHandle;
	private AbstractByteBuf rootParent;
	private ByteBuf parent;

	AbstractPooledDerivedByteBuf(Handle<? extends AbstractPooledDerivedByteBuf> recyclerHandle) {
		super(0);
		this.recyclerHandle = recyclerHandle;
	}

	final void parent(ByteBuf newParent) {
		assert newParent instanceof SimpleLeakAwareByteBuf;

		this.parent = newParent;
	}

	public final AbstractByteBuf unwrap() {
		return this.rootParent;
	}

	final <U extends AbstractPooledDerivedByteBuf> U init(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex, int maxCapacity) {
		wrapped.retain();
		this.parent = wrapped;
		this.rootParent = unwrapped;

		AbstractPooledDerivedByteBuf var7;
		try {
			this.maxCapacity(maxCapacity);
			this.setIndex0(readerIndex, writerIndex);
			this.setRefCnt(1);
			wrapped = null;
			var7 = this;
		} finally {
			if (wrapped != null) {
				this.parent = this.rootParent = null;
				wrapped.release();
			}
		}

		return (U)var7;
	}

	@Override
	protected final void deallocate() {
		ByteBuf parent = this.parent;
		this.recyclerHandle.recycle(this);
		parent.release();
	}

	@Override
	public final ByteBufAllocator alloc() {
		return this.unwrap().alloc();
	}

	@Deprecated
	@Override
	public final ByteOrder order() {
		return this.unwrap().order();
	}

	@Override
	public boolean isReadOnly() {
		return this.unwrap().isReadOnly();
	}

	@Override
	public final boolean isDirect() {
		return this.unwrap().isDirect();
	}

	@Override
	public boolean hasArray() {
		return this.unwrap().hasArray();
	}

	@Override
	public byte[] array() {
		return this.unwrap().array();
	}

	@Override
	public boolean hasMemoryAddress() {
		return this.unwrap().hasMemoryAddress();
	}

	@Override
	public final int nioBufferCount() {
		return this.unwrap().nioBufferCount();
	}

	@Override
	public final ByteBuffer internalNioBuffer(int index, int length) {
		return this.nioBuffer(index, length);
	}

	@Override
	public final ByteBuf retainedSlice() {
		int index = this.readerIndex();
		return this.retainedSlice(index, this.writerIndex() - index);
	}

	@Override
	public ByteBuf slice(int index, int length) {
		this.ensureAccessible();
		return new AbstractPooledDerivedByteBuf.PooledNonRetainedSlicedByteBuf(this, this.unwrap(), index, length);
	}

	final ByteBuf duplicate0() {
		this.ensureAccessible();
		return new AbstractPooledDerivedByteBuf.PooledNonRetainedDuplicateByteBuf(this, this.unwrap());
	}

	private static final class PooledNonRetainedDuplicateByteBuf extends UnpooledDuplicatedByteBuf {
		private final ReferenceCounted referenceCountDelegate;

		PooledNonRetainedDuplicateByteBuf(ReferenceCounted referenceCountDelegate, AbstractByteBuf buffer) {
			super(buffer);
			this.referenceCountDelegate = referenceCountDelegate;
		}

		@Override
		int refCnt0() {
			return this.referenceCountDelegate.refCnt();
		}

		@Override
		ByteBuf retain0() {
			this.referenceCountDelegate.retain();
			return this;
		}

		@Override
		ByteBuf retain0(int increment) {
			this.referenceCountDelegate.retain(increment);
			return this;
		}

		@Override
		ByteBuf touch0() {
			this.referenceCountDelegate.touch();
			return this;
		}

		@Override
		ByteBuf touch0(Object hint) {
			this.referenceCountDelegate.touch(hint);
			return this;
		}

		@Override
		boolean release0() {
			return this.referenceCountDelegate.release();
		}

		@Override
		boolean release0(int decrement) {
			return this.referenceCountDelegate.release(decrement);
		}

		@Override
		public ByteBuf duplicate() {
			this.ensureAccessible();
			return new AbstractPooledDerivedByteBuf.PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this);
		}

		@Override
		public ByteBuf retainedDuplicate() {
			return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.readerIndex(), this.writerIndex());
		}

		@Override
		public ByteBuf slice(int index, int length) {
			this.checkIndex(index, length);
			return new AbstractPooledDerivedByteBuf.PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), index, length);
		}

		@Override
		public ByteBuf retainedSlice() {
			return this.retainedSlice(this.readerIndex(), this.capacity());
		}

		@Override
		public ByteBuf retainedSlice(int index, int length) {
			return PooledSlicedByteBuf.newInstance(this.unwrap(), this, index, length);
		}
	}

	private static final class PooledNonRetainedSlicedByteBuf extends UnpooledSlicedByteBuf {
		private final ReferenceCounted referenceCountDelegate;

		PooledNonRetainedSlicedByteBuf(ReferenceCounted referenceCountDelegate, AbstractByteBuf buffer, int index, int length) {
			super(buffer, index, length);
			this.referenceCountDelegate = referenceCountDelegate;
		}

		@Override
		int refCnt0() {
			return this.referenceCountDelegate.refCnt();
		}

		@Override
		ByteBuf retain0() {
			this.referenceCountDelegate.retain();
			return this;
		}

		@Override
		ByteBuf retain0(int increment) {
			this.referenceCountDelegate.retain(increment);
			return this;
		}

		@Override
		ByteBuf touch0() {
			this.referenceCountDelegate.touch();
			return this;
		}

		@Override
		ByteBuf touch0(Object hint) {
			this.referenceCountDelegate.touch(hint);
			return this;
		}

		@Override
		boolean release0() {
			return this.referenceCountDelegate.release();
		}

		@Override
		boolean release0(int decrement) {
			return this.referenceCountDelegate.release(decrement);
		}

		@Override
		public ByteBuf duplicate() {
			this.ensureAccessible();
			return new AbstractPooledDerivedByteBuf.PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this.unwrap())
				.setIndex(this.idx(this.readerIndex()), this.idx(this.writerIndex()));
		}

		@Override
		public ByteBuf retainedDuplicate() {
			return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.idx(this.readerIndex()), this.idx(this.writerIndex()));
		}

		@Override
		public ByteBuf slice(int index, int length) {
			this.checkIndex(index, length);
			return new AbstractPooledDerivedByteBuf.PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), this.idx(index), length);
		}

		@Override
		public ByteBuf retainedSlice() {
			return this.retainedSlice(0, this.capacity());
		}

		@Override
		public ByteBuf retainedSlice(int index, int length) {
			return PooledSlicedByteBuf.newInstance(this.unwrap(), this, this.idx(index), length);
		}
	}
}
