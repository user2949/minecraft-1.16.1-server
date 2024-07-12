package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class HashCodeBuilder implements Builder<Integer> {
	private static final int DEFAULT_INITIAL_VALUE = 17;
	private static final int DEFAULT_MULTIPLIER_VALUE = 37;
	private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal();
	private final int iConstant;
	private int iTotal = 0;

	static Set<IDKey> getRegistry() {
		return (Set<IDKey>)REGISTRY.get();
	}

	static boolean isRegistered(Object value) {
		Set<IDKey> registry = getRegistry();
		return registry != null && registry.contains(new IDKey(value));
	}

	private static void reflectionAppend(Object object, Class<?> clazz, HashCodeBuilder builder, boolean useTransients, String[] excludeFields) {
		if (!isRegistered(object)) {
			try {
				register(object);
				Field[] fields = clazz.getDeclaredFields();
				AccessibleObject.setAccessible(fields, true);

				for (Field field : fields) {
					if (!ArrayUtils.contains(excludeFields, field.getName())
						&& !field.getName().contains("$")
						&& (useTransients || !Modifier.isTransient(field.getModifiers()))
						&& !Modifier.isStatic(field.getModifiers())
						&& !field.isAnnotationPresent(HashCodeExclude.class)) {
						try {
							Object fieldValue = field.get(object);
							builder.append(fieldValue);
						} catch (IllegalAccessException var14) {
							throw new InternalError("Unexpected IllegalAccessException");
						}
					}
				}
			} finally {
				unregister(object);
			}
		}
	}

	public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object) {
		return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null);
	}

	public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients) {
		return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null);
	}

	public static <T> int reflectionHashCode(
		int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object, boolean testTransients, Class<? super T> reflectUpToClass, String... excludeFields
	) {
		if (object == null) {
			throw new IllegalArgumentException("The object to build a hash code for must not be null");
		} else {
			HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
			Class<?> clazz = object.getClass();
			reflectionAppend(object, clazz, builder, testTransients, excludeFields);

			while (clazz.getSuperclass() != null && clazz != reflectUpToClass) {
				clazz = clazz.getSuperclass();
				reflectionAppend(object, clazz, builder, testTransients, excludeFields);
			}

			return builder.toHashCode();
		}
	}

	public static int reflectionHashCode(Object object, boolean testTransients) {
		return reflectionHashCode(17, 37, object, testTransients, null);
	}

	public static int reflectionHashCode(Object object, Collection<String> excludeFields) {
		return reflectionHashCode(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
	}

	public static int reflectionHashCode(Object object, String... excludeFields) {
		return reflectionHashCode(17, 37, object, false, null, excludeFields);
	}

	private static void register(Object value) {
		Set<IDKey> registry = getRegistry();
		if (registry == null) {
			registry = new HashSet();
			REGISTRY.set(registry);
		}

		registry.add(new IDKey(value));
	}

	private static void unregister(Object value) {
		Set<IDKey> registry = getRegistry();
		if (registry != null) {
			registry.remove(new IDKey(value));
			if (registry.isEmpty()) {
				REGISTRY.remove();
			}
		}
	}

	public HashCodeBuilder() {
		this.iConstant = 37;
		this.iTotal = 17;
	}

	public HashCodeBuilder(int initialOddNumber, int multiplierOddNumber) {
		Validate.isTrue(initialOddNumber % 2 != 0, "HashCodeBuilder requires an odd initial value");
		Validate.isTrue(multiplierOddNumber % 2 != 0, "HashCodeBuilder requires an odd multiplier");
		this.iConstant = multiplierOddNumber;
		this.iTotal = initialOddNumber;
	}

	public HashCodeBuilder append(boolean value) {
		this.iTotal = this.iTotal * this.iConstant + (value ? 0 : 1);
		return this;
	}

	public HashCodeBuilder append(boolean[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (boolean element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(byte value) {
		this.iTotal = this.iTotal * this.iConstant + value;
		return this;
	}

	public HashCodeBuilder append(byte[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (byte element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(char value) {
		this.iTotal = this.iTotal * this.iConstant + value;
		return this;
	}

	public HashCodeBuilder append(char[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (char element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(double value) {
		return this.append(Double.doubleToLongBits(value));
	}

	public HashCodeBuilder append(double[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (double element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(float value) {
		this.iTotal = this.iTotal * this.iConstant + Float.floatToIntBits(value);
		return this;
	}

	public HashCodeBuilder append(float[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (float element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(int value) {
		this.iTotal = this.iTotal * this.iConstant + value;
		return this;
	}

	public HashCodeBuilder append(int[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (int element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(long value) {
		this.iTotal = this.iTotal * this.iConstant + (int)(value ^ value >> 32);
		return this;
	}

	public HashCodeBuilder append(long[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (long element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(Object object) {
		if (object == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else if (object.getClass().isArray()) {
			this.appendArray(object);
		} else {
			this.iTotal = this.iTotal * this.iConstant + object.hashCode();
		}

		return this;
	}

	private void appendArray(Object object) {
		if (object instanceof long[]) {
			this.append((long[])object);
		} else if (object instanceof int[]) {
			this.append((int[])object);
		} else if (object instanceof short[]) {
			this.append((short[])object);
		} else if (object instanceof char[]) {
			this.append((char[])object);
		} else if (object instanceof byte[]) {
			this.append((byte[])object);
		} else if (object instanceof double[]) {
			this.append((double[])object);
		} else if (object instanceof float[]) {
			this.append((float[])object);
		} else if (object instanceof boolean[]) {
			this.append((boolean[])object);
		} else {
			this.append((Object[])object);
		}
	}

	public HashCodeBuilder append(Object[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (Object element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder append(short value) {
		this.iTotal = this.iTotal * this.iConstant + value;
		return this;
	}

	public HashCodeBuilder append(short[] array) {
		if (array == null) {
			this.iTotal = this.iTotal * this.iConstant;
		} else {
			for (short element : array) {
				this.append(element);
			}
		}

		return this;
	}

	public HashCodeBuilder appendSuper(int superHashCode) {
		this.iTotal = this.iTotal * this.iConstant + superHashCode;
		return this;
	}

	public int toHashCode() {
		return this.iTotal;
	}

	public Integer build() {
		return this.toHashCode();
	}

	public int hashCode() {
		return this.toHashCode();
	}
}
