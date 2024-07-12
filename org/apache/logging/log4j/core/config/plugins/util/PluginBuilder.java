package org.apache.logging.log4j.core.config.plugins.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidators;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitor;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitors;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.apache.logging.log4j.core.util.TypeUtil;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilders;

public class PluginBuilder implements Builder<Object> {
	private static final Logger LOGGER = StatusLogger.getLogger();
	private final PluginType<?> pluginType;
	private final Class<?> clazz;
	private Configuration configuration;
	private Node node;
	private LogEvent event;

	public PluginBuilder(PluginType<?> pluginType) {
		this.pluginType = pluginType;
		this.clazz = pluginType.getPluginClass();
	}

	public PluginBuilder withConfiguration(Configuration configuration) {
		this.configuration = configuration;
		return this;
	}

	public PluginBuilder withConfigurationNode(Node node) {
		this.node = node;
		return this;
	}

	public PluginBuilder forLogEvent(LogEvent event) {
		this.event = event;
		return this;
	}

	@Override
	public Object build() {
		this.verify();

		try {
			LOGGER.debug("Building Plugin[name={}, class={}].", this.pluginType.getElementName(), this.pluginType.getPluginClass().getName());
			Builder<?> builder = createBuilder(this.clazz);
			if (builder != null) {
				this.injectFields(builder);
				return builder.build();
			}
		} catch (Exception var4) {
			LOGGER.error("Unable to inject fields into builder class for plugin type {}, element {}.", this.clazz, this.node.getName(), var4);
		}

		try {
			Method factory = findFactoryMethod(this.clazz);
			Object[] params = this.generateParameters(factory);
			return factory.invoke(null, params);
		} catch (Exception var3) {
			LOGGER.error("Unable to invoke factory method in class {} for element {}.", this.clazz, this.node.getName(), var3);
			return null;
		}
	}

	private void verify() {
		Objects.requireNonNull(this.configuration, "No Configuration object was set.");
		Objects.requireNonNull(this.node, "No Node object was set.");
	}

	private static Builder<?> createBuilder(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(PluginBuilderFactory.class)
				&& Modifier.isStatic(method.getModifiers())
				&& TypeUtil.isAssignable(Builder.class, method.getReturnType())) {
				ReflectionUtil.makeAccessible(method);
				return (Builder<?>)method.invoke(null);
			}
		}

		return null;
	}

	private void injectFields(Builder<?> builder) throws IllegalAccessException {
		List<Field> fields = TypeUtil.getAllDeclaredFields(builder.getClass());
		AccessibleObject.setAccessible((AccessibleObject[])fields.toArray(new Field[0]), true);
		StringBuilder log = new StringBuilder();
		boolean invalid = false;

		for (Field field : fields) {
			log.append(log.length() == 0 ? simpleName(builder) + "(" : ", ");
			Annotation[] annotations = field.getDeclaredAnnotations();
			String[] aliases = extractPluginAliases(annotations);

			for (Annotation a : annotations) {
				if (!(a instanceof PluginAliases)) {
					PluginVisitor<? extends Annotation> visitor = PluginVisitors.findVisitor(a.annotationType());
					if (visitor != null) {
						Object value = visitor.setAliases(aliases)
							.setAnnotation(a)
							.setConversionType(field.getType())
							.setStrSubstitutor(this.configuration.getStrSubstitutor())
							.setMember(field)
							.visit(this.configuration, this.node, this.event, log);
						if (value != null) {
							field.set(builder, value);
						}
					}
				}
			}

			Collection<ConstraintValidator<?>> validators = ConstraintValidators.findValidators(annotations);
			Object value = field.get(builder);

			for (ConstraintValidator<?> validator : validators) {
				if (!validator.isValid(field.getName(), value)) {
					invalid = true;
				}
			}
		}

		log.append(log.length() == 0 ? builder.getClass().getSimpleName() + "()" : ")");
		LOGGER.debug(log.toString());
		if (invalid) {
			throw new ConfigurationException("Arguments given for element " + this.node.getName() + " are invalid");
		} else {
			this.checkForRemainingAttributes();
			this.verifyNodeChildrenUsed();
		}
	}

	private static String simpleName(Object object) {
		if (object == null) {
			return "null";
		} else {
			String cls = object.getClass().getName();
			int index = cls.lastIndexOf(46);
			return index < 0 ? cls : cls.substring(index + 1);
		}
	}

	private static Method findFactoryMethod(Class<?> clazz) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(PluginFactory.class) && Modifier.isStatic(method.getModifiers())) {
				ReflectionUtil.makeAccessible(method);
				return method;
			}
		}

		throw new IllegalStateException("No factory method found for class " + clazz.getName());
	}

	private Object[] generateParameters(Method factory) {
		StringBuilder log = new StringBuilder();
		Class<?>[] types = factory.getParameterTypes();
		Annotation[][] annotations = factory.getParameterAnnotations();
		Object[] args = new Object[annotations.length];
		boolean invalid = false;

		for (int i = 0; i < annotations.length; i++) {
			log.append(log.length() == 0 ? factory.getName() + "(" : ", ");
			String[] aliases = extractPluginAliases(annotations[i]);

			for (Annotation a : annotations[i]) {
				if (!(a instanceof PluginAliases)) {
					PluginVisitor<? extends Annotation> visitor = PluginVisitors.findVisitor(a.annotationType());
					if (visitor != null) {
						Object value = visitor.setAliases(aliases)
							.setAnnotation(a)
							.setConversionType(types[i])
							.setStrSubstitutor(this.configuration.getStrSubstitutor())
							.setMember(factory)
							.visit(this.configuration, this.node, this.event, log);
						if (value != null) {
							args[i] = value;
						}
					}
				}
			}

			Collection<ConstraintValidator<?>> validators = ConstraintValidators.findValidators(annotations[i]);
			Object value = args[i];
			String argName = "arg[" + i + "](" + simpleName(value) + ")";

			for (ConstraintValidator<?> validator : validators) {
				if (!validator.isValid(argName, value)) {
					invalid = true;
				}
			}
		}

		log.append(log.length() == 0 ? factory.getName() + "()" : ")");
		this.checkForRemainingAttributes();
		this.verifyNodeChildrenUsed();
		LOGGER.debug(log.toString());
		if (invalid) {
			throw new ConfigurationException("Arguments given for element " + this.node.getName() + " are invalid");
		} else {
			return args;
		}
	}

	private static String[] extractPluginAliases(Annotation... parmTypes) {
		String[] aliases = null;

		for (Annotation a : parmTypes) {
			if (a instanceof PluginAliases) {
				aliases = ((PluginAliases)a).value();
			}
		}

		return aliases;
	}

	private void checkForRemainingAttributes() {
		Map<String, String> attrs = this.node.getAttributes();
		if (!attrs.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (String key : attrs.keySet()) {
				if (sb.length() == 0) {
					sb.append(this.node.getName());
					sb.append(" contains ");
					if (attrs.size() == 1) {
						sb.append("an invalid element or attribute ");
					} else {
						sb.append("invalid attributes ");
					}
				} else {
					sb.append(", ");
				}

				StringBuilders.appendDqValue(sb, key);
			}

			LOGGER.error(sb.toString());
		}
	}

	private void verifyNodeChildrenUsed() {
		List<Node> children = this.node.getChildren();
		if (!this.pluginType.isDeferChildren() && !children.isEmpty()) {
			for (Node child : children) {
				String nodeType = this.node.getType().getElementName();
				String start = nodeType.equals(this.node.getName()) ? this.node.getName() : nodeType + ' ' + this.node.getName();
				LOGGER.error("{} has no parameter that matches element {}", start, child.getName());
			}
		}
	}
}
