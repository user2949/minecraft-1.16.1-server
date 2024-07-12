package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.TrustManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "TrustStore",
	category = "Core",
	printObject = true
)
public class TrustStoreConfiguration extends AbstractKeyStoreConfiguration {
	private final String trustManagerFactoryAlgorithm;

	public TrustStoreConfiguration(String location, String password, String keyStoreType, String trustManagerFactoryAlgorithm) throws StoreConfigurationException {
		super(location, password, keyStoreType);
		this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm == null ? TrustManagerFactory.getDefaultAlgorithm() : trustManagerFactoryAlgorithm;
	}

	@PluginFactory
	public static TrustStoreConfiguration createKeyStoreConfiguration(
		@PluginAttribute("location") String location,
		@PluginAttribute(value = "password",sensitive = true) String password,
		@PluginAttribute("type") String keyStoreType,
		@PluginAttribute("trustManagerFactoryAlgorithm") String trustManagerFactoryAlgorithm
	) throws StoreConfigurationException {
		return new TrustStoreConfiguration(location, password, keyStoreType, trustManagerFactoryAlgorithm);
	}

	public TrustManagerFactory initTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException {
		TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(this.trustManagerFactoryAlgorithm);
		tmFactory.init(this.getKeyStore());
		return tmFactory;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		return 31 * result + (this.trustManagerFactoryAlgorithm == null ? 0 : this.trustManagerFactoryAlgorithm.hashCode());
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
			TrustStoreConfiguration other = (TrustStoreConfiguration)obj;
			if (this.trustManagerFactoryAlgorithm == null) {
				if (other.trustManagerFactoryAlgorithm != null) {
					return false;
				}
			} else if (!this.trustManagerFactoryAlgorithm.equals(other.trustManagerFactoryAlgorithm)) {
				return false;
			}

			return true;
		}
	}
}
