package io.netty.resolver;

import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class HostsFileParser {
	private static final String WINDOWS_DEFAULT_SYSTEM_ROOT = "C:\\Windows";
	private static final String WINDOWS_HOSTS_FILE_RELATIVE_PATH = "\\system32\\drivers\\etc\\hosts";
	private static final String X_PLATFORMS_HOSTS_FILE_PATH = "/etc/hosts";
	private static final Pattern WHITESPACES = Pattern.compile("[ \t]+");
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(HostsFileParser.class);

	private static File locateHostsFile() {
		File hostsFile;
		if (PlatformDependent.isWindows()) {
			hostsFile = new File(System.getenv("SystemRoot") + "\\system32\\drivers\\etc\\hosts");
			if (!hostsFile.exists()) {
				hostsFile = new File("C:\\Windows\\system32\\drivers\\etc\\hosts");
			}
		} else {
			hostsFile = new File("/etc/hosts");
		}

		return hostsFile;
	}

	public static HostsFileEntries parseSilently() {
		File hostsFile = locateHostsFile();

		try {
			return parse(hostsFile);
		} catch (IOException var2) {
			logger.warn("Failed to load and parse hosts file at " + hostsFile.getPath(), (Throwable)var2);
			return HostsFileEntries.EMPTY;
		}
	}

	public static HostsFileEntries parse() throws IOException {
		return parse(locateHostsFile());
	}

	public static HostsFileEntries parse(File file) throws IOException {
		ObjectUtil.checkNotNull(file, "file");
		return file.exists() && file.isFile() ? parse(new BufferedReader(new FileReader(file))) : HostsFileEntries.EMPTY;
	}

	public static HostsFileEntries parse(Reader reader) throws IOException {
		ObjectUtil.checkNotNull(reader, "reader");
		BufferedReader buff = new BufferedReader(reader);

		HostsFileEntries var21;
		try {
			Map<String, Inet4Address> ipv4Entries = new HashMap();
			Map<String, Inet6Address> ipv6Entries = new HashMap();

			String line;
			while ((line = buff.readLine()) != null) {
				int commentPosition = line.indexOf(35);
				if (commentPosition != -1) {
					line = line.substring(0, commentPosition);
				}

				line = line.trim();
				if (!line.isEmpty()) {
					List<String> lineParts = new ArrayList();

					for (String s : WHITESPACES.split(line)) {
						if (!s.isEmpty()) {
							lineParts.add(s);
						}
					}

					if (lineParts.size() >= 2) {
						byte[] ipBytes = NetUtil.createByteArrayFromIpAddressString((String)lineParts.get(0));
						if (ipBytes != null) {
							for (int i = 1; i < lineParts.size(); i++) {
								String hostname = (String)lineParts.get(i);
								String hostnameLower = hostname.toLowerCase(Locale.ENGLISH);
								InetAddress address = InetAddress.getByAddress(hostname, ipBytes);
								if (address instanceof Inet4Address) {
									Inet4Address previous = (Inet4Address)ipv4Entries.put(hostnameLower, (Inet4Address)address);
									if (previous != null) {
										ipv4Entries.put(hostnameLower, previous);
									}
								} else {
									Inet6Address previous = (Inet6Address)ipv6Entries.put(hostnameLower, (Inet6Address)address);
									if (previous != null) {
										ipv6Entries.put(hostnameLower, previous);
									}
								}
							}
						}
					}
				}
			}

			var21 = ipv4Entries.isEmpty() && ipv6Entries.isEmpty() ? HostsFileEntries.EMPTY : new HostsFileEntries(ipv4Entries, ipv6Entries);
		} finally {
			try {
				buff.close();
			} catch (IOException var18) {
				logger.warn("Failed to close a reader", (Throwable)var18);
			}
		}

		return var21;
	}

	private HostsFileParser() {
	}
}
