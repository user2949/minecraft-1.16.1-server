package io.netty.resolver.dns;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class RotationalDnsServerAddresses extends DefaultDnsServerAddresses {
	private static final AtomicIntegerFieldUpdater<RotationalDnsServerAddresses> startIdxUpdater = AtomicIntegerFieldUpdater.newUpdater(
		RotationalDnsServerAddresses.class, "startIdx"
	);
	private volatile int startIdx;

	RotationalDnsServerAddresses(InetSocketAddress[] addresses) {
		super("rotational", addresses);
	}

	@Override
	public DnsServerAddressStream stream() {
		int curStartIdx;
		int nextStartIdx;
		do {
			curStartIdx = this.startIdx;
			nextStartIdx = curStartIdx + 1;
			if (nextStartIdx >= this.addresses.length) {
				nextStartIdx = 0;
			}
		} while (!startIdxUpdater.compareAndSet(this, curStartIdx, nextStartIdx));

		return new SequentialDnsServerAddressStream(this.addresses, curStartIdx);
	}
}
