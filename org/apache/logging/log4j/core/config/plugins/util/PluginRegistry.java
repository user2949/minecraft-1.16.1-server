package org.apache.logging.log4j.core.config.plugins.util;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

public class PluginRegistry {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static volatile PluginRegistry INSTANCE;
	private static final Object INSTANCE_LOCK = new Object();
	private final AtomicReference<Map<String, List<PluginType<?>>>> pluginsByCategoryRef = new AtomicReference();
	private final ConcurrentMap<Long, Map<String, List<PluginType<?>>>> pluginsByCategoryByBundleId = new ConcurrentHashMap();
	private final ConcurrentMap<String, Map<String, List<PluginType<?>>>> pluginsByCategoryByPackage = new ConcurrentHashMap();

	private PluginRegistry() {
	}

	public static PluginRegistry getInstance() {
		PluginRegistry result = INSTANCE;
		if (result == null) {
			synchronized (INSTANCE_LOCK) {
				result = INSTANCE;
				if (result == null) {
					INSTANCE = result = new PluginRegistry();
				}
			}
		}

		return result;
	}

	public void clear() {
		this.pluginsByCategoryRef.set(null);
		this.pluginsByCategoryByPackage.clear();
		this.pluginsByCategoryByBundleId.clear();
	}

	public Map<Long, Map<String, List<PluginType<?>>>> getPluginsByCategoryByBundleId() {
		return this.pluginsByCategoryByBundleId;
	}

	public Map<String, List<PluginType<?>>> loadFromMainClassLoader() {
		Map<String, List<PluginType<?>>> existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryRef.get();
		if (existing != null) {
			return existing;
		} else {
			Map<String, List<PluginType<?>>> newPluginsByCategory = this.decodeCacheFiles(Loader.getClassLoader());
			return this.pluginsByCategoryRef.compareAndSet(null, newPluginsByCategory) ? newPluginsByCategory : (Map)this.pluginsByCategoryRef.get();
		}
	}

	public void clearBundlePlugins(long bundleId) {
		this.pluginsByCategoryByBundleId.remove(bundleId);
	}

	public Map<String, List<PluginType<?>>> loadFromBundle(long bundleId, ClassLoader loader) {
		Map<String, List<PluginType<?>>> existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByBundleId.get(bundleId);
		if (existing != null) {
			return existing;
		} else {
			Map<String, List<PluginType<?>>> newPluginsByCategory = this.decodeCacheFiles(loader);
			existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByBundleId.putIfAbsent(bundleId, newPluginsByCategory);
			return existing != null ? existing : newPluginsByCategory;
		}
	}

	private Map<String, List<PluginType<?>>> decodeCacheFiles(ClassLoader loader) {
		long startTime = System.nanoTime();
		PluginCache cache = new PluginCache();

		try {
			Enumeration<URL> resources = loader.getResources("META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat");
			if (resources == null) {
				LOGGER.info("Plugin preloads not available from class loader {}", loader);
			} else {
				cache.loadCacheFiles(resources);
			}
		} catch (IOException var19) {
			LOGGER.warn("Unable to preload plugins", (Throwable)var19);
		}

		Map<String, List<PluginType<?>>> newPluginsByCategory = new HashMap();
		int pluginCount = 0;

		for (Entry<String, Map<String, PluginEntry>> outer : cache.getAllCategories().entrySet()) {
			String categoryLowerCase = (String)outer.getKey();
			List<PluginType<?>> types = new ArrayList(((Map)outer.getValue()).size());
			newPluginsByCategory.put(categoryLowerCase, types);

			for (Entry<String, PluginEntry> inner : ((Map)outer.getValue()).entrySet()) {
				PluginEntry entry = (PluginEntry)inner.getValue();
				String className = entry.getClassName();

				try {
					Class<?> clazz = loader.loadClass(className);
					PluginType<?> type = new PluginType(entry, clazz, entry.getName());
					types.add(type);
					pluginCount++;
				} catch (ClassNotFoundException var17) {
					LOGGER.info("Plugin [{}] could not be loaded due to missing classes.", className, var17);
				} catch (VerifyError var18) {
					LOGGER.info("Plugin [{}] could not be loaded due to verification error.", className, var18);
				}
			}
		}

		long endTime = System.nanoTime();
		DecimalFormat numFormat = new DecimalFormat("#0.000000");
		double seconds = (double)(endTime - startTime) * 1.0E-9;
		LOGGER.debug("Took {} seconds to load {} plugins from {}", numFormat.format(seconds), pluginCount, loader);
		return newPluginsByCategory;
	}

	public Map<String, List<PluginType<?>>> loadFromPackage(String pkg) {
		if (Strings.isBlank(pkg)) {
			return Collections.emptyMap();
		} else {
			Map<String, List<PluginType<?>>> existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByPackage.get(pkg);
			if (existing != null) {
				return existing;
			} else {
				long startTime = System.nanoTime();
				ResolverUtil resolver = new ResolverUtil();
				ClassLoader classLoader = Loader.getClassLoader();
				if (classLoader != null) {
					resolver.setClassLoader(classLoader);
				}

				resolver.findInPackage(new PluginRegistry.PluginTest(), pkg);
				Map<String, List<PluginType<?>>> newPluginsByCategory = new HashMap();

				for (Class<?> clazz : resolver.getClasses()) {
					Plugin plugin = (Plugin)clazz.getAnnotation(Plugin.class);
					String categoryLowerCase = plugin.category().toLowerCase();
					List<PluginType<?>> list = (List<PluginType<?>>)newPluginsByCategory.get(categoryLowerCase);
					if (list == null) {
						newPluginsByCategory.put(categoryLowerCase, list = new ArrayList());
					}

					PluginEntry mainEntry = new PluginEntry();
					String mainElementName = plugin.elementType().equals("") ? plugin.name() : plugin.elementType();
					mainEntry.setKey(plugin.name().toLowerCase());
					mainEntry.setName(plugin.name());
					mainEntry.setCategory(plugin.category());
					mainEntry.setClassName(clazz.getName());
					mainEntry.setPrintable(plugin.printObject());
					mainEntry.setDefer(plugin.deferChildren());
					PluginType<?> mainType = new PluginType(mainEntry, clazz, mainElementName);
					list.add(mainType);
					PluginAliases pluginAliases = (PluginAliases)clazz.getAnnotation(PluginAliases.class);
					if (pluginAliases != null) {
						for (String alias : pluginAliases.value()) {
							PluginEntry aliasEntry = new PluginEntry();
							String aliasElementName = plugin.elementType().equals("") ? alias.trim() : plugin.elementType();
							aliasEntry.setKey(alias.trim().toLowerCase());
							aliasEntry.setName(plugin.name());
							aliasEntry.setCategory(plugin.category());
							aliasEntry.setClassName(clazz.getName());
							aliasEntry.setPrintable(plugin.printObject());
							aliasEntry.setDefer(plugin.deferChildren());
							PluginType<?> aliasType = new PluginType(aliasEntry, clazz, aliasElementName);
							list.add(aliasType);
						}
					}
				}

				long endTime = System.nanoTime();
				DecimalFormat numFormat = new DecimalFormat("#0.000000");
				double seconds = (double)(endTime - startTime) * 1.0E-9;
				LOGGER.debug("Took {} seconds to load {} plugins from package {}", numFormat.format(seconds), resolver.getClasses().size(), pkg);
				existing = (Map<String, List<PluginType<?>>>)this.pluginsByCategoryByPackage.putIfAbsent(pkg, newPluginsByCategory);
				return existing != null ? existing : newPluginsByCategory;
			}
		}
	}

	public static class PluginTest implements Test {
		@Override
		public boolean matches(Class<?> type) {
			return type != null && type.isAnnotationPresent(Plugin.class);
		}

		public String toString() {
			return "annotated with @" + Plugin.class.getSimpleName();
		}

		@Override
		public boolean matches(URI resource) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean doesMatchClass() {
			return true;
		}

		@Override
		public boolean doesMatchResource() {
			return false;
		}
	}
}
