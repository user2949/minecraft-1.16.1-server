package org.apache.logging.log4j.core.appender.db.jdbc;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.apache.logging.log4j.core.appender.db.ColumnMapping;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.util.Assert;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(
	name = "JDBC",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public final class JdbcAppender extends AbstractDatabaseAppender<JdbcDatabaseManager> {
	private final String description = this.getName() + "{ manager=" + this.getManager() + " }";

	private JdbcAppender(String name, Filter filter, boolean ignoreExceptions, JdbcDatabaseManager manager) {
		super(name, filter, ignoreExceptions, manager);
	}

	@Override
	public String toString() {
		return this.description;
	}

	@Deprecated
	public static <B extends JdbcAppender.Builder<B>> JdbcAppender createAppender(
		String name, String ignore, Filter filter, ConnectionSource connectionSource, String bufferSize, String tableName, ColumnConfig[] columnConfigs
	) {
		Assert.requireNonEmpty(name, "Name cannot be empty");
		Objects.requireNonNull(connectionSource, "ConnectionSource cannot be null");
		Assert.requireNonEmpty(tableName, "Table name cannot be empty");
		Assert.requireNonEmpty(columnConfigs, "ColumnConfigs cannot be empty");
		int bufferSizeInt = AbstractAppender.parseInt(bufferSize, 0);
		boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
		return ((JdbcAppender.Builder)((JdbcAppender.Builder)((JdbcAppender.Builder)newBuilder()
						.setBufferSize(bufferSizeInt)
						.setColumnConfigs(columnConfigs)
						.setConnectionSource(connectionSource)
						.setTableName(tableName)
						.withName(name))
					.withIgnoreExceptions(ignoreExceptions))
				.withFilter(filter))
			.build();
	}

	@PluginBuilderFactory
	public static <B extends JdbcAppender.Builder<B>> B newBuilder() {
		return new JdbcAppender.Builder<B>().asBuilder();
	}

	public static class Builder<B extends JdbcAppender.Builder<B>>
		extends AbstractAppender.Builder<B>
		implements org.apache.logging.log4j.core.util.Builder<JdbcAppender> {
		@PluginElement("ConnectionSource")
		@Required(
			message = "No ConnectionSource provided"
		)
		private ConnectionSource connectionSource;
		@PluginBuilderAttribute
		private int bufferSize;
		@PluginBuilderAttribute
		@Required(
			message = "No table name provided"
		)
		private String tableName;
		@PluginElement("ColumnConfigs")
		private ColumnConfig[] columnConfigs;
		@PluginElement("ColumnMappings")
		private ColumnMapping[] columnMappings;

		public B setConnectionSource(ConnectionSource connectionSource) {
			this.connectionSource = connectionSource;
			return this.asBuilder();
		}

		public B setBufferSize(int bufferSize) {
			this.bufferSize = bufferSize;
			return this.asBuilder();
		}

		public B setTableName(String tableName) {
			this.tableName = tableName;
			return this.asBuilder();
		}

		public B setColumnConfigs(ColumnConfig... columnConfigs) {
			this.columnConfigs = columnConfigs;
			return this.asBuilder();
		}

		public B setColumnMappings(ColumnMapping... columnMappings) {
			this.columnMappings = columnMappings;
			return this.asBuilder();
		}

		public JdbcAppender build() {
			if (Assert.isEmpty(this.columnConfigs) && Assert.isEmpty(this.columnMappings)) {
				JdbcAppender.LOGGER.error("Cannot create JdbcAppender without any columns configured.");
				return null;
			} else {
				String managerName = "JdbcManager{name="
					+ this.getName()
					+ ", bufferSize="
					+ this.bufferSize
					+ ", tableName="
					+ this.tableName
					+ ", columnConfigs="
					+ Arrays.toString(this.columnConfigs)
					+ ", columnMappings="
					+ Arrays.toString(this.columnMappings)
					+ '}';
				JdbcDatabaseManager manager = JdbcDatabaseManager.getManager(
					managerName, this.bufferSize, this.connectionSource, this.tableName, this.columnConfigs, this.columnMappings
				);
				return manager == null ? null : new JdbcAppender(this.getName(), this.getFilter(), this.isIgnoreExceptions(), manager);
			}
		}

		@Deprecated
		@Override
		public Layout<? extends Serializable> getLayout() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		public B withLayout(Layout<? extends Serializable> layout) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Layout<? extends Serializable> getOrCreateLayout() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Layout<? extends Serializable> getOrCreateLayout(Charset charset) {
			throw new UnsupportedOperationException();
		}
	}
}
