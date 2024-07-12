package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultDnsCache implements DnsCache {
	private final ConcurrentMap<String, DefaultDnsCache.Entries> resolveCache = PlatformDependent.newConcurrentHashMap();
	private static final int MAX_SUPPORTED_TTL_SECS = (int)TimeUnit.DAYS.toSeconds(730L);
	private final int minTtl;
	private final int maxTtl;
	private final int negativeTtl;

	public DefaultDnsCache() {
		this(0, MAX_SUPPORTED_TTL_SECS, 0);
	}

	public DefaultDnsCache(int minTtl, int maxTtl, int negativeTtl) {
		this.minTtl = Math.min(MAX_SUPPORTED_TTL_SECS, ObjectUtil.checkPositiveOrZero(minTtl, "minTtl"));
		this.maxTtl = Math.min(MAX_SUPPORTED_TTL_SECS, ObjectUtil.checkPositiveOrZero(maxTtl, "maxTtl"));
		if (minTtl > maxTtl) {
			throw new IllegalArgumentException("minTtl: " + minTtl + ", maxTtl: " + maxTtl + " (expected: 0 <= minTtl <= maxTtl)");
		} else {
			this.negativeTtl = ObjectUtil.checkPositiveOrZero(negativeTtl, "negativeTtl");
		}
	}

	public int minTtl() {
		return this.minTtl;
	}

	public int maxTtl() {
		return this.maxTtl;
	}

	public int negativeTtl() {
		return this.negativeTtl;
	}

	@Override
	public void clear() {
		while (!this.resolveCache.isEmpty()) {
			Iterator<Entry<String, DefaultDnsCache.Entries>> i = this.resolveCache.entrySet().iterator();

			while (i.hasNext()) {
				Entry<String, DefaultDnsCache.Entries> e = (Entry<String, DefaultDnsCache.Entries>)i.next();
				i.remove();
				((DefaultDnsCache.Entries)e.getValue()).clearAndCancel();
			}
		}
	}

	@Override
	public boolean clear(String hostname) {
		ObjectUtil.checkNotNull(hostname, "hostname");
		DefaultDnsCache.Entries entries = (DefaultDnsCache.Entries)this.resolveCache.remove(hostname);
		return entries != null && entries.clearAndCancel();
	}

	private static boolean emptyAdditionals(DnsRecord[] additionals) {
		return additionals == null || additionals.length == 0;
	}

	@Override
	public List<? extends DnsCacheEntry> get(String hostname, DnsRecord[] additionals) {
		ObjectUtil.checkNotNull(hostname, "hostname");
		if (!emptyAdditionals(additionals)) {
			return Collections.emptyList();
		} else {
			DefaultDnsCache.Entries entries = (DefaultDnsCache.Entries)this.resolveCache.get(hostname);
			return entries == null ? null : (List)entries.get();
		}
	}

	@Override
	public DnsCacheEntry cache(String hostname, DnsRecord[] additionals, InetAddress address, long originalTtl, EventLoop loop) {
		ObjectUtil.checkNotNull(hostname, "hostname");
		ObjectUtil.checkNotNull(address, "address");
		ObjectUtil.checkNotNull(loop, "loop");
		DefaultDnsCache.DefaultDnsCacheEntry e = new DefaultDnsCache.DefaultDnsCacheEntry(hostname, address);
		if (this.maxTtl != 0 && emptyAdditionals(additionals)) {
			this.cache0(e, Math.max(this.minTtl, Math.min(MAX_SUPPORTED_TTL_SECS, (int)Math.min((long)this.maxTtl, originalTtl))), loop);
			return e;
		} else {
			return e;
		}
	}

	@Override
	public DnsCacheEntry cache(String hostname, DnsRecord[] additionals, Throwable cause, EventLoop loop) {
		ObjectUtil.checkNotNull(hostname, "hostname");
		ObjectUtil.checkNotNull(cause, "cause");
		ObjectUtil.checkNotNull(loop, "loop");
		DefaultDnsCache.DefaultDnsCacheEntry e = new DefaultDnsCache.DefaultDnsCacheEntry(hostname, cause);
		if (this.negativeTtl != 0 && emptyAdditionals(additionals)) {
			this.cache0(e, Math.min(MAX_SUPPORTED_TTL_SECS, this.negativeTtl), loop);
			return e;
		} else {
			return e;
		}
	}

	private void cache0(DefaultDnsCache.DefaultDnsCacheEntry e, int ttl, EventLoop loop) {
		DefaultDnsCache.Entries entries = (DefaultDnsCache.Entries)this.resolveCache.get(e.hostname());
		if (entries == null) {
			entries = new DefaultDnsCache.Entries(e);
			DefaultDnsCache.Entries oldEntries = (DefaultDnsCache.Entries)this.resolveCache.putIfAbsent(e.hostname(), entries);
			if (oldEntries != null) {
				entries = oldEntries;
			}
		}

		entries.add(e);
		this.scheduleCacheExpiration(e, ttl, loop);
	}

	private void scheduleCacheExpiration(DefaultDnsCache.DefaultDnsCacheEntry e, int ttl, EventLoop loop) {
		e.scheduleExpiration(loop, new Runnable() {
			public void run() {
				DefaultDnsCache.Entries entries = (DefaultDnsCache.Entries)DefaultDnsCache.this.resolveCache.remove(e.hostname);
				if (entries != null) {
					entries.clearAndCancel();
				}
			}
		}, (long)ttl, TimeUnit.SECONDS);
	}

	public String toString() {
		return "DefaultDnsCache(minTtl="
			+ this.minTtl
			+ ", maxTtl="
			+ this.maxTtl
			+ ", negativeTtl="
			+ this.negativeTtl
			+ ", cached resolved hostname="
			+ this.resolveCache.size()
			+ ")";
	}

	private static final class DefaultDnsCacheEntry implements DnsCacheEntry {
		private final String hostname;
		private final InetAddress address;
		private final Throwable cause;
		private volatile ScheduledFuture<?> expirationFuture;

		DefaultDnsCacheEntry(String hostname, InetAddress address) {
			this.hostname = ObjectUtil.checkNotNull(hostname, "hostname");
			this.address = ObjectUtil.checkNotNull(address, "address");
			this.cause = null;
		}

		DefaultDnsCacheEntry(String hostname, Throwable cause) {
			this.hostname = ObjectUtil.checkNotNull(hostname, "hostname");
			this.cause = ObjectUtil.checkNotNull(cause, "cause");
			this.address = null;
		}

		@Override
		public InetAddress address() {
			return this.address;
		}

		@Override
		public Throwable cause() {
			return this.cause;
		}

		String hostname() {
			return this.hostname;
		}

		void scheduleExpiration(EventLoop loop, Runnable task, long delay, TimeUnit unit) {
			assert this.expirationFuture == null : "expiration task scheduled already";

			this.expirationFuture = loop.schedule(task, delay, unit);
		}

		void cancelExpiration() {
			ScheduledFuture<?> expirationFuture = this.expirationFuture;
			if (expirationFuture != null) {
				expirationFuture.cancel(false);
			}
		}

		public String toString() {
			return this.cause != null ? this.hostname + '/' + this.cause : this.address.toString();
		}
	}

	private static final class Entries extends AtomicReference<List<DefaultDnsCache.DefaultDnsCacheEntry>> {
		Entries(DefaultDnsCache.DefaultDnsCacheEntry entry) {
			super(Collections.singletonList(entry));
		}

		void add(DefaultDnsCache.DefaultDnsCacheEntry e) {
			if (e.cause() != null) {
				List<DefaultDnsCache.DefaultDnsCacheEntry> entries = (List<DefaultDnsCache.DefaultDnsCacheEntry>)this.getAndSet(Collections.singletonList(e));
				cancelExpiration(entries);
			} else {
				while (true) {
					List<DefaultDnsCache.DefaultDnsCacheEntry> entries = (List<DefaultDnsCache.DefaultDnsCacheEntry>)this.get();
					if (!entries.isEmpty()) {
						DefaultDnsCache.DefaultDnsCacheEntry firstEntry = (DefaultDnsCache.DefaultDnsCacheEntry)entries.get(0);
						if (firstEntry.cause() != null) {
							assert entries.size() == 1;

							if (this.compareAndSet(entries, Collections.singletonList(e))) {
								firstEntry.cancelExpiration();
								return;
							}
						} else {
							List<DefaultDnsCache.DefaultDnsCacheEntry> newEntries = new ArrayList(entries.size() + 1);
							DefaultDnsCache.DefaultDnsCacheEntry replacedEntry = null;

							for (int i = 0; i < entries.size(); i++) {
								DefaultDnsCache.DefaultDnsCacheEntry entry = (DefaultDnsCache.DefaultDnsCacheEntry)entries.get(i);
								if (!e.address().equals(entry.address())) {
									newEntries.add(entry);
								} else {
									assert replacedEntry == null;

									replacedEntry = entry;
								}
							}

							newEntries.add(e);
							if (this.compareAndSet(entries, newEntries)) {
								if (replacedEntry != null) {
									replacedEntry.cancelExpiration();
								}

								return;
							}
						}
					} else if (this.compareAndSet(entries, Collections.singletonList(e))) {
						return;
					}
				}
			}
		}

		boolean clearAndCancel() {
			List<DefaultDnsCache.DefaultDnsCacheEntry> entries = (List<DefaultDnsCache.DefaultDnsCacheEntry>)this.getAndSet(Collections.emptyList());
			if (entries.isEmpty()) {
				return false;
			} else {
				cancelExpiration(entries);
				return true;
			}
		}

		private static void cancelExpiration(List<DefaultDnsCache.DefaultDnsCacheEntry> entryList) {
			int numEntries = entryList.size();

			for (int i = 0; i < numEntries; i++) {
				((DefaultDnsCache.DefaultDnsCacheEntry)entryList.get(i)).cancelExpiration();
			}
		}
	}
}
