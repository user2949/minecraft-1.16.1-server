package org.apache.commons.lang3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SerializationUtils {
	public static <T extends Serializable> T clone(T object) {
		if (object == null) {
			return null;
		} else {
			byte[] objectData = serialize(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
			SerializationUtils.ClassLoaderAwareObjectInputStream in = null;

			Serializable var5;
			try {
				in = new SerializationUtils.ClassLoaderAwareObjectInputStream(bais, object.getClass().getClassLoader());
				T readObject = (T)in.readObject();
				var5 = readObject;
			} catch (ClassNotFoundException var14) {
				throw new SerializationException("ClassNotFoundException while reading cloned object data", var14);
			} catch (IOException var15) {
				throw new SerializationException("IOException while reading cloned object data", var15);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException var16) {
					throw new SerializationException("IOException on closing cloned object data InputStream.", var16);
				}
			}

			return (T)var5;
		}
	}

	public static <T extends Serializable> T roundtrip(T msg) {
		return deserialize(serialize(msg));
	}

	public static void serialize(Serializable obj, OutputStream outputStream) {
		if (outputStream == null) {
			throw new IllegalArgumentException("The OutputStream must not be null");
		} else {
			ObjectOutputStream out = null;

			try {
				out = new ObjectOutputStream(outputStream);
				out.writeObject(obj);
			} catch (IOException var11) {
				throw new SerializationException(var11);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException var10) {
				}
			}
		}
	}

	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		serialize(obj, baos);
		return baos.toByteArray();
	}

	public static <T> T deserialize(InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException("The InputStream must not be null");
		} else {
			ObjectInputStream in = null;

			Object var3;
			try {
				in = new ObjectInputStream(inputStream);
				T obj = (T)in.readObject();
				var3 = obj;
			} catch (ClassNotFoundException var13) {
				throw new SerializationException(var13);
			} catch (IOException var14) {
				throw new SerializationException(var14);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException var12) {
				}
			}

			return (T)var3;
		}
	}

	public static <T> T deserialize(byte[] objectData) {
		if (objectData == null) {
			throw new IllegalArgumentException("The byte[] must not be null");
		} else {
			return deserialize(new ByteArrayInputStream(objectData));
		}
	}

	static class ClassLoaderAwareObjectInputStream extends ObjectInputStream {
		private static final Map<String, Class<?>> primitiveTypes = new HashMap();
		private final ClassLoader classLoader;

		public ClassLoaderAwareObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
			super(in);
			this.classLoader = classLoader;
		}

		protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
			String name = desc.getName();

			try {
				return Class.forName(name, false, this.classLoader);
			} catch (ClassNotFoundException var7) {
				try {
					return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
				} catch (ClassNotFoundException var6) {
					Class<?> cls = (Class<?>)primitiveTypes.get(name);
					if (cls != null) {
						return cls;
					} else {
						throw var6;
					}
				}
			}
		}

		static {
			primitiveTypes.put("byte", byte.class);
			primitiveTypes.put("short", short.class);
			primitiveTypes.put("int", int.class);
			primitiveTypes.put("long", long.class);
			primitiveTypes.put("float", float.class);
			primitiveTypes.put("double", double.class);
			primitiveTypes.put("boolean", boolean.class);
			primitiveTypes.put("char", char.class);
			primitiveTypes.put("void", void.class);
		}
	}
}
