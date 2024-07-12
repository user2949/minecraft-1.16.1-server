package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ComparisonChain {
	private static final ComparisonChain ACTIVE = new ComparisonChain() {
		@Override
		public ComparisonChain compare(Comparable left, Comparable right) {
			return this.classify(left.compareTo(right));
		}

		@Override
		public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, Comparator<T> comparator) {
			return this.classify(comparator.compare(left, right));
		}

		@Override
		public ComparisonChain compare(int left, int right) {
			return this.classify(Ints.compare(left, right));
		}

		@Override
		public ComparisonChain compare(long left, long right) {
			return this.classify(Longs.compare(left, right));
		}

		@Override
		public ComparisonChain compare(float left, float right) {
			return this.classify(Float.compare(left, right));
		}

		@Override
		public ComparisonChain compare(double left, double right) {
			return this.classify(Double.compare(left, right));
		}

		@Override
		public ComparisonChain compareTrueFirst(boolean left, boolean right) {
			return this.classify(Booleans.compare(right, left));
		}

		@Override
		public ComparisonChain compareFalseFirst(boolean left, boolean right) {
			return this.classify(Booleans.compare(left, right));
		}

		ComparisonChain classify(int result) {
			return result < 0 ? ComparisonChain.LESS : (result > 0 ? ComparisonChain.GREATER : ComparisonChain.ACTIVE);
		}

		@Override
		public int result() {
			return 0;
		}
	};
	private static final ComparisonChain LESS = new ComparisonChain.InactiveComparisonChain(-1);
	private static final ComparisonChain GREATER = new ComparisonChain.InactiveComparisonChain(1);

	private ComparisonChain() {
	}

	public static ComparisonChain start() {
		return ACTIVE;
	}

	public abstract ComparisonChain compare(Comparable<?> comparable1, Comparable<?> comparable2);

	public abstract <T> ComparisonChain compare(@Nullable T object1, @Nullable T object2, Comparator<T> comparator);

	public abstract ComparisonChain compare(int integer1, int integer2);

	public abstract ComparisonChain compare(long long1, long long2);

	public abstract ComparisonChain compare(float float1, float float2);

	public abstract ComparisonChain compare(double double1, double double2);

	@Deprecated
	public final ComparisonChain compare(Boolean left, Boolean right) {
		return this.compareFalseFirst(left, right);
	}

	public abstract ComparisonChain compareTrueFirst(boolean boolean1, boolean boolean2);

	public abstract ComparisonChain compareFalseFirst(boolean boolean1, boolean boolean2);

	public abstract int result();

	private static final class InactiveComparisonChain extends ComparisonChain {
		final int result;

		InactiveComparisonChain(int result) {
			this.result = result;
		}

		@Override
		public ComparisonChain compare(@Nullable Comparable left, @Nullable Comparable right) {
			return this;
		}

		@Override
		public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, @Nullable Comparator<T> comparator) {
			return this;
		}

		@Override
		public ComparisonChain compare(int left, int right) {
			return this;
		}

		@Override
		public ComparisonChain compare(long left, long right) {
			return this;
		}

		@Override
		public ComparisonChain compare(float left, float right) {
			return this;
		}

		@Override
		public ComparisonChain compare(double left, double right) {
			return this;
		}

		@Override
		public ComparisonChain compareTrueFirst(boolean left, boolean right) {
			return this;
		}

		@Override
		public ComparisonChain compareFalseFirst(boolean left, boolean right) {
			return this;
		}

		@Override
		public int result() {
			return this.result;
		}
	}
}
