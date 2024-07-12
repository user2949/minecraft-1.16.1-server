package org.apache.logging.log4j.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

public class ThreadContextDataInjector {
	public static void copyProperties(List<Property> properties, StringMap result) {
		if (properties != null) {
			for (int i = 0; i < properties.size(); i++) {
				Property prop = (Property)properties.get(i);
				result.putValue(prop.getName(), prop.getValue());
			}
		}
	}

	public static class ForCopyOnWriteThreadContextMap implements ContextDataInjector {
		@Override
		public StringMap injectContextData(List<Property> props, StringMap ignore) {
			StringMap immutableCopy = ThreadContext.getThreadContextMap().getReadOnlyContextData();
			if (props != null && !props.isEmpty()) {
				StringMap result = ContextDataFactory.createContextData(props.size() + immutableCopy.size());
				ThreadContextDataInjector.copyProperties(props, result);
				result.putAll(immutableCopy);
				return result;
			} else {
				return immutableCopy;
			}
		}

		@Override
		public ReadOnlyStringMap rawContextData() {
			return ThreadContext.getThreadContextMap().getReadOnlyContextData();
		}
	}

	public static class ForDefaultThreadContextMap implements ContextDataInjector {
		@Override
		public StringMap injectContextData(List<Property> props, StringMap ignore) {
			Map<String, String> copy = ThreadContext.getImmutableContext();
			if (props != null && !props.isEmpty()) {
				StringMap result = new JdkMapAdapterStringMap(new HashMap(copy));

				for (int i = 0; i < props.size(); i++) {
					Property prop = (Property)props.get(i);
					if (!copy.containsKey(prop.getName())) {
						result.putValue(prop.getName(), prop.getValue());
					}
				}

				result.freeze();
				return result;
			} else {
				return (StringMap)(copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : frozenStringMap(copy));
			}
		}

		private static JdkMapAdapterStringMap frozenStringMap(Map<String, String> copy) {
			JdkMapAdapterStringMap result = new JdkMapAdapterStringMap(copy);
			result.freeze();
			return result;
		}

		@Override
		public ReadOnlyStringMap rawContextData() {
			ReadOnlyThreadContextMap map = ThreadContext.getThreadContextMap();
			if (map instanceof ReadOnlyStringMap) {
				return (ReadOnlyStringMap)map;
			} else {
				Map<String, String> copy = ThreadContext.getImmutableContext();
				return (ReadOnlyStringMap)(copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : new JdkMapAdapterStringMap(copy));
			}
		}
	}

	public static class ForGarbageFreeThreadContextMap implements ContextDataInjector {
		@Override
		public StringMap injectContextData(List<Property> props, StringMap reusable) {
			ThreadContextDataInjector.copyProperties(props, reusable);
			ReadOnlyStringMap immutableCopy = ThreadContext.getThreadContextMap().getReadOnlyContextData();
			reusable.putAll(immutableCopy);
			return reusable;
		}

		@Override
		public ReadOnlyStringMap rawContextData() {
			return ThreadContext.getThreadContextMap().getReadOnlyContextData();
		}
	}
}
