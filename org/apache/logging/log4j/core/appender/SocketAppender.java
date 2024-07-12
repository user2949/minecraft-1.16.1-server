package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidHost;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidPort;
import org.apache.logging.log4j.core.layout.SerializedLayout;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.net.DatagramSocketManager;
import org.apache.logging.log4j.core.net.Protocol;
import org.apache.logging.log4j.core.net.SocketOptions;
import org.apache.logging.log4j.core.net.SslSocketManager;
import org.apache.logging.log4j.core.net.TcpSocketManager;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(
	name = "Socket",
	category = "Core",
	elementType = "appender",
	printObject = true
)
public class SocketAppender extends AbstractOutputStreamAppender<AbstractSocketManager> {
	private final Object advertisement;
	private final Advertiser advertiser;

	@PluginBuilderFactory
	public static SocketAppender.Builder newBuilder() {
		return new SocketAppender.Builder();
	}

	protected SocketAppender(
		String name,
		Layout<? extends Serializable> layout,
		Filter filter,
		AbstractSocketManager manager,
		boolean ignoreExceptions,
		boolean immediateFlush,
		Advertiser advertiser
	) {
		super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
		if (advertiser != null) {
			Map<String, String> configuration = new HashMap(layout.getContentFormat());
			configuration.putAll(manager.getContentFormat());
			configuration.put("contentType", layout.getContentType());
			configuration.put("name", name);
			this.advertisement = advertiser.advertise(configuration);
		} else {
			this.advertisement = null;
		}

		this.advertiser = advertiser;
	}

	@Override
	public boolean stop(long timeout, TimeUnit timeUnit) {
		this.setStopping();
		super.stop(timeout, timeUnit, false);
		if (this.advertiser != null) {
			this.advertiser.unadvertise(this.advertisement);
		}

		this.setStopped();
		return true;
	}

	@Deprecated
	@PluginFactory
	public static SocketAppender createAppender(
		String host,
		int port,
		Protocol protocol,
		SslConfiguration sslConfig,
		int connectTimeoutMillis,
		int reconnectDelayMillis,
		boolean immediateFail,
		String name,
		boolean immediateFlush,
		boolean ignoreExceptions,
		Layout<? extends Serializable> layout,
		Filter filter,
		boolean advertise,
		Configuration configuration
	) {
		return newBuilder()
			.withAdvertise(advertise)
			.setConfiguration(configuration)
			.withConnectTimeoutMillis(connectTimeoutMillis)
			.withFilter(filter)
			.withHost(host)
			.withIgnoreExceptions(ignoreExceptions)
			.withImmediateFail(immediateFail)
			.withLayout(layout)
			.withName(name)
			.withPort(port)
			.withProtocol(protocol)
			.withReconnectDelayMillis(reconnectDelayMillis)
			.withSslConfiguration(sslConfig)
			.build();
	}

	@Deprecated
	public static SocketAppender createAppender(
		String host,
		String portNum,
		String protocolIn,
		SslConfiguration sslConfig,
		int connectTimeoutMillis,
		String delayMillis,
		String immediateFail,
		String name,
		String immediateFlush,
		String ignore,
		Layout<? extends Serializable> layout,
		Filter filter,
		String advertise,
		Configuration config
	) {
		boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
		boolean isAdvertise = Boolean.parseBoolean(advertise);
		boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
		boolean fail = Booleans.parseBoolean(immediateFail, true);
		int reconnectDelayMillis = AbstractAppender.parseInt(delayMillis, 0);
		int port = AbstractAppender.parseInt(portNum, 0);
		Protocol p = protocolIn == null ? Protocol.UDP : Protocol.valueOf(protocolIn);
		return createAppender(
			host, port, p, sslConfig, connectTimeoutMillis, reconnectDelayMillis, fail, name, isFlush, ignoreExceptions, layout, filter, isAdvertise, config
		);
	}

	@Deprecated
	protected static AbstractSocketManager createSocketManager(
		String name,
		Protocol protocol,
		String host,
		int port,
		int connectTimeoutMillis,
		SslConfiguration sslConfig,
		int reconnectDelayMillis,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize
	) {
		return createSocketManager(name, protocol, host, port, connectTimeoutMillis, sslConfig, reconnectDelayMillis, immediateFail, layout, bufferSize, null);
	}

	protected static AbstractSocketManager createSocketManager(
		String name,
		Protocol protocol,
		String host,
		int port,
		int connectTimeoutMillis,
		SslConfiguration sslConfig,
		int reconnectDelayMillis,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize,
		SocketOptions socketOptions
	) {
		if (protocol == Protocol.TCP && sslConfig != null) {
			protocol = Protocol.SSL;
		}

		if (protocol != Protocol.SSL && sslConfig != null) {
			LOGGER.info("Appender {} ignoring SSL configuration for {} protocol", name, protocol);
		}

		switch (protocol) {
			case TCP:
				return TcpSocketManager.getSocketManager(host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, socketOptions);
			case UDP:
				return DatagramSocketManager.getSocketManager(host, port, layout, bufferSize);
			case SSL:
				return SslSocketManager.getSocketManager(
					sslConfig, host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, socketOptions
				);
			default:
				throw new IllegalArgumentException(protocol.toString());
		}
	}

	@Override
	protected void directEncodeEvent(LogEvent event) {
		this.writeByteArrayToManager(event);
	}

	public abstract static class AbstractBuilder<B extends SocketAppender.AbstractBuilder<B>> extends AbstractOutputStreamAppender.Builder<B> {
		@PluginBuilderAttribute
		private boolean advertise;
		@PluginBuilderAttribute
		private int connectTimeoutMillis;
		@PluginBuilderAttribute
		@ValidHost
		private String host = "localhost";
		@PluginBuilderAttribute
		private boolean immediateFail = true;
		@PluginBuilderAttribute
		@ValidPort
		private int port;
		@PluginBuilderAttribute
		private Protocol protocol = Protocol.TCP;
		@PluginBuilderAttribute
		@PluginAliases({"reconnectDelay", "reconnectionDelay", "delayMillis", "reconnectionDelayMillis"})
		private int reconnectDelayMillis;
		@PluginElement("SocketOptions")
		private SocketOptions socketOptions;
		@PluginElement("SslConfiguration")
		@PluginAliases({"SslConfig"})
		private SslConfiguration sslConfiguration;

		public boolean getAdvertise() {
			return this.advertise;
		}

		public int getConnectTimeoutMillis() {
			return this.connectTimeoutMillis;
		}

		public String getHost() {
			return this.host;
		}

		public int getPort() {
			return this.port;
		}

		public Protocol getProtocol() {
			return this.protocol;
		}

		public SslConfiguration getSslConfiguration() {
			return this.sslConfiguration;
		}

		public boolean getImmediateFail() {
			return this.immediateFail;
		}

		public B withAdvertise(boolean advertise) {
			this.advertise = advertise;
			return this.asBuilder();
		}

		public B withConnectTimeoutMillis(int connectTimeoutMillis) {
			this.connectTimeoutMillis = connectTimeoutMillis;
			return this.asBuilder();
		}

		public B withHost(String host) {
			this.host = host;
			return this.asBuilder();
		}

		public B withImmediateFail(boolean immediateFail) {
			this.immediateFail = immediateFail;
			return this.asBuilder();
		}

		public B withPort(int port) {
			this.port = port;
			return this.asBuilder();
		}

		public B withProtocol(Protocol protocol) {
			this.protocol = protocol;
			return this.asBuilder();
		}

		public B withReconnectDelayMillis(int reconnectDelayMillis) {
			this.reconnectDelayMillis = reconnectDelayMillis;
			return this.asBuilder();
		}

		public B withSocketOptions(SocketOptions socketOptions) {
			this.socketOptions = socketOptions;
			return this.asBuilder();
		}

		public B withSslConfiguration(SslConfiguration sslConfiguration) {
			this.sslConfiguration = sslConfiguration;
			return this.asBuilder();
		}

		public int getReconnectDelayMillis() {
			return this.reconnectDelayMillis;
		}

		public SocketOptions getSocketOptions() {
			return this.socketOptions;
		}
	}

	public static class Builder
		extends SocketAppender.AbstractBuilder<SocketAppender.Builder>
		implements org.apache.logging.log4j.core.util.Builder<SocketAppender> {
		public SocketAppender build() {
			boolean immediateFlush = this.isImmediateFlush();
			boolean bufferedIo = this.isBufferedIo();
			Layout<? extends Serializable> layout = this.getLayout();
			if (layout == null) {
				layout = SerializedLayout.createLayout();
			}

			String name = this.getName();
			if (name == null) {
				SocketAppender.LOGGER.error("No name provided for SocketAppender");
				return null;
			} else {
				Protocol protocol = this.getProtocol();
				Protocol actualProtocol = protocol != null ? protocol : Protocol.TCP;
				if (actualProtocol == Protocol.UDP) {
					immediateFlush = true;
				}

				AbstractSocketManager manager = SocketAppender.createSocketManager(
					name,
					actualProtocol,
					this.getHost(),
					this.getPort(),
					this.getConnectTimeoutMillis(),
					this.getSslConfiguration(),
					this.getReconnectDelayMillis(),
					this.getImmediateFail(),
					layout,
					this.getBufferSize(),
					this.getSocketOptions()
				);
				return new SocketAppender(
					name,
					layout,
					this.getFilter(),
					manager,
					this.isIgnoreExceptions(),
					!bufferedIo || immediateFlush,
					this.getAdvertise() ? this.getConfiguration().getAdvertiser() : null
				);
			}
		}
	}
}
