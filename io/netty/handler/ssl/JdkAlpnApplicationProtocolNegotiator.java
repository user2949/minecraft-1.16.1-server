package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import io.netty.util.internal.PlatformDependent;
import javax.net.ssl.SSLEngine;

@Deprecated
public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
	private static final boolean AVAILABLE = Conscrypt.isAvailable() || jdkAlpnSupported() || JettyAlpnSslEngine.isAvailable();
	private static final SslEngineWrapperFactory ALPN_WRAPPER = (SslEngineWrapperFactory)(AVAILABLE
		? new JdkAlpnApplicationProtocolNegotiator.AlpnWrapper()
		: new JdkAlpnApplicationProtocolNegotiator.FailureWrapper());

	public JdkAlpnApplicationProtocolNegotiator(Iterable<String> protocols) {
		this(false, protocols);
	}

	public JdkAlpnApplicationProtocolNegotiator(String... protocols) {
		this(false, protocols);
	}

	public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
		this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
	}

	public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
		this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
	}

	public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
		this(
			serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY,
			clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY,
			protocols
		);
	}

	public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
		this(
			serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY,
			clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY,
			protocols
		);
	}

	public JdkAlpnApplicationProtocolNegotiator(
		ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols
	) {
		super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
	}

	public JdkAlpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
		super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
	}

	static boolean jdkAlpnSupported() {
		return PlatformDependent.javaVersion() >= 9 && Java9SslUtils.supportsAlpn();
	}

	private static final class AlpnWrapper extends AllocatorAwareSslEngineWrapperFactory {
		private AlpnWrapper() {
		}

		@Override
		public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
			if (Conscrypt.isEngineSupported(engine)) {
				return isServer
					? ConscryptAlpnSslEngine.newServerEngine(engine, alloc, applicationNegotiator)
					: ConscryptAlpnSslEngine.newClientEngine(engine, alloc, applicationNegotiator);
			} else if (JdkAlpnApplicationProtocolNegotiator.jdkAlpnSupported()) {
				return new Java9SslEngine(engine, applicationNegotiator, isServer);
			} else if (JettyAlpnSslEngine.isAvailable()) {
				return isServer ? JettyAlpnSslEngine.newServerEngine(engine, applicationNegotiator) : JettyAlpnSslEngine.newClientEngine(engine, applicationNegotiator);
			} else {
				throw new RuntimeException("Unable to wrap SSLEngine of type " + engine.getClass().getName());
			}
		}
	}

	private static final class FailureWrapper extends AllocatorAwareSslEngineWrapperFactory {
		private FailureWrapper() {
		}

		@Override
		public SSLEngine wrapSslEngine(SSLEngine engine, ByteBufAllocator alloc, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
			throw new RuntimeException(
				"ALPN unsupported. Is your classpath configured correctly? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider. For Jetty-ALPN, see http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting"
			);
		}
	}
}
