package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.math.LongMath;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Spliterator.OfDouble;
import java.util.Spliterator.OfInt;
import java.util.Spliterator.OfLong;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class Streams {
	public static <T> Stream<T> stream(Iterable<T> iterable) {
		return iterable instanceof Collection ? ((Collection)iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
	}

	@Deprecated
	public static <T> Stream<T> stream(Collection<T> collection) {
		return collection.stream();
	}

	public static <T> Stream<T> stream(Iterator<T> iterator) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
	}

	public static <T> Stream<T> stream(Optional<T> optional) {
		return optional.isPresent() ? Stream.of(optional.get()) : Stream.of();
	}

	public static <T> Stream<T> stream(java.util.Optional<T> optional) {
		return optional.isPresent() ? Stream.of(optional.get()) : Stream.of();
	}

	@SafeVarargs
	public static <T> Stream<T> concat(Stream<? extends T>... streams) {
		boolean isParallel = false;
		int characteristics = 336;
		long estimatedSize = 0L;
		Builder<Spliterator<? extends T>> splitrsBuilder = new Builder<>(streams.length);

		for (Stream<? extends T> stream : streams) {
			isParallel |= stream.isParallel();
			Spliterator<? extends T> splitr = stream.spliterator();
			splitrsBuilder.add(splitr);
			characteristics &= splitr.characteristics();
			estimatedSize = LongMath.saturatedAdd(estimatedSize, splitr.estimateSize());
		}

		return StreamSupport.stream(CollectSpliterators.flatMap(splitrsBuilder.build().spliterator(), splitrx -> splitrx, characteristics, estimatedSize), isParallel);
	}

	public static IntStream concat(IntStream... streams) {
		return Stream.of(streams).flatMapToInt(stream -> stream);
	}

	public static LongStream concat(LongStream... streams) {
		return Stream.of(streams).flatMapToLong(stream -> stream);
	}

	public static DoubleStream concat(DoubleStream... streams) {
		return Stream.of(streams).flatMapToDouble(stream -> stream);
	}

	public static IntStream stream(OptionalInt optional) {
		return optional.isPresent() ? IntStream.of(optional.getAsInt()) : IntStream.empty();
	}

	public static LongStream stream(OptionalLong optional) {
		return optional.isPresent() ? LongStream.of(optional.getAsLong()) : LongStream.empty();
	}

	public static DoubleStream stream(OptionalDouble optional) {
		return optional.isPresent() ? DoubleStream.of(optional.getAsDouble()) : DoubleStream.empty();
	}

	public static <T> java.util.Optional<T> findLast(Stream<T> stream) {
		class 1OptionalState<T> {
			boolean set = false;
			T value = (T)null;

			void set(@Nullable T value) {
				this.set = true;
				this.value = value;
			}

			T get() {
				Preconditions.checkState(this.set);
				return this.value;
			}
		}

		1OptionalState<T> state = new 1OptionalState<>();
		Deque<Spliterator<T>> splits = new ArrayDeque();
		splits.addLast(stream.spliterator());

		while (!splits.isEmpty()) {
			Spliterator<T> spliterator = (Spliterator<T>)splits.removeLast();
			if (spliterator.getExactSizeIfKnown() != 0L) {
				if (spliterator.hasCharacteristics(16384)) {
					while (true) {
						Spliterator<T> prefix = spliterator.trySplit();
						if (prefix == null || prefix.getExactSizeIfKnown() == 0L) {
							break;
						}

						if (spliterator.getExactSizeIfKnown() == 0L) {
							spliterator = prefix;
							break;
						}
					}

					spliterator.forEachRemaining(state::set);
					return java.util.Optional.of(state.get());
				}

				Spliterator<T> prefixx = spliterator.trySplit();
				if (prefixx != null && prefixx.getExactSizeIfKnown() != 0L) {
					splits.addLast(prefixx);
					splits.addLast(spliterator);
				} else {
					spliterator.forEachRemaining(state::set);
					if (state.set) {
						return java.util.Optional.of(state.get());
					}
				}
			}
		}

		return java.util.Optional.empty();
	}

	public static OptionalInt findLast(IntStream stream) {
		java.util.Optional<Integer> boxedLast = findLast(stream.boxed());
		return boxedLast.isPresent() ? OptionalInt.of((Integer)boxedLast.get()) : OptionalInt.empty();
	}

	public static OptionalLong findLast(LongStream stream) {
		java.util.Optional<Long> boxedLast = findLast(stream.boxed());
		return boxedLast.isPresent() ? OptionalLong.of((Long)boxedLast.get()) : OptionalLong.empty();
	}

	public static OptionalDouble findLast(DoubleStream stream) {
		java.util.Optional<Double> boxedLast = findLast(stream.boxed());
		return boxedLast.isPresent() ? OptionalDouble.of((Double)boxedLast.get()) : OptionalDouble.empty();
	}

	public static <A, B, R> Stream<R> zip(Stream<A> streamA, Stream<B> streamB, BiFunction<? super A, ? super B, R> function) {
		Preconditions.checkNotNull(streamA);
		Preconditions.checkNotNull(streamB);
		Preconditions.checkNotNull(function);
		boolean isParallel = streamA.isParallel() || streamB.isParallel();
		Spliterator<A> splitrA = streamA.spliterator();
		Spliterator<B> splitrB = streamB.spliterator();
		int characteristics = splitrA.characteristics() & splitrB.characteristics() & 80;
		final Iterator<A> itrA = Spliterators.iterator(splitrA);
		final Iterator<B> itrB = Spliterators.iterator(splitrB);
		return StreamSupport.stream(new AbstractSpliterator<R>(Math.min(splitrA.estimateSize(), splitrB.estimateSize()), characteristics) {
			public boolean tryAdvance(Consumer<? super R> action) {
				if (itrA.hasNext() && itrB.hasNext()) {
					action.accept(function.apply(itrA.next(), itrB.next()));
					return true;
				} else {
					return false;
				}
			}
		}, isParallel);
	}

	public static <T, R> Stream<R> mapWithIndex(Stream<T> stream, Streams.FunctionWithIndex<? super T, ? extends R> function) {
		Preconditions.checkNotNull(stream);
		Preconditions.checkNotNull(function);
		boolean isParallel = stream.isParallel();
		Spliterator<T> fromSpliterator = stream.spliterator();
		if (!fromSpliterator.hasCharacteristics(16384)) {
			final Iterator<T> fromIterator = Spliterators.iterator(fromSpliterator);
			return StreamSupport.stream(new AbstractSpliterator<R>(fromSpliterator.estimateSize(), fromSpliterator.characteristics() & 80) {
				long index = 0L;

				public boolean tryAdvance(Consumer<? super R> action) {
					if (fromIterator.hasNext()) {
						action.accept(function.apply((T)fromIterator.next(), this.index++));
						return true;
					} else {
						return false;
					}
				}
			}, isParallel);
		} else {
			class 1Splitr extends Streams.MapWithIndexSpliterator<Spliterator<T>, R, 1Splitr> implements Consumer<T> {
				T holder;

				_Splitr/* $VF was: 1Splitr*/(Spliterator<T> splitr, long index) {
					super(splitr, index);
				}

				public void accept(@Nullable T t) {
					this.holder = t;
				}

				public boolean tryAdvance(Consumer<? super R> action) {
					if (this.fromSpliterator.tryAdvance(this)) {
						boolean var2;
						try {
							action.accept(function.apply(this.holder, this.index++));
							var2 = true;
						} finally {
							this.holder = null;
						}

						return var2;
					} else {
						return false;
					}
				}

				1Splitr createSplit(Spliterator<T> from, long i) {
					return new 1Splitr(from, i);
				}
			}

			return StreamSupport.stream(new 1Splitr(fromSpliterator, 0L), isParallel);
		}
	}

	public static <R> Stream<R> mapWithIndex(IntStream stream, Streams.IntFunctionWithIndex<R> function) {
		Preconditions.checkNotNull(stream);
		Preconditions.checkNotNull(function);
		boolean isParallel = stream.isParallel();
		OfInt fromSpliterator = stream.spliterator();
		if (!fromSpliterator.hasCharacteristics(16384)) {
			final java.util.PrimitiveIterator.OfInt fromIterator = Spliterators.iterator(fromSpliterator);
			return StreamSupport.stream(new AbstractSpliterator<R>(fromSpliterator.estimateSize(), fromSpliterator.characteristics() & 80) {
				long index = 0L;

				public boolean tryAdvance(Consumer<? super R> action) {
					if (fromIterator.hasNext()) {
						action.accept(function.apply(fromIterator.nextInt(), this.index++));
						return true;
					} else {
						return false;
					}
				}
			}, isParallel);
		} else {
			class 2Splitr extends Streams.MapWithIndexSpliterator<OfInt, R, 2Splitr> implements IntConsumer, Spliterator<R> {
				int holder;

				_Splitr/* $VF was: 2Splitr*/(OfInt splitr, long index) {
					super(splitr, index);
				}

				public void accept(int t) {
					this.holder = t;
				}

				public boolean tryAdvance(Consumer<? super R> action) {
					if (this.fromSpliterator.tryAdvance(this)) {
						action.accept(function.apply(this.holder, this.index++));
						return true;
					} else {
						return false;
					}
				}

				2Splitr createSplit(OfInt from, long i) {
					return new 2Splitr(from, i);
				}
			}

			return StreamSupport.stream(new 2Splitr(fromSpliterator, 0L), isParallel);
		}
	}

	public static <R> Stream<R> mapWithIndex(LongStream stream, Streams.LongFunctionWithIndex<R> function) {
		Preconditions.checkNotNull(stream);
		Preconditions.checkNotNull(function);
		boolean isParallel = stream.isParallel();
		OfLong fromSpliterator = stream.spliterator();
		if (!fromSpliterator.hasCharacteristics(16384)) {
			final java.util.PrimitiveIterator.OfLong fromIterator = Spliterators.iterator(fromSpliterator);
			return StreamSupport.stream(new AbstractSpliterator<R>(fromSpliterator.estimateSize(), fromSpliterator.characteristics() & 80) {
				long index = 0L;

				public boolean tryAdvance(Consumer<? super R> action) {
					if (fromIterator.hasNext()) {
						action.accept(function.apply(fromIterator.nextLong(), this.index++));
						return true;
					} else {
						return false;
					}
				}
			}, isParallel);
		} else {
			class 3Splitr extends Streams.MapWithIndexSpliterator<OfLong, R, 3Splitr> implements LongConsumer, Spliterator<R> {
				long holder;

				_Splitr/* $VF was: 3Splitr*/(OfLong splitr, long index) {
					super(splitr, index);
				}

				public void accept(long t) {
					this.holder = t;
				}

				public boolean tryAdvance(Consumer<? super R> action) {
					if (this.fromSpliterator.tryAdvance(this)) {
						action.accept(function.apply(this.holder, this.index++));
						return true;
					} else {
						return false;
					}
				}

				3Splitr createSplit(OfLong from, long i) {
					return new 3Splitr(from, i);
				}
			}

			return StreamSupport.stream(new 3Splitr(fromSpliterator, 0L), isParallel);
		}
	}

	public static <R> Stream<R> mapWithIndex(DoubleStream stream, Streams.DoubleFunctionWithIndex<R> function) {
		Preconditions.checkNotNull(stream);
		Preconditions.checkNotNull(function);
		boolean isParallel = stream.isParallel();
		OfDouble fromSpliterator = stream.spliterator();
		if (!fromSpliterator.hasCharacteristics(16384)) {
			final java.util.PrimitiveIterator.OfDouble fromIterator = Spliterators.iterator(fromSpliterator);
			return StreamSupport.stream(new AbstractSpliterator<R>(fromSpliterator.estimateSize(), fromSpliterator.characteristics() & 80) {
				long index = 0L;

				public boolean tryAdvance(Consumer<? super R> action) {
					if (fromIterator.hasNext()) {
						action.accept(function.apply(fromIterator.nextDouble(), this.index++));
						return true;
					} else {
						return false;
					}
				}
			}, isParallel);
		} else {
			class 4Splitr extends Streams.MapWithIndexSpliterator<OfDouble, R, 4Splitr> implements DoubleConsumer, Spliterator<R> {
				double holder;

				_Splitr/* $VF was: 4Splitr*/(OfDouble splitr, long index) {
					super(splitr, index);
				}

				public void accept(double t) {
					this.holder = t;
				}

				public boolean tryAdvance(Consumer<? super R> action) {
					if (this.fromSpliterator.tryAdvance(this)) {
						action.accept(function.apply(this.holder, this.index++));
						return true;
					} else {
						return false;
					}
				}

				4Splitr createSplit(OfDouble from, long i) {
					return new 4Splitr(from, i);
				}
			}

			return StreamSupport.stream(new 4Splitr(fromSpliterator, 0L), isParallel);
		}
	}

	private Streams() {
	}

	@Beta
	public interface DoubleFunctionWithIndex<R> {
		R apply(double double1, long long2);
	}

	@Beta
	public interface FunctionWithIndex<T, R> {
		R apply(T object, long long2);
	}

	@Beta
	public interface IntFunctionWithIndex<R> {
		R apply(int integer, long long2);
	}

	@Beta
	public interface LongFunctionWithIndex<R> {
		R apply(long long1, long long2);
	}

	private abstract static class MapWithIndexSpliterator<F extends Spliterator<?>, R, S extends Streams.MapWithIndexSpliterator<F, R, S>>
		implements Spliterator<R> {
		final F fromSpliterator;
		long index;

		MapWithIndexSpliterator(F fromSpliterator, long index) {
			this.fromSpliterator = fromSpliterator;
			this.index = index;
		}

		abstract S createSplit(F spliterator, long long2);

		public S trySplit() {
			F split = (F)this.fromSpliterator.trySplit();
			if (split == null) {
				return null;
			} else {
				S result = this.createSplit(split, this.index);
				this.index = this.index + split.getExactSizeIfKnown();
				return result;
			}
		}

		public long estimateSize() {
			return this.fromSpliterator.estimateSize();
		}

		public int characteristics() {
			return this.fromSpliterator.characteristics() & 16464;
		}
	}
}
