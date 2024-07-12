package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.NetUtil;
import java.io.IOException;
import java.util.Map;

public class DefaultSctpServerChannelConfig extends DefaultChannelConfig implements SctpServerChannelConfig {
	private final com.sun.nio.sctp.SctpServerChannel javaChannel;
	private volatile int backlog = NetUtil.SOMAXCONN;

	public DefaultSctpServerChannelConfig(SctpServerChannel channel, com.sun.nio.sctp.SctpServerChannel javaChannel) {
		super(channel);
		if (javaChannel == null) {
			throw new NullPointerException("javaChannel");
		} else {
			this.javaChannel = javaChannel;
		}
	}

	@Override
	public Map<ChannelOption<?>, Object> getOptions() {
		return this.getOptions(super.getOptions(), new ChannelOption[]{ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS});
	}

	@Override
	public <T> T getOption(ChannelOption<T> option) {
		if (option == ChannelOption.SO_RCVBUF) {
			return (T)this.getReceiveBufferSize();
		} else if (option == ChannelOption.SO_SNDBUF) {
			return (T)this.getSendBufferSize();
		} else {
			return (T)(option == SctpChannelOption.SCTP_INIT_MAXSTREAMS ? this.getInitMaxStreams() : super.getOption(option));
		}
	}

	@Override
	public <T> boolean setOption(ChannelOption<T> option, T value) {
		this.validate(option, value);
		if (option == ChannelOption.SO_RCVBUF) {
			this.setReceiveBufferSize((Integer)value);
		} else if (option == ChannelOption.SO_SNDBUF) {
			this.setSendBufferSize((Integer)value);
		} else {
			if (option != SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
				return super.setOption(option, value);
			}

			this.setInitMaxStreams((InitMaxStreams)value);
		}

		return true;
	}

	@Override
	public int getSendBufferSize() {
		try {
			return (Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF);
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	@Override
	public SctpServerChannelConfig setSendBufferSize(int sendBufferSize) {
		try {
			this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, sendBufferSize);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getReceiveBufferSize() {
		try {
			return (Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF);
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	@Override
	public SctpServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
		try {
			this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, receiveBufferSize);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public InitMaxStreams getInitMaxStreams() {
		try {
			return (InitMaxStreams)this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
		} catch (IOException var2) {
			throw new ChannelException(var2);
		}
	}

	@Override
	public SctpServerChannelConfig setInitMaxStreams(InitMaxStreams initMaxStreams) {
		try {
			this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, initMaxStreams);
			return this;
		} catch (IOException var3) {
			throw new ChannelException(var3);
		}
	}

	@Override
	public int getBacklog() {
		return this.backlog;
	}

	@Override
	public SctpServerChannelConfig setBacklog(int backlog) {
		if (backlog < 0) {
			throw new IllegalArgumentException("backlog: " + backlog);
		} else {
			this.backlog = backlog;
			return this;
		}
	}

	@Deprecated
	@Override
	public SctpServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
		super.setMaxMessagesPerRead(maxMessagesPerRead);
		return this;
	}

	@Override
	public SctpServerChannelConfig setWriteSpinCount(int writeSpinCount) {
		super.setWriteSpinCount(writeSpinCount);
		return this;
	}

	@Override
	public SctpServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
		super.setConnectTimeoutMillis(connectTimeoutMillis);
		return this;
	}

	@Override
	public SctpServerChannelConfig setAllocator(ByteBufAllocator allocator) {
		super.setAllocator(allocator);
		return this;
	}

	@Override
	public SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
		super.setRecvByteBufAllocator(allocator);
		return this;
	}

	@Override
	public SctpServerChannelConfig setAutoRead(boolean autoRead) {
		super.setAutoRead(autoRead);
		return this;
	}

	@Override
	public SctpServerChannelConfig setAutoClose(boolean autoClose) {
		super.setAutoClose(autoClose);
		return this;
	}

	@Override
	public SctpServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
		super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
		return this;
	}

	@Override
	public SctpServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
		super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
		return this;
	}

	@Override
	public SctpServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
		super.setWriteBufferWaterMark(writeBufferWaterMark);
		return this;
	}

	@Override
	public SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
		super.setMessageSizeEstimator(estimator);
		return this;
	}
}
