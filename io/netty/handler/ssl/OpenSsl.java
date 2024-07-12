package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class OpenSsl {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);
	private static final Throwable UNAVAILABILITY_CAUSE;
	static final List<String> DEFAULT_CIPHERS;
	static final Set<String> AVAILABLE_CIPHER_SUITES;
	private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
	private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
	private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
	private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
	private static final boolean USE_KEYMANAGER_FACTORY;
	private static final boolean SUPPORTS_OCSP;
	static final Set<String> SUPPORTED_PROTOCOLS_SET;

	private static boolean doesSupportOcsp() {
		boolean supportsOcsp = false;
		if ((long)version() >= 268443648L) {
			long sslCtx = -1L;

			try {
				sslCtx = SSLContext.make(16, 1);
				SSLContext.enableOcsp(sslCtx, false);
				supportsOcsp = true;
			} catch (Exception var7) {
			} finally {
				if (sslCtx != -1L) {
					SSLContext.free(sslCtx);
				}
			}
		}

		return supportsOcsp;
	}

	private static boolean doesSupportProtocol(int protocol) {
		long sslCtx = -1L;

		boolean var4;
		try {
			sslCtx = SSLContext.make(protocol, 2);
			return true;
		} catch (Exception var8) {
			var4 = false;
		} finally {
			if (sslCtx != -1L) {
				SSLContext.free(sslCtx);
			}
		}

		return var4;
	}

	public static boolean isAvailable() {
		return UNAVAILABILITY_CAUSE == null;
	}

	public static boolean isAlpnSupported() {
		return (long)version() >= 268443648L;
	}

	public static boolean isOcspSupported() {
		return SUPPORTS_OCSP;
	}

	public static int version() {
		return isAvailable() ? SSL.version() : -1;
	}

	public static String versionString() {
		return isAvailable() ? SSL.versionString() : null;
	}

	public static void ensureAvailability() {
		if (UNAVAILABILITY_CAUSE != null) {
			throw (Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE);
		}
	}

	public static Throwable unavailabilityCause() {
		return UNAVAILABILITY_CAUSE;
	}

	@Deprecated
	public static Set<String> availableCipherSuites() {
		return availableOpenSslCipherSuites();
	}

	public static Set<String> availableOpenSslCipherSuites() {
		return AVAILABLE_OPENSSL_CIPHER_SUITES;
	}

	public static Set<String> availableJavaCipherSuites() {
		return AVAILABLE_JAVA_CIPHER_SUITES;
	}

	public static boolean isCipherSuiteAvailable(String cipherSuite) {
		String converted = CipherSuiteConverter.toOpenSsl(cipherSuite);
		if (converted != null) {
			cipherSuite = converted;
		}

		return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
	}

	public static boolean supportsKeyManagerFactory() {
		return SUPPORTS_KEYMANAGER_FACTORY;
	}

	public static boolean supportsHostnameValidation() {
		return SUPPORTS_HOSTNAME_VALIDATION;
	}

	static boolean useKeyManagerFactory() {
		return USE_KEYMANAGER_FACTORY;
	}

	static long memoryAddress(ByteBuf buf) {
		assert buf.isDirect();

		return buf.hasMemoryAddress() ? buf.memoryAddress() : Buffer.address(buf.nioBuffer());
	}

	private OpenSsl() {
	}

	private static void loadTcNative() throws Exception {
		String os = PlatformDependent.normalizedOs();
		String arch = PlatformDependent.normalizedArch();
		Set<String> libNames = new LinkedHashSet(4);
		String staticLibName = "netty_tcnative";
		libNames.add(staticLibName + "_" + os + '_' + arch);
		if ("linux".equalsIgnoreCase(os)) {
			libNames.add(staticLibName + "_" + os + '_' + arch + "_fedora");
		}

		libNames.add(staticLibName + "_" + arch);
		libNames.add(staticLibName);
		NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[])libNames.toArray(new String[libNames.size()]));
	}

	private static boolean initializeTcNative() throws Exception {
		return Library.initialize();
	}

	static void releaseIfNeeded(ReferenceCounted counted) {
		if (counted.refCnt() > 0) {
			ReferenceCountUtil.safeRelease(counted);
		}
	}

	static {
		Throwable cause = null;
		if (SystemPropertyUtil.getBoolean("io.netty.handler.ssl.noOpenSsl", false)) {
			cause = new UnsupportedOperationException("OpenSSL was explicit disabled with -Dio.netty.handler.ssl.noOpenSsl=true");
			logger.debug("netty-tcnative explicit disabled; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.", cause);
		} else {
			try {
				Class.forName("io.netty.internal.tcnative.SSL", false, OpenSsl.class.getClassLoader());
			} catch (ClassNotFoundException var41) {
				cause = var41;
				logger.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
			}

			if (cause == null) {
				try {
					loadTcNative();
				} catch (Throwable var40) {
					cause = var40;
					logger.debug(
						"Failed to load netty-tcnative; "
							+ OpenSslEngine.class.getSimpleName()
							+ " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information.",
						var40
					);
				}

				try {
					initializeTcNative();
					cause = null;
				} catch (Throwable var45) {
					if (cause == null) {
						cause = var45;
					}

					logger.debug(
						"Failed to initialize netty-tcnative; "
							+ OpenSslEngine.class.getSimpleName()
							+ " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information.",
						var45
					);
				}
			}
		}

		UNAVAILABILITY_CAUSE = cause;
		if (cause == null) {
			logger.debug("netty-tcnative using native library: {}", SSL.versionString());
			List<String> defaultCiphers = new ArrayList();
			Set<String> availableOpenSslCipherSuites = new LinkedHashSet(128);
			boolean supportsKeyManagerFactory = false;
			boolean useKeyManagerFactory = false;
			boolean supportsHostNameValidation = false;

			try {
				long sslCtx = SSLContext.make(31, 1);
				long certBio = 0L;
				SelfSignedCertificate cert = null;

				try {
					SSLContext.setCipherSuite(sslCtx, "ALL");
					long ssl = SSL.newSSL(sslCtx, true);

					try {
						for (String c : SSL.getCiphers(ssl)) {
							if (c != null && !c.isEmpty() && !availableOpenSslCipherSuites.contains(c)) {
								availableOpenSslCipherSuites.add(c);
							}
						}

						try {
							SSL.setHostNameValidation(ssl, 0, "netty.io");
							supportsHostNameValidation = true;
						} catch (Throwable var39) {
							logger.debug("Hostname Verification not supported.");
						}

						try {
							cert = new SelfSignedCertificate();
							certBio = ReferenceCountedOpenSslContext.toBIO(cert.cert());
							SSL.setCertificateChainBio(ssl, certBio, false);
							supportsKeyManagerFactory = true;

							try {
								useKeyManagerFactory = (Boolean)AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
									public Boolean run() {
										return SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true);
									}
								});
							} catch (Throwable var37) {
								logger.debug("Failed to get useKeyManagerFactory system property.");
							}
						} catch (Throwable var38) {
							logger.debug("KeyManagerFactory not supported.");
						}
					} finally {
						SSL.freeSSL(ssl);
						if (certBio != 0L) {
							SSL.freeBIO(certBio);
						}

						if (cert != null) {
							cert.delete();
						}
					}
				} finally {
					SSLContext.free(sslCtx);
				}
			} catch (Exception var44) {
				logger.warn("Failed to get the list of available OpenSSL cipher suites.", (Throwable)var44);
			}

			AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.unmodifiableSet(availableOpenSslCipherSuites);
			Set<String> availableJavaCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() * 2);

			for (String cipher : AVAILABLE_OPENSSL_CIPHER_SUITES) {
				availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "TLS"));
				availableJavaCipherSuites.add(CipherSuiteConverter.toJava(cipher, "SSL"));
			}

			SslUtils.addIfSupported(availableJavaCipherSuites, defaultCiphers, SslUtils.DEFAULT_CIPHER_SUITES);
			SslUtils.useFallbackCiphersIfDefaultIsEmpty(defaultCiphers, availableJavaCipherSuites);
			DEFAULT_CIPHERS = Collections.unmodifiableList(defaultCiphers);
			AVAILABLE_JAVA_CIPHER_SUITES = Collections.unmodifiableSet(availableJavaCipherSuites);
			Set<String> availableCipherSuites = new LinkedHashSet(AVAILABLE_OPENSSL_CIPHER_SUITES.size() + AVAILABLE_JAVA_CIPHER_SUITES.size());
			availableCipherSuites.addAll(AVAILABLE_OPENSSL_CIPHER_SUITES);
			availableCipherSuites.addAll(AVAILABLE_JAVA_CIPHER_SUITES);
			AVAILABLE_CIPHER_SUITES = availableCipherSuites;
			SUPPORTS_KEYMANAGER_FACTORY = supportsKeyManagerFactory;
			SUPPORTS_HOSTNAME_VALIDATION = supportsHostNameValidation;
			USE_KEYMANAGER_FACTORY = useKeyManagerFactory;
			Set<String> protocols = new LinkedHashSet(6);
			protocols.add("SSLv2Hello");
			if (doesSupportProtocol(1)) {
				protocols.add("SSLv2");
			}

			if (doesSupportProtocol(2)) {
				protocols.add("SSLv3");
			}

			if (doesSupportProtocol(4)) {
				protocols.add("TLSv1");
			}

			if (doesSupportProtocol(8)) {
				protocols.add("TLSv1.1");
			}

			if (doesSupportProtocol(16)) {
				protocols.add("TLSv1.2");
			}

			SUPPORTED_PROTOCOLS_SET = Collections.unmodifiableSet(protocols);
			SUPPORTS_OCSP = doesSupportOcsp();
			if (logger.isDebugEnabled()) {
				logger.debug("Supported protocols (OpenSSL): {} ", Arrays.asList(SUPPORTED_PROTOCOLS_SET));
				logger.debug("Default cipher suites (OpenSSL): {}", DEFAULT_CIPHERS);
			}
		} else {
			DEFAULT_CIPHERS = Collections.emptyList();
			AVAILABLE_OPENSSL_CIPHER_SUITES = Collections.emptySet();
			AVAILABLE_JAVA_CIPHER_SUITES = Collections.emptySet();
			AVAILABLE_CIPHER_SUITES = Collections.emptySet();
			SUPPORTS_KEYMANAGER_FACTORY = false;
			SUPPORTS_HOSTNAME_VALIDATION = false;
			USE_KEYMANAGER_FACTORY = false;
			SUPPORTED_PROTOCOLS_SET = Collections.emptySet();
			SUPPORTS_OCSP = false;
		}
	}
}