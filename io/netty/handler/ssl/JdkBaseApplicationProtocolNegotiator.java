package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;

class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
	private final List<String> protocols;
	private final ProtocolSelectorFactory selectorFactory;
	private final ProtocolSelectionListenerFactory listenerFactory;
	private final SslEngineWrapperFactory wrapperFactory;
	static final ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
		@Override
		public ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
			return new JdkBaseApplicationProtocolNegotiator.FailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
		}
	};
	static final ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
		@Override
		public ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
			return new JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelector((JdkSslEngine)engine, supportedProtocols);
		}
	};
	static final ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
		@Override
		public ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
			return new JdkBaseApplicationProtocolNegotiator.FailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
		}
	};
	static final ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
		@Override
		public ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
			return new JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelectionListener((JdkSslEngine)engine, supportedProtocols);
		}
	};

	JdkBaseApplicationProtocolNegotiator(
		SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols
	) {
		this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
	}

	JdkBaseApplicationProtocolNegotiator(
		SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols
	) {
		this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
	}

	private JdkBaseApplicationProtocolNegotiator(
		SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, List<String> protocols
	) {
		this.wrapperFactory = ObjectUtil.checkNotNull(wrapperFactory, "wrapperFactory");
		this.selectorFactory = ObjectUtil.checkNotNull(selectorFactory, "selectorFactory");
		this.listenerFactory = ObjectUtil.checkNotNull(listenerFactory, "listenerFactory");
		this.protocols = Collections.unmodifiableList(ObjectUtil.checkNotNull(protocols, "protocols"));
	}

	@Override
	public List<String> protocols() {
		return this.protocols;
	}

	@Override
	public ProtocolSelectorFactory protocolSelectorFactory() {
		return this.selectorFactory;
	}

	@Override
	public ProtocolSelectionListenerFactory protocolListenerFactory() {
		return this.listenerFactory;
	}

	@Override
	public SslEngineWrapperFactory wrapperFactory() {
		return this.wrapperFactory;
	}

	private static final class FailProtocolSelectionListener extends JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelectionListener {
		FailProtocolSelectionListener(JdkSslEngine engineWrapper, List<String> supportedProtocols) {
			super(engineWrapper, supportedProtocols);
		}

		@Override
		protected void noSelectedMatchFound(String protocol) throws Exception {
			throw new SSLHandshakeException("No compatible protocols found");
		}
	}

	private static final class FailProtocolSelector extends JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelector {
		FailProtocolSelector(JdkSslEngine engineWrapper, Set<String> supportedProtocols) {
			super(engineWrapper, supportedProtocols);
		}

		@Override
		public String noSelectMatchFound() throws Exception {
			throw new SSLHandshakeException("Selected protocol is not supported");
		}
	}

	private static class NoFailProtocolSelectionListener implements ProtocolSelectionListener {
		private final JdkSslEngine engineWrapper;
		private final List<String> supportedProtocols;

		NoFailProtocolSelectionListener(JdkSslEngine engineWrapper, List<String> supportedProtocols) {
			this.engineWrapper = engineWrapper;
			this.supportedProtocols = supportedProtocols;
		}

		@Override
		public void unsupported() {
			this.engineWrapper.setNegotiatedApplicationProtocol(null);
		}

		@Override
		public void selected(String protocol) throws Exception {
			if (this.supportedProtocols.contains(protocol)) {
				this.engineWrapper.setNegotiatedApplicationProtocol(protocol);
			} else {
				this.noSelectedMatchFound(protocol);
			}
		}

		protected void noSelectedMatchFound(String protocol) throws Exception {
		}
	}

	static class NoFailProtocolSelector implements ProtocolSelector {
		private final JdkSslEngine engineWrapper;
		private final Set<String> supportedProtocols;

		NoFailProtocolSelector(JdkSslEngine engineWrapper, Set<String> supportedProtocols) {
			this.engineWrapper = engineWrapper;
			this.supportedProtocols = supportedProtocols;
		}

		@Override
		public void unsupported() {
			this.engineWrapper.setNegotiatedApplicationProtocol(null);
		}

		@Override
		public String select(List<String> protocols) throws Exception {
			for (String p : this.supportedProtocols) {
				if (protocols.contains(p)) {
					this.engineWrapper.setNegotiatedApplicationProtocol(p);
					return p;
				}
			}

			return this.noSelectMatchFound();
		}

		public String noSelectMatchFound() throws Exception {
			this.engineWrapper.setNegotiatedApplicationProtocol(null);
			return null;
		}
	}
}
