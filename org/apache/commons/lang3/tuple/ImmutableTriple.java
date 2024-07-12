package org.apache.commons.lang3.tuple;

public final class ImmutableTriple<L, M, R> extends Triple<L, M, R> {
	private static final long serialVersionUID = 1L;
	public final L left;
	public final M middle;
	public final R right;

	public static <L, M, R> ImmutableTriple<L, M, R> of(L left, M middle, R right) {
		return new ImmutableTriple<>(left, middle, right);
	}

	public ImmutableTriple(L left, M middle, R right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	@Override
	public L getLeft() {
		return this.left;
	}

	@Override
	public M getMiddle() {
		return this.middle;
	}

	@Override
	public R getRight() {
		return this.right;
	}
}
