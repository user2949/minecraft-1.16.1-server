package org.apache.logging.log4j.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.core.util.NullOutputStream;
import org.apache.logging.log4j.util.Strings;

public class TcpSocketManager extends AbstractSocketManager {
	public static final int DEFAULT_RECONNECTION_DELAY_MILLIS = 30000;
	private static final int DEFAULT_PORT = 4560;
	private static final TcpSocketManager.TcpSocketManagerFactory FACTORY = new TcpSocketManager.TcpSocketManagerFactory();
	private final int reconnectionDelay;
	private TcpSocketManager.Reconnector reconnector;
	private Socket socket;
	private final SocketOptions socketOptions;
	private final boolean retry;
	private final boolean immediateFail;
	private final int connectTimeoutMillis;

	@Deprecated
	public TcpSocketManager(
		String name,
		OutputStream os,
		Socket socket,
		InetAddress inetAddress,
		String host,
		int port,
		int connectTimeoutMillis,
		int delay,
		boolean immediateFail,
		Layout<? extends Serializable> layout,
		int bufferSize
	) {
		this(name, os, socket, inetAddress, host, port, connectTimeoutMillis, delay, immediateFail, layout, bufferSize, null);
	}

	public TcpSocketManager(
		String name,
		OutputStream os,
		Socket socket,
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
		super(name, os, inetAddress, host, port, layout, true, bufferSize);
		this.connectTimeoutMillis = connectTimeoutMillis;
		this.reconnectionDelay = delay;
		this.socket = socket;
		this.immediateFail = immediateFail;
		this.retry = delay > 0;
		if (socket == null) {
			this.reconnector = this.createReconnector();
			this.reconnector.start();
		}

		this.socketOptions = socketOptions;
	}

	@Deprecated
	public static TcpSocketManager getSocketManager(
		String host, int port, int connectTimeoutMillis, int reconnectDelayMillis, boolean immediateFail, Layout<? extends Serializable> layout, int bufferSize
	) {
		return getSocketManager(host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, null);
	}

	public static TcpSocketManager getSocketManager(
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
				port = 4560;
			}

			if (reconnectDelayMillis == 0) {
				reconnectDelayMillis = 30000;
			}

			return (TcpSocketManager)getManager(
				"TCP:" + host + ':' + port,
				new TcpSocketManager.FactoryData(host, port, connectTimeoutMillis, reconnectDelayMillis, immediateFail, layout, bufferSize, socketOptions),
				FACTORY
			);
		}
	}

	@Override
	protected void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
		if (this.socket == null) {
			if (this.reconnector != null && !this.immediateFail) {
				this.reconnector.latch();
			}

			if (this.socket == null) {
				String msg = "Error writing to " + this.getName() + " socket not available";
				throw new AppenderLoggingException(msg);
			}
		}

		synchronized (this) {
			try {
				OutputStream outputStream = this.getOutputStream();
				outputStream.write(bytes, offset, length);
				if (immediateFlush) {
					outputStream.flush();
				}
			} catch (IOException var9) {
				if (this.retry && this.reconnector == null) {
					this.reconnector = this.createReconnector();
					this.reconnector.start();
				}

				String msg = "Error writing to " + this.getName();
				throw new AppenderLoggingException(msg, var9);
			}
		}
	}

	@Override
	protected synchronized boolean closeOutputStream() {
		boolean closed = super.closeOutputStream();
		if (this.reconnector != null) {
			this.reconnector.shutdown();
			this.reconnector.interrupt();
			this.reconnector = null;
		}

		Socket oldSocket = this.socket;
		this.socket = null;
		if (oldSocket != null) {
			try {
				oldSocket.close();
			} catch (IOException var4) {
				LOGGER.error("Could not close socket {}", this.socket);
				return false;
			}
		}

		return closed;
	}

	public int getConnectTimeoutMillis() {
		return this.connectTimeoutMillis;
	}

	@Override
	public Map<String, String> getContentFormat() {
		Map<String, String> result = new HashMap(super.getContentFormat());
		result.put("protocol", "tcp");
		result.put("direction", "out");
		return result;
	}

	private TcpSocketManager.Reconnector createReconnector() {
		TcpSocketManager.Reconnector recon = new TcpSocketManager.Reconnector(this);
		recon.setDaemon(true);
		recon.setPriority(1);
		return recon;
	}

	protected Socket createSocket(InetAddress host, int port) throws IOException {
		return this.createSocket(host.getHostName(), port);
	}

	protected Socket createSocket(String host, int port) throws IOException {
		Socket newSocket = new Socket();
		newSocket.connect(new InetSocketAddress(host, port), this.connectTimeoutMillis);
		if (this.socketOptions != null) {
			this.socketOptions.apply(newSocket);
		}

		return newSocket;
	}

	public SocketOptions getSocketOptions() {
		return this.socketOptions;
	}

	public Socket getSocket() {
		return this.socket;
	}

	private static class FactoryData {
		private final String host;
		private final int port;
		private final int connectTimeoutMillis;
		private final int reconnectDelayMillis;
		private final boolean immediateFail;
		private final Layout<? extends Serializable> layout;
		private final int bufferSize;
		private final SocketOptions socketOptions;

		public FactoryData(
			String host,
			int port,
			int connectTimeoutMillis,
			int reconnectDelayMillis,
			boolean immediateFail,
			Layout<? extends Serializable> layout,
			int bufferSize,
			SocketOptions socketOptions
		) {
			this.host = host;
			this.port = port;
			this.connectTimeoutMillis = connectTimeoutMillis;
			this.reconnectDelayMillis = reconnectDelayMillis;
			this.immediateFail = immediateFail;
			this.layout = layout;
			this.bufferSize = bufferSize;
			this.socketOptions = socketOptions;
		}
	}

	private class Reconnector extends Log4jThread {
		private final CountDownLatch latch = new CountDownLatch(1);
		private boolean shutdown = false;
		private final Object owner;

		public Reconnector(OutputStreamManager owner) {
			super("TcpSocketManager-Reconnector");
			this.owner = owner;
		}

		public void latch() {
			try {
				this.latch.await();
			} catch (InterruptedException var2) {
			}
		}

		public void shutdown() {
			this.shutdown = true;
		}

		public void run() {
			while (!this.shutdown) {
				try {
					sleep((long)TcpSocketManager.this.reconnectionDelay);
					Socket sock = TcpSocketManager.this.createSocket(TcpSocketManager.this.inetAddress, TcpSocketManager.this.port);
					OutputStream newOS = sock.getOutputStream();
					synchronized (this.owner) {
						try {
							TcpSocketManager.this.getOutputStream().close();
						} catch (IOException var13) {
						}

						TcpSocketManager.this.setOutputStream(newOS);
						TcpSocketManager.this.socket = sock;
						TcpSocketManager.this.reconnector = null;
						this.shutdown = true;
					}

					TcpSocketManager.LOGGER.debug("Connection to " + TcpSocketManager.this.host + ':' + TcpSocketManager.this.port + " reestablished.");
				} catch (InterruptedException var15) {
					TcpSocketManager.LOGGER.debug("Reconnection interrupted.");
				} catch (ConnectException var16) {
					TcpSocketManager.LOGGER.debug(TcpSocketManager.this.host + ':' + TcpSocketManager.this.port + " refused connection");
				} catch (IOException var17) {
					TcpSocketManager.LOGGER.debug("Unable to reconnect to " + TcpSocketManager.this.host + ':' + TcpSocketManager.this.port);
				} finally {
					this.latch.countDown();
				}
			}
		}
	}

	protected static class TcpSocketManagerFactory implements ManagerFactory<TcpSocketManager, TcpSocketManager.FactoryData> {
		public TcpSocketManager createManager(String name, TcpSocketManager.FactoryData data) {
			InetAddress inetAddress;
			try {
				inetAddress = InetAddress.getByName(data.host);
			} catch (UnknownHostException var7) {
				TcpSocketManager.LOGGER.error("Could not find address of " + data.host, var7, var7);
				return null;
			}

			Socket socket = null;

			try {
				socket = createSocket(data);
				OutputStream os = socket.getOutputStream();
				return new TcpSocketManager(
					name,
					os,
					socket,
					inetAddress,
					data.host,
					data.port,
					data.connectTimeoutMillis,
					data.reconnectDelayMillis,
					data.immediateFail,
					data.layout,
					data.bufferSize,
					data.socketOptions
				);
			} catch (IOException var8) {
				TcpSocketManager.LOGGER.error("TcpSocketManager (" + name + ") " + var8, (Throwable)var8);
				OutputStream osx = NullOutputStream.getInstance();
				if (data.reconnectDelayMillis == 0) {
					Closer.closeSilently(socket);
					return null;
				} else {
					return new TcpSocketManager(
						name,
						osx,
						null,
						inetAddress,
						data.host,
						data.port,
						data.connectTimeoutMillis,
						data.reconnectDelayMillis,
						data.immediateFail,
						data.layout,
						data.bufferSize,
						data.socketOptions
					);
				}
			}
		}

		static Socket createSocket(TcpSocketManager.FactoryData data) throws IOException, SocketException {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(data.host, data.port), data.connectTimeoutMillis);
			SocketOptions socketOptions = data.socketOptions;
			if (socketOptions != null) {
				socketOptions.apply(socket);
			}

			return socket;
		}
	}
}
