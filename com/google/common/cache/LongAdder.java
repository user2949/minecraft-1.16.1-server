package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.cache.Striped64.Cell;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@GwtCompatible(
	emulated = true
)
final class LongAdder extends Striped64 implements Serializable, LongAddable {
	private static final long serialVersionUID = 7249069246863182397L;

	@Override
	final long fn(long v, long x) {
		return v + x;
	}

	public LongAdder() {
	}

	@Override
	public void add(long x) {
		Cell[] as = this.cells;
		if (this.cells == null) {
			long b = this.base;
			if (this.casBase(this.base, b + x)) {
				return;
			}
		}

		boolean uncontended = true;
		int[] hc;
		Cell a;
		int n;
		if ((hc = (int[])threadHashCode.get()) != null && as != null && (n = as.length) >= 1 && (a = as[n - 1 & hc[0]]) != null) {
			long v = a.value;
			if (uncontended = a.cas(a.value, v + x)) {
				return;
			}
		}

		this.retryUpdate(x, hc, uncontended);
	}

	@Override
	public void increment() {
		this.add(1L);
	}

	public void decrement() {
		this.add(-1L);
	}

	@Override
	public long sum() {
		long sum = this.base;
		Cell[] as = this.cells;
		if (as != null) {
			for (Cell a : as) {
				if (a != null) {
					sum += a.value;
				}
			}
		}

		return sum;
	}

	public void reset() {
		this.internalReset(0L);
	}

	public long sumThenReset() {
		long sum = this.base;
		Cell[] as = this.cells;
		this.base = 0L;
		if (as != null) {
			for (Cell a : as) {
				if (a != null) {
					sum += a.value;
					a.value = 0L;
				}
			}
		}

		return sum;
	}

	public String toString() {
		return Long.toString(this.sum());
	}

	public long longValue() {
		return this.sum();
	}

	public int intValue() {
		return (int)this.sum();
	}

	public float floatValue() {
		return (float)this.sum();
	}

	public double doubleValue() {
		return (double)this.sum();
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeLong(this.sum());
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		this.busy = 0;
		this.cells = null;
		this.base = s.readLong();
	}
}
