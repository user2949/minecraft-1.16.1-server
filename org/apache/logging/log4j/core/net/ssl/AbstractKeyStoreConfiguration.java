package org.apache.logging.log4j.core.net.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class AbstractKeyStoreConfiguration extends StoreConfiguration<KeyStore> {
	private final KeyStore keyStore;
	private final String keyStoreType;

	public AbstractKeyStoreConfiguration(String location, String password, String keyStoreType) throws StoreConfigurationException {
		super(location, password);
		this.keyStoreType = keyStoreType == null ? "JKS" : keyStoreType;
		this.keyStore = this.load();
	}

	protected KeyStore load() throws StoreConfigurationException {
		LOGGER.debug("Loading keystore from file with params(location={})", this.getLocation());

		try {
			if (this.getLocation() == null) {
				throw new IOException("The location is null");
			} else {
				FileInputStream fin = new FileInputStream(this.getLocation());
				Throwable var2 = null;

				KeyStore var4;
				try {
					KeyStore ks = KeyStore.getInstance(this.keyStoreType);
					ks.load(fin, this.getPasswordAsCharArray());
					LOGGER.debug("Keystore successfully loaded with params(location={})", this.getLocation());
					var4 = ks;
				} catch (Throwable var18) {
					var2 = var18;
					throw var18;
				} finally {
					if (fin != null) {
						if (var2 != null) {
							try {
								fin.close();
							} catch (Throwable var17) {
								var2.addSuppressed(var17);
							}
						} else {
							fin.close();
						}
					}
				}

				return var4;
			}
		} catch (CertificateException var20) {
			LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type" + this.keyStoreType, var20);
			throw new StoreConfigurationException(var20);
		} catch (NoSuchAlgorithmException var21) {
			LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found", var21);
			throw new StoreConfigurationException(var21);
		} catch (KeyStoreException var22) {
			LOGGER.error(var22);
			throw new StoreConfigurationException(var22);
		} catch (FileNotFoundException var23) {
			LOGGER.error("The keystore file(" + this.getLocation() + ") is not found", var23);
			throw new StoreConfigurationException(var23);
		} catch (IOException var24) {
			LOGGER.error("Something is wrong with the format of the keystore or the given password", var24);
			throw new StoreConfigurationException(var24);
		}
	}

	public KeyStore getKeyStore() {
		return this.keyStore;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (this.keyStore == null ? 0 : this.keyStore.hashCode());
		return 31 * result + (this.keyStoreType == null ? 0 : this.keyStoreType.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equals(obj)) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			AbstractKeyStoreConfiguration other = (AbstractKeyStoreConfiguration)obj;
			if (this.keyStore == null) {
				if (other.keyStore != null) {
					return false;
				}
			} else if (!this.keyStore.equals(other.keyStore)) {
				return false;
			}

			if (this.keyStoreType == null) {
				if (other.keyStoreType != null) {
					return false;
				}
			} else if (!this.keyStoreType.equals(other.keyStoreType)) {
				return false;
			}

			return true;
		}
	}
}
