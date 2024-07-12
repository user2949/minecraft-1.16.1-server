package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BooleanSupplier;
import javax.annotation.concurrent.GuardedBy;

@Beta
@GwtIncompatible
public final class Monitor {
	private final boolean fair;
	private final ReentrantLock lock;
	@GuardedBy("lock")
	private Monitor.Guard activeGuards = null;

	public Monitor() {
		this(false);
	}

	public Monitor(boolean fair) {
		this.fair = fair;
		this.lock = new ReentrantLock(fair);
	}

	public Monitor.Guard newGuard(BooleanSupplier isSatisfied) {
		Preconditions.checkNotNull(isSatisfied, "isSatisfied");
		return new Monitor.Guard(this) {
			@Override
			public boolean isSatisfied() {
				return isSatisfied.getAsBoolean();
			}
		};
	}

	public void enter() {
		this.lock.lock();
	}

	public void enterInterruptibly() throws InterruptedException {
		this.lock.lockInterruptibly();
	}

	public boolean enter(long time, TimeUnit unit) {
		long timeoutNanos = toSafeNanos(time, unit);
		ReentrantLock lock = this.lock;
		if (!this.fair && lock.tryLock()) {
			return true;
		} else {
			boolean interrupted = Thread.interrupted();

			try {
				long startTime = System.nanoTime();
				long remainingNanos = timeoutNanos;

				while (true) {
					try {
						return lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
					} catch (InterruptedException var16) {
						interrupted = true;
						remainingNanos = remainingNanos(startTime, timeoutNanos);
					}
				}
			} finally {
				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
		return this.lock.tryLock(time, unit);
	}

	public boolean tryEnter() {
		return this.lock.tryLock();
	}

	public void enterWhen(Monitor.Guard guard) throws InterruptedException {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
			lock.lockInterruptibly();
			boolean satisfied = false;

			try {
				if (!guard.isSatisfied()) {
					this.await(guard, signalBeforeWaiting);
				}

				satisfied = true;
			} finally {
				if (!satisfied) {
					this.leave();
				}
			}
		}
	}

	public void enterWhenUninterruptibly(Monitor.Guard guard) {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
			lock.lock();
			boolean satisfied = false;

			try {
				if (!guard.isSatisfied()) {
					this.awaitUninterruptibly(guard, signalBeforeWaiting);
				}

				satisfied = true;
			} finally {
				if (!satisfied) {
					this.leave();
				}
			}
		}
	}

	public boolean enterWhen(Monitor.Guard guard, long time, TimeUnit unit) throws InterruptedException {
		long timeoutNanos = toSafeNanos(time, unit);
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock;
			boolean reentrant;
			long startTime;
			label186: {
				lock = this.lock;
				reentrant = lock.isHeldByCurrentThread();
				startTime = 0L;
				if (!this.fair) {
					if (Thread.interrupted()) {
						throw new InterruptedException();
					}

					if (lock.tryLock()) {
						break label186;
					}
				}

				startTime = initNanoTime(timeoutNanos);
				if (!lock.tryLock(time, unit)) {
					return false;
				}
			}

			boolean satisfied = false;
			boolean threw = true;

			boolean var13;
			try {
				satisfied = guard.isSatisfied() || this.awaitNanos(guard, startTime == 0L ? timeoutNanos : remainingNanos(startTime, timeoutNanos), reentrant);
				threw = false;
				var13 = satisfied;
			} finally {
				if (!satisfied) {
					try {
						if (threw && !reentrant) {
							this.signalNextWaiter();
						}
					} finally {
						lock.unlock();
					}
				}
			}

			return var13;
		}
	}

	public boolean enterWhenUninterruptibly(Monitor.Guard guard, long time, TimeUnit unit) {
		long timeoutNanos = toSafeNanos(time, unit);
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			long startTime = 0L;
			boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
			boolean interrupted = Thread.interrupted();

			try {
				if (this.fair || !lock.tryLock()) {
					startTime = initNanoTime(timeoutNanos);
					long remainingNanos = timeoutNanos;

					while (true) {
						try {
							if (!lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS)) {
								return false;
							}
							break;
						} catch (InterruptedException var27) {
							interrupted = true;
							remainingNanos = remainingNanos(startTime, timeoutNanos);
						}
					}
				}

				boolean satisfied = false;

				try {
					while (true) {
						try {
							if (guard.isSatisfied()) {
								satisfied = true;
							} else {
								long remainingNanos;
								if (startTime == 0L) {
									startTime = initNanoTime(timeoutNanos);
									remainingNanos = timeoutNanos;
								} else {
									remainingNanos = remainingNanos(startTime, timeoutNanos);
								}

								satisfied = this.awaitNanos(guard, remainingNanos, signalBeforeWaiting);
							}

							return satisfied;
						} catch (InterruptedException var25) {
							interrupted = true;
							signalBeforeWaiting = false;
						}
					}
				} finally {
					if (!satisfied) {
						lock.unlock();
					}
				}
			} finally {
				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public boolean enterIf(Monitor.Guard guard) {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			lock.lock();
			boolean satisfied = false;

			boolean var4;
			try {
				var4 = satisfied = guard.isSatisfied();
			} finally {
				if (!satisfied) {
					lock.unlock();
				}
			}

			return var4;
		}
	}

	public boolean enterIfInterruptibly(Monitor.Guard guard) throws InterruptedException {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			lock.lockInterruptibly();
			boolean satisfied = false;

			boolean var4;
			try {
				var4 = satisfied = guard.isSatisfied();
			} finally {
				if (!satisfied) {
					lock.unlock();
				}
			}

			return var4;
		}
	}

	public boolean enterIf(Monitor.Guard guard, long time, TimeUnit unit) {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else if (!this.enter(time, unit)) {
			return false;
		} else {
			boolean satisfied = false;

			boolean var6;
			try {
				var6 = satisfied = guard.isSatisfied();
			} finally {
				if (!satisfied) {
					this.lock.unlock();
				}
			}

			return var6;
		}
	}

	public boolean enterIfInterruptibly(Monitor.Guard guard, long time, TimeUnit unit) throws InterruptedException {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			if (!lock.tryLock(time, unit)) {
				return false;
			} else {
				boolean satisfied = false;

				boolean var7;
				try {
					var7 = satisfied = guard.isSatisfied();
				} finally {
					if (!satisfied) {
						lock.unlock();
					}
				}

				return var7;
			}
		}
	}

	public boolean tryEnterIf(Monitor.Guard guard) {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			ReentrantLock lock = this.lock;
			if (!lock.tryLock()) {
				return false;
			} else {
				boolean satisfied = false;

				boolean var4;
				try {
					var4 = satisfied = guard.isSatisfied();
				} finally {
					if (!satisfied) {
						lock.unlock();
					}
				}

				return var4;
			}
		}
	}

	public void waitFor(Monitor.Guard guard) throws InterruptedException {
		if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
			throw new IllegalMonitorStateException();
		} else {
			if (!guard.isSatisfied()) {
				this.await(guard, true);
			}
		}
	}

	public void waitForUninterruptibly(Monitor.Guard guard) {
		if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
			throw new IllegalMonitorStateException();
		} else {
			if (!guard.isSatisfied()) {
				this.awaitUninterruptibly(guard, true);
			}
		}
	}

	public boolean waitFor(Monitor.Guard guard, long time, TimeUnit unit) throws InterruptedException {
		long timeoutNanos = toSafeNanos(time, unit);
		if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
			throw new IllegalMonitorStateException();
		} else if (guard.isSatisfied()) {
			return true;
		} else if (Thread.interrupted()) {
			throw new InterruptedException();
		} else {
			return this.awaitNanos(guard, timeoutNanos, true);
		}
	}

	public boolean waitForUninterruptibly(Monitor.Guard guard, long time, TimeUnit unit) {
		long timeoutNanos = toSafeNanos(time, unit);
		if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
			throw new IllegalMonitorStateException();
		} else if (guard.isSatisfied()) {
			return true;
		} else {
			boolean signalBeforeWaiting = true;
			long startTime = initNanoTime(timeoutNanos);
			boolean interrupted = Thread.interrupted();

			try {
				long remainingNanos = timeoutNanos;

				while (true) {
					try {
						return this.awaitNanos(guard, remainingNanos, signalBeforeWaiting);
					} catch (InterruptedException var18) {
						interrupted = true;
						if (guard.isSatisfied()) {
							return true;
						}

						signalBeforeWaiting = false;
						remainingNanos = remainingNanos(startTime, timeoutNanos);
					}
				}
			} finally {
				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public void leave() {
		ReentrantLock lock = this.lock;

		try {
			if (lock.getHoldCount() == 1) {
				this.signalNextWaiter();
			}
		} finally {
			lock.unlock();
		}
	}

	public boolean isFair() {
		return this.fair;
	}

	public boolean isOccupied() {
		return this.lock.isLocked();
	}

	public boolean isOccupiedByCurrentThread() {
		return this.lock.isHeldByCurrentThread();
	}

	public int getOccupiedDepth() {
		return this.lock.getHoldCount();
	}

	public int getQueueLength() {
		return this.lock.getQueueLength();
	}

	public boolean hasQueuedThreads() {
		return this.lock.hasQueuedThreads();
	}

	public boolean hasQueuedThread(Thread thread) {
		return this.lock.hasQueuedThread(thread);
	}

	public boolean hasWaiters(Monitor.Guard guard) {
		return this.getWaitQueueLength(guard) > 0;
	}

	public int getWaitQueueLength(Monitor.Guard guard) {
		if (guard.monitor != this) {
			throw new IllegalMonitorStateException();
		} else {
			this.lock.lock();

			int var2;
			try {
				var2 = guard.waiterCount;
			} finally {
				this.lock.unlock();
			}

			return var2;
		}
	}

	private static long toSafeNanos(long time, TimeUnit unit) {
		long timeoutNanos = unit.toNanos(time);
		return timeoutNanos <= 0L ? 0L : (timeoutNanos > 6917529027641081853L ? 6917529027641081853L : timeoutNanos);
	}

	private static long initNanoTime(long timeoutNanos) {
		if (timeoutNanos <= 0L) {
			return 0L;
		} else {
			long startTime = System.nanoTime();
			return startTime == 0L ? 1L : startTime;
		}
	}

	private static long remainingNanos(long startTime, long timeoutNanos) {
		return timeoutNanos <= 0L ? 0L : timeoutNanos - (System.nanoTime() - startTime);
	}

	@GuardedBy("lock")
	private void signalNextWaiter() {
		for (Monitor.Guard guard = this.activeGuards; guard != null; guard = guard.next) {
			if (this.isSatisfied(guard)) {
				guard.condition.signal();
				break;
			}
		}
	}

	@GuardedBy("lock")
	private boolean isSatisfied(Monitor.Guard guard) {
		try {
			return guard.isSatisfied();
		} catch (Throwable var3) {
			this.signalAllWaiters();
			throw Throwables.propagate(var3);
		}
	}

	@GuardedBy("lock")
	private void signalAllWaiters() {
		for (Monitor.Guard guard = this.activeGuards; guard != null; guard = guard.next) {
			guard.condition.signalAll();
		}
	}

	@GuardedBy("lock")
	private void beginWaitingFor(Monitor.Guard guard) {
		int waiters = guard.waiterCount++;
		if (waiters == 0) {
			guard.next = this.activeGuards;
			this.activeGuards = guard;
		}
	}

	@GuardedBy("lock")
	private void endWaitingFor(Monitor.Guard guard) {
		int waiters = --guard.waiterCount;
		if (waiters == 0) {
			Monitor.Guard p = this.activeGuards;

			Monitor.Guard pred;
			for (pred = null; p != guard; p = p.next) {
				pred = p;
			}

			if (pred == null) {
				this.activeGuards = p.next;
			} else {
				pred.next = p.next;
			}

			p.next = null;
		}
	}

	@GuardedBy("lock")
	private void await(Monitor.Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
		if (signalBeforeWaiting) {
			this.signalNextWaiter();
		}

		this.beginWaitingFor(guard);

		try {
			do {
				guard.condition.await();
			} while (!guard.isSatisfied());
		} finally {
			this.endWaitingFor(guard);
		}
	}

	@GuardedBy("lock")
	private void awaitUninterruptibly(Monitor.Guard guard, boolean signalBeforeWaiting) {
		if (signalBeforeWaiting) {
			this.signalNextWaiter();
		}

		this.beginWaitingFor(guard);

		try {
			do {
				guard.condition.awaitUninterruptibly();
			} while (!guard.isSatisfied());
		} finally {
			this.endWaitingFor(guard);
		}
	}

	@GuardedBy("lock")
	private boolean awaitNanos(Monitor.Guard guard, long nanos, boolean signalBeforeWaiting) throws InterruptedException {
		boolean firstTime = true;

		boolean var10;
		try {
			while (nanos > 0L) {
				if (firstTime) {
					if (signalBeforeWaiting) {
						this.signalNextWaiter();
					}

					this.beginWaitingFor(guard);
					firstTime = false;
				}

				nanos = guard.condition.awaitNanos(nanos);
				if (guard.isSatisfied()) {
					return true;
				}
			}

			var10 = false;
		} finally {
			if (!firstTime) {
				this.endWaitingFor(guard);
			}
		}

		return var10;
	}

	@Beta
	public abstract static class Guard {
		@Weak
		final Monitor monitor;
		final Condition condition;
		@GuardedBy("monitor.lock")
		int waiterCount = 0;
		@GuardedBy("monitor.lock")
		Monitor.Guard next;

		protected Guard(Monitor monitor) {
			this.monitor = Preconditions.checkNotNull(monitor, "monitor");
			this.condition = monitor.lock.newCondition();
		}

		public abstract boolean isSatisfied();
	}
}
