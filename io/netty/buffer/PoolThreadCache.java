package io.netty.buffer;

import io.netty.buffer.PoolArena.SizeClass;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.Queue;

final class PoolThreadCache {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
	final PoolArena<byte[]> heapArena;
	final PoolArena<ByteBuffer> directArena;
	private final PoolThreadCache.MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;
	private final PoolThreadCache.MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
	private final PoolThreadCache.MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
	private final PoolThreadCache.MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
	private final PoolThreadCache.MemoryRegionCache<byte[]>[] normalHeapCaches;
	private final PoolThreadCache.MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
	private final int numShiftsNormalDirect;
	private final int numShiftsNormalHeap;
	private final int freeSweepAllocationThreshold;
	private int allocations;

	PoolThreadCache(
		PoolArena<byte[]> heapArena,
		PoolArena<ByteBuffer> directArena,
		int tinyCacheSize,
		int smallCacheSize,
		int normalCacheSize,
		int maxCachedBufferCapacity,
		int freeSweepAllocationThreshold
	) {
		if (maxCachedBufferCapacity < 0) {
			throw new IllegalArgumentException("maxCachedBufferCapacity: " + maxCachedBufferCapacity + " (expected: >= 0)");
		} else {
			this.freeSweepAllocationThreshold = freeSweepAllocationThreshold;
			this.heapArena = heapArena;
			this.directArena = directArena;
			if (directArena != null) {
				this.tinySubPageDirectCaches = createSubPageCaches(tinyCacheSize, 32, SizeClass.Tiny);
				this.smallSubPageDirectCaches = createSubPageCaches(smallCacheSize, directArena.numSmallSubpagePools, SizeClass.Small);
				this.numShiftsNormalDirect = log2(directArena.pageSize);
				this.normalDirectCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, directArena);
				directArena.numThreadCaches.getAndIncrement();
			} else {
				this.tinySubPageDirectCaches = null;
				this.smallSubPageDirectCaches = null;
				this.normalDirectCaches = null;
				this.numShiftsNormalDirect = -1;
			}

			if (heapArena != null) {
				this.tinySubPageHeapCaches = createSubPageCaches(tinyCacheSize, 32, SizeClass.Tiny);
				this.smallSubPageHeapCaches = createSubPageCaches(smallCacheSize, heapArena.numSmallSubpagePools, SizeClass.Small);
				this.numShiftsNormalHeap = log2(heapArena.pageSize);
				this.normalHeapCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, heapArena);
				heapArena.numThreadCaches.getAndIncrement();
			} else {
				this.tinySubPageHeapCaches = null;
				this.smallSubPageHeapCaches = null;
				this.normalHeapCaches = null;
				this.numShiftsNormalHeap = -1;
			}

			if ((
					this.tinySubPageDirectCaches != null
						|| this.smallSubPageDirectCaches != null
						|| this.normalDirectCaches != null
						|| this.tinySubPageHeapCaches != null
						|| this.smallSubPageHeapCaches != null
						|| this.normalHeapCaches != null
				)
				&& freeSweepAllocationThreshold < 1) {
				throw new IllegalArgumentException("freeSweepAllocationThreshold: " + freeSweepAllocationThreshold + " (expected: > 0)");
			}
		}
	}

	private static <T> PoolThreadCache.MemoryRegionCache<T>[] createSubPageCaches(int cacheSize, int numCaches, SizeClass sizeClass) {
		if (cacheSize > 0 && numCaches > 0) {
			PoolThreadCache.MemoryRegionCache<T>[] cache = new PoolThreadCache.MemoryRegionCache[numCaches];

			for (int i = 0; i < cache.length; i++) {
				cache[i] = new PoolThreadCache.SubPageMemoryRegionCache<>(cacheSize, sizeClass);
			}

			return cache;
		} else {
			return null;
		}
	}

	private static <T> PoolThreadCache.MemoryRegionCache<T>[] createNormalCaches(int cacheSize, int maxCachedBufferCapacity, PoolArena<T> area) {
		if (cacheSize > 0 && maxCachedBufferCapacity > 0) {
			int max = Math.min(area.chunkSize, maxCachedBufferCapacity);
			int arraySize = Math.max(1, log2(max / area.pageSize) + 1);
			PoolThreadCache.MemoryRegionCache<T>[] cache = new PoolThreadCache.MemoryRegionCache[arraySize];

			for (int i = 0; i < cache.length; i++) {
				cache[i] = new PoolThreadCache.NormalMemoryRegionCache<>(cacheSize);
			}

			return cache;
		} else {
			return null;
		}
	}

	private static int log2(int val) {
		int res;
		for (res = 0; val > 1; res++) {
			val >>= 1;
		}

		return res;
	}

	boolean allocateTiny(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
		return this.allocate(this.cacheForTiny(area, normCapacity), buf, reqCapacity);
	}

	boolean allocateSmall(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
		return this.allocate(this.cacheForSmall(area, normCapacity), buf, reqCapacity);
	}

	boolean allocateNormal(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int normCapacity) {
		return this.allocate(this.cacheForNormal(area, normCapacity), buf, reqCapacity);
	}

	private boolean allocate(PoolThreadCache.MemoryRegionCache<?> cache, PooledByteBuf buf, int reqCapacity) {
		if (cache == null) {
			return false;
		} else {
			boolean allocated = cache.allocate(buf, reqCapacity);
			if (++this.allocations >= this.freeSweepAllocationThreshold) {
				this.allocations = 0;
				this.trim();
			}

			return allocated;
		}
	}

	boolean add(PoolArena<?> area, PoolChunk chunk, long handle, int normCapacity, SizeClass sizeClass) {
		PoolThreadCache.MemoryRegionCache<?> cache = this.cache(area, normCapacity, sizeClass);
		return cache == null ? false : cache.add(chunk, handle);
	}

	private PoolThreadCache.MemoryRegionCache<?> cache(PoolArena<?> area, int normCapacity, SizeClass sizeClass) {
		switch (sizeClass) {
			case Normal:
				return this.cacheForNormal(area, normCapacity);
			case Small:
				return this.cacheForSmall(area, normCapacity);
			case Tiny:
				return this.cacheForTiny(area, normCapacity);
			default:
				throw new Error();
		}
	}

	void free() {
		int numFreed = free(this.tinySubPageDirectCaches)
			+ free(this.smallSubPageDirectCaches)
			+ free(this.normalDirectCaches)
			+ free(this.tinySubPageHeapCaches)
			+ free(this.smallSubPageHeapCaches)
			+ free(this.normalHeapCaches);
		if (numFreed > 0 && logger.isDebugEnabled()) {
			logger.debug("Freed {} thread-local buffer(s) from thread: {}", numFreed, Thread.currentThread().getName());
		}

		if (this.directArena != null) {
			this.directArena.numThreadCaches.getAndDecrement();
		}

		if (this.heapArena != null) {
			this.heapArena.numThreadCaches.getAndDecrement();
		}
	}

	private static int free(PoolThreadCache.MemoryRegionCache<?>[] caches) {
		if (caches == null) {
			return 0;
		} else {
			int numFreed = 0;

			for (PoolThreadCache.MemoryRegionCache<?> c : caches) {
				numFreed += free(c);
			}

			return numFreed;
		}
	}

	private static int free(PoolThreadCache.MemoryRegionCache<?> cache) {
		return cache == null ? 0 : cache.free();
	}

	void trim() {
		trim(this.tinySubPageDirectCaches);
		trim(this.smallSubPageDirectCaches);
		trim(this.normalDirectCaches);
		trim(this.tinySubPageHeapCaches);
		trim(this.smallSubPageHeapCaches);
		trim(this.normalHeapCaches);
	}

	private static void trim(PoolThreadCache.MemoryRegionCache<?>[] caches) {
		if (caches != null) {
			for (PoolThreadCache.MemoryRegionCache<?> c : caches) {
				trim(c);
			}
		}
	}

	private static void trim(PoolThreadCache.MemoryRegionCache<?> cache) {
		if (cache != null) {
			cache.trim();
		}
	}

	private PoolThreadCache.MemoryRegionCache<?> cacheForTiny(PoolArena<?> area, int normCapacity) {
		int idx = PoolArena.tinyIdx(normCapacity);
		return area.isDirect() ? cache(this.tinySubPageDirectCaches, idx) : cache(this.tinySubPageHeapCaches, idx);
	}

	private PoolThreadCache.MemoryRegionCache<?> cacheForSmall(PoolArena<?> area, int normCapacity) {
		int idx = PoolArena.smallIdx(normCapacity);
		return area.isDirect() ? cache(this.smallSubPageDirectCaches, idx) : cache(this.smallSubPageHeapCaches, idx);
	}

	private PoolThreadCache.MemoryRegionCache<?> cacheForNormal(PoolArena<?> area, int normCapacity) {
		if (area.isDirect()) {
			int idx = log2(normCapacity >> this.numShiftsNormalDirect);
			return cache(this.normalDirectCaches, idx);
		} else {
			int idx = log2(normCapacity >> this.numShiftsNormalHeap);
			return cache(this.normalHeapCaches, idx);
		}
	}

	private static <T> PoolThreadCache.MemoryRegionCache<T> cache(PoolThreadCache.MemoryRegionCache<T>[] cache, int idx) {
		return cache != null && idx <= cache.length - 1 ? cache[idx] : null;
	}

	private abstract static class MemoryRegionCache<T> {
		private final int size;
		private final Queue<PoolThreadCache.MemoryRegionCache.Entry<T>> queue;
		private final SizeClass sizeClass;
		private int allocations;
		private static final Recycler<PoolThreadCache.MemoryRegionCache.Entry> RECYCLER = new Recycler<PoolThreadCache.MemoryRegionCache.Entry>() {
			protected PoolThreadCache.MemoryRegionCache.Entry newObject(Handle<PoolThreadCache.MemoryRegionCache.Entry> handle) {
				return new PoolThreadCache.MemoryRegionCache.Entry(handle);
			}
		};

		MemoryRegionCache(int size, SizeClass sizeClass) {
			this.size = MathUtil.safeFindNextPositivePowerOfTwo(size);
			this.queue = PlatformDependent.newFixedMpscQueue(this.size);
			this.sizeClass = sizeClass;
		}

		protected abstract void initBuf(PoolChunk<T> poolChunk, long long2, PooledByteBuf<T> pooledByteBuf, int integer);

		public final boolean add(PoolChunk<T> chunk, long handle) {
			PoolThreadCache.MemoryRegionCache.Entry<T> entry = newEntry(chunk, handle);
			boolean queued = this.queue.offer(entry);
			if (!queued) {
				entry.recycle();
			}

			return queued;
		}

		public final boolean allocate(PooledByteBuf<T> buf, int reqCapacity) {
			PoolThreadCache.MemoryRegionCache.Entry<T> entry = (PoolThreadCache.MemoryRegionCache.Entry<T>)this.queue.poll();
			if (entry == null) {
				return false;
			} else {
				this.initBuf(entry.chunk, entry.handle, buf, reqCapacity);
				entry.recycle();
				this.allocations++;
				return true;
			}
		}

		public final int free() {
			return this.free(Integer.MAX_VALUE);
		}

		private int free(int max) {
			int numFreed;
			for (numFreed = 0; numFreed < max; numFreed++) {
				PoolThreadCache.MemoryRegionCache.Entry<T> entry = (PoolThreadCache.MemoryRegionCache.Entry<T>)this.queue.poll();
				if (entry == null) {
					return numFreed;
				}

				this.freeEntry(entry);
			}

			return numFreed;
		}

		public final void trim() {
			int free = this.size - this.allocations;
			this.allocations = 0;
			if (free > 0) {
				this.free(free);
			}
		}

		private void freeEntry(PoolThreadCache.MemoryRegionCache.Entry entry) {
			PoolChunk chunk = entry.chunk;
			long handle = entry.handle;
			entry.recycle();
			chunk.arena.freeChunk(chunk, handle, this.sizeClass);
		}

		private static PoolThreadCache.MemoryRegionCache.Entry newEntry(PoolChunk<?> chunk, long handle) {
			PoolThreadCache.MemoryRegionCache.Entry entry = RECYCLER.get();
			entry.chunk = (PoolChunk<T>)chunk;
			entry.handle = handle;
			return entry;
		}

		static final class Entry<T> {
			final Handle<PoolThreadCache.MemoryRegionCache.Entry<?>> recyclerHandle;
			PoolChunk<T> chunk;
			long handle = -1L;

			Entry(Handle<PoolThreadCache.MemoryRegionCache.Entry<?>> recyclerHandle) {
				this.recyclerHandle = recyclerHandle;
			}

			void recycle() {
				this.chunk = null;
				this.handle = -1L;
				this.recyclerHandle.recycle(this);
			}
		}
	}

	private static final class NormalMemoryRegionCache<T> extends PoolThreadCache.MemoryRegionCache<T> {
		NormalMemoryRegionCache(int size) {
			super(size, SizeClass.Normal);
		}

		@Override
		protected void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
			chunk.initBuf(buf, handle, reqCapacity);
		}
	}

	private static final class SubPageMemoryRegionCache<T> extends PoolThreadCache.MemoryRegionCache<T> {
		SubPageMemoryRegionCache(int size, SizeClass sizeClass) {
			super(size, sizeClass);
		}

		@Override
		protected void initBuf(PoolChunk<T> chunk, long handle, PooledByteBuf<T> buf, int reqCapacity) {
			chunk.initBufWithSubpage(buf, handle, reqCapacity);
		}
	}
}
