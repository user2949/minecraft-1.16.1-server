package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelOutboundBuffer.MessageProcessor;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.Limits;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.concurrent.FastThreadLocal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

final class NativeDatagramPacketArray implements MessageProcessor {
	private static final FastThreadLocal<NativeDatagramPacketArray> ARRAY = new FastThreadLocal<NativeDatagramPacketArray>() {
		protected NativeDatagramPacketArray initialValue() throws Exception {
			return new NativeDatagramPacketArray();
		}

		protected void onRemoval(NativeDatagramPacketArray value) throws Exception {
			NativeDatagramPacketArray.NativeDatagramPacket[] packetsArray = value.packets;

			for (NativeDatagramPacketArray.NativeDatagramPacket datagramPacket : packetsArray) {
				datagramPacket.release();
			}
		}
	};
	private final NativeDatagramPacketArray.NativeDatagramPacket[] packets = new NativeDatagramPacketArray.NativeDatagramPacket[Limits.UIO_MAX_IOV];
	private int count;

	private NativeDatagramPacketArray() {
		for (int i = 0; i < this.packets.length; i++) {
			this.packets[i] = new NativeDatagramPacketArray.NativeDatagramPacket();
		}
	}

	boolean add(DatagramPacket packet) {
		if (this.count == this.packets.length) {
			return false;
		} else {
			ByteBuf content = packet.content();
			int len = content.readableBytes();
			if (len == 0) {
				return true;
			} else {
				NativeDatagramPacketArray.NativeDatagramPacket p = this.packets[this.count];
				InetSocketAddress recipient = packet.recipient();
				if (!p.init(content, recipient)) {
					return false;
				} else {
					this.count++;
					return true;
				}
			}
		}
	}

	@Override
	public boolean processMessage(Object msg) throws Exception {
		return msg instanceof DatagramPacket && this.add((DatagramPacket)msg);
	}

	int count() {
		return this.count;
	}

	NativeDatagramPacketArray.NativeDatagramPacket[] packets() {
		return this.packets;
	}

	static NativeDatagramPacketArray getInstance(ChannelOutboundBuffer buffer) throws Exception {
		NativeDatagramPacketArray array = ARRAY.get();
		array.count = 0;
		buffer.forEachFlushedMessage(array);
		return array;
	}

	static final class NativeDatagramPacket {
		private final IovArray array = new IovArray();
		private long memoryAddress;
		private int count;
		private byte[] addr;
		private int scopeId;
		private int port;

		private void release() {
			this.array.release();
		}

		private boolean init(ByteBuf buf, InetSocketAddress recipient) {
			this.array.clear();
			if (!this.array.add(buf)) {
				return false;
			} else {
				this.memoryAddress = this.array.memoryAddress(0);
				this.count = this.array.count();
				InetAddress address = recipient.getAddress();
				if (address instanceof Inet6Address) {
					this.addr = address.getAddress();
					this.scopeId = ((Inet6Address)address).getScopeId();
				} else {
					this.addr = NativeInetAddress.ipv4MappedIpv6Address(address.getAddress());
					this.scopeId = 0;
				}

				this.port = recipient.getPort();
				return true;
			}
		}
	}
}
