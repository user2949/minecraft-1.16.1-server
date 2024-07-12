package org.apache.logging.log4j.core.util;

import java.util.concurrent.locks.LockSupport;

public final class CachedClock implements Clock {
	private static final int UPDATE_THRESHOLD = 1000;
	private static volatile CachedClock instance;
	private static final Object INSTANCE_LOCK = new Object();
	private volatile long millis = System.currentTimeMillis();
	private short count = 0;

	private CachedClock() {
		Thread updater = new Log4jThread(new Runnable() {
			public void run() {
				while (true) {
					long time = System.currentTimeMillis();
					CachedClock.this.millis = time;
					LockSupport.parkNanos(1000000L);
				}
			}
		}, "CachedClock Updater Thread");
		updater.setDaemon(true);
		updater.start();
	}

	public static CachedClock instance() {
		CachedClock result = instance;
		if (result == null) {
			synchronized (INSTANCE_LOCK) {
				result = instance;
				if (result == null) {
					instance = result = new CachedClock();
				}
			}
		}

		return result;
	}

	@Override
	public long currentTimeMillis() {
		if (++this.count > 1000) {
			this.millis = System.currentTimeMillis();
			this.count = 0;
		}

		return this.millis;
	}
}
