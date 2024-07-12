package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class ResourceLeakDetectorFactory {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);
	private static volatile ResourceLeakDetectorFactory factoryInstance = new ResourceLeakDetectorFactory.DefaultResourceLeakDetectorFactory();

	public static ResourceLeakDetectorFactory instance() {
		return factoryInstance;
	}

	public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory factory) {
		factoryInstance = ObjectUtil.checkNotNull(factory, "factory");
	}

	public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource) {
		return this.newResourceLeakDetector(resource, 128);
	}

	@Deprecated
	public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> class1, int integer, long long3);

	public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
		return this.newResourceLeakDetector(resource, 128, Long.MAX_VALUE);
	}

	private static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
		private final Constructor<?> obsoleteCustomClassConstructor;
		private final Constructor<?> customClassConstructor;

		DefaultResourceLeakDetectorFactory() {
			String customLeakDetector;
			try {
				customLeakDetector = (String)AccessController.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
					}
				});
			} catch (Throwable var3) {
				ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", var3);
				customLeakDetector = null;
			}

			if (customLeakDetector == null) {
				this.obsoleteCustomClassConstructor = this.customClassConstructor = null;
			} else {
				this.obsoleteCustomClassConstructor = obsoleteCustomClassConstructor(customLeakDetector);
				this.customClassConstructor = customClassConstructor(customLeakDetector);
			}
		}

		private static Constructor<?> obsoleteCustomClassConstructor(String customLeakDetector) {
			try {
				Class<?> detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
				if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
					return detectorClass.getConstructor(Class.class, int.class, long.class);
				}

				ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", customLeakDetector);
			} catch (Throwable var2) {
				ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, var2);
			}

			return null;
		}

		private static Constructor<?> customClassConstructor(String customLeakDetector) {
			try {
				Class<?> detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
				if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
					return detectorClass.getConstructor(Class.class, int.class);
				}

				ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", customLeakDetector);
			} catch (Throwable var2) {
				ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, var2);
			}

			return null;
		}

		@Override
		public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval, long maxActive) {
			if (this.obsoleteCustomClassConstructor != null) {
				try {
					ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector<T>)this.obsoleteCustomClassConstructor.newInstance(resource, samplingInterval, maxActive);
					ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", this.obsoleteCustomClassConstructor.getDeclaringClass().getName());
					return leakDetector;
				} catch (Throwable var6) {
					ResourceLeakDetectorFactory.logger
						.error(
							"Could not load custom resource leak detector provided: {} with the given resource: {}",
							this.obsoleteCustomClassConstructor.getDeclaringClass().getName(),
							resource,
							var6
						);
				}
			}

			ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>(resource, samplingInterval, maxActive);
			ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", resourceLeakDetector);
			return resourceLeakDetector;
		}

		@Override
		public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval) {
			if (this.customClassConstructor != null) {
				try {
					ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector<T>)this.customClassConstructor.newInstance(resource, samplingInterval);
					ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", this.customClassConstructor.getDeclaringClass().getName());
					return leakDetector;
				} catch (Throwable var4) {
					ResourceLeakDetectorFactory.logger
						.error(
							"Could not load custom resource leak detector provided: {} with the given resource: {}",
							this.customClassConstructor.getDeclaringClass().getName(),
							resource,
							var4
						);
				}
			}

			ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector<>(resource, samplingInterval);
			ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", resourceLeakDetector);
			return resourceLeakDetector;
		}
	}
}
