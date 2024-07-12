package io.netty.resolver;

import java.net.InetAddress;

public interface HostsFileEntriesResolver {
	HostsFileEntriesResolver DEFAULT = new DefaultHostsFileEntriesResolver();

	InetAddress address(String string, ResolvedAddressTypes resolvedAddressTypes);
}
