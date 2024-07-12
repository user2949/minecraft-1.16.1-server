package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
	private static final SslEngineWrapperFactory NPN_WRAPPER = new SslEngineWrapperFactory() {
		{
			if (!JettyNpnSslEngine.isAvailable()) {
				throw new RuntimeException("NPN unsupported. Is your classpath configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
			}
		}

		@Override
		public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
			return new JettyNpnSslEngine(engine, applicationNegotiator, isServer);
		}
	};

	public JdkNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
		this(false, protocols);
	}

	public JdkNpnApplicationProtocolNegotiator(String... protocols) {
		this(false, protocols);
	}

	public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
		this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
	}

	public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
		this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
	}

	public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
		this(
			clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY,
			serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY,
			protocols
		);
	}

	public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
		this(
			clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY,
			serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY,
			protocols
		);
	}

	public JdkNpnApplicationProtocolNegotiator(
		ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols
	) {
		super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
	}

	public JdkNpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
		super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
	}
}
