package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.ExitCondition;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Supplier;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;

public class SpscLinkedQueue<E> extends BaseLinkedQueue<E> {
	public SpscLinkedQueue() {
		LinkedQueueNode<E> node = this.newNode();
		this.spProducerNode(node);
		this.spConsumerNode(node);
		node.soNext(null);
	}

	@Override
	public boolean offer(E e) {
		if (null == e) {
			throw new NullPointerException();
		} else {
			LinkedQueueNode<E> nextNode = this.newNode(e);
			this.lpProducerNode().soNext(nextNode);
			this.spProducerNode(nextNode);
			return true;
		}
	}

	@Override
	public E poll() {
		return (E)this.relaxedPoll();
	}

	@Override
	public E peek() {
		return (E)this.relaxedPeek();
	}

	@Override
	public int fill(Supplier<E> s) {
		long result = 0L;

		do {
			this.fill(s, 4096);
			result += 4096L;
		} while (result <= 2147479551L);

		return (int)result;
	}

	@Override
	public int fill(Supplier<E> s, int limit) {
		if (limit == 0) {
			return 0;
		} else {
			LinkedQueueNode<E> tail = this.newNode(s.get());
			LinkedQueueNode<E> head = tail;

			for (int i = 1; i < limit; i++) {
				LinkedQueueNode<E> temp = this.newNode(s.get());
				tail.soNext(temp);
				tail = temp;
			}

			LinkedQueueNode<E> oldPNode = this.lpProducerNode();
			oldPNode.soNext(head);
			this.spProducerNode(tail);
			return limit;
		}
	}

	@Override
	public void fill(Supplier<E> s, WaitStrategy wait, ExitCondition exit) {
		LinkedQueueNode<E> chaserNode = this.producerNode;

		while (exit.keepRunning()) {
			for (int i = 0; i < 4096; i++) {
				LinkedQueueNode<E> nextNode = this.newNode(s.get());
				chaserNode.soNext(nextNode);
				chaserNode = nextNode;
				this.producerNode = nextNode;
			}
		}
	}
}
