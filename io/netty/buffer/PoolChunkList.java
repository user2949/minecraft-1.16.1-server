package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class PoolChunkList<T> implements PoolChunkListMetric {
	private static final Iterator<PoolChunkMetric> EMPTY_METRICS = Collections.emptyList().iterator();
	private final PoolArena<T> arena;
	private final PoolChunkList<T> nextList;
	private final int minUsage;
	private final int maxUsage;
	private final int maxCapacity;
	private PoolChunk<T> head;
	private PoolChunkList<T> prevList;

	PoolChunkList(PoolArena<T> arena, PoolChunkList<T> nextList, int minUsage, int maxUsage, int chunkSize) {
		assert minUsage <= maxUsage;

		this.arena = arena;
		this.nextList = nextList;
		this.minUsage = minUsage;
		this.maxUsage = maxUsage;
		this.maxCapacity = calculateMaxCapacity(minUsage, chunkSize);
	}

	private static int calculateMaxCapacity(int minUsage, int chunkSize) {
		minUsage = minUsage0(minUsage);
		return minUsage == 100 ? 0 : (int)((long)chunkSize * (100L - (long)minUsage) / 100L);
	}

	void prevList(PoolChunkList<T> prevList) {
		assert this.prevList == null;

		this.prevList = prevList;
	}

	boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
		if (this.head != null && normCapacity <= this.maxCapacity) {
			PoolChunk<T> cur = this.head;

			do {
				long handle = cur.allocate(normCapacity);
				if (handle >= 0L) {
					cur.initBuf(buf, handle, reqCapacity);
					if (cur.usage() >= this.maxUsage) {
						this.remove(cur);
						this.nextList.add(cur);
					}

					return true;
				}

				cur = cur.next;
			} while (cur != null);

			return false;
		} else {
			return false;
		}
	}

	boolean free(PoolChunk<T> chunk, long handle) {
		chunk.free(handle);
		if (chunk.usage() < this.minUsage) {
			this.remove(chunk);
			return this.move0(chunk);
		} else {
			return true;
		}
	}

	private boolean move(PoolChunk<T> chunk) {
		assert chunk.usage() < this.maxUsage;

		if (chunk.usage() < this.minUsage) {
			return this.move0(chunk);
		} else {
			this.add0(chunk);
			return true;
		}
	}

	private boolean move0(PoolChunk<T> chunk) {
		if (this.prevList == null) {
			assert chunk.usage() == 0;

			return false;
		} else {
			return this.prevList.move(chunk);
		}
	}

	void add(PoolChunk<T> chunk) {
		if (chunk.usage() >= this.maxUsage) {
			this.nextList.add(chunk);
		} else {
			this.add0(chunk);
		}
	}

	void add0(PoolChunk<T> chunk) {
		chunk.parent = this;
		if (this.head == null) {
			this.head = chunk;
			chunk.prev = null;
			chunk.next = null;
		} else {
			chunk.prev = null;
			chunk.next = this.head;
			this.head.prev = chunk;
			this.head = chunk;
		}
	}

	private void remove(PoolChunk<T> cur) {
		if (cur == this.head) {
			this.head = cur.next;
			if (this.head != null) {
				this.head.prev = null;
			}
		} else {
			PoolChunk<T> next = cur.next;
			cur.prev.next = next;
			if (next != null) {
				next.prev = cur.prev;
			}
		}
	}

	@Override
	public int minUsage() {
		return minUsage0(this.minUsage);
	}

	@Override
	public int maxUsage() {
		return Math.min(this.maxUsage, 100);
	}

	private static int minUsage0(int value) {
		return Math.max(1, value);
	}

	public Iterator<PoolChunkMetric> iterator() {
		synchronized (this.arena) {
			if (this.head == null) {
				return EMPTY_METRICS;
			} else {
				List<PoolChunkMetric> metrics = new ArrayList();
				PoolChunk<T> cur = this.head;

				do {
					metrics.add(cur);
					cur = cur.next;
				} while (cur != null);

				return metrics.iterator();
			}
		}
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		synchronized (this.arena) {
			if (this.head == null) {
				return "none";
			} else {
				PoolChunk<T> cur = this.head;

				while (true) {
					buf.append(cur);
					cur = cur.next;
					if (cur == null) {
						return buf.toString();
					}

					buf.append(StringUtil.NEWLINE);
				}
			}
		}
	}

	void destroy(PoolArena<T> arena) {
		for (PoolChunk<T> chunk = this.head; chunk != null; chunk = chunk.next) {
			arena.destroyChunk(chunk);
		}

		this.head = null;
	}
}
