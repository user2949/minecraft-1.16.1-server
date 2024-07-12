package com.mojang.datafixers.functions;

import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public abstract class PointFree<T> {
	private volatile boolean initialized;
	@Nullable
	private Function<DynamicOps<?>, T> value;

	public Function<DynamicOps<?>, T> evalCached() {
		if (!this.initialized) {
			synchronized (this) {
				if (!this.initialized) {
					this.value = this.eval();
					this.initialized = true;
				}
			}
		}

		return this.value;
	}

	public abstract Function<DynamicOps<?>, T> eval();

	Optional<? extends PointFree<T>> all(PointFreeRule rule, Type<T> type) {
		return Optional.of(this);
	}

	Optional<? extends PointFree<T>> one(PointFreeRule rule, Type<T> type) {
		return Optional.empty();
	}

	public final String toString() {
		return this.toString(0);
	}

	public static String indent(int level) {
		return StringUtils.repeat("  ", level);
	}

	public abstract String toString(int integer);
}
