package io.netty.resolver.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DatagramDnsResponseDecoder;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.InetNameResolver;
import io.netty.resolver.ResolvedAddressTypes;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DnsNameResolver extends InetNameResolver {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsNameResolver.class);
	private static final String LOCALHOST = "localhost";
	private static final InetAddress LOCALHOST_ADDRESS;
	private static final DnsRecord[] EMPTY_ADDITIONALS = new DnsRecord[0];
	private static final DnsRecordType[] IPV4_ONLY_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.A};
	private static final InternetProtocolFamily[] IPV4_ONLY_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv4};
	private static final DnsRecordType[] IPV4_PREFERRED_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.A, DnsRecordType.AAAA};
	private static final InternetProtocolFamily[] IPV4_PREFERRED_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{
		InternetProtocolFamily.IPv4, InternetProtocolFamily.IPv6
	};
	private static final DnsRecordType[] IPV6_ONLY_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.AAAA};
	private static final InternetProtocolFamily[] IPV6_ONLY_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv6};
	private static final DnsRecordType[] IPV6_PREFERRED_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.AAAA, DnsRecordType.A};
	private static final InternetProtocolFamily[] IPV6_PREFERRED_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{
		InternetProtocolFamily.IPv6, InternetProtocolFamily.IPv4
	};
	static final ResolvedAddressTypes DEFAULT_RESOLVE_ADDRESS_TYPES;
	static final String[] DEFAULT_SEARCH_DOMAINS;
	private static final int DEFAULT_NDOTS;
	private static final DatagramDnsResponseDecoder DECODER;
	private static final DatagramDnsQueryEncoder ENCODER;
	final Future<Channel> channelFuture;
	final DatagramChannel ch;
	final DnsQueryContextManager queryContextManager = new DnsQueryContextManager();
	private final DnsCache resolveCache;
	private final DnsCache authoritativeDnsServerCache;
	private final FastThreadLocal<DnsServerAddressStream> nameServerAddrStream = new FastThreadLocal<DnsServerAddressStream>() {
		protected DnsServerAddressStream initialValue() throws Exception {
			return DnsNameResolver.this.dnsServerAddressStreamProvider.nameServerAddressStream("");
		}
	};
	private final long queryTimeoutMillis;
	private final int maxQueriesPerResolve;
	private final ResolvedAddressTypes resolvedAddressTypes;
	private final InternetProtocolFamily[] resolvedInternetProtocolFamilies;
	private final boolean recursionDesired;
	private final int maxPayloadSize;
	private final boolean optResourceEnabled;
	private final HostsFileEntriesResolver hostsFileEntriesResolver;
	private final DnsServerAddressStreamProvider dnsServerAddressStreamProvider;
	private final String[] searchDomains;
	private final int ndots;
	private final boolean supportsAAAARecords;
	private final boolean supportsARecords;
	private final InternetProtocolFamily preferredAddressType;
	private final DnsRecordType[] resolveRecordTypes;
	private final boolean decodeIdn;
	private final DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory;

	public DnsNameResolver(
		EventLoop eventLoop,
		ChannelFactory<? extends DatagramChannel> channelFactory,
		DnsCache resolveCache,
		DnsCache authoritativeDnsServerCache,
		DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory,
		long queryTimeoutMillis,
		ResolvedAddressTypes resolvedAddressTypes,
		boolean recursionDesired,
		int maxQueriesPerResolve,
		boolean traceEnabled,
		int maxPayloadSize,
		boolean optResourceEnabled,
		HostsFileEntriesResolver hostsFileEntriesResolver,
		DnsServerAddressStreamProvider dnsServerAddressStreamProvider,
		String[] searchDomains,
		int ndots,
		boolean decodeIdn
	) {
		super(eventLoop);
		this.queryTimeoutMillis = ObjectUtil.checkPositive(queryTimeoutMillis, "queryTimeoutMillis");
		this.resolvedAddressTypes = resolvedAddressTypes != null ? resolvedAddressTypes : DEFAULT_RESOLVE_ADDRESS_TYPES;
		this.recursionDesired = recursionDesired;
		this.maxQueriesPerResolve = ObjectUtil.checkPositive(maxQueriesPerResolve, "maxQueriesPerResolve");
		this.maxPayloadSize = ObjectUtil.checkPositive(maxPayloadSize, "maxPayloadSize");
		this.optResourceEnabled = optResourceEnabled;
		this.hostsFileEntriesResolver = ObjectUtil.checkNotNull(hostsFileEntriesResolver, "hostsFileEntriesResolver");
		this.dnsServerAddressStreamProvider = ObjectUtil.checkNotNull(dnsServerAddressStreamProvider, "dnsServerAddressStreamProvider");
		this.resolveCache = ObjectUtil.checkNotNull(resolveCache, "resolveCache");
		this.authoritativeDnsServerCache = ObjectUtil.checkNotNull(authoritativeDnsServerCache, "authoritativeDnsServerCache");
		this.dnsQueryLifecycleObserverFactory = (DnsQueryLifecycleObserverFactory)(traceEnabled
			? (
				dnsQueryLifecycleObserverFactory instanceof NoopDnsQueryLifecycleObserverFactory
					? new TraceDnsQueryLifeCycleObserverFactory()
					: new BiDnsQueryLifecycleObserverFactory(new TraceDnsQueryLifeCycleObserverFactory(), dnsQueryLifecycleObserverFactory)
			)
			: ObjectUtil.checkNotNull(dnsQueryLifecycleObserverFactory, "dnsQueryLifecycleObserverFactory"));
		this.searchDomains = searchDomains != null ? (String[])searchDomains.clone() : DEFAULT_SEARCH_DOMAINS;
		this.ndots = ndots >= 0 ? ndots : DEFAULT_NDOTS;
		this.decodeIdn = decodeIdn;
		switch (this.resolvedAddressTypes) {
			case IPV4_ONLY:
				this.supportsAAAARecords = false;
				this.supportsARecords = true;
				this.resolveRecordTypes = IPV4_ONLY_RESOLVED_RECORD_TYPES;
				this.resolvedInternetProtocolFamilies = IPV4_ONLY_RESOLVED_PROTOCOL_FAMILIES;
				this.preferredAddressType = InternetProtocolFamily.IPv4;
				break;
			case IPV4_PREFERRED:
				this.supportsAAAARecords = true;
				this.supportsARecords = true;
				this.resolveRecordTypes = IPV4_PREFERRED_RESOLVED_RECORD_TYPES;
				this.resolvedInternetProtocolFamilies = IPV4_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
				this.preferredAddressType = InternetProtocolFamily.IPv4;
				break;
			case IPV6_ONLY:
				this.supportsAAAARecords = true;
				this.supportsARecords = false;
				this.resolveRecordTypes = IPV6_ONLY_RESOLVED_RECORD_TYPES;
				this.resolvedInternetProtocolFamilies = IPV6_ONLY_RESOLVED_PROTOCOL_FAMILIES;
				this.preferredAddressType = InternetProtocolFamily.IPv6;
				break;
			case IPV6_PREFERRED:
				this.supportsAAAARecords = true;
				this.supportsARecords = true;
				this.resolveRecordTypes = IPV6_PREFERRED_RESOLVED_RECORD_TYPES;
				this.resolvedInternetProtocolFamilies = IPV6_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
				this.preferredAddressType = InternetProtocolFamily.IPv6;
				break;
			default:
				throw new IllegalArgumentException("Unknown ResolvedAddressTypes " + resolvedAddressTypes);
		}

		Bootstrap b = new Bootstrap();
		b.group(this.executor());
		b.channelFactory(channelFactory);
		b.option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, Boolean.valueOf(true));
		final DnsNameResolver.DnsResponseHandler responseHandler = new DnsNameResolver.DnsResponseHandler(this.executor().newPromise());
		b.handler(new ChannelInitializer<DatagramChannel>() {
			protected void initChannel(DatagramChannel ch) throws Exception {
				ch.pipeline().addLast(DnsNameResolver.DECODER, DnsNameResolver.ENCODER, responseHandler);
			}
		});
		this.channelFuture = responseHandler.channelActivePromise;
		this.ch = (DatagramChannel)b.register().channel();
		this.ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(maxPayloadSize));
		this.ch.closeFuture().addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				resolveCache.clear();
			}
		});
	}

	int dnsRedirectPort(InetAddress server) {
		return 53;
	}

	final DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory() {
		return this.dnsQueryLifecycleObserverFactory;
	}

	protected DnsServerAddressStream uncachedRedirectDnsServerStream(List<InetSocketAddress> nameServers) {
		return DnsServerAddresses.sequential(nameServers).stream();
	}

	public DnsCache resolveCache() {
		return this.resolveCache;
	}

	public DnsCache authoritativeDnsServerCache() {
		return this.authoritativeDnsServerCache;
	}

	public long queryTimeoutMillis() {
		return this.queryTimeoutMillis;
	}

	public ResolvedAddressTypes resolvedAddressTypes() {
		return this.resolvedAddressTypes;
	}

	InternetProtocolFamily[] resolvedInternetProtocolFamiliesUnsafe() {
		return this.resolvedInternetProtocolFamilies;
	}

	final String[] searchDomains() {
		return this.searchDomains;
	}

	final int ndots() {
		return this.ndots;
	}

	final boolean supportsAAAARecords() {
		return this.supportsAAAARecords;
	}

	final boolean supportsARecords() {
		return this.supportsARecords;
	}

	final InternetProtocolFamily preferredAddressType() {
		return this.preferredAddressType;
	}

	final DnsRecordType[] resolveRecordTypes() {
		return this.resolveRecordTypes;
	}

	final boolean isDecodeIdn() {
		return this.decodeIdn;
	}

	public boolean isRecursionDesired() {
		return this.recursionDesired;
	}

	public int maxQueriesPerResolve() {
		return this.maxQueriesPerResolve;
	}

	public int maxPayloadSize() {
		return this.maxPayloadSize;
	}

	public boolean isOptResourceEnabled() {
		return this.optResourceEnabled;
	}

	public HostsFileEntriesResolver hostsFileEntriesResolver() {
		return this.hostsFileEntriesResolver;
	}

	@Override
	public void close() {
		if (this.ch.isOpen()) {
			this.ch.close();
		}
	}

	protected EventLoop executor() {
		return (EventLoop)super.executor();
	}

	private InetAddress resolveHostsFileEntry(String hostname) {
		if (this.hostsFileEntriesResolver == null) {
			return null;
		} else {
			InetAddress address = this.hostsFileEntriesResolver.address(hostname, this.resolvedAddressTypes);
			return address == null && PlatformDependent.isWindows() && "localhost".equalsIgnoreCase(hostname) ? LOCALHOST_ADDRESS : address;
		}
	}

	public final Future<InetAddress> resolve(String inetHost, Iterable<DnsRecord> additionals) {
		return this.resolve(inetHost, additionals, this.executor().newPromise());
	}

	public final Future<InetAddress> resolve(String inetHost, Iterable<DnsRecord> additionals, Promise<InetAddress> promise) {
		ObjectUtil.checkNotNull(promise, "promise");
		DnsRecord[] additionalsArray = toArray(additionals, true);

		try {
			this.doResolve(inetHost, additionalsArray, promise, this.resolveCache);
			return promise;
		} catch (Exception var6) {
			return promise.setFailure(var6);
		}
	}

	public final Future<List<InetAddress>> resolveAll(String inetHost, Iterable<DnsRecord> additionals) {
		return this.resolveAll(inetHost, additionals, this.executor().newPromise());
	}

	public final Future<List<InetAddress>> resolveAll(String inetHost, Iterable<DnsRecord> additionals, Promise<List<InetAddress>> promise) {
		ObjectUtil.checkNotNull(promise, "promise");
		DnsRecord[] additionalsArray = toArray(additionals, true);

		try {
			this.doResolveAll(inetHost, additionalsArray, promise, this.resolveCache);
			return promise;
		} catch (Exception var6) {
			return promise.setFailure(var6);
		}
	}

	@Override
	protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
		this.doResolve(inetHost, EMPTY_ADDITIONALS, promise, this.resolveCache);
	}

	public final Future<List<DnsRecord>> resolveAll(DnsQuestion question) {
		return this.resolveAll(question, EMPTY_ADDITIONALS, this.executor().newPromise());
	}

	public final Future<List<DnsRecord>> resolveAll(DnsQuestion question, Iterable<DnsRecord> additionals) {
		return this.resolveAll(question, additionals, this.executor().newPromise());
	}

	public final Future<List<DnsRecord>> resolveAll(DnsQuestion question, Iterable<DnsRecord> additionals, Promise<List<DnsRecord>> promise) {
		DnsRecord[] additionalsArray = toArray(additionals, true);
		return this.resolveAll(question, additionalsArray, promise);
	}

	private Future<List<DnsRecord>> resolveAll(DnsQuestion question, DnsRecord[] additionals, Promise<List<DnsRecord>> promise) {
		ObjectUtil.checkNotNull(question, "question");
		ObjectUtil.checkNotNull(promise, "promise");
		DnsRecordType type = question.type();
		String hostname = question.name();
		if (type == DnsRecordType.A || type == DnsRecordType.AAAA) {
			InetAddress hostsFileEntry = this.resolveHostsFileEntry(hostname);
			if (hostsFileEntry != null) {
				ByteBuf content = null;
				if (hostsFileEntry instanceof Inet4Address) {
					if (type == DnsRecordType.A) {
						content = Unpooled.wrappedBuffer(hostsFileEntry.getAddress());
					}
				} else if (hostsFileEntry instanceof Inet6Address && type == DnsRecordType.AAAA) {
					content = Unpooled.wrappedBuffer(hostsFileEntry.getAddress());
				}

				if (content != null) {
					trySuccess(promise, Collections.singletonList(new DefaultDnsRawRecord(hostname, type, 86400L, content)));
					return promise;
				}
			}
		}

		DnsServerAddressStream nameServerAddrs = this.dnsServerAddressStreamProvider.nameServerAddressStream(hostname);
		new DnsRecordResolveContext(this, question, additionals, nameServerAddrs).resolve(promise);
		return promise;
	}

	private static DnsRecord[] toArray(Iterable<DnsRecord> additionals, boolean validateType) {
		ObjectUtil.checkNotNull(additionals, "additionals");
		if (additionals instanceof Collection) {
			Collection<DnsRecord> records = (Collection<DnsRecord>)additionals;

			for (DnsRecord r : additionals) {
				validateAdditional(r, validateType);
			}

			return (DnsRecord[])records.toArray(new DnsRecord[records.size()]);
		} else {
			Iterator<DnsRecord> additionalsIt = additionals.iterator();
			if (!additionalsIt.hasNext()) {
				return EMPTY_ADDITIONALS;
			} else {
				List<DnsRecord> records = new ArrayList();

				do {
					DnsRecord r = (DnsRecord)additionalsIt.next();
					validateAdditional(r, validateType);
					records.add(r);
				} while (additionalsIt.hasNext());

				return (DnsRecord[])records.toArray(new DnsRecord[records.size()]);
			}
		}
	}

	private static void validateAdditional(DnsRecord record, boolean validateType) {
		ObjectUtil.checkNotNull(record, "record");
		if (validateType && record instanceof DnsRawRecord) {
			throw new IllegalArgumentException("DnsRawRecord implementations not allowed: " + record);
		}
	}

	private InetAddress loopbackAddress() {
		return this.preferredAddressType().localhost();
	}

	protected void doResolve(String inetHost, DnsRecord[] additionals, Promise<InetAddress> promise, DnsCache resolveCache) throws Exception {
		if (inetHost != null && !inetHost.isEmpty()) {
			byte[] bytes = NetUtil.createByteArrayFromIpAddressString(inetHost);
			if (bytes != null) {
				promise.setSuccess(InetAddress.getByAddress(bytes));
			} else {
				String hostname = hostname(inetHost);
				InetAddress hostsFileEntry = this.resolveHostsFileEntry(hostname);
				if (hostsFileEntry != null) {
					promise.setSuccess(hostsFileEntry);
				} else {
					if (!this.doResolveCached(hostname, additionals, promise, resolveCache)) {
						this.doResolveUncached(hostname, additionals, promise, resolveCache);
					}
				}
			}
		} else {
			promise.setSuccess(this.loopbackAddress());
		}
	}

	private boolean doResolveCached(String hostname, DnsRecord[] additionals, Promise<InetAddress> promise, DnsCache resolveCache) {
		List<? extends DnsCacheEntry> cachedEntries = resolveCache.get(hostname, additionals);
		if (cachedEntries != null && !cachedEntries.isEmpty()) {
			Throwable cause = ((DnsCacheEntry)cachedEntries.get(0)).cause();
			if (cause != null) {
				tryFailure(promise, cause);
				return true;
			} else {
				int numEntries = cachedEntries.size();

				for (InternetProtocolFamily f : this.resolvedInternetProtocolFamilies) {
					for (int i = 0; i < numEntries; i++) {
						DnsCacheEntry e = (DnsCacheEntry)cachedEntries.get(i);
						if (f.addressType().isInstance(e.address())) {
							trySuccess(promise, e.address());
							return true;
						}
					}
				}

				return false;
			}
		} else {
			return false;
		}
	}

	static <T> void trySuccess(Promise<T> promise, T result) {
		if (!promise.trySuccess(result)) {
			logger.warn("Failed to notify success ({}) to a promise: {}", result, promise);
		}
	}

	private static void tryFailure(Promise<?> promise, Throwable cause) {
		if (!promise.tryFailure(cause)) {
			logger.warn("Failed to notify failure to a promise: {}", promise, cause);
		}
	}

	private void doResolveUncached(String hostname, DnsRecord[] additionals, Promise<InetAddress> promise, DnsCache resolveCache) {
		Promise<List<InetAddress>> allPromise = this.executor().newPromise();
		this.doResolveAllUncached(hostname, additionals, allPromise, resolveCache);
		allPromise.addListener(new FutureListener<List<InetAddress>>() {
			@Override
			public void operationComplete(Future<List<InetAddress>> future) {
				if (future.isSuccess()) {
					DnsNameResolver.trySuccess(promise, (InetAddress)future.getNow().get(0));
				} else {
					DnsNameResolver.tryFailure(promise, future.cause());
				}
			}
		});
	}

	@Override
	protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
		this.doResolveAll(inetHost, EMPTY_ADDITIONALS, promise, this.resolveCache);
	}

	protected void doResolveAll(String inetHost, DnsRecord[] additionals, Promise<List<InetAddress>> promise, DnsCache resolveCache) throws Exception {
		if (inetHost != null && !inetHost.isEmpty()) {
			byte[] bytes = NetUtil.createByteArrayFromIpAddressString(inetHost);
			if (bytes != null) {
				promise.setSuccess(Collections.singletonList(InetAddress.getByAddress(bytes)));
			} else {
				String hostname = hostname(inetHost);
				InetAddress hostsFileEntry = this.resolveHostsFileEntry(hostname);
				if (hostsFileEntry != null) {
					promise.setSuccess(Collections.singletonList(hostsFileEntry));
				} else {
					if (!this.doResolveAllCached(hostname, additionals, promise, resolveCache)) {
						this.doResolveAllUncached(hostname, additionals, promise, resolveCache);
					}
				}
			}
		} else {
			promise.setSuccess(Collections.singletonList(this.loopbackAddress()));
		}
	}

	private boolean doResolveAllCached(String hostname, DnsRecord[] additionals, Promise<List<InetAddress>> promise, DnsCache resolveCache) {
		List<? extends DnsCacheEntry> cachedEntries = resolveCache.get(hostname, additionals);
		if (cachedEntries != null && !cachedEntries.isEmpty()) {
			Throwable cause = ((DnsCacheEntry)cachedEntries.get(0)).cause();
			if (cause != null) {
				tryFailure(promise, cause);
				return true;
			} else {
				List<InetAddress> result = null;
				int numEntries = cachedEntries.size();

				for (InternetProtocolFamily f : this.resolvedInternetProtocolFamilies) {
					for (int i = 0; i < numEntries; i++) {
						DnsCacheEntry e = (DnsCacheEntry)cachedEntries.get(i);
						if (f.addressType().isInstance(e.address())) {
							if (result == null) {
								result = new ArrayList(numEntries);
							}

							result.add(e.address());
						}
					}
				}

				if (result != null) {
					trySuccess(promise, result);
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private void doResolveAllUncached(String hostname, DnsRecord[] additionals, Promise<List<InetAddress>> promise, DnsCache resolveCache) {
		DnsServerAddressStream nameServerAddrs = this.dnsServerAddressStreamProvider.nameServerAddressStream(hostname);
		new DnsAddressResolveContext(this, hostname, additionals, nameServerAddrs, resolveCache).resolve(promise);
	}

	private static String hostname(String inetHost) {
		String hostname = IDN.toASCII(inetHost);
		if (StringUtil.endsWith(inetHost, '.') && !StringUtil.endsWith(hostname, '.')) {
			hostname = hostname + ".";
		}

		return hostname;
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion question) {
		return this.query(this.nextNameServerAddress(), question);
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion question, Iterable<DnsRecord> additionals) {
		return this.query(this.nextNameServerAddress(), question, additionals);
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(
		DnsQuestion question, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise
	) {
		return this.query(this.nextNameServerAddress(), question, Collections.emptyList(), promise);
	}

	private InetSocketAddress nextNameServerAddress() {
		return this.nameServerAddrStream.get().next();
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question) {
		return this.query0(nameServerAddr, question, EMPTY_ADDITIONALS, this.ch.eventLoop().newPromise());
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress nameServerAddr, DnsQuestion question, Iterable<DnsRecord> additionals) {
		return this.query0(nameServerAddr, question, toArray(additionals, false), this.ch.eventLoop().newPromise());
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(
		InetSocketAddress nameServerAddr, DnsQuestion question, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise
	) {
		return this.query0(nameServerAddr, question, EMPTY_ADDITIONALS, promise);
	}

	public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(
		InetSocketAddress nameServerAddr,
		DnsQuestion question,
		Iterable<DnsRecord> additionals,
		Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise
	) {
		return this.query0(nameServerAddr, question, toArray(additionals, false), promise);
	}

	public static boolean isTransportOrTimeoutError(Throwable cause) {
		return cause != null && cause.getCause() instanceof DnsNameResolverException;
	}

	public static boolean isTimeoutError(Throwable cause) {
		return cause != null && cause.getCause() instanceof DnsNameResolverTimeoutException;
	}

	final Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query0(
		InetSocketAddress nameServerAddr, DnsQuestion question, DnsRecord[] additionals, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise
	) {
		return this.query0(nameServerAddr, question, additionals, this.ch.newPromise(), promise);
	}

	final Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query0(
		InetSocketAddress nameServerAddr,
		DnsQuestion question,
		DnsRecord[] additionals,
		ChannelPromise writePromise,
		Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise
	) {
		assert !writePromise.isVoid();

		Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> castPromise = cast(ObjectUtil.checkNotNull(promise, "promise"));

		try {
			new DnsQueryContext(this, nameServerAddr, question, additionals, castPromise).query(writePromise);
			return castPromise;
		} catch (Exception var8) {
			return castPromise.setFailure(var8);
		}
	}

	private static Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> cast(Promise<?> promise) {
		return (Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>>)promise;
	}

	static {
		if (NetUtil.isIpV4StackPreferred()) {
			DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV4_ONLY;
			LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
		} else if (NetUtil.isIpV6AddressesPreferred()) {
			DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV6_PREFERRED;
			LOCALHOST_ADDRESS = NetUtil.LOCALHOST6;
		} else {
			DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV4_PREFERRED;
			LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
		}

		String[] searchDomains;
		try {
			Class<?> configClass = Class.forName("sun.net.dns.ResolverConfiguration");
			Method open = configClass.getMethod("open");
			Method nameservers = configClass.getMethod("searchlist");
			Object instance = open.invoke(null);
			List<String> list = (List<String>)nameservers.invoke(instance);
			searchDomains = (String[])list.toArray(new String[list.size()]);
		} catch (Exception var7) {
			searchDomains = EmptyArrays.EMPTY_STRINGS;
		}

		DEFAULT_SEARCH_DOMAINS = searchDomains;

		int ndots;
		try {
			ndots = UnixResolverDnsServerAddressStreamProvider.parseEtcResolverFirstNdots();
		} catch (Exception var6) {
			ndots = 1;
		}

		DEFAULT_NDOTS = ndots;
		DECODER = new DatagramDnsResponseDecoder();
		ENCODER = new DatagramDnsQueryEncoder();
	}

	private final class DnsResponseHandler extends ChannelInboundHandlerAdapter {
		private final Promise<Channel> channelActivePromise;

		DnsResponseHandler(Promise<Channel> channelActivePromise) {
			this.channelActivePromise = channelActivePromise;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				DatagramDnsResponse res = (DatagramDnsResponse)msg;
				int queryId = res.id();
				if (DnsNameResolver.logger.isDebugEnabled()) {
					DnsNameResolver.logger.debug("{} RECEIVED: [{}: {}], {}", DnsNameResolver.this.ch, queryId, res.sender(), res);
				}

				DnsQueryContext qCtx = DnsNameResolver.this.queryContextManager.get(res.sender(), queryId);
				if (qCtx != null) {
					qCtx.finish(res);
					return;
				}

				DnsNameResolver.logger.warn("{} Received a DNS response with an unknown ID: {}", DnsNameResolver.this.ch, queryId);
			} finally {
				ReferenceCountUtil.safeRelease(msg);
			}
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			this.channelActivePromise.setSuccess(ctx.channel());
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			DnsNameResolver.logger.warn("{} Unexpected exception: ", DnsNameResolver.this.ch, cause);
		}
	}
}
