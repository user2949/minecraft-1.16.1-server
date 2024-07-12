package io.netty.util.internal.shaded.org.jctools.queues;

public interface MessagePassingQueue<T> {
	int UNBOUNDED_CAPACITY = -1;

	boolean offer(T object);

	T poll();

	T peek();

	int size();

	void clear();

	boolean isEmpty();

	int capacity();

	boolean relaxedOffer(T object);

	T relaxedPoll();

	T relaxedPeek();

	int drain(MessagePassingQueue.Consumer<T> consumer);

	int fill(MessagePassingQueue.Supplier<T> supplier);

	int drain(MessagePassingQueue.Consumer<T> consumer, int integer);

	int fill(MessagePassingQueue.Supplier<T> supplier, int integer);

	void drain(MessagePassingQueue.Consumer<T> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition);

	void fill(MessagePassingQueue.Supplier<T> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition);

	public interface Consumer<T> {
		void accept(T object);
	}

	public interface ExitCondition {
		boolean keepRunning();
	}

	public interface Supplier<T> {
		T get();
	}

	public interface WaitStrategy {
		int idle(int integer);
	}
}
