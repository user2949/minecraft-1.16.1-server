package io.netty.handler.ssl;

interface OpenSslEngineMap {
	ReferenceCountedOpenSslEngine remove(long long1);

	void add(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine);

	ReferenceCountedOpenSslEngine get(long long1);
}
