package io.netty.channel;

import io.netty.util.IntSupplier;

public interface SelectStrategy {
	int SELECT = -1;
	int CONTINUE = -2;

	int calculateStrategy(IntSupplier intSupplier, boolean boolean2) throws Exception;
}
