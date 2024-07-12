package io.netty.util.internal.shaded.org.jctools.queues;

public final class IndexedQueueSizeUtil {
	public static int size(IndexedQueueSizeUtil.IndexedQueue iq) {
		long after = iq.lvConsumerIndex();

		long before;
		long currentProducerIndex;
		do {
			before = after;
			currentProducerIndex = iq.lvProducerIndex();
			after = iq.lvConsumerIndex();
		} while (before != after);

		long size = currentProducerIndex - after;
		return size > 2147483647L ? Integer.MAX_VALUE : (int)size;
	}

	public static boolean isEmpty(IndexedQueueSizeUtil.IndexedQueue iq) {
		return iq.lvConsumerIndex() == iq.lvProducerIndex();
	}

	public interface IndexedQueue {
		long lvConsumerIndex();

		long lvProducerIndex();
	}
}
