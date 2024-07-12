package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;

public interface SpdyFrameDecoderDelegate {
	void readDataFrame(int integer, boolean boolean2, ByteBuf byteBuf);

	void readSynStreamFrame(int integer1, int integer2, byte byte3, boolean boolean4, boolean boolean5);

	void readSynReplyFrame(int integer, boolean boolean2);

	void readRstStreamFrame(int integer1, int integer2);

	void readSettingsFrame(boolean boolean1);

	void readSetting(int integer1, int integer2, boolean boolean3, boolean boolean4);

	void readSettingsEnd();

	void readPingFrame(int integer);

	void readGoAwayFrame(int integer1, int integer2);

	void readHeadersFrame(int integer, boolean boolean2);

	void readWindowUpdateFrame(int integer1, int integer2);

	void readHeaderBlock(ByteBuf byteBuf);

	void readHeaderBlockEnd();

	void readFrameError(String string);
}
