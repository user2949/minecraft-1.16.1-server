package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
	private final Map<Type, InstanceCreator<?>> instanceCreators;

	public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators) {
		this.instanceCreators = instanceCreators;
	}

	public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
		final Type type = typeToken.getType();
		Class<? super T> rawType = typeToken.getRawType();
		final InstanceCreator<T> typeCreator = (InstanceCreator<T>)this.instanceCreators.get(type);
		if (typeCreator != null) {
			return new ObjectConstructor<T>() {
				@Override
				public T construct() {
					return typeCreator.createInstance(type);
				}
			};
		} else {
			final InstanceCreator<T> rawTypeCreator = (InstanceCreator<T>)this.instanceCreators.get(rawType);
			if (rawTypeCreator != null) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return rawTypeCreator.createInstance(type);
					}
				};
			} else {
				ObjectConstructor<T> defaultConstructor = this.newDefaultConstructor(rawType);
				if (defaultConstructor != null) {
					return defaultConstructor;
				} else {
					ObjectConstructor<T> defaultImplementation = this.newDefaultImplementationConstructor(type, rawType);
					return defaultImplementation != null ? defaultImplementation : this.newUnsafeAllocator(type, rawType);
				}
			}
		}
	}

	private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType) {
		try {
			final Constructor<? super T> constructor = rawType.getDeclaredConstructor();
			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}

			return new ObjectConstructor<T>() {
				@Override
				public T construct() {
					try {
						Object[] args = null;
						return (T)constructor.newInstance(args);
					} catch (InstantiationException var2) {
						throw new RuntimeException("Failed to invoke " + constructor + " with no args", var2);
					} catch (InvocationTargetException var3) {
						throw new RuntimeException("Failed to invoke " + constructor + " with no args", var3.getTargetException());
					} catch (IllegalAccessException var4) {
						throw new AssertionError(var4);
					}
				}
			};
		} catch (NoSuchMethodException var3) {
			return null;
		}
	}

	private <T> ObjectConstructor<T> newDefaultImplementationConstructor(Type type, Class<? super T> rawType) {
		if (Collection.class.isAssignableFrom(rawType)) {
			if (SortedSet.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new TreeSet());
					}
				};
			} else if (EnumSet.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						if (type instanceof ParameterizedType) {
							Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
							if (elementType instanceof Class) {
								return (T)EnumSet.noneOf((Class)elementType);
							} else {
								throw new JsonIOException("Invalid EnumSet type: " + type.toString());
							}
						} else {
							throw new JsonIOException("Invalid EnumSet type: " + type.toString());
						}
					}
				};
			} else if (Set.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new LinkedHashSet());
					}
				};
			} else {
				return Queue.class.isAssignableFrom(rawType) ? new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new ArrayDeque());
					}
				} : new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new ArrayList());
					}
				};
			}
		} else if (Map.class.isAssignableFrom(rawType)) {
			if (ConcurrentNavigableMap.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new ConcurrentSkipListMap());
					}
				};
			} else if (ConcurrentMap.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new ConcurrentHashMap());
					}
				};
			} else if (SortedMap.class.isAssignableFrom(rawType)) {
				return new ObjectConstructor<T>() {
					@Override
					public T construct() {
						return (T)(new TreeMap());
					}
				};
			} else {
				return type instanceof ParameterizedType
						&& !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())
					? new ObjectConstructor<T>() {
						@Override
						public T construct() {
							return (T)(new LinkedHashMap());
						}
					}
					: new ObjectConstructor<T>() {
						@Override
						public T construct() {
							return (T)(new LinkedTreeMap());
						}
					};
			}
		} else {
			return null;
		}
	}

	private <T> ObjectConstructor<T> newUnsafeAllocator(Type type, Class<? super T> rawType) {
		return new ObjectConstructor<T>() {
			private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

			@Override
			public T construct() {
				try {
					return this.unsafeAllocator.newInstance((Class<T>)rawType);
				} catch (Exception var2) {
					throw new RuntimeException(
						"Unable to invoke no-args constructor for " + type + ". Register an InstanceCreator with Gson for this type may fix this problem.", var2
					);
				}
			}
		};
	}

	public String toString() {
		return this.instanceCreators.toString();
	}
}
