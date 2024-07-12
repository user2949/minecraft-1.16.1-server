package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import com.google.common.cache.LocalCache.LocalLoadingCache;
import com.google.common.cache.LocalCache.LocalManualCache;
import com.google.common.cache.LocalCache.Strength;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;

@GwtCompatible(
	emulated = true
)
public final class CacheBuilder<K, V> {
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
	private static final int DEFAULT_EXPIRATION_NANOS = 0;
	private static final int DEFAULT_REFRESH_NANOS = 0;
	static final Supplier<? extends StatsCounter> NULL_STATS_COUNTER = Suppliers.ofInstance(new StatsCounter() {
		@Override
		public void recordHits(int count) {
		}

		@Override
		public void recordMisses(int count) {
		}

		@Override
		public void recordLoadSuccess(long loadTime) {
		}

		@Override
		public void recordLoadException(long loadTime) {
		}

		@Override
		public void recordEviction() {
		}

		@Override
		public CacheStats snapshot() {
			return CacheBuilder.EMPTY_STATS;
		}
	});
	static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
	static final Supplier<StatsCounter> CACHE_STATS_COUNTER = new Supplier<StatsCounter>() {
		public StatsCounter get() {
			return new SimpleStatsCounter();
		}
	};
	static final Ticker NULL_TICKER = new Ticker() {
		@Override
		public long read() {
			return 0L;
		}
	};
	private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
	static final int UNSET_INT = -1;
	boolean strictParsing = true;
	int initialCapacity = -1;
	int concurrencyLevel = -1;
	long maximumSize = -1L;
	long maximumWeight = -1L;
	Weigher<? super K, ? super V> weigher;
	Strength keyStrength;
	Strength valueStrength;
	long expireAfterWriteNanos = -1L;
	long expireAfterAccessNanos = -1L;
	long refreshNanos = -1L;
	Equivalence<Object> keyEquivalence;
	Equivalence<Object> valueEquivalence;
	RemovalListener<? super K, ? super V> removalListener;
	Ticker ticker;
	Supplier<? extends StatsCounter> statsCounterSupplier = NULL_STATS_COUNTER;

	CacheBuilder() {
	}

	public static CacheBuilder<Object, Object> newBuilder() {
		return new CacheBuilder<>();
	}

	@GwtIncompatible
	public static CacheBuilder<Object, Object> from(CacheBuilderSpec spec) {
		return spec.toCacheBuilder().lenientParsing();
	}

	@GwtIncompatible
	public static CacheBuilder<Object, Object> from(String spec) {
		return from(CacheBuilderSpec.parse(spec));
	}

	@GwtIncompatible
	CacheBuilder<K, V> lenientParsing() {
		this.strictParsing = false;
		return this;
	}

	@GwtIncompatible
	CacheBuilder<K, V> keyEquivalence(Equivalence<Object> equivalence) {
		Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
		this.keyEquivalence = Preconditions.checkNotNull(equivalence);
		return this;
	}

	Equivalence<Object> getKeyEquivalence() {
		return MoreObjects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
	}

	@GwtIncompatible
	CacheBuilder<K, V> valueEquivalence(Equivalence<Object> equivalence) {
		Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
		this.valueEquivalence = Preconditions.checkNotNull(equivalence);
		return this;
	}

	Equivalence<Object> getValueEquivalence() {
		return MoreObjects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
	}

	public CacheBuilder<K, V> initialCapacity(int initialCapacity) {
		Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
		Preconditions.checkArgument(initialCapacity >= 0);
		this.initialCapacity = initialCapacity;
		return this;
	}

	int getInitialCapacity() {
		return this.initialCapacity == -1 ? 16 : this.initialCapacity;
	}

	public CacheBuilder<K, V> concurrencyLevel(int concurrencyLevel) {
		Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
		Preconditions.checkArgument(concurrencyLevel > 0);
		this.concurrencyLevel = concurrencyLevel;
		return this;
	}

	int getConcurrencyLevel() {
		return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
	}

	public CacheBuilder<K, V> maximumSize(long size) {
		Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
		Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
		Preconditions.checkState(this.weigher == null, "maximum size can not be combined with weigher");
		Preconditions.checkArgument(size >= 0L, "maximum size must not be negative");
		this.maximumSize = size;
		return this;
	}

	@GwtIncompatible
	public CacheBuilder<K, V> maximumWeight(long weight) {
		Preconditions.checkState(this.maximumWeight == -1L, "maximum weight was already set to %s", this.maximumWeight);
		Preconditions.checkState(this.maximumSize == -1L, "maximum size was already set to %s", this.maximumSize);
		this.maximumWeight = weight;
		Preconditions.checkArgument(weight >= 0L, "maximum weight must not be negative");
		return this;
	}

	@GwtIncompatible
	public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
		Preconditions.checkState(this.weigher == null);
		if (this.strictParsing) {
			Preconditions.checkState(this.maximumSize == -1L, "weigher can not be combined with maximum size", this.maximumSize);
		}

		this.weigher = Preconditions.checkNotNull(weigher);
		return this;
	}

	long getMaximumWeight() {
		if (this.expireAfterWriteNanos != 0L && this.expireAfterAccessNanos != 0L) {
			return this.weigher == null ? this.maximumSize : this.maximumWeight;
		} else {
			return 0L;
		}
	}

	<K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher() {
		return MoreObjects.firstNonNull(this.weigher, CacheBuilder.OneWeigher.INSTANCE);
	}

	@GwtIncompatible
	public CacheBuilder<K, V> weakKeys() {
		return this.setKeyStrength(Strength.WEAK);
	}

	CacheBuilder<K, V> setKeyStrength(Strength strength) {
		Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
		this.keyStrength = Preconditions.checkNotNull(strength);
		return this;
	}

	Strength getKeyStrength() {
		return MoreObjects.firstNonNull(this.keyStrength, Strength.STRONG);
	}

	@GwtIncompatible
	public CacheBuilder<K, V> weakValues() {
		return this.setValueStrength(Strength.WEAK);
	}

	@GwtIncompatible
	public CacheBuilder<K, V> softValues() {
		return this.setValueStrength(Strength.SOFT);
	}

	CacheBuilder<K, V> setValueStrength(Strength strength) {
		Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
		this.valueStrength = Preconditions.checkNotNull(strength);
		return this;
	}

	Strength getValueStrength() {
		return MoreObjects.firstNonNull(this.valueStrength, Strength.STRONG);
	}

	public CacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
		Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
		Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
		this.expireAfterWriteNanos = unit.toNanos(duration);
		return this;
	}

	long getExpireAfterWriteNanos() {
		return this.expireAfterWriteNanos == -1L ? 0L : this.expireAfterWriteNanos;
	}

	public CacheBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
		Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
		Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
		this.expireAfterAccessNanos = unit.toNanos(duration);
		return this;
	}

	long getExpireAfterAccessNanos() {
		return this.expireAfterAccessNanos == -1L ? 0L : this.expireAfterAccessNanos;
	}

	@GwtIncompatible
	public CacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
		Preconditions.checkNotNull(unit);
		Preconditions.checkState(this.refreshNanos == -1L, "refresh was already set to %s ns", this.refreshNanos);
		Preconditions.checkArgument(duration > 0L, "duration must be positive: %s %s", duration, unit);
		this.refreshNanos = unit.toNanos(duration);
		return this;
	}

	long getRefreshNanos() {
		return this.refreshNanos == -1L ? 0L : this.refreshNanos;
	}

	public CacheBuilder<K, V> ticker(Ticker ticker) {
		Preconditions.checkState(this.ticker == null);
		this.ticker = Preconditions.checkNotNull(ticker);
		return this;
	}

	Ticker getTicker(boolean recordsTime) {
		if (this.ticker != null) {
			return this.ticker;
		} else {
			return recordsTime ? Ticker.systemTicker() : NULL_TICKER;
		}
	}

	@CheckReturnValue
	public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> listener) {
		Preconditions.checkState(this.removalListener == null);
		this.removalListener = Preconditions.checkNotNull(listener);
		return this;
	}

	<K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
		return MoreObjects.firstNonNull(this.removalListener, CacheBuilder.NullListener.INSTANCE);
	}

	public CacheBuilder<K, V> recordStats() {
		this.statsCounterSupplier = CACHE_STATS_COUNTER;
		return this;
	}

	boolean isRecordingStats() {
		return this.statsCounterSupplier == CACHE_STATS_COUNTER;
	}

	Supplier<? extends StatsCounter> getStatsCounterSupplier() {
		return this.statsCounterSupplier;
	}

	public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
		this.checkWeightWithWeigher();
		return new LocalLoadingCache<>(this, loader);
	}

	public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
		this.checkWeightWithWeigher();
		this.checkNonLoadingCache();
		return new LocalManualCache<>(this);
	}

	private void checkNonLoadingCache() {
		Preconditions.checkState(this.refreshNanos == -1L, "refreshAfterWrite requires a LoadingCache");
	}

	private void checkWeightWithWeigher() {
		if (this.weigher == null) {
			Preconditions.checkState(this.maximumWeight == -1L, "maximumWeight requires weigher");
		} else if (this.strictParsing) {
			Preconditions.checkState(this.maximumWeight != -1L, "weigher requires maximumWeight");
		} else if (this.maximumWeight == -1L) {
			logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
		}
	}

	public String toString() {
		ToStringHelper s = MoreObjects.toStringHelper(this);
		if (this.initialCapacity != -1) {
			s.add("initialCapacity", this.initialCapacity);
		}

		if (this.concurrencyLevel != -1) {
			s.add("concurrencyLevel", this.concurrencyLevel);
		}

		if (this.maximumSize != -1L) {
			s.add("maximumSize", this.maximumSize);
		}

		if (this.maximumWeight != -1L) {
			s.add("maximumWeight", this.maximumWeight);
		}

		if (this.expireAfterWriteNanos != -1L) {
			s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
		}

		if (this.expireAfterAccessNanos != -1L) {
			s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
		}

		if (this.keyStrength != null) {
			s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
		}

		if (this.valueStrength != null) {
			s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
		}

		if (this.keyEquivalence != null) {
			s.addValue("keyEquivalence");
		}

		if (this.valueEquivalence != null) {
			s.addValue("valueEquivalence");
		}

		if (this.removalListener != null) {
			s.addValue("removalListener");
		}

		return s.toString();
	}

	static enum NullListener implements RemovalListener<Object, Object> {
		INSTANCE;

		@Override
		public void onRemoval(RemovalNotification<Object, Object> notification) {
		}
	}

	static enum OneWeigher implements Weigher<Object, Object> {
		INSTANCE;

		@Override
		public int weigh(Object key, Object value) {
			return 1;
		}
	}
}
