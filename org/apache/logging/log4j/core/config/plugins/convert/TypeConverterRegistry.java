package org.apache.logging.log4j.core.config.plugins.convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;
import java.util.UnknownFormatConversionException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.core.util.TypeUtil;
import org.apache.logging.log4j.status.StatusLogger;

public class TypeConverterRegistry {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private static volatile TypeConverterRegistry INSTANCE;
	private static final Object INSTANCE_LOCK = new Object();
	private final ConcurrentMap<Type, TypeConverter<?>> registry = new ConcurrentHashMap();

	public static TypeConverterRegistry getInstance() {
		TypeConverterRegistry result = INSTANCE;
		if (result == null) {
			synchronized (INSTANCE_LOCK) {
				result = INSTANCE;
				if (result == null) {
					INSTANCE = result = new TypeConverterRegistry();
				}
			}
		}

		return result;
	}

	public TypeConverter<?> findCompatibleConverter(Type type) {
		Objects.requireNonNull(type, "No type was provided");
		TypeConverter<?> primary = (TypeConverter<?>)this.registry.get(type);
		if (primary != null) {
			return primary;
		} else {
			if (type instanceof Class) {
				Class<?> clazz = (Class<?>)type;
				if (clazz.isEnum()) {
					EnumConverter<? extends Enum> converter = new EnumConverter(clazz.asSubclass(Enum.class));
					this.registry.putIfAbsent(type, converter);
					return converter;
				}
			}

			for (Entry<Type, TypeConverter<?>> entry : this.registry.entrySet()) {
				Type key = (Type)entry.getKey();
				if (TypeUtil.isAssignable(type, key)) {
					LOGGER.debug("Found compatible TypeConverter<{}> for type [{}].", key, type);
					TypeConverter<?> value = (TypeConverter<?>)entry.getValue();
					this.registry.putIfAbsent(type, value);
					return value;
				}
			}

			throw new UnknownFormatConversionException(type.toString());
		}
	}

	private TypeConverterRegistry() {
		LOGGER.trace("TypeConverterRegistry initializing.");
		PluginManager manager = new PluginManager("TypeConverter");
		manager.collectPlugins();
		this.loadKnownTypeConverters(manager.getPlugins().values());
		this.registerPrimitiveTypes();
	}

	private void loadKnownTypeConverters(Collection<PluginType<?>> knownTypes) {
		for (PluginType<?> knownType : knownTypes) {
			Class<?> clazz = knownType.getPluginClass();
			if (TypeConverter.class.isAssignableFrom(clazz)) {
				Class<? extends TypeConverter> pluginClass = clazz.asSubclass(TypeConverter.class);
				Type conversionType = getTypeConverterSupportedType(pluginClass);
				TypeConverter<?> converter = ReflectionUtil.instantiate((Class<TypeConverter<?>>)pluginClass);
				if (this.registry.putIfAbsent(conversionType, converter) != null) {
					LOGGER.warn("Found a TypeConverter [{}] for type [{}] that already exists.", converter, conversionType);
				}
			}
		}
	}

	private static Type getTypeConverterSupportedType(Class<? extends TypeConverter> typeConverterClass) {
		for (Type type : typeConverterClass.getGenericInterfaces()) {
			if (type instanceof ParameterizedType) {
				ParameterizedType pType = (ParameterizedType)type;
				if (TypeConverter.class.equals(pType.getRawType())) {
					return pType.getActualTypeArguments()[0];
				}
			}
		}

		return void.class;
	}

	private void registerPrimitiveTypes() {
		this.registerTypeAlias(Boolean.class, boolean.class);
		this.registerTypeAlias(Byte.class, byte.class);
		this.registerTypeAlias(Character.class, char.class);
		this.registerTypeAlias(Double.class, double.class);
		this.registerTypeAlias(Float.class, float.class);
		this.registerTypeAlias(Integer.class, int.class);
		this.registerTypeAlias(Long.class, long.class);
		this.registerTypeAlias(Short.class, short.class);
	}

	private void registerTypeAlias(Type knownType, Type aliasType) {
		this.registry.putIfAbsent(aliasType, this.registry.get(knownType));
	}
}
