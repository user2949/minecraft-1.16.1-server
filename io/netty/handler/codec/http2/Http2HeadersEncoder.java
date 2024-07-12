package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;

public interface Http2HeadersEncoder {
	Http2HeadersEncoder.SensitivityDetector NEVER_SENSITIVE = new Http2HeadersEncoder.SensitivityDetector() {
		@Override
		public boolean isSensitive(CharSequence name, CharSequence value) {
			return false;
		}
	};
	Http2HeadersEncoder.SensitivityDetector ALWAYS_SENSITIVE = new Http2HeadersEncoder.SensitivityDetector() {
		@Override
		public boolean isSensitive(CharSequence name, CharSequence value) {
			return true;
		}
	};

	void encodeHeaders(int integer, Http2Headers http2Headers, ByteBuf byteBuf) throws Http2Exception;

	Http2HeadersEncoder.Configuration configuration();

	public interface Configuration {
		void maxHeaderTableSize(long long1) throws Http2Exception;

		long maxHeaderTableSize();

		void maxHeaderListSize(long long1) throws Http2Exception;

		long maxHeaderListSize();
	}

	public interface SensitivityDetector {
		boolean isSensitive(CharSequence charSequence1, CharSequence charSequence2);
	}
}
