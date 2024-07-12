package io.netty.channel.rxtx;

import io.netty.channel.ChannelOption;
import io.netty.channel.rxtx.RxtxChannelConfig.Databits;
import io.netty.channel.rxtx.RxtxChannelConfig.Paritybit;
import io.netty.channel.rxtx.RxtxChannelConfig.Stopbits;

@Deprecated
public final class RxtxChannelOption<T> extends ChannelOption<T> {
	public static final ChannelOption<Integer> BAUD_RATE = valueOf(RxtxChannelOption.class, "BAUD_RATE");
	public static final ChannelOption<Boolean> DTR = valueOf(RxtxChannelOption.class, "DTR");
	public static final ChannelOption<Boolean> RTS = valueOf(RxtxChannelOption.class, "RTS");
	public static final ChannelOption<Stopbits> STOP_BITS = valueOf(RxtxChannelOption.class, "STOP_BITS");
	public static final ChannelOption<Databits> DATA_BITS = valueOf(RxtxChannelOption.class, "DATA_BITS");
	public static final ChannelOption<Paritybit> PARITY_BIT = valueOf(RxtxChannelOption.class, "PARITY_BIT");
	public static final ChannelOption<Integer> WAIT_TIME = valueOf(RxtxChannelOption.class, "WAIT_TIME");
	public static final ChannelOption<Integer> READ_TIMEOUT = valueOf(RxtxChannelOption.class, "READ_TIMEOUT");

	private RxtxChannelOption() {
		super(null);
	}
}
