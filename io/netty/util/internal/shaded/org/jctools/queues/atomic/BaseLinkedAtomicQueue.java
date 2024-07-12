package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import java.util.Iterator;

abstract class BaseLinkedAtomicQueue<E> extends BaseLinkedAtomicQueuePad2<E> {
	public final Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	public String toString() {
		return this.getClass().getName();
	}

	protected final LinkedQueueAtomicNode<E> newNode() {
		return new LinkedQueueAtomicNode<>();
	}

	protected final LinkedQueueAtomicNode<E> newNode(E e) {
		return new LinkedQueueAtomicNode<>(e);
	}

	@Override
	public final int size() {
		LinkedQueueAtomicNode<E> chaserNode = this.lvConsumerNode();
		LinkedQueueAtomicNode<E> producerNode = this.lvProducerNode();

		int size;
		for (size = 0; chaserNode != producerNode && chaserNode != null && size < Integer.MAX_VALUE; size++) {
			LinkedQueueAtomicNode<E> next = chaserNode.lvNext();
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

	protected E getSingleConsumerNodeValue(LinkedQueueAtomicNode<E> currConsumerNode, LinkedQueueAtomicNode<E> nextNode) {
		E nextValue = nextNode.getAndNullValue();
		currConsumerNode.soNext(currConsumerNode);
		this.spConsumerNode(nextNode);
		return nextValue;
	}

	@Override
	public E relaxedPoll() {
		LinkedQueueAtomicNode<E> currConsumerNode = this.lpConsumerNode();
		LinkedQueueAtomicNode<E> nextNode = currConsumerNode.lvNext();
		return nextNode != null ? this.getSingleConsumerNodeValue(currConsumerNode, nextNode) : null;
	}

	@Override
	public E relaxedPeek() {
		LinkedQueueAtomicNode<E> nextNode = this.lpConsumerNode().lvNext();
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
		LinkedQueueAtomicNode<E> chaserNode = this.consumerNode;

		for (int i = 0; i < limit; i++) {
			LinkedQueueAtomicNode<E> nextNode = chaserNode.lvNext();
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
		LinkedQueueAtomicNode<E> chaserNode = this.consumerNode;
		int idleCounter = 0;

		while (exit.keepRunning()) {
			for (int i = 0; i < 4096; i++) {
				LinkedQueueAtomicNode<E> nextNode = chaserNode.lvNext();
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
