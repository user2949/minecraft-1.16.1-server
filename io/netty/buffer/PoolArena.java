package io.netty.buffer;

import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PoolArena<T> implements PoolArenaMetric {
	static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
	static final int numTinySubpagePools = 32;
	final PooledByteBufAllocator parent;
	private final int maxOrder;
	final int pageSize;
	final int pageShifts;
	final int chunkSize;
	final int subpageOverflowMask;
	final int numSmallSubpagePools;
	final int directMemoryCacheAlignment;
	final int directMemoryCacheAlignmentMask;
	private final PoolSubpage<T>[] tinySubpagePools;
	private final PoolSubpage<T>[] smallSubpagePools;
	private final PoolChunkList<T> q050;
	private final PoolChunkList<T> q025;
	private final PoolChunkList<T> q000;
	private final PoolChunkList<T> qInit;
	private final PoolChunkList<T> q075;
	private final PoolChunkList<T> q100;
	private final List<PoolChunkListMetric> chunkListMetrics;
	private long allocationsNormal;
	private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
	private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
	private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
	private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
	private long deallocationsTiny;
	private long deallocationsSmall;
	private long deallocationsNormal;
	private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
	final AtomicInteger numThreadCaches = new AtomicInteger();

	protected PoolArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int cacheAlignment) {
		this.parent = parent;
		this.pageSize = pageSize;
		this.maxOrder = maxOrder;
		this.pageShifts = pageShifts;
		this.chunkSize = chunkSize;
		this.directMemoryCacheAlignment = cacheAlignment;
		this.directMemoryCacheAlignmentMask = cacheAlignment - 1;
		this.subpageOverflowMask = ~(pageSize - 1);
		this.tinySubpagePools = this.newSubpagePoolArray(32);

		for (int i = 0; i < this.tinySubpagePools.length; i++) {
			this.tinySubpagePools[i] = this.newSubpagePoolHead(pageSize);
		}

		this.numSmallSubpagePools = pageShifts - 9;
		this.smallSubpagePools = this.newSubpagePoolArray(this.numSmallSubpagePools);

		for (int i = 0; i < this.smallSubpagePools.length; i++) {
			this.smallSubpagePools[i] = this.newSubpagePoolHead(pageSize);
		}

		this.q100 = new PoolChunkList<>(this, null, 100, Integer.MAX_VALUE, chunkSize);
		this.q075 = new PoolChunkList<>(this, this.q100, 75, 100, chunkSize);
		this.q050 = new PoolChunkList<>(this, this.q075, 50, 100, chunkSize);
		this.q025 = new PoolChunkList<>(this, this.q050, 25, 75, chunkSize);
		this.q000 = new PoolChunkList<>(this, this.q025, 1, 50, chunkSize);
		this.qInit = new PoolChunkList<>(this, this.q000, Integer.MIN_VALUE, 25, chunkSize);
		this.q100.prevList(this.q075);
		this.q075.prevList(this.q050);
		this.q050.prevList(this.q025);
		this.q025.prevList(this.q000);
		this.q000.prevList(null);
		this.qInit.prevList(this.qInit);
		List<PoolChunkListMetric> metrics = new ArrayList(6);
		metrics.add(this.qInit);
		metrics.add(this.q000);
		metrics.add(this.q025);
		metrics.add(this.q050);
		metrics.add(this.q075);
		metrics.add(this.q100);
		this.chunkListMetrics = Collections.unmodifiableList(metrics);
	}

	private PoolSubpage<T> newSubpagePoolHead(int pageSize) {
		PoolSubpage<T> head = new PoolSubpage<>(pageSize);
		head.prev = head;
		head.next = head;
		return head;
	}

	private PoolSubpage<T>[] newSubpagePoolArray(int size) {
		return new PoolSubpage[size];
	}

	abstract boolean isDirect();

	PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
		PooledByteBuf<T> buf = this.newByteBuf(maxCapacity);
		this.allocate(cache, buf, reqCapacity);
		return buf;
	}

	static int tinyIdx(int normCapacity) {
		return normCapacity >>> 4;
	}

	static int smallIdx(int normCapacity) {
		int tableIdx = 0;

		for (int i = normCapacity >>> 10; i != 0; tableIdx++) {
			i >>>= 1;
		}

		return tableIdx;
	}

	boolean isTinyOrSmall(int normCapacity) {
		return (normCapacity & this.subpageOverflowMask) == 0;
	}

	static boolean isTiny(int normCapacity) {
		return (normCapacity & -512) == 0;
	}

	private void allocate(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity) {
		int normCapacity = this.normalizeCapacity(reqCapacity);
		if (this.isTinyOrSmall(normCapacity)) {
			boolean tiny = isTiny(normCapacity);
			int tableIdx;
			PoolSubpage<T>[] table;
			if (tiny) {
				if (cache.allocateTiny(this, buf, reqCapacity, normCapacity)) {
					return;
				}

				tableIdx = tinyIdx(normCapacity);
				table = this.tinySubpagePools;
			} else {
				if (cache.allocateSmall(this, buf, reqCapacity, normCapacity)) {
					return;
				}

				tableIdx = smallIdx(normCapacity);
				table = this.smallSubpagePools;
			}

			PoolSubpage<T> head = table[tableIdx];
			synchronized (head) {
				PoolSubpage<T> s = head.next;
				if (s != head) {
					if ($assertionsDisabled || s.doNotDestroy && s.elemSize == normCapacity) {
						long handle = s.allocate();

						assert handle >= 0L;

						s.chunk.initBufWithSubpage(buf, handle, reqCapacity);
						this.incTinySmallAllocation(tiny);
						return;
					}

					throw new AssertionError();
				}
			}

			synchronized (this) {
				this.allocateNormal(buf, reqCapacity, normCapacity);
			}

			this.incTinySmallAllocation(tiny);
		} else {
			if (normCapacity <= this.chunkSize) {
				if (cache.allocateNormal(this, buf, reqCapacity, normCapacity)) {
					return;
				}

				synchronized (this) {
					this.allocateNormal(buf, reqCapacity, normCapacity);
					this.allocationsNormal++;
				}
			} else {
				this.allocateHuge(buf, reqCapacity);
			}
		}
	}

	private void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
		if (!this.q050.allocate(buf, reqCapacity, normCapacity)
			&& !this.q025.allocate(buf, reqCapacity, normCapacity)
			&& !this.q000.allocate(buf, reqCapacity, normCapacity)
			&& !this.qInit.allocate(buf, reqCapacity, normCapacity)
			&& !this.q075.allocate(buf, reqCapacity, normCapacity)) {
			PoolChunk<T> c = this.newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
			long handle = c.allocate(normCapacity);

			assert handle > 0L;

			c.initBuf(buf, handle, reqCapacity);
			this.qInit.add(c);
		}
	}

	private void incTinySmallAllocation(boolean tiny) {
		if (tiny) {
			this.allocationsTiny.increment();
		} else {
			this.allocationsSmall.increment();
		}
	}

	private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
		PoolChunk<T> chunk = this.newUnpooledChunk(reqCapacity);
		this.activeBytesHuge.add((long)chunk.chunkSize());
		buf.initUnpooled(chunk, reqCapacity);
		this.allocationsHuge.increment();
	}

	void free(PoolChunk<T> chunk, long handle, int normCapacity, PoolThreadCache cache) {
		if (chunk.unpooled) {
			int size = chunk.chunkSize();
			this.destroyChunk(chunk);
			this.activeBytesHuge.add((long)(-size));
			this.deallocationsHuge.increment();
		} else {
			PoolArena.SizeClass sizeClass = this.sizeClass(normCapacity);
			if (cache != null && cache.add(this, chunk, handle, normCapacity, sizeClass)) {
				return;
			}

			this.freeChunk(chunk, handle, sizeClass);
		}
	}

	private PoolArena.SizeClass sizeClass(int normCapacity) {
		if (!this.isTinyOrSmall(normCapacity)) {
			return PoolArena.SizeClass.Normal;
		} else {
			return isTiny(normCapacity) ? PoolArena.SizeClass.Tiny : PoolArena.SizeClass.Small;
		}
	}

	void freeChunk(PoolChunk<T> chunk, long handle, PoolArena.SizeClass sizeClass) {
		boolean destroyChunk;
		synchronized (this) {
			switch (sizeClass) {
				case Normal:
					this.deallocationsNormal++;
					break;
				case Small:
					this.deallocationsSmall++;
					break;
				case Tiny:
					this.deallocationsTiny++;
					break;
				default:
					throw new Error();
			}

			destroyChunk = !chunk.parent.free(chunk, handle);
		}

		if (destroyChunk) {
			this.destroyChunk(chunk);
		}
	}

	PoolSubpage<T> findSubpagePoolHead(int elemSize) {
		int tableIdx;
		PoolSubpage<T>[] table;
		if (isTiny(elemSize)) {
			tableIdx = elemSize >>> 4;
			table = this.tinySubpagePools;
		} else {
			tableIdx = 0;

			for (int var4 = elemSize >>> 10; var4 != 0; tableIdx++) {
				var4 >>>= 1;
			}

			table = this.smallSubpagePools;
		}

		return table[tableIdx];
	}

	int normalizeCapacity(int reqCapacity) {
		if (reqCapacity < 0) {
			throw new IllegalArgumentException("capacity: " + reqCapacity + " (expected: 0+)");
		} else if (reqCapacity >= this.chunkSize) {
			return this.directMemoryCacheAlignment == 0 ? reqCapacity : this.alignCapacity(reqCapacity);
		} else if (!isTiny(reqCapacity)) {
			int normalizedCapacity = reqCapacity - 1;
			normalizedCapacity |= normalizedCapacity >>> 1;
			normalizedCapacity |= normalizedCapacity >>> 2;
			normalizedCapacity |= normalizedCapacity >>> 4;
			normalizedCapacity |= normalizedCapacity >>> 8;
			normalizedCapacity |= normalizedCapacity >>> 16;
			if (++normalizedCapacity < 0) {
				normalizedCapacity >>>= 1;
			}

			assert this.directMemoryCacheAlignment == 0 || (normalizedCapacity & this.directMemoryCacheAlignmentMask) == 0;

			return normalizedCapacity;
		} else if (this.directMemoryCacheAlignment > 0) {
			return this.alignCapacity(reqCapacity);
		} else {
			return (reqCapacity & 15) == 0 ? reqCapacity : (reqCapacity & -16) + 16;
		}
	}

	int alignCapacity(int reqCapacity) {
		int delta = reqCapacity & this.directMemoryCacheAlignmentMask;
		return delta == 0 ? reqCapacity : reqCapacity + this.directMemoryCacheAlignment - delta;
	}

	void reallocate(PooledByteBuf<T> buf, int newCapacity, boolean freeOldMemory) {
		if (newCapacity >= 0 && newCapacity <= buf.maxCapacity()) {
			int oldCapacity = buf.length;
			if (oldCapacity != newCapacity) {
				PoolChunk<T> oldChunk = buf.chunk;
				long oldHandle = buf.handle;
				T oldMemory = buf.memory;
				int oldOffset = buf.offset;
				int oldMaxLength = buf.maxLength;
				int readerIndex = buf.readerIndex();
				int writerIndex = buf.writerIndex();
				this.allocate(this.parent.threadCache(), buf, newCapacity);
				if (newCapacity > oldCapacity) {
					this.memoryCopy(oldMemory, oldOffset, buf.memory, buf.offset, oldCapacity);
				} else if (newCapacity < oldCapacity) {
					if (readerIndex < newCapacity) {
						if (writerIndex > newCapacity) {
							writerIndex = newCapacity;
						}

						this.memoryCopy(oldMemory, oldOffset + readerIndex, buf.memory, buf.offset + readerIndex, writerIndex - readerIndex);
					} else {
						writerIndex = newCapacity;
						readerIndex = newCapacity;
					}
				}

				buf.setIndex(readerIndex, writerIndex);
				if (freeOldMemory) {
					this.free(oldChunk, oldHandle, oldMaxLength, buf.cache);
				}
			}
		} else {
			throw new IllegalArgumentException("newCapacity: " + newCapacity);
		}
	}

	@Override
	public int numThreadCaches() {
		return this.numThreadCaches.get();
	}

	@Override
	public int numTinySubpages() {
		return this.tinySubpagePools.length;
	}

	@Override
	public int numSmallSubpages() {
		return this.smallSubpagePools.length;
	}

	@Override
	public int numChunkLists() {
		return this.chunkListMetrics.size();
	}

	@Override
	public List<PoolSubpageMetric> tinySubpages() {
		return subPageMetricList(this.tinySubpagePools);
	}

	@Override
	public List<PoolSubpageMetric> smallSubpages() {
		return subPageMetricList(this.smallSubpagePools);
	}

	@Override
	public List<PoolChunkListMetric> chunkLists() {
		return this.chunkListMetrics;
	}

	private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] pages) {
		List<PoolSubpageMetric> metrics = new ArrayList();

		for (PoolSubpage<?> head : pages) {
			if (head.next != head) {
				PoolSubpage<?> s = head.next;

				while (true) {
					metrics.add(s);
					s = s.next;
					if (s == head) {
						break;
					}
				}
			}
		}

		return metrics;
	}

	@Override
	public long numAllocations() {
		long allocsNormal;
		synchronized (this) {
			allocsNormal = this.allocationsNormal;
		}

		return this.allocationsTiny.value() + this.allocationsSmall.value() + allocsNormal + this.allocationsHuge.value();
	}

	@Override
	public long numTinyAllocations() {
		return this.allocationsTiny.value();
	}

	@Override
	public long numSmallAllocations() {
		return this.allocationsSmall.value();
	}

	@Override
	public synchronized long numNormalAllocations() {
		return this.allocationsNormal;
	}

	@Override
	public long numDeallocations() {
		long deallocs;
		synchronized (this) {
			deallocs = this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal;
		}

		return deallocs + this.deallocationsHuge.value();
	}

	@Override
	public synchronized long numTinyDeallocations() {
		return this.deallocationsTiny;
	}

	@Override
	public synchronized long numSmallDeallocations() {
		return this.deallocationsSmall;
	}

	@Override
	public synchronized long numNormalDeallocations() {
		return this.deallocationsNormal;
	}

	@Override
	public long numHugeAllocations() {
		return this.allocationsHuge.value();
	}

	@Override
	public long numHugeDeallocations() {
		return this.deallocationsHuge.value();
	}

	@Override
	public long numActiveAllocations() {
		long val = this.allocationsTiny.value() + this.allocationsSmall.value() + this.allocationsHuge.value() - this.deallocationsHuge.value();
		synchronized (this) {
			val += this.allocationsNormal - (this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal);
		}

		return Math.max(val, 0L);
	}

	@Override
	public long numActiveTinyAllocations() {
		return Math.max(this.numTinyAllocations() - this.numTinyDeallocations(), 0L);
	}

	@Override
	public long numActiveSmallAllocations() {
		return Math.max(this.numSmallAllocations() - this.numSmallDeallocations(), 0L);
	}

	@Override
	public long numActiveNormalAllocations() {
		long val;
		synchronized (this) {
			val = this.allocationsNormal - this.deallocationsNormal;
		}

		return Math.max(val, 0L);
	}

	@Override
	public long numActiveHugeAllocations() {
		return Math.max(this.numHugeAllocations() - this.numHugeDeallocations(), 0L);
	}

	@Override
	public long numActiveBytes() {
		long val = this.activeBytesHuge.value();
		synchronized (this) {
			for (int i = 0; i < this.chunkListMetrics.size(); i++) {
				for (PoolChunkMetric m : (PoolChunkListMetric)this.chunkListMetrics.get(i)) {
					val += (long)m.chunkSize();
				}
			}
		}

		return Math.max(0L, val);
	}

	protected abstract PoolChunk<T> newChunk(int integer1, int integer2, int integer3, int integer4);

	protected abstract PoolChunk<T> newUnpooledChunk(int integer);

	protected abstract PooledByteBuf<T> newByteBuf(int integer);

	protected abstract void memoryCopy(T object1, int integer2, T object3, int integer4, int integer5);

	protected abstract void destroyChunk(PoolChunk<T> poolChunk);

	public synchronized String toString() {
		StringBuilder buf = new StringBuilder()
			.append("Chunk(s) at 0~25%:")
			.append(StringUtil.NEWLINE)
			.append(this.qInit)
			.append(StringUtil.NEWLINE)
			.append("Chunk(s) at 0~50%:")
			.append(StringUtil.NEWLINE)
			.append(this.q000)
			.append(StringUtil.NEWLINE)
			.append("Chunk(s) at 25~75%:")
			.append(StringUtil.NEWLINE)
			.append(this.q025)
			.append(StringUtil.NEWLINE)
			.append("Chunk(s) at 50~100%:")
			.append(StringUtil.NEWLINE)
			.append(this.q050)
			.append(StringUtil.NEWLINE)
			.append("Chunk(s) at 75~100%:")
			.append(StringUtil.NEWLINE)
			.append(this.q075)
			.append(StringUtil.NEWLINE)
			.append("Chunk(s) at 100%:")
			.append(StringUtil.NEWLINE)
			.append(this.q100)
			.append(StringUtil.NEWLINE)
			.append("tiny subpages:");
		appendPoolSubPages(buf, this.tinySubpagePools);
		buf.append(StringUtil.NEWLINE).append("small subpages:");
		appendPoolSubPages(buf, this.smallSubpagePools);
		buf.append(StringUtil.NEWLINE);
		return buf.toString();
	}

	private static void appendPoolSubPages(StringBuilder buf, PoolSubpage<?>[] subpages) {
		for (int i = 0; i < subpages.length; i++) {
			PoolSubpage<?> head = subpages[i];
			if (head.next != head) {
				buf.append(StringUtil.NEWLINE).append(i).append(": ");
				PoolSubpage<?> s = head.next;

				while (true) {
					buf.append(s);
					s = s.next;
					if (s == head) {
						break;
					}
				}
			}
		}
	}

	protected final void finalize() throws Throwable {
		try {
			super.finalize();
		} finally {
			destroyPoolSubPages(this.smallSubpagePools);
			destroyPoolSubPages(this.tinySubpagePools);
			this.destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
		}
	}

	private static void destroyPoolSubPages(PoolSubpage<?>[] pages) {
		for (PoolSubpage<?> page : pages) {
			page.destroy();
		}
	}

	private void destroyPoolChunkLists(PoolChunkList<T>... chunkLists) {
		for (PoolChunkList<T> chunkList : chunkLists) {
			chunkList.destroy(this);
		}
	}

	static final class DirectArena extends PoolArena<ByteBuffer> {
		DirectArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
			super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
		}

		@Override
		boolean isDirect() {
			return true;
		}

		private int offsetCacheLine(ByteBuffer memory) {
			return HAS_UNSAFE ? (int)(PlatformDependent.directBufferAddress(memory) & (long)this.directMemoryCacheAlignmentMask) : 0;
		}

		@Override
		protected PoolChunk<ByteBuffer> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
			if (this.directMemoryCacheAlignment == 0) {
				return new PoolChunk<>(this, allocateDirect(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
			} else {
				ByteBuffer memory = allocateDirect(chunkSize + this.directMemoryCacheAlignment);
				return new PoolChunk<>(this, memory, pageSize, maxOrder, pageShifts, chunkSize, this.offsetCacheLine(memory));
			}
		}

		@Override
		protected PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
			if (this.directMemoryCacheAlignment == 0) {
				return new PoolChunk<>(this, allocateDirect(capacity), capacity, 0);
			} else {
				ByteBuffer memory = allocateDirect(capacity + this.directMemoryCacheAlignment);
				return new PoolChunk<>(this, memory, capacity, this.offsetCacheLine(memory));
			}
		}

		private static ByteBuffer allocateDirect(int capacity) {
			return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(capacity) : ByteBuffer.allocateDirect(capacity);
		}

		@Override
		protected void destroyChunk(PoolChunk<ByteBuffer> chunk) {
			if (PlatformDependent.useDirectBufferNoCleaner()) {
				PlatformDependent.freeDirectNoCleaner(chunk.memory);
			} else {
				PlatformDependent.freeDirectBuffer(chunk.memory);
			}
		}

		@Override
		protected PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
			return (PooledByteBuf<ByteBuffer>)(HAS_UNSAFE ? PooledUnsafeDirectByteBuf.newInstance(maxCapacity) : PooledDirectByteBuf.newInstance(maxCapacity));
		}

		protected void memoryCopy(ByteBuffer src, int srcOffset, ByteBuffer dst, int dstOffset, int length) {
			if (length != 0) {
				if (HAS_UNSAFE) {
					PlatformDependent.copyMemory(
						PlatformDependent.directBufferAddress(src) + (long)srcOffset, PlatformDependent.directBufferAddress(dst) + (long)dstOffset, (long)length
					);
				} else {
					src = src.duplicate();
					dst = dst.duplicate();
					src.position(srcOffset).limit(srcOffset + length);
					dst.position(dstOffset);
					dst.put(src);
				}
			}
		}
	}

	static final class HeapArena extends PoolArena<byte[]> {
		HeapArena(PooledByteBufAllocator parent, int pageSize, int maxOrder, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
			super(parent, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
		}

		private static byte[] newByteArray(int size) {
			return PlatformDependent.allocateUninitializedArray(size);
		}

		@Override
		boolean isDirect() {
			return false;
		}

		@Override
		protected PoolChunk<byte[]> newChunk(int pageSize, int maxOrder, int pageShifts, int chunkSize) {
			return new PoolChunk<>(this, newByteArray(chunkSize), pageSize, maxOrder, pageShifts, chunkSize, 0);
		}

		@Override
		protected PoolChunk<byte[]> newUnpooledChunk(int capacity) {
			return new PoolChunk<>(this, newByteArray(capacity), capacity, 0);
		}

		@Override
		protected void destroyChunk(PoolChunk<byte[]> chunk) {
		}

		@Override
		protected PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
			return (PooledByteBuf<byte[]>)(HAS_UNSAFE ? PooledUnsafeHeapByteBuf.newUnsafeInstance(maxCapacity) : PooledHeapByteBuf.newInstance(maxCapacity));
		}

		protected void memoryCopy(byte[] src, int srcOffset, byte[] dst, int dstOffset, int length) {
			if (length != 0) {
				System.arraycopy(src, srcOffset, dst, dstOffset, length);
			}
		}
	}

	static enum SizeClass {
		Tiny,
		Small,
		Normal;
	}
}
