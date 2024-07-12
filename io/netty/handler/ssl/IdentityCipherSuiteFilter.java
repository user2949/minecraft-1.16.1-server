package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
	public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter(true);
	public static final IdentityCipherSuiteFilter INSTANCE_DEFAULTING_TO_SUPPORTED_CIPHERS = new IdentityCipherSuiteFilter(false);
	private final boolean defaultToDefaultCiphers;

	private IdentityCipherSuiteFilter(boolean defaultToDefaultCiphers) {
		this.defaultToDefaultCiphers = defaultToDefaultCiphers;
	}

	@Override
	public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
		if (ciphers == null) {
			return this.defaultToDefaultCiphers
				? (String[])defaultCiphers.toArray(new String[defaultCiphers.size()])
				: (String[])supportedCiphers.toArray(new String[supportedCiphers.size()]);
		} else {
			List<String> newCiphers = new ArrayList(supportedCiphers.size());

			for (String c : ciphers) {
				if (c == null) {
					break;
				}

				newCiphers.add(c);
			}

			return (String[])newCiphers.toArray(new String[newCiphers.size()]);
		}
	}
}
