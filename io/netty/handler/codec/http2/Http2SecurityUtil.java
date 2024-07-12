package io.netty.handler.codec.http2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Http2SecurityUtil {
	public static final List<String> CIPHERS = Collections.unmodifiableList(new ArrayList(Http2SecurityUtil.CIPHERS_JAVA_MOZILLA_MODERN_SECURITY));
	private static final List<String> CIPHERS_JAVA_MOZILLA_MODERN_SECURITY = Collections.unmodifiableList(
		Arrays.asList(
			"TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
			"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
			"TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256",
			"TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256",
			"TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
			"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"
		)
	);

	private Http2SecurityUtil() {
	}
}
