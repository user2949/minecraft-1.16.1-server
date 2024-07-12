package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(
	name = "Ssl",
	category = "Core",
	printObject = true
)
public class SslConfiguration {
	private static final StatusLogger LOGGER = StatusLogger.getLogger();
	private final KeyStoreConfiguration keyStoreConfig;
	private final TrustStoreConfiguration trustStoreConfig;
	private final SSLContext sslContext;
	private final String protocol;

	private SslConfiguration(String protocol, KeyStoreConfiguration keyStoreConfig, TrustStoreConfiguration trustStoreConfig) {
		this.keyStoreConfig = keyStoreConfig;
		this.trustStoreConfig = trustStoreConfig;
		this.protocol = protocol == null ? "SSL" : protocol;
		this.sslContext = this.createSslContext();
	}

	public SSLSocketFactory getSslSocketFactory() {
		return this.sslContext.getSocketFactory();
	}

	public SSLServerSocketFactory getSslServerSocketFactory() {
		return this.sslContext.getServerSocketFactory();
	}

	private SSLContext createSslContext() {
		SSLContext context = null;

		try {
			context = this.createSslContextBasedOnConfiguration();
			LOGGER.debug("Creating SSLContext with the given parameters");
		} catch (TrustStoreConfigurationException var3) {
			context = this.createSslContextWithTrustStoreFailure();
		} catch (KeyStoreConfigurationException var4) {
			context = this.createSslContextWithKeyStoreFailure();
		}

		return context;
	}

	private SSLContext createSslContextWithTrustStoreFailure() {
		SSLContext context;
		try {
			context = this.createSslContextWithDefaultTrustManagerFactory();
			LOGGER.debug("Creating SSLContext with default truststore");
		} catch (KeyStoreConfigurationException var3) {
			context = this.createDefaultSslContext();
			LOGGER.debug("Creating SSLContext with default configuration");
		}

		return context;
	}

	private SSLContext createSslContextWithKeyStoreFailure() {
		SSLContext context;
		try {
			context = this.createSslContextWithDefaultKeyManagerFactory();
			LOGGER.debug("Creating SSLContext with default keystore");
		} catch (TrustStoreConfigurationException var3) {
			context = this.createDefaultSslContext();
			LOGGER.debug("Creating SSLContext with default configuration");
		}

		return context;
	}

	private SSLContext createSslContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
		return this.createSslContext(false, false);
	}

	private SSLContext createSslContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
		try {
			return this.createSslContext(true, false);
		} catch (KeyStoreConfigurationException var2) {
			LOGGER.debug("Exception occured while using default keystore. This should be a BUG");
			return null;
		}
	}

	private SSLContext createSslContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException {
		try {
			return this.createSslContext(false, true);
		} catch (TrustStoreConfigurationException var2) {
			LOGGER.debug("Exception occured while using default truststore. This should be a BUG");
			return null;
		}
	}

	private SSLContext createDefaultSslContext() {
		try {
			return SSLContext.getDefault();
		} catch (NoSuchAlgorithmException var2) {
			LOGGER.error("Failed to create an SSLContext with default configuration", var2);
			return null;
		}
	}

	private SSLContext createSslContext(boolean loadDefaultKeyManagerFactory, boolean loadDefaultTrustManagerFactory) throws KeyStoreConfigurationException, TrustStoreConfigurationException {
		try {
			KeyManager[] kManagers = null;
			TrustManager[] tManagers = null;
			SSLContext newSslContext = SSLContext.getInstance(this.protocol);
			if (!loadDefaultKeyManagerFactory) {
				KeyManagerFactory kmFactory = this.loadKeyManagerFactory();
				kManagers = kmFactory.getKeyManagers();
			}

			if (!loadDefaultTrustManagerFactory) {
				TrustManagerFactory tmFactory = this.loadTrustManagerFactory();
				tManagers = tmFactory.getTrustManagers();
			}

			newSslContext.init(kManagers, tManagers, null);
			return newSslContext;
		} catch (NoSuchAlgorithmException var7) {
			LOGGER.error("No Provider supports a TrustManagerFactorySpi implementation for the specified protocol", var7);
			throw new TrustStoreConfigurationException(var7);
		} catch (KeyManagementException var8) {
			LOGGER.error("Failed to initialize the SSLContext", var8);
			throw new KeyStoreConfigurationException(var8);
		}
	}

	private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
		if (this.trustStoreConfig == null) {
			throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
		} else {
			try {
				return this.trustStoreConfig.initTrustManagerFactory();
			} catch (NoSuchAlgorithmException var2) {
				LOGGER.error("The specified algorithm is not available from the specified provider", var2);
				throw new TrustStoreConfigurationException(var2);
			} catch (KeyStoreException var3) {
				LOGGER.error("Failed to initialize the TrustManagerFactory", var3);
				throw new TrustStoreConfigurationException(var3);
			}
		}
	}

	private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
		if (this.keyStoreConfig == null) {
			throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
		} else {
			try {
				return this.keyStoreConfig.initKeyManagerFactory();
			} catch (NoSuchAlgorithmException var2) {
				LOGGER.error("The specified algorithm is not available from the specified provider", var2);
				throw new KeyStoreConfigurationException(var2);
			} catch (KeyStoreException var3) {
				LOGGER.error("Failed to initialize the TrustManagerFactory", var3);
				throw new KeyStoreConfigurationException(var3);
			} catch (UnrecoverableKeyException var4) {
				LOGGER.error("The key cannot be recovered (e.g. the given password is wrong)", var4);
				throw new KeyStoreConfigurationException(var4);
			}
		}
	}

	@PluginFactory
	public static SslConfiguration createSSLConfiguration(
		@PluginAttribute("protocol") String protocol,
		@PluginElement("KeyStore") KeyStoreConfiguration keyStoreConfig,
		@PluginElement("TrustStore") TrustStoreConfiguration trustStoreConfig
	) {
		return new SslConfiguration(protocol, keyStoreConfig, trustStoreConfig);
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.keyStoreConfig == null ? 0 : this.keyStoreConfig.hashCode());
		result = 31 * result + (this.protocol == null ? 0 : this.protocol.hashCode());
		result = 31 * result + (this.sslContext == null ? 0 : this.sslContext.hashCode());
		return 31 * result + (this.trustStoreConfig == null ? 0 : this.trustStoreConfig.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			SslConfiguration other = (SslConfiguration)obj;
			if (this.keyStoreConfig == null) {
				if (other.keyStoreConfig != null) {
					return false;
				}
			} else if (!this.keyStoreConfig.equals(other.keyStoreConfig)) {
				return false;
			}

			if (this.protocol == null) {
				if (other.protocol != null) {
					return false;
				}
			} else if (!this.protocol.equals(other.protocol)) {
				return false;
			}

			if (this.sslContext == null) {
				if (other.sslContext != null) {
					return false;
				}
			} else if (!this.sslContext.equals(other.sslContext)) {
				return false;
			}

			if (this.trustStoreConfig == null) {
				if (other.trustStoreConfig != null) {
					return false;
				}
			} else if (!this.trustStoreConfig.equals(other.trustStoreConfig)) {
				return false;
			}

			return true;
		}
	}
}
