package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import java.util.Iterator;

abstract class BaseLinkedQueue<E> extends BaseLinkedQueuePad2<E> {
	public final Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return this.getClass().getName();
	}

	protected final LinkedQueueNode<E> newNode() {
		return new LinkedQueueNode<>();
	}

	protected final LinkedQueueNode<E> newNode(E e) {
		return new LinkedQueueNode<>(e);
	}

	@Override
	public final int size() {
		LinkedQueueNode<E> chaserNode = this.lvConsumerNode();
		LinkedQueueNode<E> producerNode = this.lvProducerNode();

		int size;
		for (size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; size++) {
			LinkedQueueNode<E> next = chaserNode.lvNext();
			if (next == chaserNode) {
				return size;
			}

			chaserNode = next;
		}

		return size;
	}

	@Override
	public final boolean isEmpty() {
		return this.lvConsumerNode() == this.lvProducerNode();
	}

	protected E getSingleConsumerNodeValue(LinkedQueueNode<E> currConsumerNode, LinkedQueueNode<E> nextNode) {
		E nextValue = nextNode.getAndNullValue();
		currConsumerNode.soNext(currConsumerNode);
		this.spConsumerNode(nextNode);
		return nextValue;
	}

	@Override
	public E relaxedPoll() {
		LinkedQueueNode<E> currConsumerNode = this.lpConsumerNode();
		LinkedQueueNode<E> nextNode = currConsumerNode.lvNext();
		return nextNode != null ? this.getSingleConsumerNodeValue(currConsumerNode, nextNode) : null;
	}

	@Override
	public E relaxedPeek() {
		LinkedQueueNode<E> nextNode = this.lpConsumerNode().lvNext();
		return nextNode != null ? nextNode.lpValue() : null;
	}

	@Override
	public boolean relaxedOffer(E e) {
		return this.offer(e);
	}

	@Override
	public int drain(Consumer<E> c) {
		long result = 0L;

		int drained;
		do {
			drained = this.drain(c, 4096);
			result += (long)drained;
		} while (drained == 4096 && result <= 2147479551L);

		return (int)result;
	}

	@Override
	public int drain(Consumer<E> c, int limit) {
		LinkedQueueNode<E> chaserNode = this.consumerNode;

		for (int i = 0; i < limit; i++) {
			LinkedQueueNode<E> nextNode = chaserNode.lvNext();
			if (nextNode == null) {
				return i;
			}

			E nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
			chaserNode = nextNode;
			c.accept(nextValue);
		}

		return limit;
	}

	@Override
	public void drain(Consumer<E> c, WaitStrategy wait, ExitCondition exit) {
		LinkedQueueNode<E> chaserNode = this.consumerNode;
		int idleCounter = 0;

		while (exit.keepRunning()) {
			for (int i = 0; i < 4096; i++) {
				LinkedQueueNode<E> nextNode = chaserNode.lvNext();
				if (nextNode == null) {
					idleCounter = wait.idle(idleCounter);
				} else {
					idleCounter = 0;
					E nextValue = this.getSingleConsumerNodeValue(chaserNode, nextNode);
					chaserNode = nextNode;
					c.accept(nextValue);
				}
			}
		}
	}

	@Override
	public int capacity() {
		return -1;
	}
}
