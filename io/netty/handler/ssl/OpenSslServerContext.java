package io.netty.handler.ssl;

import io.netty.handler.ssl.ReferenceCountedOpenSslServerContext.ServerContext;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

public final class OpenSslServerContext extends OpenSslContext {
	private final OpenSslServerSessionContext sessionContext;
	private final OpenSslKeyMaterialManager keyMaterialManager;

	@Deprecated
	public OpenSslServerContext(File certChainFile, File keyFile) throws SSLException {
		this(certChainFile, keyFile, null);
	}

	@Deprecated
	public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
		this(certChainFile, keyFile, keyPassword, null, IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0L, 0L);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout
	) throws SSLException {
		this(certChainFile, keyFile, keyPassword, ciphers, IdentityCipherSuiteFilter.INSTANCE, apn, sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout
	) throws SSLException {
		this(certChainFile, keyFile, keyPassword, ciphers, toApplicationProtocolConfig(nextProtocols), sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile,
		File keyFile,
		String keyPassword,
		TrustManagerFactory trustManagerFactory,
		Iterable<String> ciphers,
		ApplicationProtocolConfig config,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(certChainFile, keyFile, keyPassword, trustManagerFactory, ciphers, toNegotiator(config), sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile,
		File keyFile,
		String keyPassword,
		TrustManagerFactory trustManagerFactory,
		Iterable<String> ciphers,
		OpenSslApplicationProtocolNegotiator apn,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(null, trustManagerFactory, certChainFile, keyFile, keyPassword, null, ciphers, null, apn, sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile,
		File keyFile,
		String keyPassword,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		ApplicationProtocolConfig apn,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(null, null, certChainFile, keyFile, keyPassword, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File trustCertCollectionFile,
		TrustManagerFactory trustManagerFactory,
		File keyCertChainFile,
		File keyFile,
		String keyPassword,
		KeyManagerFactory keyManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		ApplicationProtocolConfig config,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(
			trustCertCollectionFile,
			trustManagerFactory,
			keyCertChainFile,
			keyFile,
			keyPassword,
			keyManagerFactory,
			ciphers,
			cipherFilter,
			toNegotiator(config),
			sessionCacheSize,
			sessionTimeout
		);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile,
		File keyFile,
		String keyPassword,
		TrustManagerFactory trustManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		ApplicationProtocolConfig config,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(null, trustManagerFactory, certChainFile, keyFile, keyPassword, null, ciphers, cipherFilter, toNegotiator(config), sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File certChainFile,
		File keyFile,
		String keyPassword,
		TrustManagerFactory trustManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		OpenSslApplicationProtocolNegotiator apn,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(null, trustManagerFactory, certChainFile, keyFile, keyPassword, null, ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout);
	}

	@Deprecated
	public OpenSslServerContext(
		File trustCertCollectionFile,
		TrustManagerFactory trustManagerFactory,
		File keyCertChainFile,
		File keyFile,
		String keyPassword,
		KeyManagerFactory keyManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		OpenSslApplicationProtocolNegotiator apn,
		long sessionCacheSize,
		long sessionTimeout
	) throws SSLException {
		this(
			toX509CertificatesInternal(trustCertCollectionFile),
			trustManagerFactory,
			toX509CertificatesInternal(keyCertChainFile),
			toPrivateKeyInternal(keyFile, keyPassword),
			keyPassword,
			keyManagerFactory,
			ciphers,
			cipherFilter,
			apn,
			sessionCacheSize,
			sessionTimeout,
			ClientAuth.NONE,
			null,
			false,
			false
		);
	}

	OpenSslServerContext(
		X509Certificate[] trustCertCollection,
		TrustManagerFactory trustManagerFactory,
		X509Certificate[] keyCertChain,
		PrivateKey key,
		String keyPassword,
		KeyManagerFactory keyManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		ApplicationProtocolConfig apn,
		long sessionCacheSize,
		long sessionTimeout,
		ClientAuth clientAuth,
		String[] protocols,
		boolean startTls,
		boolean enableOcsp
	) throws SSLException {
		this(
			trustCertCollection,
			trustManagerFactory,
			keyCertChain,
			key,
			keyPassword,
			keyManagerFactory,
			ciphers,
			cipherFilter,
			toNegotiator(apn),
			sessionCacheSize,
			sessionTimeout,
			clientAuth,
			protocols,
			startTls,
			enableOcsp
		);
	}

	private OpenSslServerContext(
		X509Certificate[] trustCertCollection,
		TrustManagerFactory trustManagerFactory,
		X509Certificate[] keyCertChain,
		PrivateKey key,
		String keyPassword,
		KeyManagerFactory keyManagerFactory,
		Iterable<String> ciphers,
		CipherSuiteFilter cipherFilter,
		OpenSslApplicationProtocolNegotiator apn,
		long sessionCacheSize,
		long sessionTimeout,
		ClientAuth clientAuth,
		String[] protocols,
		boolean startTls,
		boolean enableOcsp
	) throws SSLException {
		super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, 1, keyCertChain, clientAuth, protocols, startTls, enableOcsp);
		boolean success = false;

		try {
			ServerContext context = ReferenceCountedOpenSslServerContext.newSessionContext(
				this, this.ctx, this.engineMap, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory
			);
			this.sessionContext = context.sessionContext;
			this.keyMaterialManager = context.keyMaterialManager;
			success = true;
		} finally {
			if (!success) {
				this.release();
			}
		}
	}

	public OpenSslServerSessionContext sessionContext() {
		return this.sessionContext;
	}

	@Override
	OpenSslKeyMaterialManager keyMaterialManager() {
		return this.keyMaterialManager;
	}
}
