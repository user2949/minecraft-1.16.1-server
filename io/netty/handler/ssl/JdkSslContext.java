package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;

public class JdkSslContext extends SslContext {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
	static final String PROTOCOL = "TLS";
	private static final String[] DEFAULT_PROTOCOLS;
	private static final List<String> DEFAULT_CIPHERS;
	private static final Set<String> SUPPORTED_CIPHERS;
	private final String[] protocols;
	private final String[] cipherSuites;
	private final List<String> unmodifiableCipherSuites;
	private final JdkApplicationProtocolNegotiator apn;
	private final ClientAuth clientAuth;
	private final SSLContext sslContext;
	private final boolean isClient;

	public JdkSslContext(SSLContext sslContext, boolean isClient, ClientAuth clientAuth) {
		this(sslContext, isClient, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth, null, false);
	}

	public JdkSslContext(
		SSLContext sslContext, boolean isClient, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, ClientAuth clientAuth
	) {
		this(sslContext, isClient, ciphers, cipherFilter, toNegotiator(apn, !isClient), clientAuth, null, false);
	}

	JdkSslContext(
		SSLContext sslContext,
		boolean isClient,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		JdkApplicationProtocolNegotiator apn,
		ClientAuth clientAuth,
		String[] protocols,
		boolean startTls
	) {
		super(startTls);
		this.apn = ObjectUtil.checkNotNull(apn, "apn");
		this.clientAuth = ObjectUtil.checkNotNull(clientAuth, "clientAuth");
		this.cipherSuites = ObjectUtil.checkNotNull(cipherFilter, "cipherFilter").filterCipherSuites(ciphers, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
		this.protocols = protocols == null ? DEFAULT_PROTOCOLS : protocols;
		this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
		this.sslContext = ObjectUtil.checkNotNull(sslContext, "sslContext");
		this.isClient = isClient;
	}

	public final SSLContext context() {
		return this.sslContext;
	}

	@Override
	public final boolean isClient() {
		return this.isClient;
	}

	@Override
	public final SSLSessionContext sessionContext() {
		return this.isServer() ? this.context().getServerSessionContext() : this.context().getClientSessionContext();
	}

	@Override
	public final List<String> cipherSuites() {
		return this.unmodifiableCipherSuites;
	}

	@Override
	public final long sessionCacheSize() {
		return (long)this.sessionContext().getSessionCacheSize();
	}

	@Override
	public final long sessionTimeout() {
		return (long)this.sessionContext().getSessionTimeout();
	}

	@Override
	public final SSLEngine newEngine(ByteBufAllocator alloc) {
		return this.configureAndWrapEngine(this.context().createSSLEngine(), alloc);
	}

	@Override
	public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
		return this.configureAndWrapEngine(this.context().createSSLEngine(peerHost, peerPort), alloc);
	}

	private SSLEngine configureAndWrapEngine(SSLEngine engine, ByteBufAllocator alloc) {
		engine.setEnabledCipherSuites(this.cipherSuites);
		engine.setEnabledProtocols(this.protocols);
		engine.setUseClientMode(this.isClient());
		if (this.isServer()) {
			switch (this.clientAuth) {
				case OPTIONAL:
					engine.setWantClientAuth(true);
					break;
				case REQUIRE:
					engine.setNeedClientAuth(true);
				case NONE:
					break;
				default:
					throw new Error("Unknown auth " + this.clientAuth);
			}
		}

		SslEngineWrapperFactory factory = this.apn.wrapperFactory();
		return factory instanceof AllocatorAwareSslEngineWrapperFactory
			? ((AllocatorAwareSslEngineWrapperFactory)factory).wrapSslEngine(engine, alloc, this.apn, this.isServer())
			: factory.wrapSslEngine(engine, this.apn, this.isServer());
	}

	public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
		return this.apn;
	}

	static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config, boolean isServer) {
		if (config == null) {
			return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
		} else {
			switch (config.protocol()) {
				case NONE:
					return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
				case ALPN:
					if (isServer) {
						switch (config.selectorFailureBehavior()) {
							case FATAL_ALERT:
								return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
							case NO_ADVERTISE:
								return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
							default:
								throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
						}
					} else {
						switch (config.selectedListenerFailureBehavior()) {
							case ACCEPT:
								return new JdkAlpnApplicationProtocolNegotiator(false, config.supportedProtocols());
							case FATAL_ALERT:
								return new JdkAlpnApplicationProtocolNegotiator(true, config.supportedProtocols());
							default:
								throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
						}
					}
				case NPN:
					if (isServer) {
						switch (config.selectedListenerFailureBehavior()) {
							case ACCEPT:
								return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
							case FATAL_ALERT:
								return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
							default:
								throw new UnsupportedOperationException("JDK provider does not support " + config.selectedListenerFailureBehavior() + " failure behavior");
						}
					} else {
						switch (config.selectorFailureBehavior()) {
							case FATAL_ALERT:
								return new JdkNpnApplicationProtocolNegotiator(true, config.supportedProtocols());
							case NO_ADVERTISE:
								return new JdkNpnApplicationProtocolNegotiator(false, config.supportedProtocols());
							default:
								throw new UnsupportedOperationException("JDK provider does not support " + config.selectorFailureBehavior() + " failure behavior");
						}
					}
				default:
					throw new UnsupportedOperationException("JDK provider does not support " + config.protocol() + " protocol");
			}
		}
	}

	@Deprecated
	protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, File keyFile, String keyPassword, KeyManagerFactory kmf) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
		String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = "SunX509";
		}

		return buildKeyManagerFactory(certChainFile, algorithm, keyFile, keyPassword, kmf);
	}

	@Deprecated
	protected static KeyManagerFactory buildKeyManagerFactory(File certChainFile, String keyAlgorithm, File keyFile, String keyPassword, KeyManagerFactory kmf) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
		return buildKeyManagerFactory(toX509Certificates(certChainFile), keyAlgorithm, toPrivateKey(keyFile, keyPassword), keyPassword, kmf);
	}

	static {
		SSLContext context;
		try {
			context = SSLContext.getInstance("TLS");
			context.init(null, null, null);
		} catch (Exception var11) {
			throw new Error("failed to initialize the default SSL context", var11);
		}

		SSLEngine engine = context.createSSLEngine();
		String[] supportedProtocols = engine.getSupportedProtocols();
		Set<String> supportedProtocolsSet = new HashSet(supportedProtocols.length);

		for (int i = 0; i < supportedProtocols.length; i++) {
			supportedProtocolsSet.add(supportedProtocols[i]);
		}

		List<String> protocols = new ArrayList();
		SslUtils.addIfSupported(supportedProtocolsSet, protocols, "TLSv1.2", "TLSv1.1", "TLSv1");
		if (!protocols.isEmpty()) {
			DEFAULT_PROTOCOLS = (String[])protocols.toArray(new String[protocols.size()]);
		} else {
			DEFAULT_PROTOCOLS = engine.getEnabledProtocols();
		}

		String[] supportedCiphers = engine.getSupportedCipherSuites();
		SUPPORTED_CIPHERS = new HashSet(supportedCiphers.length);

		for (int var12 = 0; var12 < supportedCiphers.length; var12++) {
			String supportedCipher = supportedCiphers[var12];
			SUPPORTED_CIPHERS.add(supportedCipher);
			if (supportedCipher.startsWith("SSL_")) {
				String tlsPrefixedCipherName = "TLS_" + supportedCipher.substring("SSL_".length());

				try {
					engine.setEnabledCipherSuites(new String[]{tlsPrefixedCipherName});
					SUPPORTED_CIPHERS.add(tlsPrefixedCipherName);
				} catch (IllegalArgumentException var10) {
				}
			}
		}

		List<String> ciphers = new ArrayList();
		SslUtils.addIfSupported(SUPPORTED_CIPHERS, ciphers, SslUtils.DEFAULT_CIPHER_SUITES);
		SslUtils.useFallbackCiphersIfDefaultIsEmpty(ciphers, engine.getEnabledCipherSuites());
		DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
		if (logger.isDebugEnabled()) {
			logger.debug("Default protocols (JDK): {} ", Arrays.asList(DEFAULT_PROTOCOLS));
			logger.debug("Default cipher suites (JDK): {}", DEFAULT_CIPHERS);
		}
	}
}
