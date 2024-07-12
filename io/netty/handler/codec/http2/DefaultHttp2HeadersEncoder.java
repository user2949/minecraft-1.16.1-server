package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.Http2HeadersEncoder.Configuration;
import io.netty.handler.codec.http2.Http2HeadersEncoder.SensitivityDetector;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2HeadersEncoder implements Http2HeadersEncoder, Configuration {
	private final HpackEncoder hpackEncoder;
	private final SensitivityDetector sensitivityDetector;
	private final ByteBuf tableSizeChangeOutput = Unpooled.buffer();

	public DefaultHttp2HeadersEncoder() {
		this(NEVER_SENSITIVE);
	}

	public DefaultHttp2HeadersEncoder(SensitivityDetector sensitivityDetector) {
		this(sensitivityDetector, new HpackEncoder());
	}

	public DefaultHttp2HeadersEncoder(SensitivityDetector sensitivityDetector, boolean ignoreMaxHeaderListSize) {
		this(sensitivityDetector, new HpackEncoder(ignoreMaxHeaderListSize));
	}

	public DefaultHttp2HeadersEncoder(SensitivityDetector sensitivityDetector, boolean ignoreMaxHeaderListSize, int dynamicTableArraySizeHint) {
		this(sensitivityDetector, new HpackEncoder(ignoreMaxHeaderListSize, dynamicTableArraySizeHint));
	}

	DefaultHttp2HeadersEncoder(SensitivityDetector sensitivityDetector, HpackEncoder hpackEncoder) {
		this.sensitivityDetector = ObjectUtil.checkNotNull(sensitivityDetector, "sensitiveDetector");
		this.hpackEncoder = ObjectUtil.checkNotNull(hpackEncoder, "hpackEncoder");
	}

	@Override
	public void encodeHeaders(int streamId, Http2Headers headers, ByteBuf buffer) throws Http2Exception {
		try {
			if (this.tableSizeChangeOutput.isReadable()) {
				buffer.writeBytes(this.tableSizeChangeOutput);
				this.tableSizeChangeOutput.clear();
			}

			this.hpackEncoder.encodeHeaders(streamId, buffer, headers, this.sensitivityDetector);
		} catch (Http2Exception var5) {
			throw var5;
		} catch (Throwable var6) {
			throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, var6, "Failed encoding headers block: %s", var6.getMessage());
		}
	}

	@Override
	public void maxHeaderTableSize(long max) throws Http2Exception {
		this.hpackEncoder.setMaxHeaderTableSize(this.tableSizeChangeOutput, max);
	}

	@Override
	public long maxHeaderTableSize() {
		return this.hpackEncoder.getMaxHeaderTableSize();
	}

	@Override
	public void maxHeaderListSize(long max) throws Http2Exception {
		this.hpackEncoder.setMaxHeaderListSize(max);
	}

	@Override
	public long maxHeaderListSize() {
		return this.hpackEncoder.getMaxHeaderListSize();
	}

	@Override
	public Configuration configuration() {
		return this;
	}
}
