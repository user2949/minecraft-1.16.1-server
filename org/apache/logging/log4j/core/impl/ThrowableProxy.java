package org.apache.logging.log4j.core.impl;

import java.io.Serializable;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.logging.log4j.core.pattern.PlainTextRenderer;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.ReflectionUtil;

public class ThrowableProxy implements Serializable {
	private static final String TAB = "\t";
	private static final String CAUSED_BY_LABEL = "Caused by: ";
	private static final String SUPPRESSED_LABEL = "Suppressed: ";
	private static final String WRAPPED_BY_LABEL = "Wrapped by: ";
	private static final ThrowableProxy[] EMPTY_THROWABLE_PROXY_ARRAY = new ThrowableProxy[0];
	private static final char EOL = '\n';
	private static final String EOL_STR = String.valueOf('\n');
	private static final long serialVersionUID = -2752771578252251910L;
	private final ThrowableProxy causeProxy;
	private int commonElementCount;
	private final ExtendedStackTraceElement[] extendedStackTrace;
	private final String localizedMessage;
	private final String message;
	private final String name;
	private final ThrowableProxy[] suppressedProxies;
	private final transient Throwable throwable;

	private ThrowableProxy() {
		this.throwable = null;
		this.name = null;
		this.extendedStackTrace = null;
		this.causeProxy = null;
		this.message = null;
		this.localizedMessage = null;
		this.suppressedProxies = EMPTY_THROWABLE_PROXY_ARRAY;
	}

	public ThrowableProxy(Throwable throwable) {
		this(throwable, null);
	}

	private ThrowableProxy(Throwable throwable, Set<Throwable> visited) {
		this.throwable = throwable;
		this.name = throwable.getClass().getName();
		this.message = throwable.getMessage();
		this.localizedMessage = throwable.getLocalizedMessage();
		Map<String, ThrowableProxy.CacheEntry> map = new HashMap();
		Stack<Class<?>> stack = ReflectionUtil.getCurrentStackTrace();
		this.extendedStackTrace = this.toExtendedStackTrace(stack, map, null, throwable.getStackTrace());
		Throwable throwableCause = throwable.getCause();
		Set<Throwable> causeVisited = new HashSet(1);
		this.causeProxy = throwableCause == null ? null : new ThrowableProxy(throwable, stack, map, throwableCause, visited, causeVisited);
		this.suppressedProxies = this.toSuppressedProxies(throwable, visited);
	}

	private ThrowableProxy(
		Throwable parent,
		Stack<Class<?>> stack,
		Map<String, ThrowableProxy.CacheEntry> map,
		Throwable cause,
		Set<Throwable> suppressedVisited,
		Set<Throwable> causeVisited
	) {
		causeVisited.add(cause);
		this.throwable = cause;
		this.name = cause.getClass().getName();
		this.message = this.throwable.getMessage();
		this.localizedMessage = this.throwable.getLocalizedMessage();
		this.extendedStackTrace = this.toExtendedStackTrace(stack, map, parent.getStackTrace(), cause.getStackTrace());
		Throwable causeCause = cause.getCause();
		this.causeProxy = causeCause != null && !causeVisited.contains(causeCause)
			? new ThrowableProxy(parent, stack, map, causeCause, suppressedVisited, causeVisited)
			: null;
		this.suppressedProxies = this.toSuppressedProxies(cause, suppressedVisited);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			ThrowableProxy other = (ThrowableProxy)obj;
			if (this.causeProxy == null) {
				if (other.causeProxy != null) {
					return false;
				}
			} else if (!this.causeProxy.equals(other.causeProxy)) {
				return false;
			}

			if (this.commonElementCount != other.commonElementCount) {
				return false;
			} else {
				if (this.name == null) {
					if (other.name != null) {
						return false;
					}
				} else if (!this.name.equals(other.name)) {
					return false;
				}

				return !Arrays.equals(this.extendedStackTrace, other.extendedStackTrace) ? false : Arrays.equals(this.suppressedProxies, other.suppressedProxies);
			}
		}
	}

	private void formatCause(StringBuilder sb, String prefix, ThrowableProxy cause, List<String> ignorePackages, TextRenderer textRenderer) {
		this.formatThrowableProxy(sb, prefix, "Caused by: ", cause, ignorePackages, textRenderer);
	}

	private void formatThrowableProxy(
		StringBuilder sb, String prefix, String causeLabel, ThrowableProxy throwableProxy, List<String> ignorePackages, TextRenderer textRenderer
	) {
		if (throwableProxy != null) {
			textRenderer.render(prefix, sb, "Prefix");
			textRenderer.render(causeLabel, sb, "CauseLabel");
			throwableProxy.renderOn(sb, textRenderer);
			textRenderer.render(EOL_STR, sb, "Text");
			this.formatElements(
				sb, prefix, throwableProxy.commonElementCount, throwableProxy.getStackTrace(), throwableProxy.extendedStackTrace, ignorePackages, textRenderer
			);
			this.formatSuppressed(sb, prefix + "\t", throwableProxy.suppressedProxies, ignorePackages, textRenderer);
			this.formatCause(sb, prefix, throwableProxy.causeProxy, ignorePackages, textRenderer);
		}
	}

	void renderOn(StringBuilder output, TextRenderer textRenderer) {
		String msg = this.message;
		textRenderer.render(this.name, output, "Name");
		if (msg != null) {
			textRenderer.render(": ", output, "NameMessageSeparator");
			textRenderer.render(msg, output, "Message");
		}
	}

	private void formatSuppressed(StringBuilder sb, String prefix, ThrowableProxy[] suppressedProxies, List<String> ignorePackages, TextRenderer textRenderer) {
		if (suppressedProxies != null) {
			for (ThrowableProxy suppressedProxy : suppressedProxies) {
				this.formatThrowableProxy(sb, prefix, "Suppressed: ", suppressedProxy, ignorePackages, textRenderer);
			}
		}
	}

	private void formatElements(
		StringBuilder sb,
		String prefix,
		int commonCount,
		StackTraceElement[] causedTrace,
		ExtendedStackTraceElement[] extStackTrace,
		List<String> ignorePackages,
		TextRenderer textRenderer
	) {
		if (ignorePackages != null && !ignorePackages.isEmpty()) {
			int count = 0;

			for (int i = 0; i < extStackTrace.length; i++) {
				if (!this.ignoreElement(causedTrace[i], ignorePackages)) {
					if (count > 0) {
						this.appendSuppressedCount(sb, prefix, count, textRenderer);
						count = 0;
					}

					this.formatEntry(extStackTrace[i], sb, prefix, textRenderer);
				} else {
					count++;
				}
			}

			if (count > 0) {
				this.appendSuppressedCount(sb, prefix, count, textRenderer);
			}
		} else {
			for (ExtendedStackTraceElement element : extStackTrace) {
				this.formatEntry(element, sb, prefix, textRenderer);
			}
		}

		if (commonCount != 0) {
			textRenderer.render(prefix, sb, "Prefix");
			textRenderer.render("\t... ", sb, "More");
			textRenderer.render(Integer.toString(commonCount), sb, "More");
			textRenderer.render(" more", sb, "More");
			textRenderer.render(EOL_STR, sb, "Text");
		}
	}

	private void appendSuppressedCount(StringBuilder sb, String prefix, int count, TextRenderer textRenderer) {
		textRenderer.render(prefix, sb, "Prefix");
		if (count == 1) {
			textRenderer.render("\t... ", sb, "Suppressed");
		} else {
			textRenderer.render("\t... suppressed ", sb, "Suppressed");
			textRenderer.render(Integer.toString(count), sb, "Suppressed");
			textRenderer.render(" lines", sb, "Suppressed");
		}

		textRenderer.render(EOL_STR, sb, "Text");
	}

	private void formatEntry(ExtendedStackTraceElement extStackTraceElement, StringBuilder sb, String prefix, TextRenderer textRenderer) {
		textRenderer.render(prefix, sb, "Prefix");
		textRenderer.render("\tat ", sb, "At");
		extStackTraceElement.renderOn(sb, textRenderer);
		textRenderer.render(EOL_STR, sb, "Text");
	}

	public void formatWrapper(StringBuilder sb, ThrowableProxy cause) {
		this.formatWrapper(sb, cause, null, PlainTextRenderer.getInstance());
	}

	public void formatWrapper(StringBuilder sb, ThrowableProxy cause, List<String> ignorePackages) {
		this.formatWrapper(sb, cause, ignorePackages, PlainTextRenderer.getInstance());
	}

	public void formatWrapper(StringBuilder sb, ThrowableProxy cause, List<String> ignorePackages, TextRenderer textRenderer) {
		Throwable caused = cause.getCauseProxy() != null ? cause.getCauseProxy().getThrowable() : null;
		if (caused != null) {
			this.formatWrapper(sb, cause.causeProxy, ignorePackages, textRenderer);
			sb.append("Wrapped by: ");
		}

		cause.renderOn(sb, textRenderer);
		textRenderer.render(EOL_STR, sb, "Text");
		this.formatElements(sb, "", cause.commonElementCount, cause.getThrowable().getStackTrace(), cause.extendedStackTrace, ignorePackages, textRenderer);
	}

	public ThrowableProxy getCauseProxy() {
		return this.causeProxy;
	}

	public String getCauseStackTraceAsString() {
		return this.getCauseStackTraceAsString(null, PlainTextRenderer.getInstance());
	}

	public String getCauseStackTraceAsString(List<String> packages) {
		return this.getCauseStackTraceAsString(packages, PlainTextRenderer.getInstance());
	}

	public String getCauseStackTraceAsString(List<String> ignorePackages, TextRenderer textRenderer) {
		StringBuilder sb = new StringBuilder();
		if (this.causeProxy != null) {
			this.formatWrapper(sb, this.causeProxy, ignorePackages, textRenderer);
			sb.append("Wrapped by: ");
		}

		this.renderOn(sb, textRenderer);
		textRenderer.render(EOL_STR, sb, "Text");
		this.formatElements(sb, "", 0, this.throwable.getStackTrace(), this.extendedStackTrace, ignorePackages, textRenderer);
		return sb.toString();
	}

	public int getCommonElementCount() {
		return this.commonElementCount;
	}

	public ExtendedStackTraceElement[] getExtendedStackTrace() {
		return this.extendedStackTrace;
	}

	public String getExtendedStackTraceAsString() {
		return this.getExtendedStackTraceAsString(null, PlainTextRenderer.getInstance());
	}

	public String getExtendedStackTraceAsString(List<String> ignorePackages) {
		return this.getExtendedStackTraceAsString(ignorePackages, PlainTextRenderer.getInstance());
	}

	public String getExtendedStackTraceAsString(List<String> ignorePackages, TextRenderer textRenderer) {
		StringBuilder sb = new StringBuilder(1024);
		textRenderer.render(this.name, sb, "Name");
		textRenderer.render(": ", sb, "NameMessageSeparator");
		textRenderer.render(this.message, sb, "Message");
		textRenderer.render(EOL_STR, sb, "Text");
		StackTraceElement[] causedTrace = this.throwable != null ? this.throwable.getStackTrace() : null;
		this.formatElements(sb, "", 0, causedTrace, this.extendedStackTrace, ignorePackages, textRenderer);
		this.formatSuppressed(sb, "\t", this.suppressedProxies, ignorePackages, textRenderer);
		this.formatCause(sb, "", this.causeProxy, ignorePackages, textRenderer);
		return sb.toString();
	}

	public String getLocalizedMessage() {
		return this.localizedMessage;
	}

	public String getMessage() {
		return this.message;
	}

	public String getName() {
		return this.name;
	}

	public StackTraceElement[] getStackTrace() {
		return this.throwable == null ? null : this.throwable.getStackTrace();
	}

	public ThrowableProxy[] getSuppressedProxies() {
		return this.suppressedProxies;
	}

	public String getSuppressedStackTrace() {
		ThrowableProxy[] suppressed = this.getSuppressedProxies();
		if (suppressed != null && suppressed.length != 0) {
			StringBuilder sb = new StringBuilder("Suppressed Stack Trace Elements:").append('\n');

			for (ThrowableProxy proxy : suppressed) {
				sb.append(proxy.getExtendedStackTraceAsString());
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public Throwable getThrowable() {
		return this.throwable;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.causeProxy == null ? 0 : this.causeProxy.hashCode());
		result = 31 * result + this.commonElementCount;
		result = 31 * result + (this.extendedStackTrace == null ? 0 : Arrays.hashCode(this.extendedStackTrace));
		result = 31 * result + (this.suppressedProxies == null ? 0 : Arrays.hashCode(this.suppressedProxies));
		return 31 * result + (this.name == null ? 0 : this.name.hashCode());
	}

	private boolean ignoreElement(StackTraceElement element, List<String> ignorePackages) {
		if (ignorePackages != null) {
			String className = element.getClassName();

			for (String pkg : ignorePackages) {
				if (className.startsWith(pkg)) {
					return true;
				}
			}
		}

		return false;
	}

	private Class<?> loadClass(ClassLoader lastLoader, String className) {
		if (lastLoader != null) {
			try {
				Class<?> clazz = lastLoader.loadClass(className);
				if (clazz != null) {
					return clazz;
				}
			} catch (Throwable var7) {
			}
		}

		try {
			Class<?> clazz = LoaderUtil.loadClass(className);
			return clazz;
		} catch (NoClassDefFoundError | ClassNotFoundException var5) {
			return this.loadClass(className);
		} catch (SecurityException var6) {
			return null;
		}
	}

	private Class<?> loadClass(String className) {
		try {
			return Loader.loadClass(className, this.getClass().getClassLoader());
		} catch (NoClassDefFoundError | SecurityException | ClassNotFoundException var3) {
			return null;
		}
	}

	private ThrowableProxy.CacheEntry toCacheEntry(StackTraceElement stackTraceElement, Class<?> callerClass, boolean exact) {
		String location = "?";
		String version = "?";
		ClassLoader lastLoader = null;
		if (callerClass != null) {
			try {
				CodeSource source = callerClass.getProtectionDomain().getCodeSource();
				if (source != null) {
					URL locationURL = source.getLocation();
					if (locationURL != null) {
						String str = locationURL.toString().replace('\\', '/');
						int index = str.lastIndexOf("/");
						if (index >= 0 && index == str.length() - 1) {
							index = str.lastIndexOf("/", index - 1);
							location = str.substring(index + 1);
						} else {
							location = str.substring(index + 1);
						}
					}
				}
			} catch (Exception var11) {
			}

			Package pkg = callerClass.getPackage();
			if (pkg != null) {
				String ver = pkg.getImplementationVersion();
				if (ver != null) {
					version = ver;
				}
			}

			lastLoader = callerClass.getClassLoader();
		}

		return new ThrowableProxy.CacheEntry(new ExtendedClassInfo(exact, location, version), lastLoader);
	}

	ExtendedStackTraceElement[] toExtendedStackTrace(
		Stack<Class<?>> stack, Map<String, ThrowableProxy.CacheEntry> map, StackTraceElement[] rootTrace, StackTraceElement[] stackTrace
	) {
		int stackLength;
		if (rootTrace != null) {
			int rootIndex = rootTrace.length - 1;

			int stackIndex;
			for (stackIndex = stackTrace.length - 1; rootIndex >= 0 && stackIndex >= 0 && rootTrace[rootIndex].equals(stackTrace[stackIndex]); stackIndex--) {
				rootIndex--;
			}

			this.commonElementCount = stackTrace.length - 1 - stackIndex;
			stackLength = stackIndex + 1;
		} else {
			this.commonElementCount = 0;
			stackLength = stackTrace.length;
		}

		ExtendedStackTraceElement[] extStackTrace = new ExtendedStackTraceElement[stackLength];
		Class<?> clazz = stack.isEmpty() ? null : (Class)stack.peek();
		ClassLoader lastLoader = null;

		for (int i = stackLength - 1; i >= 0; i--) {
			StackTraceElement stackTraceElement = stackTrace[i];
			String className = stackTraceElement.getClassName();
			ExtendedClassInfo extClassInfo;
			if (clazz != null && className.equals(clazz.getName())) {
				ThrowableProxy.CacheEntry entry = this.toCacheEntry(stackTraceElement, clazz, true);
				extClassInfo = entry.element;
				lastLoader = entry.loader;
				stack.pop();
				clazz = stack.isEmpty() ? null : (Class)stack.peek();
			} else {
				ThrowableProxy.CacheEntry cacheEntry = (ThrowableProxy.CacheEntry)map.get(className);
				if (cacheEntry != null) {
					extClassInfo = cacheEntry.element;
					if (cacheEntry.loader != null) {
						lastLoader = cacheEntry.loader;
					}
				} else {
					ThrowableProxy.CacheEntry entry = this.toCacheEntry(stackTraceElement, this.loadClass(lastLoader, className), false);
					extClassInfo = entry.element;
					map.put(stackTraceElement.toString(), entry);
					if (entry.loader != null) {
						lastLoader = entry.loader;
					}
				}
			}

			extStackTrace[i] = new ExtendedStackTraceElement(stackTraceElement, extClassInfo);
		}

		return extStackTrace;
	}

	public String toString() {
		String msg = this.message;
		return msg != null ? this.name + ": " + msg : this.name;
	}

	private ThrowableProxy[] toSuppressedProxies(Throwable thrown, Set<Throwable> suppressedVisited) {
		try {
			Throwable[] suppressed = thrown.getSuppressed();
			if (suppressed == null) {
				return EMPTY_THROWABLE_PROXY_ARRAY;
			} else {
				List<ThrowableProxy> proxies = new ArrayList(suppressed.length);
				if (suppressedVisited == null) {
					suppressedVisited = new HashSet(proxies.size());
				}

				for (int i = 0; i < suppressed.length; i++) {
					Throwable candidate = suppressed[i];
					if (!suppressedVisited.contains(candidate)) {
						suppressedVisited.add(candidate);
						proxies.add(new ThrowableProxy(candidate, suppressedVisited));
					}
				}

				return (ThrowableProxy[])proxies.toArray(new ThrowableProxy[proxies.size()]);
			}
		} catch (Exception var7) {
			StatusLogger.getLogger().error(var7);
			return null;
		}
	}

	static class CacheEntry {
		private final ExtendedClassInfo element;
		private final ClassLoader loader;

		public CacheEntry(ExtendedClassInfo element, ClassLoader loader) {
			this.element = element;
			this.loader = loader;
		}
	}
}
