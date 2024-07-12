package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;

final class Java8SslUtils {
	private Java8SslUtils() {
	}

	static List<String> getSniHostNames(SSLParameters sslParameters) {
		List<SNIServerName> names = sslParameters.getServerNames();
		if (names != null && !names.isEmpty()) {
			List<String> strings = new ArrayList(names.size());

			for (SNIServerName serverName : names) {
				if (!(serverName instanceof SNIHostName)) {
					throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + serverName);
				}

				strings.add(((SNIHostName)serverName).getAsciiName());
			}

			return strings;
		} else {
			return Collections.emptyList();
		}
	}

	static void setSniHostNames(SSLParameters sslParameters, List<String> names) {
		List<SNIServerName> sniServerNames = new ArrayList(names.size());

		for (String name : names) {
			sniServerNames.add(new SNIHostName(name));
		}

		sslParameters.setServerNames(sniServerNames);
	}

	static boolean getUseCipherSuitesOrder(SSLParameters sslParameters) {
		return sslParameters.getUseCipherSuitesOrder();
	}

	static void setUseCipherSuitesOrder(SSLParameters sslParameters, boolean useOrder) {
		sslParameters.setUseCipherSuitesOrder(useOrder);
	}

	static void setSNIMatchers(SSLParameters sslParameters, Collection<?> matchers) {
		sslParameters.setSNIMatchers(matchers);
	}

	static boolean checkSniHostnameMatch(Collection<?> matchers, String hostname) {
		if (matchers != null && !matchers.isEmpty()) {
			SNIHostName name = new SNIHostName(hostname);

			for (SNIMatcher matcher : matchers) {
				if (matcher.getType() == 0 && matcher.matches(name)) {
					return true;
				}
			}

			return false;
		} else {
			return true;
		}
	}
}
