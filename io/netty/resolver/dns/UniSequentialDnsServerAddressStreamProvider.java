package io.netty.resolver.dns;

import io.netty.util.internal.ObjectUtil;

abstract class UniSequentialDnsServerAddressStreamProvider implements DnsServerAddressStreamProvider {
	private final DnsServerAddresses addresses;

	UniSequentialDnsServerAddressStreamProvider(DnsServerAddresses addresses) {
		this.addresses = ObjectUtil.checkNotNull(addresses, "addresses");
	}

	@Override
	public final DnsServerAddressStream nameServerAddressStream(String hostname) {
		return this.addresses.stream();
	}
}
