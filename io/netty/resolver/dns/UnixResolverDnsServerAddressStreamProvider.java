package io.netty.resolver.dns;

import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UnixResolverDnsServerAddressStreamProvider implements DnsServerAddressStreamProvider {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnixResolverDnsServerAddressStreamProvider.class);
	private static final String ETC_RESOLV_CONF_FILE = "/etc/resolv.conf";
	private static final String ETC_RESOLVER_DIR = "/etc/resolver";
	private static final String NAMESERVER_ROW_LABEL = "nameserver";
	private static final String SORTLIST_ROW_LABEL = "sortlist";
	private static final String OPTIONS_ROW_LABEL = "options";
	private static final String DOMAIN_ROW_LABEL = "domain";
	private static final String PORT_ROW_LABEL = "port";
	private static final String NDOTS_LABEL = "ndots:";
	static final int DEFAULT_NDOTS = 1;
	private final DnsServerAddresses defaultNameServerAddresses;
	private final Map<String, DnsServerAddresses> domainToNameServerStreamMap;

	static DnsServerAddressStreamProvider parseSilently() {
		try {
			UnixResolverDnsServerAddressStreamProvider nameServerCache = new UnixResolverDnsServerAddressStreamProvider("/etc/resolv.conf", "/etc/resolver");
			return (DnsServerAddressStreamProvider)(nameServerCache.mayOverrideNameServers() ? nameServerCache : DefaultDnsServerAddressStreamProvider.INSTANCE);
		} catch (Exception var1) {
			logger.debug("failed to parse {} and/or {}", "/etc/resolv.conf", "/etc/resolver", var1);
			return DefaultDnsServerAddressStreamProvider.INSTANCE;
		}
	}

	public UnixResolverDnsServerAddressStreamProvider(File etcResolvConf, File... etcResolverFiles) throws IOException {
		Map<String, DnsServerAddresses> etcResolvConfMap = parse(ObjectUtil.checkNotNull(etcResolvConf, "etcResolvConf"));
		boolean useEtcResolverFiles = etcResolverFiles != null && etcResolverFiles.length != 0;
		this.domainToNameServerStreamMap = useEtcResolverFiles ? parse(etcResolverFiles) : etcResolvConfMap;
		DnsServerAddresses defaultNameServerAddresses = (DnsServerAddresses)etcResolvConfMap.get(etcResolvConf.getName());
		if (defaultNameServerAddresses == null) {
			Collection<DnsServerAddresses> values = etcResolvConfMap.values();
			if (values.isEmpty()) {
				throw new IllegalArgumentException(etcResolvConf + " didn't provide any name servers");
			}

			this.defaultNameServerAddresses = (DnsServerAddresses)values.iterator().next();
		} else {
			this.defaultNameServerAddresses = defaultNameServerAddresses;
		}

		if (useEtcResolverFiles) {
			this.domainToNameServerStreamMap.putAll(etcResolvConfMap);
		}
	}

	public UnixResolverDnsServerAddressStreamProvider(String etcResolvConf, String etcResolverDir) throws IOException {
		this(etcResolvConf == null ? null : new File(etcResolvConf), etcResolverDir == null ? null : new File(etcResolverDir).listFiles());
	}

	@Override
	public DnsServerAddressStream nameServerAddressStream(String hostname) {
		while (true) {
			int i = hostname.indexOf(46, 1);
			if (i < 0 || i == hostname.length() - 1) {
				return this.defaultNameServerAddresses.stream();
			}

			DnsServerAddresses addresses = (DnsServerAddresses)this.domainToNameServerStreamMap.get(hostname);
			if (addresses != null) {
				return addresses.stream();
			}

			hostname = hostname.substring(i + 1);
		}
	}

	private boolean mayOverrideNameServers() {
		return !this.domainToNameServerStreamMap.isEmpty() || this.defaultNameServerAddresses.stream().next() != null;
	}

	private static Map<String, DnsServerAddresses> parse(File... etcResolverFiles) throws IOException {
		Map<String, DnsServerAddresses> domainToNameServerStreamMap = new HashMap(etcResolverFiles.length << 1);

		for (File etcResolverFile : etcResolverFiles) {
			if (etcResolverFile.isFile()) {
				FileReader fr = new FileReader(etcResolverFile);
				BufferedReader br = null;

				try {
					br = new BufferedReader(fr);
					List<InetSocketAddress> addresses = new ArrayList(2);
					String domainName = etcResolverFile.getName();
					int port = 53;

					String line;
					while ((line = br.readLine()) != null) {
						line = line.trim();
						char c;
						if (!line.isEmpty() && (c = line.charAt(0)) != '#' && c != ';') {
							if (line.startsWith("nameserver")) {
								int i = StringUtil.indexOfNonWhiteSpace(line, "nameserver".length());
								if (i < 0) {
									throw new IllegalArgumentException("error parsing label nameserver in file " + etcResolverFile + ". value: " + line);
								}

								String maybeIP = line.substring(i);
								if (!NetUtil.isValidIpV4Address(maybeIP) && !NetUtil.isValidIpV6Address(maybeIP)) {
									i = maybeIP.lastIndexOf(46);
									if (i + 1 >= maybeIP.length()) {
										throw new IllegalArgumentException("error parsing label nameserver in file " + etcResolverFile + ". invalid IP value: " + line);
									}

									port = Integer.parseInt(maybeIP.substring(i + 1));
									maybeIP = maybeIP.substring(0, i);
								}

								addresses.add(SocketUtils.socketAddress(maybeIP, port));
							} else if (line.startsWith("domain")) {
								int ix = StringUtil.indexOfNonWhiteSpace(line, "domain".length());
								if (ix < 0) {
									throw new IllegalArgumentException("error parsing label domain in file " + etcResolverFile + " value: " + line);
								}

								domainName = line.substring(ix);
								if (!addresses.isEmpty()) {
									putIfAbsent(domainToNameServerStreamMap, domainName, addresses);
								}

								addresses = new ArrayList(2);
							} else if (line.startsWith("port")) {
								int ixx = StringUtil.indexOfNonWhiteSpace(line, "port".length());
								if (ixx < 0) {
									throw new IllegalArgumentException("error parsing label port in file " + etcResolverFile + " value: " + line);
								}

								port = Integer.parseInt(line.substring(ixx));
							} else if (line.startsWith("sortlist")) {
								logger.info("row type {} not supported. ignoring line: {}", "sortlist", line);
							}
						}
					}

					if (!addresses.isEmpty()) {
						putIfAbsent(domainToNameServerStreamMap, domainName, addresses);
					}
				} finally {
					if (br == null) {
						fr.close();
					} else {
						br.close();
					}
				}
			}
		}

		return domainToNameServerStreamMap;
	}

	private static void putIfAbsent(Map<String, DnsServerAddresses> domainToNameServerStreamMap, String domainName, List<InetSocketAddress> addresses) {
		putIfAbsent(domainToNameServerStreamMap, domainName, DnsServerAddresses.sequential(addresses));
	}

	private static void putIfAbsent(Map<String, DnsServerAddresses> domainToNameServerStreamMap, String domainName, DnsServerAddresses addresses) {
		DnsServerAddresses existingAddresses = (DnsServerAddresses)domainToNameServerStreamMap.put(domainName, addresses);
		if (existingAddresses != null) {
			domainToNameServerStreamMap.put(domainName, existingAddresses);
			logger.debug("Domain name {} already maps to addresses {} so new addresses {} will be discarded", domainName, existingAddresses, addresses);
		}
	}

	static int parseEtcResolverFirstNdots() throws IOException {
		return parseEtcResolverFirstNdots(new File("/etc/resolv.conf"));
	}

	static int parseEtcResolverFirstNdots(File etcResolvConf) throws IOException {
		FileReader fr = new FileReader(etcResolvConf);
		BufferedReader br = null;

		int var6;
		try {
			br = new BufferedReader(fr);

			String line;
			do {
				if ((line = br.readLine()) == null) {
					return 1;
				}
			} while (!line.startsWith("options"));

			int i = line.indexOf("ndots:");
			if (i < 0) {
				return 1;
			}

			i += "ndots:".length();
			int j = line.indexOf(32, i);
			var6 = Integer.parseInt(line.substring(i, j < 0 ? line.length() : j));
		} finally {
			if (br == null) {
				fr.close();
			} else {
				br.close();
			}
		}

		return var6;
	}
}
