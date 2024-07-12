package org.apache.logging.log4j.core.config.plugins.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

public class ResolverUtil {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static final String VFSZIP = "vfszip";
	private static final String VFS = "vfs";
	private static final String BUNDLE_RESOURCE = "bundleresource";
	private final Set<Class<?>> classMatches = new HashSet();
	private final Set<URI> resourceMatches = new HashSet();
	private ClassLoader classloader;

	public Set<Class<?>> getClasses() {
		return this.classMatches;
	}

	public Set<URI> getResources() {
		return this.resourceMatches;
	}

	public ClassLoader getClassLoader() {
		return this.classloader != null ? this.classloader : (this.classloader = Loader.getClassLoader(ResolverUtil.class, null));
	}

	public void setClassLoader(ClassLoader aClassloader) {
		this.classloader = aClassloader;
	}

	public void find(ResolverUtil.Test test, String... packageNames) {
		if (packageNames != null) {
			for (String pkg : packageNames) {
				this.findInPackage(test, pkg);
			}
		}
	}

	public void findInPackage(ResolverUtil.Test test, String packageName) {
		packageName = packageName.replace('.', '/');
		ClassLoader loader = this.getClassLoader();

		Enumeration<URL> urls;
		try {
			urls = loader.getResources(packageName);
		} catch (IOException var16) {
			LOGGER.warn("Could not read package: " + packageName, (Throwable)var16);
			return;
		}

		while (urls.hasMoreElements()) {
			try {
				URL url = (URL)urls.nextElement();
				String urlPath = this.extractPath(url);
				LOGGER.info("Scanning for classes in [" + urlPath + "] matching criteria: " + test);
				if ("vfszip".equals(url.getProtocol())) {
					String path = urlPath.substring(0, urlPath.length() - packageName.length() - 2);
					URL newURL = new URL(url.getProtocol(), url.getHost(), path);
					JarInputStream stream = new JarInputStream(newURL.openStream());

					try {
						this.loadImplementationsInJar(test, packageName, path, stream);
					} finally {
						this.close(stream, newURL);
					}
				} else if ("vfs".equals(url.getProtocol())) {
					String containerPath = urlPath.substring(1, urlPath.length() - packageName.length() - 2);
					File containerFile = new File(containerPath);
					if (containerFile.isDirectory()) {
						this.loadImplementationsInDirectory(test, packageName, new File(containerFile, packageName));
					} else {
						this.loadImplementationsInJar(test, packageName, containerFile);
					}
				} else if ("bundleresource".equals(url.getProtocol())) {
					this.loadImplementationsInBundle(test, packageName);
				} else {
					File file = new File(urlPath);
					if (file.isDirectory()) {
						this.loadImplementationsInDirectory(test, packageName, file);
					} else {
						this.loadImplementationsInJar(test, packageName, file);
					}
				}
			} catch (URISyntaxException | IOException var15) {
				LOGGER.warn("Could not read entries", (Throwable)var15);
			}
		}
	}

	String extractPath(URL url) throws UnsupportedEncodingException, URISyntaxException {
		String urlPath = url.getPath();
		if (urlPath.startsWith("jar:")) {
			urlPath = urlPath.substring(4);
		}

		if (urlPath.startsWith("file:")) {
			urlPath = urlPath.substring(5);
		}

		int bangIndex = urlPath.indexOf(33);
		if (bangIndex > 0) {
			urlPath = urlPath.substring(0, bangIndex);
		}

		String protocol = url.getProtocol();
		List<String> neverDecode = Arrays.asList("vfs", "vfszip", "bundleresource");
		if (neverDecode.contains(protocol)) {
			return urlPath;
		} else {
			String cleanPath = new URI(urlPath).getPath();
			return new File(cleanPath).exists() ? cleanPath : URLDecoder.decode(urlPath, StandardCharsets.UTF_8.name());
		}
	}

	private void loadImplementationsInBundle(ResolverUtil.Test test, String packageName) {
		BundleWiring wiring = (BundleWiring)FrameworkUtil.getBundle(ResolverUtil.class).adapt(BundleWiring.class);

		for (String name : wiring.listResources(packageName, "*.class", 1)) {
			this.addIfMatching(test, name);
		}
	}

	private void loadImplementationsInDirectory(ResolverUtil.Test test, String parent, File location) {
		File[] files = location.listFiles();
		if (files != null) {
			for (File file : files) {
				StringBuilder builder = new StringBuilder();
				builder.append(parent).append('/').append(file.getName());
				String packageOrClass = parent == null ? file.getName() : builder.toString();
				if (file.isDirectory()) {
					this.loadImplementationsInDirectory(test, packageOrClass, file);
				} else if (this.isTestApplicable(test, file.getName())) {
					this.addIfMatching(test, packageOrClass);
				}
			}
		}
	}

	private boolean isTestApplicable(ResolverUtil.Test test, String path) {
		return test.doesMatchResource() || path.endsWith(".class") && test.doesMatchClass();
	}

	private void loadImplementationsInJar(ResolverUtil.Test test, String parent, File jarFile) {
		JarInputStream jarStream = null;

		try {
			jarStream = new JarInputStream(new FileInputStream(jarFile));
			this.loadImplementationsInJar(test, parent, jarFile.getPath(), jarStream);
		} catch (FileNotFoundException var10) {
			LOGGER.error("Could not search jar file '" + jarFile + "' for classes matching criteria: " + test + " file not found", (Throwable)var10);
		} catch (IOException var11) {
			LOGGER.error("Could not search jar file '" + jarFile + "' for classes matching criteria: " + test + " due to an IOException", (Throwable)var11);
		} finally {
			this.close(jarStream, jarFile);
		}
	}

	private void close(JarInputStream jarStream, Object source) {
		if (jarStream != null) {
			try {
				jarStream.close();
			} catch (IOException var4) {
				LOGGER.error("Error closing JAR file stream for {}", source, var4);
			}
		}
	}

	private void loadImplementationsInJar(ResolverUtil.Test test, String parent, String path, JarInputStream stream) {
		JarEntry entry;
		try {
			while ((entry = stream.getNextJarEntry()) != null) {
				String name = entry.getName();
				if (!entry.isDirectory() && name.startsWith(parent) && this.isTestApplicable(test, name)) {
					this.addIfMatching(test, name);
				}
			}
		} catch (IOException var7) {
			LOGGER.error("Could not search jar file '" + path + "' for classes matching criteria: " + test + " due to an IOException", (Throwable)var7);
		}
	}

	protected void addIfMatching(ResolverUtil.Test test, String fqn) {
		try {
			ClassLoader loader = this.getClassLoader();
			if (test.doesMatchClass()) {
				String externalName = fqn.substring(0, fqn.indexOf(46)).replace('/', '.');
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Checking to see if class " + externalName + " matches criteria [" + test + ']');
				}

				Class<?> type = loader.loadClass(externalName);
				if (test.matches(type)) {
					this.classMatches.add(type);
				}
			}

			if (test.doesMatchResource()) {
				URL url = loader.getResource(fqn);
				if (url == null) {
					url = loader.getResource(fqn.substring(1));
				}

				if (url != null && test.matches(url.toURI())) {
					this.resourceMatches.add(url.toURI());
				}
			}
		} catch (Throwable var6) {
			LOGGER.warn("Could not examine class '" + fqn, var6);
		}
	}

	public interface Test {
		boolean matches(Class<?> class1);

		boolean matches(URI uRI);

		boolean doesMatchClass();

		boolean doesMatchResource();
	}
}
