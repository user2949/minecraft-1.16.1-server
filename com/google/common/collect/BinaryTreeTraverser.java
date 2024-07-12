package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;

@Beta
@GwtCompatible
public abstract class BinaryTreeTraverser<T> extends TreeTraverser<T> {
	public abstract Optional<T> leftChild(T object);

	public abstract Optional<T> rightChild(T object);

	@Override
	public final Iterable<T> children(T root) {
		Preconditions.checkNotNull(root);
		return new FluentIterable<T>() {
			public Iterator<T> iterator() {
				return new AbstractIterator<T>() {
					boolean doneLeft;
					boolean doneRight;

					@Override
					protected T computeNext() {
						if (!this.doneLeft) {
							this.doneLeft = true;
							Optional<T> left = BinaryTreeTraverser.this.leftChild(root);
							if (left.isPresent()) {
								return left.get();
							}
						}

						if (!this.doneRight) {
							this.doneRight = true;
							Optional<T> right = BinaryTreeTraverser.this.rightChild(root);
							if (right.isPresent()) {
								return right.get();
							}
						}

						return (T)this.endOfData();
					}
				};
			}

			public void forEach(Consumer<? super T> action) {
				BinaryTreeTraverser.acceptIfPresent(action, BinaryTreeTraverser.this.leftChild(root));
				BinaryTreeTraverser.acceptIfPresent(action, BinaryTreeTraverser.this.rightChild(root));
			}
		};
	}

	@Override
	UnmodifiableIterator<T> preOrderIterator(T root) {
		return new BinaryTreeTraverser.PreOrderIterator(root);
	}

	@Override
	UnmodifiableIterator<T> postOrderIterator(T root) {
		return new BinaryTreeTraverser.PostOrderIterator(root);
	}

	public final FluentIterable<T> inOrderTraversal(T root) {
		Preconditions.checkNotNull(root);
		return new FluentIterable<T>() {
			public UnmodifiableIterator<T> iterator() {
				return BinaryTreeTraverser.this.new InOrderIterator(root);
			}

			public void forEach(Consumer<? super T> action) {
				Preconditions.checkNotNull(action);
				(new Consumer<T>() {
					public void accept(T t) {
						BinaryTreeTraverser.acceptIfPresent(this, BinaryTreeTraverser.this.leftChild(t));
						action.accept(t);
						BinaryTreeTraverser.acceptIfPresent(this, BinaryTreeTraverser.this.rightChild(t));
					}
				}).accept(root);
			}
		};
	}

	private static <T> void pushIfPresent(Deque<T> stack, Optional<T> node) {
		if (node.isPresent()) {
			stack.addLast(node.get());
		}
	}

	private static <T> void acceptIfPresent(Consumer<? super T> action, Optional<T> node) {
		if (node.isPresent()) {
			action.accept(node.get());
		}
	}

	private final class InOrderIterator extends AbstractIterator<T> {
		private final Deque<T> stack = new ArrayDeque(8);
		private final BitSet hasExpandedLeft = new BitSet();

		InOrderIterator(T root) {
			this.stack.addLast(root);
		}

		@Override
		protected T computeNext() {
			while (!this.stack.isEmpty()) {
				T node = (T)this.stack.getLast();
				if (this.hasExpandedLeft.get(this.stack.size() - 1)) {
					this.stack.removeLast();
					this.hasExpandedLeft.clear(this.stack.size());
					BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(node));
					return node;
				}

				this.hasExpandedLeft.set(this.stack.size() - 1);
				BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(node));
			}

			return (T)this.endOfData();
		}
	}

	private final class PostOrderIterator extends UnmodifiableIterator<T> {
		private final Deque<T> stack = new ArrayDeque(8);
		private final BitSet hasExpanded;

		PostOrderIterator(T root) {
			this.stack.addLast(root);
			this.hasExpanded = new BitSet();
		}

		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		public T next() {
			while (true) {
				T node = (T)this.stack.getLast();
				boolean expandedNode = this.hasExpanded.get(this.stack.size() - 1);
				if (expandedNode) {
					this.stack.removeLast();
					this.hasExpanded.clear(this.stack.size());
					return node;
				}

				this.hasExpanded.set(this.stack.size() - 1);
				BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(node));
				BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(node));
			}
		}
	}

	private final class PreOrderIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
		private final Deque<T> stack = new ArrayDeque(8);

		PreOrderIterator(T root) {
			this.stack.addLast(root);
		}

		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public T next() {
			T result = (T)this.stack.removeLast();
			BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.rightChild(result));
			BinaryTreeTraverser.pushIfPresent(this.stack, BinaryTreeTraverser.this.leftChild(result));
			return result;
		}

		@Override
		public T peek() {
			return (T)this.stack.getLast();
		}
	}
}
