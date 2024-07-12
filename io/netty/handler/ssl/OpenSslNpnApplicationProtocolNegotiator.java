package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Deprecated
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
	private final List<String> protocols;

	public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
		this.protocols = ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
	}

	public OpenSslNpnApplicationProtocolNegotiator(String... protocols) {
		this.protocols = ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
	}

	@Override
	public Protocol protocol() {
		return Protocol.NPN;
	}

	@Override
	public List<String> protocols() {
		return this.protocols;
	}

	@Override
	public SelectorFailureBehavior selectorFailureBehavior() {
		return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
	}

	@Override
	public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
		return SelectedListenerFailureBehavior.ACCEPT;
	}
}
