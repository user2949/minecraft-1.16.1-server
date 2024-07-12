package org.apache.logging.log4j.core.appender.db.jpa;

import java.lang.reflect.Constructor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager.AbstractFactoryData;

public final class JpaDatabaseManager extends AbstractDatabaseManager {
	private static final JpaDatabaseManager.JPADatabaseManagerFactory FACTORY = new JpaDatabaseManager.JPADatabaseManagerFactory();
	private final String entityClassName;
	private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
	private final String persistenceUnitName;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;

	private JpaDatabaseManager(
		String name,
		int bufferSize,
		Class<? extends AbstractLogEventWrapperEntity> entityClass,
		Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor,
		String persistenceUnitName
	) {
		super(name, bufferSize);
		this.entityClassName = entityClass.getName();
		this.entityConstructor = entityConstructor;
		this.persistenceUnitName = persistenceUnitName;
	}

	@Override
	protected void startupInternal() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
	}

	@Override
	protected boolean shutdownInternal() {
		boolean closed = true;
		if (this.entityManager != null || this.transaction != null) {
			closed &= this.commitAndClose();
		}

		if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
			this.entityManagerFactory.close();
		}

		return closed;
	}

	@Override
	protected void connectAndStart() {
		try {
			this.entityManager = this.entityManagerFactory.createEntityManager();
			this.transaction = this.entityManager.getTransaction();
			this.transaction.begin();
		} catch (Exception var2) {
			throw new AppenderLoggingException("Cannot write logging event or flush buffer; manager cannot create EntityManager or transaction.", var2);
		}
	}

	@Override
	protected void writeInternal(LogEvent event) {
		if (this.isRunning() && this.entityManagerFactory != null && this.entityManager != null && this.transaction != null) {
			AbstractLogEventWrapperEntity entity;
			try {
				entity = (AbstractLogEventWrapperEntity)this.entityConstructor.newInstance(event);
			} catch (Exception var4) {
				throw new AppenderLoggingException("Failed to instantiate entity class [" + this.entityClassName + "].", var4);
			}

			try {
				this.entityManager.persist(entity);
			} catch (Exception var5) {
				if (this.transaction != null && this.transaction.isActive()) {
					this.transaction.rollback();
					this.transaction = null;
				}

				throw new AppenderLoggingException("Failed to insert record for log event in JPA manager: " + var5.getMessage(), var5);
			}
		} else {
			throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
		}
	}

	@Override
	protected boolean commitAndClose() {
		boolean closed = true;

		try {
			if (this.transaction != null && this.transaction.isActive()) {
				this.transaction.commit();
			}
		} catch (Exception var42) {
			if (this.transaction != null && this.transaction.isActive()) {
				this.transaction.rollback();
			}
		} finally {
			this.transaction = null;

			try {
				if (this.entityManager != null && this.entityManager.isOpen()) {
					this.entityManager.close();
				}
			} catch (Exception var40) {
				this.logWarn("Failed to close entity manager while logging event or flushing buffer", var40);
				closed = false;
			} finally {
				this.entityManager = null;
			}
		}

		return closed;
	}

	public static JpaDatabaseManager getJPADatabaseManager(
		String name,
		int bufferSize,
		Class<? extends AbstractLogEventWrapperEntity> entityClass,
		Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor,
		String persistenceUnitName
	) {
		return AbstractDatabaseManager.getManager(name, new JpaDatabaseManager.FactoryData(bufferSize, entityClass, entityConstructor, persistenceUnitName), FACTORY);
	}

	private static final class FactoryData extends AbstractFactoryData {
		private final Class<? extends AbstractLogEventWrapperEntity> entityClass;
		private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
		private final String persistenceUnitName;

		protected FactoryData(
			int bufferSize,
			Class<? extends AbstractLogEventWrapperEntity> entityClass,
			Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor,
			String persistenceUnitName
		) {
			super(bufferSize);
			this.entityClass = entityClass;
			this.entityConstructor = entityConstructor;
			this.persistenceUnitName = persistenceUnitName;
		}
	}

	private static final class JPADatabaseManagerFactory implements ManagerFactory<JpaDatabaseManager, JpaDatabaseManager.FactoryData> {
		private JPADatabaseManagerFactory() {
		}

		public JpaDatabaseManager createManager(String name, JpaDatabaseManager.FactoryData data) {
			return new JpaDatabaseManager(name, data.getBufferSize(), data.entityClass, data.entityConstructor, data.persistenceUnitName);
		}
	}
}
