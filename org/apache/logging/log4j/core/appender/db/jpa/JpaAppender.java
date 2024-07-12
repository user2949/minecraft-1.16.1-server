package org.apache.logging.log4j.core.appender.db.jpa;

import java.lang.reflect.Constructor;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.Strings;

@Plugin(
	name = "JPA",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public final class JpaAppender extends AbstractDatabaseAppender<JpaDatabaseManager> {
	private final String description = this.getName() + "{ manager=" + this.getManager() + " }";

	private JpaAppender(String name, Filter filter, boolean ignoreExceptions, JpaDatabaseManager manager) {
		super(name, filter, ignoreExceptions, manager);
	}

	@Override
	public String toString() {
		return this.description;
	}

	@PluginFactory
	public static JpaAppender createAppender(
		@PluginAttribute("name") String name,
		@PluginAttribute("ignoreExceptions") String ignore,
		@PluginElement("Filter") Filter filter,
		@PluginAttribute("bufferSize") String bufferSize,
		@PluginAttribute("entityClassName") String entityClassName,
		@PluginAttribute("persistenceUnitName") String persistenceUnitName
	) {
		if (!Strings.isEmpty(entityClassName) && !Strings.isEmpty(persistenceUnitName)) {
			int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
			boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);

			try {
				Class<? extends AbstractLogEventWrapperEntity> entityClass = LoaderUtil.loadClass(entityClassName).asSubclass(AbstractLogEventWrapperEntity.class);

				try {
					entityClass.getConstructor();
				} catch (NoSuchMethodException var12) {
					LOGGER.error("Entity class [{}] does not have a no-arg constructor. The JPA provider will reject it.", entityClassName);
					return null;
				}

				Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor = entityClass.getConstructor(LogEvent.class);
				String managerName = "jpaManager{ description="
					+ name
					+ ", bufferSize="
					+ bufferSizeInt
					+ ", persistenceUnitName="
					+ persistenceUnitName
					+ ", entityClass="
					+ entityClass.getName()
					+ '}';
				JpaDatabaseManager manager = JpaDatabaseManager.getJPADatabaseManager(managerName, bufferSizeInt, entityClass, entityConstructor, persistenceUnitName);
				return manager == null ? null : new JpaAppender(name, filter, ignoreExceptions, manager);
			} catch (ClassNotFoundException var13) {
				LOGGER.error("Could not load entity class [{}].", entityClassName, var13);
				return null;
			} catch (NoSuchMethodException var14) {
				LOGGER.error("Entity class [{}] does not have a constructor with a single argument of type LogEvent.", entityClassName);
				return null;
			} catch (ClassCastException var15) {
				LOGGER.error("Entity class [{}] does not extend AbstractLogEventWrapperEntity.", entityClassName);
				return null;
			}
		} else {
			LOGGER.error("Attributes entityClassName and persistenceUnitName are required for JPA Appender.");
			return null;
		}
	}
}
