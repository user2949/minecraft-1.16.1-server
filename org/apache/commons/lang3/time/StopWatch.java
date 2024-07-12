package org.apache.commons.lang3.time;

import java.util.concurrent.TimeUnit;

public class StopWatch {
	private static final long NANO_2_MILLIS = 1000000L;
	private StopWatch.State runningState = StopWatch.State.UNSTARTED;
	private StopWatch.SplitState splitState = StopWatch.SplitState.UNSPLIT;
	private long startTime;
	private long startTimeMillis;
	private long stopTime;

	public static StopWatch createStarted() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}

	public void start() {
		if (this.runningState == StopWatch.State.STOPPED) {
			throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
		} else if (this.runningState != StopWatch.State.UNSTARTED) {
			throw new IllegalStateException("Stopwatch already started. ");
		} else {
			this.startTime = System.nanoTime();
			this.startTimeMillis = System.currentTimeMillis();
			this.runningState = StopWatch.State.RUNNING;
		}
	}

	public void stop() {
		if (this.runningState != StopWatch.State.RUNNING && this.runningState != StopWatch.State.SUSPENDED) {
			throw new IllegalStateException("Stopwatch is not running. ");
		} else {
			if (this.runningState == StopWatch.State.RUNNING) {
				this.stopTime = System.nanoTime();
			}

			this.runningState = StopWatch.State.STOPPED;
		}
	}

	public void reset() {
		this.runningState = StopWatch.State.UNSTARTED;
		this.splitState = StopWatch.SplitState.UNSPLIT;
	}

	public void split() {
		if (this.runningState != StopWatch.State.RUNNING) {
			throw new IllegalStateException("Stopwatch is not running. ");
		} else {
			this.stopTime = System.nanoTime();
			this.splitState = StopWatch.SplitState.SPLIT;
		}
	}

	public void unsplit() {
		if (this.splitState != StopWatch.SplitState.SPLIT) {
			throw new IllegalStateException("Stopwatch has not been split. ");
		} else {
			this.splitState = StopWatch.SplitState.UNSPLIT;
		}
	}

	public void suspend() {
		if (this.runningState != StopWatch.State.RUNNING) {
			throw new IllegalStateException("Stopwatch must be running to suspend. ");
		} else {
			this.stopTime = System.nanoTime();
			this.runningState = StopWatch.State.SUSPENDED;
		}
	}

	public void resume() {
		if (this.runningState != StopWatch.State.SUSPENDED) {
			throw new IllegalStateException("Stopwatch must be suspended to resume. ");
		} else {
			this.startTime = this.startTime + (System.nanoTime() - this.stopTime);
			this.runningState = StopWatch.State.RUNNING;
		}
	}

	public long getTime() {
		return this.getNanoTime() / 1000000L;
	}

	public long getTime(TimeUnit timeUnit) {
		return timeUnit.convert(this.getNanoTime(), TimeUnit.NANOSECONDS);
	}

	public long getNanoTime() {
		if (this.runningState == StopWatch.State.STOPPED || this.runningState == StopWatch.State.SUSPENDED) {
			return this.stopTime - this.startTime;
		} else if (this.runningState == StopWatch.State.UNSTARTED) {
			return 0L;
		} else if (this.runningState == StopWatch.State.RUNNING) {
			return System.nanoTime() - this.startTime;
		} else {
			throw new RuntimeException("Illegal running state has occurred.");
		}
	}

	public long getSplitTime() {
		return this.getSplitNanoTime() / 1000000L;
	}

	public long getSplitNanoTime() {
		if (this.splitState != StopWatch.SplitState.SPLIT) {
			throw new IllegalStateException("Stopwatch must be split to get the split time. ");
		} else {
			return this.stopTime - this.startTime;
		}
	}

	public long getStartTime() {
		if (this.runningState == StopWatch.State.UNSTARTED) {
			throw new IllegalStateException("Stopwatch has not been started");
		} else {
			return this.startTimeMillis;
		}
	}

	public String toString() {
		return DurationFormatUtils.formatDurationHMS(this.getTime());
	}

	public String toSplitString() {
		return DurationFormatUtils.formatDurationHMS(this.getSplitTime());
	}

	public boolean isStarted() {
		return this.runningState.isStarted();
	}

	public boolean isSuspended() {
		return this.runningState.isSuspended();
	}

	public boolean isStopped() {
		return this.runningState.isStopped();
	}

	private static enum SplitState {
		SPLIT,
		UNSPLIT;
	}

	private static enum State {
		UNSTARTED {
			@Override
			boolean isStarted() {
				return false;
			}

			@Override
			boolean isStopped() {
				return true;
			}

			@Override
			boolean isSuspended() {
				return false;
			}
		},
		RUNNING {
			@Override
			boolean isStarted() {
				return true;
			}

			@Override
			boolean isStopped() {
				return false;
			}

			@Override
			boolean isSuspended() {
				return false;
			}
		},
		STOPPED {
			@Override
			boolean isStarted() {
				return false;
			}

			@Override
			boolean isStopped() {
				return true;
			}

			@Override
			boolean isSuspended() {
				return false;
			}
		},
		SUSPENDED {
			@Override
			boolean isStarted() {
				return true;
			}

			@Override
			boolean isStopped() {
				return false;
			}

			@Override
			boolean isSuspended() {
				return true;
			}
		};

		private State() {
		}

		abstract boolean isStarted();

		abstract boolean isStopped();

		abstract boolean isSuspended();
	}
}
