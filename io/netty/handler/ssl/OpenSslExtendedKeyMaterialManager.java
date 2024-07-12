package io.netty.handler.ssl;

import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

final class OpenSslExtendedKeyMaterialManager extends OpenSslKeyMaterialManager {
	private final X509ExtendedKeyManager keyManager;

	OpenSslExtendedKeyMaterialManager(X509ExtendedKeyManager keyManager, String password) {
		super(keyManager, password);
		this.keyManager = keyManager;
	}

	@Override
	protected String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
		return this.keyManager.chooseEngineClientAlias(keyTypes, issuer, engine);
	}

	@Override
	protected String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type) {
		return this.keyManager.chooseEngineServerAlias(type, null, engine);
	}
}
