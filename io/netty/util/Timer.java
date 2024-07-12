package io.netty.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Timer {
	Timeout newTimeout(TimerTask timerTask, long long2, TimeUnit timeUnit);

	Set<Timeout> stop();
}
