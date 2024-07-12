package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;

@Deprecated
public interface JdkApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
	JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory();

	JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory();

	JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory();

	public abstract static class AllocatorAwareSslEngineWrapperFactory implements JdkApplicationProtocolNegotiator.SslEngineWrapperFactory {
		@Override
		public final SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
			return this.wrapSslEngine(engine, ByteBufAllocator.DEFAULT, applicationNegotiator, isServer);
		}

		abstract SSLEngine wrapSslEngine(
			SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean boolean4
		);
	}

	public interface ProtocolSelectionListener {
		void unsupported();

		void selected(String string) throws Exception;
	}

	public interface ProtocolSelectionListenerFactory {
		JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list);
	}

	public interface ProtocolSelector {
		void unsupported();

		String select(List<String> list) throws Exception;
	}

	public interface ProtocolSelectorFactory {
		JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set);
	}

	public interface SslEngineWrapperFactory {
		SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean boolean3);
	}
}
