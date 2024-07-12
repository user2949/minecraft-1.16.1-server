package org.apache.logging.log4j.core.net.server;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.net.server.AbstractSocketServer.ServerConfigurationFactory;
import org.apache.logging.log4j.core.util.BasicCommandLineArguments;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.message.EntryMessage;

public class TcpSocketServer<T extends InputStream> extends AbstractSocketServer<T> {
	private final ConcurrentMap<Long, TcpSocketServer<T>.SocketHandler> handlers = new ConcurrentHashMap();
	private final ServerSocket serverSocket;

	public static TcpSocketServer<InputStream> createJsonSocketServer(int port) throws IOException {
		LOGGER.entry(new Object[]{"createJsonSocketServer", port});
		TcpSocketServer<InputStream> socketServer = new TcpSocketServer<>(port, new JsonInputStreamLogEventBridge());
		return LOGGER.exit(socketServer);
	}

	public static TcpSocketServer<ObjectInputStream> createSerializedSocketServer(int port) throws IOException {
		LOGGER.entry(new Object[]{port});
		TcpSocketServer<ObjectInputStream> socketServer = new TcpSocketServer(port, new ObjectInputStreamLogEventBridge());
		return LOGGER.exit(socketServer);
	}

	public static TcpSocketServer<ObjectInputStream> createSerializedSocketServer(int port, int backlog, InetAddress localBindAddress) throws IOException {
		LOGGER.entry(new Object[]{port});
		TcpSocketServer<ObjectInputStream> socketServer = new TcpSocketServer(port, backlog, localBindAddress, new ObjectInputStreamLogEventBridge());
		return LOGGER.exit(socketServer);
	}

	public static TcpSocketServer<InputStream> createXmlSocketServer(int port) throws IOException {
		LOGGER.entry(new Object[]{port});
		TcpSocketServer<InputStream> socketServer = new TcpSocketServer<>(port, new XmlInputStreamLogEventBridge());
		return LOGGER.exit(socketServer);
	}

	public static void main(String[] args) throws Exception {
		TcpSocketServer.CommandLineArguments cla = BasicCommandLineArguments.parseCommandLine(args, TcpSocketServer.class, new TcpSocketServer.CommandLineArguments());
		if (!cla.isHelp()) {
			if (cla.getConfigLocation() != null) {
				ConfigurationFactory.setConfigurationFactory(new ServerConfigurationFactory(cla.getConfigLocation()));
			}

			TcpSocketServer<ObjectInputStream> socketServer = createSerializedSocketServer(cla.getPort(), cla.getBacklog(), cla.getLocalBindAddress());
			Thread serverThread = socketServer.startNewThread();
			if (cla.isInteractive()) {
				socketServer.awaitTermination(serverThread);
			}
		}
	}

	public TcpSocketServer(int port, int backlog, InetAddress localBindAddress, LogEventBridge<T> logEventInput) throws IOException {
		this(port, logEventInput, new ServerSocket(port, backlog, localBindAddress));
	}

	public TcpSocketServer(int port, LogEventBridge<T> logEventInput) throws IOException {
		this(port, logEventInput, extracted(port));
	}

	private static ServerSocket extracted(int port) throws IOException {
		return new ServerSocket(port);
	}

	public TcpSocketServer(int port, LogEventBridge<T> logEventInput, ServerSocket serverSocket) throws IOException {
		super(port, logEventInput);
		this.serverSocket = serverSocket;
	}

	public void run() {
		EntryMessage entry = this.logger.traceEntry();

		while (this.isActive()) {
			if (this.serverSocket.isClosed()) {
				return;
			}

			try {
				this.logger.debug("Listening for a connection {}...", this.serverSocket);
				Socket clientSocket = this.serverSocket.accept();
				this.logger.debug("Acepted connection on {}...", this.serverSocket);
				this.logger.debug("Socket accepted: {}", clientSocket);
				clientSocket.setSoLinger(true, 0);
				TcpSocketServer<T>.SocketHandler handler = new TcpSocketServer.SocketHandler(clientSocket);
				this.handlers.put(handler.getId(), handler);
				handler.start();
			} catch (IOException var7) {
				if (this.serverSocket.isClosed()) {
					this.logger.traceExit(entry);
					return;
				}

				this.logger.error("Exception encountered on accept. Ignoring. Stack trace :", (Throwable)var7);
			}
		}

		for (Entry<Long, TcpSocketServer<T>.SocketHandler> handlerEntry : this.handlers.entrySet()) {
			TcpSocketServer<T>.SocketHandler handler = (TcpSocketServer.SocketHandler)handlerEntry.getValue();
			handler.shutdown();

			try {
				handler.join();
			} catch (InterruptedException var6) {
			}
		}

		this.logger.traceExit(entry);
	}

	@Override
	public void shutdown() throws IOException {
		EntryMessage entry = this.logger.traceEntry();
		this.setActive(false);
		Thread.currentThread().interrupt();
		this.serverSocket.close();
		this.logger.traceExit(entry);
	}

	protected static class CommandLineArguments extends org.apache.logging.log4j.core.net.server.AbstractSocketServer.CommandLineArguments {
		@Parameter(
			names = {"--backlog", "-b"},
			validateWith = PositiveInteger.class,
			description = "Server socket backlog."
		)
		private int backlog = 50;

		int getBacklog() {
			return this.backlog;
		}

		void setBacklog(int backlog) {
			this.backlog = backlog;
		}
	}

	private class SocketHandler extends Log4jThread {
		private final T inputStream;
		private volatile boolean shutdown = false;

		public SocketHandler(Socket socket) throws IOException {
			this.inputStream = TcpSocketServer.this.logEventInput.wrapStream(socket.getInputStream());
		}

		public void run() {
			EntryMessage entry = TcpSocketServer.this.logger.traceEntry();
			boolean closed = false;

			try {
				try {
					while (!this.shutdown) {
						TcpSocketServer.this.logEventInput.logEvents(this.inputStream, TcpSocketServer.this);
					}
				} catch (EOFException var9) {
					closed = true;
				} catch (OptionalDataException var10) {
					TcpSocketServer.this.logger.error("OptionalDataException eof=" + var10.eof + " length=" + var10.length, (Throwable)var10);
				} catch (IOException var11) {
					TcpSocketServer.this.logger.error("IOException encountered while reading from socket", (Throwable)var11);
				}

				if (!closed) {
					Closer.closeSilently(this.inputStream);
				}
			} finally {
				TcpSocketServer.this.handlers.remove(this.getId());
			}

			TcpSocketServer.this.logger.traceExit(entry);
		}

		public void shutdown() {
			this.shutdown = true;
			this.interrupt();
		}
	}
}
