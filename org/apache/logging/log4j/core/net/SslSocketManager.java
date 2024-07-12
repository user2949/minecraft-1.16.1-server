package org.apache.logging.log4j.core.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.util.Strings;

public class SslSocketManager extends TcpSocketManager {
	public static final int DEFAULT_PORT = 6514;
	private static final SslSocketManager.SslSocketManagerFactory FACTORY = new SslSocketManager.SslSocketManagerFactory();
	private final SslConfiguration sslConfig;

	/** @deprecated */
	public SslSocketManager(
		String name,
		OutputStream os,
		Socket sock,
		SslConfiguration sslConfig,
		InetAddress inetAddress,
		String host,
		int port,
		int connectTimeoutMillis,
		int delay,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize
	) {
		super(name, os, sock, inetAddress, host, port, connectTimeoutMillis, delay, immediateFail, layout, bufferSize, null);
		this.sslConfig = sslConfig;
	}

	public SslSocketManager(
		String name,
		OutputStream os,
		Socket sock,
		SslConfiguration sslConfig,
		InetAddress inetAddress,
		String host,
		int port,
		int connectTimeoutMillis,
		int delay,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize,
		SocketOptions socketOptions
	) {
		super(name, os, sock, inetAddress, host, port, connectTimeoutMillis, delay, immediateFail, layout, bufferSize, socketOptions);
		this.sslConfig = sslConfig;
	}

	@Deprecated
	public static SslSocketManager getSocketManager(
		SslConfiguration sslConfig,
		String host,
		int port,
		int connectTimeoutMillis,
		int reconnectDelayMillis,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize
	) {
		return getSocketManager(sslConfig, host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, null);
	}

	public static SslSocketManager getSocketManager(
		SslConfiguration sslConfig,
		String host,
		int port,
		int connectTimeoutMillis,
		int reconnectDelayMillis,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize,
		SocketOptions socketOptions
	) {
		if (Strings.isEmpty(host)) {
			throw new IllegalArgumentException("A host name is required");
		} else {
			if (port <= 0) {
				port = 6514;
			}

			if (reconnectDelayMillis == 0) {
				reconnectDelayMillis = 30000;
			}

			return (SslSocketManager)getManager(
				"TLS:" + host + ':' + port,
				new SslSocketManager.SslFactoryData(sslConfig, host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, socketOptions),
				FACTORY
			);
		}
	}

	@Override
	protected Socket createSocket(String host, int port) throws IOException {
		SSLSocketFactory socketFactory = createSslSocketFactory(this.sslConfig);
		InetSocketAddress address = new InetSocketAddress(host, port);
		Socket newSocket = socketFactory.createSocket();
		newSocket.connect(address, this.getConnectTimeoutMillis());
		return newSocket;
	}

	private static SSLSocketFactory createSslSocketFactory(SslConfiguration sslConf) {
		SSLSocketFactory socketFactory;
		if (sslConf != null) {
			socketFactory = sslConf.getSslSocketFactory();
		} else {
			socketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		}

		return socketFactory;
	}

	private static class SslFactoryData {
		protected SslConfiguration sslConfiguration;
		private final String host;
		private final int port;
		private final int connectTimeoutMillis;
		private final int delayMillis;
		private final boolean immediateFail;
		private final Layout<? extends Serializable> layout;
		private final int bufferSize;
		private final SocketOptions socketOptions;

		public SslFactoryData(
			SslConfiguration sslConfiguration,
			String host,
			int port,
			int connectTimeoutMillis,
			int delayMillis,
			boolean immediateFail,
			Layout<? extends Serializable> layout,
			int bufferSize,
			SocketOptions socketOptions
		) {
			this.host = host;
			this.port = port;
			this.connectTimeoutMillis = connectTimeoutMillis;
			this.delayMillis = delayMillis;
			this.immediateFail = immediateFail;
			this.layout = layout;
			this.sslConfiguration = sslConfiguration;
			this.bufferSize = bufferSize;
			this.socketOptions = socketOptions;
		}
	}

	private static class SslSocketManagerFactory implements ManagerFactory<SslSocketManager, SslSocketManager.SslFactoryData> {
		private SslSocketManagerFactory() {
		}

		public SslSocketManager createManager(String name, SslSocketManager.SslFactoryData data) {
			InetAddress inetAddress = null;
			OutputStream os = null;
			Socket socket = null;

			try {
				inetAddress = this.resolveAddress(data.host);
				socket = this.createSocket(data);
				os = socket.getOutputStream();
				this.checkDelay(data.delayMillis, os);
			} catch (IOException var7) {
				SslSocketManager.LOGGER.error("SslSocketManager ({})", name, var7);
				os = new ByteArrayOutputStream();
			} catch (SslSocketManager.SslSocketManagerFactory.TlsSocketManagerFactoryException var8) {
				SslSocketManager.LOGGER.catching(Level.DEBUG, var8);
				Closer.closeSilently(socket);
				return null;
			}

			return new SslSocketManager(
				name,
				os,
				socket,
				data.sslConfiguration,
				inetAddress,
				data.host,
				data.port,
				data.connectTimeoutMillis,
				data.delayMillis,
				data.immediateFail,
				data.layout,
				data.bufferSize,
				data.socketOptions
			);
		}

		private InetAddress resolveAddress(String hostName) throws SslSocketManager.SslSocketManagerFactory.TlsSocketManagerFactoryException {
			try {
				return InetAddress.getByName(hostName);
			} catch (UnknownHostException var4) {
				SslSocketManager.LOGGER.error("Could not find address of {}", hostName, var4);
				throw new SslSocketManager.SslSocketManagerFactory.TlsSocketManagerFactoryException();
			}
		}

		private void checkDelay(int delay, OutputStream os) throws SslSocketManager.SslSocketManagerFactory.TlsSocketManagerFactoryException {
			if (delay == 0 && os == null) {
				throw new SslSocketManager.SslSocketManagerFactory.TlsSocketManagerFactoryException();
			}
		}

		private Socket createSocket(SslSocketManager.SslFactoryData data) throws IOException {
			SSLSocketFactory socketFactory = SslSocketManager.createSslSocketFactory(data.sslConfiguration);
			SSLSocket socket = (SSLSocket)socketFactory.createSocket();
			SocketOptions socketOptions = data.socketOptions;
			if (socketOptions != null) {
				socketOptions.apply(socket);
			}

			socket.connect(new InetSocketAddress(data.host, data.port), data.connectTimeoutMillis);
			if (socketOptions != null) {
				socketOptions.apply(socket);
			}

			return socket;
		}

		private static class TlsSocketManagerFactoryException extends Exception {
			private static final long serialVersionUID = 1L;

			private TlsSocketManagerFactoryException() {
			}
		}
	}
}
