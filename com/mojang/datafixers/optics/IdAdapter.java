package com.mojang.datafixers.optics;

class IdAdapter<S, T> implements Adapter<S, T, S, T> {
	@Override
	public S from(S s) {
		return s;
	}

	@Override
	public T to(T b) {
		return b;
	}

	public boolean equals(Object obj) {
		return obj instanceof IdAdapter;
	}

	public String toString() {
		return "id";
	}
}
