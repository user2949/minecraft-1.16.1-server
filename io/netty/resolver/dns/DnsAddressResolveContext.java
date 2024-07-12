package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

final class DnsAddressResolveContext extends DnsResolveContext<InetAddress> {
	private final DnsCache resolveCache;

	DnsAddressResolveContext(DnsNameResolver parent, String hostname, DnsRecord[] additionals, DnsServerAddressStream nameServerAddrs, DnsCache resolveCache) {
		super(parent, hostname, 1, parent.resolveRecordTypes(), additionals, nameServerAddrs);
		this.resolveCache = resolveCache;
	}

	@Override
	DnsResolveContext<InetAddress> newResolverContext(
		DnsNameResolver parent, String hostname, int dnsClass, DnsRecordType[] expectedTypes, DnsRecord[] additionals, DnsServerAddressStream nameServerAddrs
	) {
		return new DnsAddressResolveContext(parent, hostname, additionals, nameServerAddrs, this.resolveCache);
	}

	InetAddress convertRecord(DnsRecord record, String hostname, DnsRecord[] additionals, EventLoop eventLoop) {
		return DnsAddressDecoder.decodeAddress(record, hostname, this.parent.isDecodeIdn());
	}

	@Override
	List<InetAddress> filterResults(List<InetAddress> unfiltered) {
		Class<? extends InetAddress> inetAddressType = this.parent.preferredAddressType().addressType();
		int size = unfiltered.size();
		int numExpected = 0;

		for (int i = 0; i < size; i++) {
			InetAddress address = (InetAddress)unfiltered.get(i);
			if (inetAddressType.isInstance(address)) {
				numExpected++;
			}
		}

		if (numExpected != size && numExpected != 0) {
			List<InetAddress> filtered = new ArrayList(numExpected);

			for (int ix = 0; ix < size; ix++) {
				InetAddress address = (InetAddress)unfiltered.get(ix);
				if (inetAddressType.isInstance(address)) {
					filtered.add(address);
				}
			}

			return filtered;
		} else {
			return unfiltered;
		}
	}

	void cache(String hostname, DnsRecord[] additionals, DnsRecord result, InetAddress convertedResult) {
		this.resolveCache.cache(hostname, additionals, convertedResult, result.timeToLive(), this.parent.ch.eventLoop());
	}

	@Override
	void cache(String hostname, DnsRecord[] additionals, UnknownHostException cause) {
		this.resolveCache.cache(hostname, additionals, cause, this.parent.ch.eventLoop());
	}
}
