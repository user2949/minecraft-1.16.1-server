package io.netty.util.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

public final class SocketUtils {
	private SocketUtils() {
	}

	public static void connect(Socket socket, SocketAddress remoteAddress, int timeout) throws IOException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws IOException {
					socket.connect(remoteAddress, timeout);
					return null;
				}
			});
		} catch (PrivilegedActionException var4) {
			throw (IOException)var4.getCause();
		}
	}

	public static void bind(Socket socket, SocketAddress bindpoint) throws IOException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws IOException {
					socket.bind(bindpoint);
					return null;
				}
			});
		} catch (PrivilegedActionException var3) {
			throw (IOException)var3.getCause();
		}
	}

	public static boolean connect(SocketChannel socketChannel, SocketAddress remoteAddress) throws IOException {
		try {
			return (Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>() {
				public Boolean run() throws IOException {
					return socketChannel.connect(remoteAddress);
				}
			});
		} catch (PrivilegedActionException var3) {
			throw (IOException)var3.getCause();
		}
	}

	public static void bind(SocketChannel socketChannel, SocketAddress address) throws IOException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws IOException {
					socketChannel.bind(address);
					return null;
				}
			});
		} catch (PrivilegedActionException var3) {
			throw (IOException)var3.getCause();
		}
	}

	public static SocketChannel accept(ServerSocketChannel serverSocketChannel) throws IOException {
		try {
			return (SocketChannel)AccessController.doPrivileged(new PrivilegedExceptionAction<SocketChannel>() {
				public SocketChannel run() throws IOException {
					return serverSocketChannel.accept();
				}
			});
		} catch (PrivilegedActionException var2) {
			throw (IOException)var2.getCause();
		}
	}

	public static void bind(DatagramChannel networkChannel, SocketAddress address) throws IOException {
		try {
			AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
				public Void run() throws IOException {
					networkChannel.bind(address);
					return null;
				}
			});
		} catch (PrivilegedActionException var3) {
			throw (IOException)var3.getCause();
		}
	}

	public static SocketAddress localSocketAddress(ServerSocket socket) {
		return (SocketAddress)AccessController.doPrivileged(new PrivilegedAction<SocketAddress>() {
			public SocketAddress run() {
				return socket.getLocalSocketAddress();
			}
		});
	}

	public static InetAddress addressByName(String hostname) throws UnknownHostException {
		try {
			return (InetAddress)AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress>() {
				public InetAddress run() throws UnknownHostException {
					return InetAddress.getByName(hostname);
				}
			});
		} catch (PrivilegedActionException var2) {
			throw (UnknownHostException)var2.getCause();
		}
	}

	public static InetAddress[] allAddressesByName(String hostname) throws UnknownHostException {
		try {
			return (InetAddress[])AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress[]>() {
				public InetAddress[] run() throws UnknownHostException {
					return InetAddress.getAllByName(hostname);
				}
			});
		} catch (PrivilegedActionException var2) {
			throw (UnknownHostException)var2.getCause();
		}
	}

	public static InetSocketAddress socketAddress(String hostname, int port) {
		return (InetSocketAddress)AccessController.doPrivileged(new PrivilegedAction<InetSocketAddress>() {
			public InetSocketAddress run() {
				return new InetSocketAddress(hostname, port);
			}
		});
	}

	public static Enumeration<InetAddress> addressesFromNetworkInterface(NetworkInterface intf) {
		return (Enumeration<InetAddress>)AccessController.doPrivileged(new PrivilegedAction<Enumeration<InetAddress>>() {
			public Enumeration<InetAddress> run() {
				return intf.getInetAddresses();
			}
		});
	}

	public static InetAddress loopbackAddress() {
		return (InetAddress)AccessController.doPrivileged(new PrivilegedAction<InetAddress>() {
			public InetAddress run() {
				if (PlatformDependent.javaVersion() >= 7) {
					return InetAddress.getLoopbackAddress();
				} else {
					try {
						return InetAddress.getByName(null);
					} catch (UnknownHostException var2) {
						throw new IllegalStateException(var2);
					}
				}
			}
		});
	}

	public static byte[] hardwareAddressFromNetworkInterface(NetworkInterface intf) throws SocketException {
		try {
			return (byte[])AccessController.doPrivileged(new PrivilegedExceptionAction<byte[]>() {
				public byte[] run() throws SocketException {
					return intf.getHardwareAddress();
				}
			});
		} catch (PrivilegedActionException var2) {
			throw (SocketException)var2.getCause();
		}
	}
}
