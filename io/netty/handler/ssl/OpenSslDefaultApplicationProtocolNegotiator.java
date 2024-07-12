package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Deprecated
public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
	private final ApplicationProtocolConfig config;

	public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig config) {
		this.config = ObjectUtil.checkNotNull(config, "config");
	}

	@Override
	public List<String> protocols() {
		return this.config.supportedProtocols();
	}

	@Override
	public Protocol protocol() {
		return this.config.protocol();
	}

	@Override
	public SelectorFailureBehavior selectorFailureBehavior() {
		return this.config.selectorFailureBehavior();
	}

	@Override
	public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
		return this.config.selectedListenerFailureBehavior();
	}
}
