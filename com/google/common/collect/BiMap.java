package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface BiMap<K, V> extends Map<K, V> {
	@Nullable
	@CanIgnoreReturnValue
	V put(@Nullable K object1, @Nullable V object2);

	@Nullable
	@CanIgnoreReturnValue
	V forcePut(@Nullable K object1, @Nullable V object2);

	void putAll(Map<? extends K, ? extends V> map);

	Set<V> values();

	BiMap<V, K> inverse();
}
