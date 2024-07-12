package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;

final class Java9SslEngine extends JdkSslEngine {
	private final ProtocolSelectionListener selectionListener;
	private final Java9SslEngine.AlpnSelector alpnSelector;

	Java9SslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
		super(engine);
		if (isServer) {
			this.selectionListener = null;
			this.alpnSelector = new Java9SslEngine.AlpnSelector(
				applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols()))
			);
			Java9SslUtils.setHandshakeApplicationProtocolSelector(engine, this.alpnSelector);
		} else {
			this.selectionListener = applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols());
			this.alpnSelector = null;
			Java9SslUtils.setApplicationProtocols(engine, applicationNegotiator.protocols());
		}
	}

	private SSLEngineResult verifyProtocolSelection(SSLEngineResult result) throws SSLException {
		if (result.getHandshakeStatus() != HandshakeStatus.FINISHED) {
			return result;
		} else if (this.alpnSelector == null) {
			try {
				String protocol = this.getApplicationProtocol();

				assert protocol != null;

				if (protocol.isEmpty()) {
					this.selectionListener.unsupported();
				} else {
					this.selectionListener.selected(protocol);
				}

				return result;
			} catch (Throwable var3) {
				throw SslUtils.toSSLHandshakeException(var3);
			}
		} else {
			assert this.selectionListener == null;

			this.alpnSelector.checkUnsupported();
			return result;
		}
	}

	@Override
	public SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
		return this.verifyProtocolSelection(super.wrap(src, dst));
	}

	@Override
	public SSLEngineResult wrap(ByteBuffer[] srcs, ByteBuffer dst) throws SSLException {
		return this.verifyProtocolSelection(super.wrap(srcs, dst));
	}

	@Override
	public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int len, ByteBuffer dst) throws SSLException {
		return this.verifyProtocolSelection(super.wrap(srcs, offset, len, dst));
	}

	@Override
	public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
		return this.verifyProtocolSelection(super.unwrap(src, dst));
	}

	@Override
	public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
		return this.verifyProtocolSelection(super.unwrap(src, dsts));
	}

	@Override
	public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dst, int offset, int len) throws SSLException {
		return this.verifyProtocolSelection(super.unwrap(src, dst, offset, len));
	}

	@Override
	void setNegotiatedApplicationProtocol(String applicationProtocol) {
	}

	@Override
	public String getNegotiatedApplicationProtocol() {
		String protocol = this.getApplicationProtocol();
		if (protocol != null) {
			return protocol.isEmpty() ? null : protocol;
		} else {
			return protocol;
		}
	}

	public String getApplicationProtocol() {
		return Java9SslUtils.getApplicationProtocol(this.getWrappedEngine());
	}

	public String getHandshakeApplicationProtocol() {
		return Java9SslUtils.getHandshakeApplicationProtocol(this.getWrappedEngine());
	}

	public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
		Java9SslUtils.setHandshakeApplicationProtocolSelector(this.getWrappedEngine(), selector);
	}

	public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
		return Java9SslUtils.getHandshakeApplicationProtocolSelector(this.getWrappedEngine());
	}

	private final class AlpnSelector implements BiFunction<SSLEngine, List<String>, String> {
		private final ProtocolSelector selector;
		private boolean called;

		AlpnSelector(ProtocolSelector selector) {
			this.selector = selector;
		}

		public String apply(SSLEngine sslEngine, List<String> strings) {
			assert !this.called;

			this.called = true;

			try {
				String selected = this.selector.select(strings);
				return selected == null ? "" : selected;
			} catch (Exception var4) {
				return null;
			}
		}

		void checkUnsupported() {
			if (!this.called) {
				String protocol = Java9SslEngine.this.getApplicationProtocol();

				assert protocol != null;

				if (protocol.isEmpty()) {
					this.selector.unsupported();
				}
			}
		}
	}
}
