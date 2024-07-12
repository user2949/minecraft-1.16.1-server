package org.apache.logging.log4j.core.util;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class NetUtils {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final String UNKNOWN_LOCALHOST = "UNKNOWN_LOCALHOST";

	private NetUtils() {
	}

	public static String getLocalHostname() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (UnknownHostException var7) {
			try {
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

				while (interfaces.hasMoreElements()) {
					NetworkInterface nic = (NetworkInterface)interfaces.nextElement();
					Enumeration<InetAddress> addresses = nic.getInetAddresses();

					while (addresses.hasMoreElements()) {
						InetAddress address = (InetAddress)addresses.nextElement();
						if (!address.isLoopbackAddress()) {
							String hostname = address.getHostName();
							if (hostname != null) {
								return hostname;
							}
						}
					}
				}
			} catch (SocketException var6) {
				LOGGER.error("Could not determine local host name", (Throwable)var7);
				return "UNKNOWN_LOCALHOST";
			}

			LOGGER.error("Could not determine local host name", (Throwable)var7);
			return "UNKNOWN_LOCALHOST";
		}
	}

	public static URI toURI(String path) {
		try {
			return new URI(path);
		} catch (URISyntaxException var4) {
			try {
				URL url = new URL(path);
				return new URI(url.getProtocol(), url.getHost(), url.getPath(), null);
			} catch (URISyntaxException | MalformedURLException var3) {
				return new File(path).toURI();
			}
		}
	}
}
