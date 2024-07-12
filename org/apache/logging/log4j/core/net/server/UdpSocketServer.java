package org.apache.logging.log4j.core.net.server;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.net.server.AbstractSocketServer.CommandLineArguments;
import org.apache.logging.log4j.core.net.server.AbstractSocketServer.ServerConfigurationFactory;
import org.apache.logging.log4j.core.util.BasicCommandLineArguments;

public class UdpSocketServer<T extends InputStream> extends AbstractSocketServer<T> {
	private final DatagramSocket datagramSocket;
	private final int maxBufferSize = 67584;

	public static UdpSocketServer<InputStream> createJsonSocketServer(int port) throws IOException {
		return new UdpSocketServer<>(port, new JsonInputStreamLogEventBridge());
	}

	public static UdpSocketServer<ObjectInputStream> createSerializedSocketServer(int port) throws IOException {
		return new UdpSocketServer(port, new ObjectInputStreamLogEventBridge());
	}

	public static UdpSocketServer<InputStream> createXmlSocketServer(int port) throws IOException {
		return new UdpSocketServer<>(port, new XmlInputStreamLogEventBridge());
	}

	public static void main(String[] args) throws Exception {
		CommandLineArguments cla = BasicCommandLineArguments.parseCommandLine(args, UdpSocketServer.class, new CommandLineArguments());
		if (!cla.isHelp()) {
			if (cla.getConfigLocation() != null) {
				ConfigurationFactory.setConfigurationFactory(new ServerConfigurationFactory(cla.getConfigLocation()));
			}

			UdpSocketServer<ObjectInputStream> socketServer = createSerializedSocketServer(cla.getPort());
			Thread serverThread = socketServer.startNewThread();
			if (cla.isInteractive()) {
				socketServer.awaitTermination(serverThread);
			}
		}
	}

	public UdpSocketServer(int port, LogEventBridge<T> logEventInput) throws IOException {
		super(port, logEventInput);
		this.datagramSocket = new DatagramSocket(port);
	}

	public void run() {
		while (this.isActive()) {
			if (this.datagramSocket.isClosed()) {
				return;
			}

			try {
				byte[] buf = new byte[67584];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				this.datagramSocket.receive(packet);
				ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
				this.logEventInput.logEvents(this.logEventInput.wrapStream(bais), this);
			} catch (OptionalDataException var4) {
				if (this.datagramSocket.isClosed()) {
					return;
				}

				this.logger.error("OptionalDataException eof=" + var4.eof + " length=" + var4.length, (Throwable)var4);
			} catch (EOFException var5) {
				if (this.datagramSocket.isClosed()) {
					return;
				}

				this.logger.info("EOF encountered");
			} catch (IOException var6) {
				if (this.datagramSocket.isClosed()) {
					return;
				}

				this.logger.error("Exception encountered on accept. Ignoring. Stack Trace :", (Throwable)var6);
			}
		}
	}

	@Override
	public void shutdown() {
		this.setActive(false);
		Thread.currentThread().interrupt();
		this.datagramSocket.close();
	}
}
