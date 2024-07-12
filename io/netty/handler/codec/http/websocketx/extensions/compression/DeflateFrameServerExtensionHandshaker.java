package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtension;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
import java.util.Collections;

public final class DeflateFrameServerExtensionHandshaker implements WebSocketServerExtensionHandshaker {
	static final String X_WEBKIT_DEFLATE_FRAME_EXTENSION = "x-webkit-deflate-frame";
	static final String DEFLATE_FRAME_EXTENSION = "deflate-frame";
	private final int compressionLevel;

	public DeflateFrameServerExtensionHandshaker() {
		this(6);
	}

	public DeflateFrameServerExtensionHandshaker(int compressionLevel) {
		if (compressionLevel >= 0 && compressionLevel <= 9) {
			this.compressionLevel = compressionLevel;
		} else {
			throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
		}
	}

	@Override
	public WebSocketServerExtension handshakeExtension(WebSocketExtensionData extensionData) {
		if (!"x-webkit-deflate-frame".equals(extensionData.name()) && !"deflate-frame".equals(extensionData.name())) {
			return null;
		} else {
			return extensionData.parameters().isEmpty()
				? new DeflateFrameServerExtensionHandshaker.DeflateFrameServerExtension(this.compressionLevel, extensionData.name())
				: null;
		}
	}

	private static class DeflateFrameServerExtension implements WebSocketServerExtension {
		private final String extensionName;
		private final int compressionLevel;

		public DeflateFrameServerExtension(int compressionLevel, String extensionName) {
			this.extensionName = extensionName;
			this.compressionLevel = compressionLevel;
		}

		@Override
		public int rsv() {
			return 4;
		}

		@Override
		public WebSocketExtensionEncoder newExtensionEncoder() {
			return new PerFrameDeflateEncoder(this.compressionLevel, 15, false);
		}

		@Override
		public WebSocketExtensionDecoder newExtensionDecoder() {
			return new PerFrameDeflateDecoder(false);
		}

		@Override
		public WebSocketExtensionData newReponseData() {
			return new WebSocketExtensionData(this.extensionName, Collections.emptyMap());
		}
	}
}
