package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class JZlibDecoder extends ZlibDecoder {
	private final Inflater z = new Inflater();
	private byte[] dictionary;
	private volatile boolean finished;

	public JZlibDecoder() {
		this(ZlibWrapper.ZLIB);
	}

	public JZlibDecoder(ZlibWrapper wrapper) {
		if (wrapper == null) {
			throw new NullPointerException("wrapper");
		} else {
			int resultCode = this.z.init(ZlibUtil.convertWrapperType(wrapper));
			if (resultCode != 0) {
				ZlibUtil.fail(this.z, "initialization failure", resultCode);
			}
		}
	}

	public JZlibDecoder(byte[] dictionary) {
		if (dictionary == null) {
			throw new NullPointerException("dictionary");
		} else {
			this.dictionary = dictionary;
			int resultCode = this.z.inflateInit(JZlib.W_ZLIB);
			if (resultCode != 0) {
				ZlibUtil.fail(this.z, "initialization failure", resultCode);
			}
		}
	}

	@Override
	public boolean isClosed() {
		return this.finished;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (this.finished) {
			in.skipBytes(in.readableBytes());
		} else {
			int inputLength = in.readableBytes();
			if (inputLength != 0) {
				try {
					this.z.avail_in = inputLength;
					if (in.hasArray()) {
						this.z.next_in = in.array();
						this.z.next_in_index = in.arrayOffset() + in.readerIndex();
					} else {
						byte[] array = new byte[inputLength];
						in.getBytes(in.readerIndex(), array);
						this.z.next_in = array;
						this.z.next_in_index = 0;
					}

					int oldNextInIndex = this.z.next_in_index;
					ByteBuf decompressed = ctx.alloc().heapBuffer(inputLength << 1);

					try {
						while (true) {
							decompressed.ensureWritable(this.z.avail_in << 1);
							this.z.avail_out = decompressed.writableBytes();
							this.z.next_out = decompressed.array();
							this.z.next_out_index = decompressed.arrayOffset() + decompressed.writerIndex();
							int oldNextOutIndex = this.z.next_out_index;
							int resultCode = this.z.inflate(2);
							int outputLength = this.z.next_out_index - oldNextOutIndex;
							if (outputLength > 0) {
								decompressed.writerIndex(decompressed.writerIndex() + outputLength);
							}

							switch (resultCode) {
								case -5:
									if (this.z.avail_in <= 0) {
										return;
									}
									break;
								case -4:
								case -3:
								case -2:
								case -1:
								default:
									ZlibUtil.fail(this.z, "decompression failure", resultCode);
								case 0:
									break;
								case 1:
									this.finished = true;
									this.z.inflateEnd();
									return;
								case 2:
									if (this.dictionary == null) {
										ZlibUtil.fail(this.z, "decompression failure", resultCode);
									} else {
										resultCode = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
										if (resultCode != 0) {
											ZlibUtil.fail(this.z, "failed to set the dictionary", resultCode);
										}
									}
							}
						}
					} finally {
						in.skipBytes(this.z.next_in_index - oldNextInIndex);
						if (decompressed.isReadable()) {
							out.add(decompressed);
						} else {
							decompressed.release();
						}
					}
				} finally {
					this.z.next_in = null;
					this.z.next_out = null;
				}
			}
		}
	}
}
