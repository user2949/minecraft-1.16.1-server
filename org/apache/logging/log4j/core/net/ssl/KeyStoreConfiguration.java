package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "KeyStore",
	category = "Core",
	printObject = true
)
public class KeyStoreConfiguration extends AbstractKeyStoreConfiguration {
	private final String keyManagerFactoryAlgorithm;

	public KeyStoreConfiguration(String location, String password, String keyStoreType, String keyManagerFactoryAlgorithm) throws StoreConfigurationException {
		super(location, password, keyStoreType);
		this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm == null ? KeyManagerFactory.getDefaultAlgorithm() : keyManagerFactoryAlgorithm;
	}

	@PluginFactory
	public static KeyStoreConfiguration createKeyStoreConfiguration(
		@PluginAttribute("location") String location,
		@PluginAttribute(value = "password",sensitive = true) String password,
		@PluginAttribute("type") String keyStoreType,
		@PluginAttribute("keyManagerFactoryAlgorithm") String keyManagerFactoryAlgorithm
	) throws StoreConfigurationException {
		return new KeyStoreConfiguration(location, password, keyStoreType, keyManagerFactoryAlgorithm);
	}

	public KeyManagerFactory initKeyManagerFactory() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
		KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(this.keyManagerFactoryAlgorithm);
		kmFactory.init(this.getKeyStore(), this.getPasswordAsCharArray());
		return kmFactory;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		return 31 * result + (this.keyManagerFactoryAlgorithm == null ? 0 : this.keyManagerFactoryAlgorithm.hashCode());
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
			KeyStoreConfiguration other = (KeyStoreConfiguration)obj;
			if (this.keyManagerFactoryAlgorithm == null) {
				if (other.keyManagerFactoryAlgorithm != null) {
					return false;
				}
			} else if (!this.keyManagerFactoryAlgorithm.equals(other.keyManagerFactoryAlgorithm)) {
				return false;
			}

			return true;
		}
	}
}
