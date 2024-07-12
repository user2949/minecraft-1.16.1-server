import com.google.common.base.MoreObjects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class yh<T extends yh<T>> {
	private static final Logger a = LogManager.getLogger();
	private final Properties b;

	public yh(Properties properties) {
		this.b = properties;
	}

	public static Properties b(Path path) {
		Properties properties2 = new Properties();

		try {
			InputStream inputStream3 = Files.newInputStream(path);
			Throwable var3 = null;

			try {
				properties2.load(inputStream3);
			} catch (Throwable var13) {
				var3 = var13;
				throw var13;
			} finally {
				if (inputStream3 != null) {
					if (var3 != null) {
						try {
							inputStream3.close();
						} catch (Throwable var12) {
							var3.addSuppressed(var12);
						}
					} else {
						inputStream3.close();
					}
				}
			}
		} catch (IOException var15) {
			a.error("Failed to load properties from file: " + path);
		}

		return properties2;
	}

	public void c(Path path) {
		try {
			OutputStream outputStream3 = Files.newOutputStream(path);
			Throwable var3 = null;

			try {
				this.b.store(outputStream3, "Minecraft server properties");
			} catch (Throwable var13) {
				var3 = var13;
				throw var13;
			} finally {
				if (outputStream3 != null) {
					if (var3 != null) {
						try {
							outputStream3.close();
						} catch (Throwable var12) {
							var3.addSuppressed(var12);
						}
					} else {
						outputStream3.close();
					}
				}
			}
		} catch (IOException var15) {
			a.error("Failed to store properties to file: " + path);
		}
	}

	private static <V extends Number> Function<String, V> a(Function<String, V> function) {
		return string -> {
			try {
				return (Number)function.apply(string);
			} catch (NumberFormatException var3) {
				return null;
			}
		};
	}

	protected static <V> Function<String, V> a(IntFunction<V> intFunction, Function<String, V> function) {
		return string -> {
			try {
				return intFunction.apply(Integer.parseInt(string));
			} catch (NumberFormatException var4) {
				return function.apply(string);
			}
		};
	}

	@Nullable
	private String c(String string) {
		return (String)this.b.get(string);
	}

	@Nullable
	protected <V> V a(String string, Function<String, V> function) {
		String string4 = this.c(string);
		if (string4 == null) {
			return null;
		} else {
			this.b.remove(string);
			return (V)function.apply(string4);
		}
	}

	protected <V> V a(String string, Function<String, V> function2, Function<V, String> function3, V object) {
		String string6 = this.c(string);
		V object7 = MoreObjects.firstNonNull((V)(string6 != null ? function2.apply(string6) : null), object);
		this.b.put(string, function3.apply(object7));
		return object7;
	}

	protected <V> yh<T>.a<V> b(String string, Function<String, V> function2, Function<V, String> function3, V object) {
		String string6 = this.c(string);
		V object7 = MoreObjects.firstNonNull((V)(string6 != null ? function2.apply(string6) : null), object);
		this.b.put(string, function3.apply(object7));
		return new yh.a<>(string, object7, function3);
	}

	protected <V> V a(String string, Function<String, V> function2, UnaryOperator<V> unaryOperator, Function<V, String> function4, V object) {
		return this.a(string, stringx -> {
			V object4 = (V)function2.apply(stringx);
			return object4 != null ? unaryOperator.apply(object4) : null;
		}, function4, object);
	}

	protected <V> V a(String string, Function<String, V> function, V object) {
		return this.a(string, function, Objects::toString, object);
	}

	protected <V> yh<T>.a<V> b(String string, Function<String, V> function, V object) {
		return this.b(string, function, Objects::toString, object);
	}

	protected String a(String string1, String string2) {
		return this.a(string1, Function.identity(), Function.identity(), string2);
	}

	@Nullable
	protected String a(String string) {
		return this.a(string, Function.identity());
	}

	protected int a(String string, int integer) {
		return this.a(string, a(Integer::parseInt), Integer.valueOf(integer));
	}

	protected yh<T>.a<Integer> b(String string, int integer) {
		return this.b(string, a(Integer::parseInt), integer);
	}

	protected int a(String string, UnaryOperator<Integer> unaryOperator, int integer) {
		return this.a(string, a(Integer::parseInt), unaryOperator, Objects::toString, integer);
	}

	protected long a(String string, long long2) {
		return this.a(string, a(Long::parseLong), long2);
	}

	protected boolean a(String string, boolean boolean2) {
		return this.a(string, Boolean::valueOf, boolean2);
	}

	protected yh<T>.a<Boolean> b(String string, boolean boolean2) {
		return this.b(string, Boolean::valueOf, boolean2);
	}

	@Nullable
	protected Boolean b(String string) {
		return this.a(string, Boolean::valueOf);
	}

	protected Properties a() {
		Properties properties2 = new Properties();
		properties2.putAll(this.b);
		return properties2;
	}

	protected abstract T b(Properties properties);

	public class a<V> implements Supplier<V> {
		private final String b;
		private final V c;
		private final Function<V, String> d;

		private a(String string, V object, Function<V, String> function) {
			this.b = string;
			this.c = object;
			this.d = function;
		}

		public V get() {
			return this.c;
		}

		public T a(V object) {
			Properties properties3 = yh.this.a();
			properties3.put(this.b, this.d.apply(object));
			return yh.this.b(properties3);
		}
	}
}
