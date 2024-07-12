package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;

final class HpackHuffmanDecoder {
	private static final Http2Exception EOS_DECODED = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - EOS Decoded"), HpackHuffmanDecoder.class, "decode(..)"
	);
	private static final Http2Exception INVALID_PADDING = ThrowableUtil.unknownStackTrace(
		Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - Invalid Padding"), HpackHuffmanDecoder.class, "decode(..)"
	);
	private static final HpackHuffmanDecoder.Node ROOT = buildTree(HpackUtil.HUFFMAN_CODES, HpackUtil.HUFFMAN_CODE_LENGTHS);
	private final HpackHuffmanDecoder.DecoderProcessor processor;

	HpackHuffmanDecoder(int initialCapacity) {
		this.processor = new HpackHuffmanDecoder.DecoderProcessor(initialCapacity);
	}

	public AsciiString decode(ByteBuf buf, int length) throws Http2Exception {
		this.processor.reset();
		buf.forEachByte(buf.readerIndex(), length, this.processor);
		buf.skipBytes(length);
		return this.processor.end();
	}

	private static HpackHuffmanDecoder.Node buildTree(int[] codes, byte[] lengths) {
		HpackHuffmanDecoder.Node root = new HpackHuffmanDecoder.Node();

		for (int i = 0; i < codes.length; i++) {
			insert(root, i, codes[i], lengths[i]);
		}

		return root;
	}

	private static void insert(HpackHuffmanDecoder.Node root, int symbol, int code, byte length) {
		HpackHuffmanDecoder.Node current = root;

		while (length > 8) {
			if (current.isTerminal()) {
				throw new IllegalStateException("invalid Huffman code: prefix not unique");
			}

			length = (byte)(length - 8);
			int i = code >>> length & 0xFF;
			if (current.children[i] == null) {
				current.children[i] = new HpackHuffmanDecoder.Node();
			}

			current = current.children[i];
		}

		HpackHuffmanDecoder.Node terminal = new HpackHuffmanDecoder.Node(symbol, length);
		int shift = 8 - length;
		int start = code << shift & 0xFF;
		int end = 1 << shift;

		for (int i = start; i < start + end; i++) {
			current.children[i] = terminal;
		}
	}

	private static final class DecoderProcessor implements ByteProcessor {
		private final int initialCapacity;
		private byte[] bytes;
		private int index;
		private HpackHuffmanDecoder.Node node;
		private int current;
		private int currentBits;
		private int symbolBits;

		DecoderProcessor(int initialCapacity) {
			this.initialCapacity = ObjectUtil.checkPositive(initialCapacity, "initialCapacity");
		}

		void reset() {
			this.node = HpackHuffmanDecoder.ROOT;
			this.current = 0;
			this.currentBits = 0;
			this.symbolBits = 0;
			this.bytes = new byte[this.initialCapacity];
			this.index = 0;
		}

		@Override
		public boolean process(byte value) throws Http2Exception {
			this.current = this.current << 8 | value & 255;
			this.currentBits += 8;
			this.symbolBits += 8;

			do {
				this.node = this.node.children[this.current >>> this.currentBits - 8 & 0xFF];
				this.currentBits = this.currentBits - this.node.bits;
				if (this.node.isTerminal()) {
					if (this.node.symbol == 256) {
						throw HpackHuffmanDecoder.EOS_DECODED;
					}

					this.append(this.node.symbol);
					this.node = HpackHuffmanDecoder.ROOT;
					this.symbolBits = this.currentBits;
				}
			} while (this.currentBits >= 8);

			return true;
		}

		AsciiString end() throws Http2Exception {
			while (this.currentBits > 0) {
				this.node = this.node.children[this.current << 8 - this.currentBits & 0xFF];
				if (this.node.isTerminal() && this.node.bits <= this.currentBits) {
					if (this.node.symbol == 256) {
						throw HpackHuffmanDecoder.EOS_DECODED;
					}

					this.currentBits = this.currentBits - this.node.bits;
					this.append(this.node.symbol);
					this.node = HpackHuffmanDecoder.ROOT;
					this.symbolBits = this.currentBits;
					continue;
				}
				break;
			}

			int mask = (1 << this.symbolBits) - 1;
			if (this.symbolBits <= 7 && (this.current & mask) == mask) {
				return new AsciiString(this.bytes, 0, this.index, false);
			} else {
				throw HpackHuffmanDecoder.INVALID_PADDING;
			}
		}

		private void append(int i) {
			if (this.bytes.length == this.index) {
				int newLength = this.bytes.length >= 1024 ? this.bytes.length + this.initialCapacity : this.bytes.length << 1;
				byte[] newBytes = new byte[newLength];
				System.arraycopy(this.bytes, 0, newBytes, 0, this.bytes.length);
				this.bytes = newBytes;
			}

			this.bytes[this.index++] = (byte)i;
		}
	}

	private static final class Node {
		private final int symbol;
		private final int bits;
		private final HpackHuffmanDecoder.Node[] children;

		Node() {
			this.symbol = 0;
			this.bits = 8;
			this.children = new HpackHuffmanDecoder.Node[256];
		}

		Node(int symbol, int bits) {
			assert bits > 0 && bits <= 8;

			this.symbol = symbol;
			this.bits = bits;
			this.children = null;
		}

		private boolean isTerminal() {
			return this.children == null;
		}
	}
}
