package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.npn.NextProtoNego;
import org.eclipse.jetty.npn.NextProtoNego.ClientProvider;
import org.eclipse.jetty.npn.NextProtoNego.ServerProvider;

final class JettyNpnSslEngine extends JdkSslEngine {
	private static boolean available;

	static boolean isAvailable() {
		updateAvailability();
		return available;
	}

	private static void updateAvailability() {
		if (!available) {
			try {
				Class.forName("sun.security.ssl.NextProtoNegoExtension", true, null);
				available = true;
			} catch (Exception var1) {
			}
		}
	}

	JettyNpnSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean server) {
		super(engine);
		ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
		if (server) {
			final ProtocolSelectionListener protocolListener = ObjectUtil.checkNotNull(
				applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener"
			);
			NextProtoNego.put(engine, new ServerProvider() {
				public void unsupported() {
					protocolListener.unsupported();
				}

				public List<String> protocols() {
					return applicationNegotiator.protocols();
				}

				public void protocolSelected(String protocol) {
					try {
						protocolListener.selected(protocol);
					} catch (Throwable var3) {
						PlatformDependent.throwException(var3);
					}
				}
			});
		} else {
			final ProtocolSelector protocolSelector = ObjectUtil.checkNotNull(
				applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector"
			);
			NextProtoNego.put(engine, new ClientProvider() {
				public boolean supports() {
					return true;
				}

				public void unsupported() {
					protocolSelector.unsupported();
				}

				public String selectProtocol(List<String> protocols) {
					try {
						return protocolSelector.select(protocols);
					} catch (Throwable var3) {
						PlatformDependent.throwException(var3);
						return null;
					}
				}
			});
		}
	}

	@Override
	public void closeInbound() throws SSLException {
		NextProtoNego.remove(this.getWrappedEngine());
		super.closeInbound();
	}

	@Override
	public void closeOutbound() {
		NextProtoNego.remove(this.getWrappedEngine());
		super.closeOutbound();
	}
}
