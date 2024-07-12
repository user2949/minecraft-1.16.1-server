package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.spdy.SpdyHeaders.HttpNames;
import io.netty.handler.codec.spdy.SpdyHttpHeaders.Names;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class SpdyHttpEncoder extends MessageToMessageEncoder<HttpObject> {
	private int currentStreamId;
	private final boolean validateHeaders;
	private final boolean headersToLowerCase;

	public SpdyHttpEncoder(SpdyVersion version) {
		this(version, true, true);
	}

	public SpdyHttpEncoder(SpdyVersion version, boolean headersToLowerCase, boolean validateHeaders) {
		if (version == null) {
			throw new NullPointerException("version");
		} else {
			this.headersToLowerCase = headersToLowerCase;
			this.validateHeaders = validateHeaders;
		}
	}

	protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
		boolean valid = false;
		boolean last = false;
		if (msg instanceof HttpRequest) {
			HttpRequest httpRequest = (HttpRequest)msg;
			SpdySynStreamFrame spdySynStreamFrame = this.createSynStreamFrame(httpRequest);
			out.add(spdySynStreamFrame);
			last = spdySynStreamFrame.isLast() || spdySynStreamFrame.isUnidirectional();
			valid = true;
		}

		if (msg instanceof HttpResponse) {
			HttpResponse httpResponse = (HttpResponse)msg;
			SpdyHeadersFrame spdyHeadersFrame = this.createHeadersFrame(httpResponse);
			out.add(spdyHeadersFrame);
			last = spdyHeadersFrame.isLast();
			valid = true;
		}

		if (msg instanceof HttpContent && !last) {
			HttpContent chunk = (HttpContent)msg;
			chunk.content().retain();
			SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.currentStreamId, chunk.content());
			if (!(chunk instanceof LastHttpContent)) {
				out.add(spdyDataFrame);
			} else {
				LastHttpContent trailer = (LastHttpContent)chunk;
				HttpHeaders trailers = trailer.trailingHeaders();
				if (trailers.isEmpty()) {
					spdyDataFrame.setLast(true);
					out.add(spdyDataFrame);
				} else {
					SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId, this.validateHeaders);
					spdyHeadersFrame.setLast(true);
					Iterator<Entry<CharSequence, CharSequence>> itr = trailers.iteratorCharSequence();

					while (itr.hasNext()) {
						Entry<CharSequence, CharSequence> entry = (Entry<CharSequence, CharSequence>)itr.next();
						CharSequence headerName = (CharSequence)(this.headersToLowerCase
							? AsciiString.of((CharSequence)entry.getKey()).toLowerCase()
							: (CharSequence)entry.getKey());
						spdyHeadersFrame.headers().add(headerName, (CharSequence)entry.getValue());
					}

					out.add(spdyDataFrame);
					out.add(spdyHeadersFrame);
				}
			}

			valid = true;
		}

		if (!valid) {
			throw new UnsupportedMessageTypeException(msg);
		}
	}

	private SpdySynStreamFrame createSynStreamFrame(HttpRequest httpRequest) throws Exception {
		HttpHeaders httpHeaders = httpRequest.headers();
		int streamId = httpHeaders.getInt(Names.STREAM_ID);
		int associatedToStreamId = httpHeaders.getInt(Names.ASSOCIATED_TO_STREAM_ID, 0);
		byte priority = (byte)httpHeaders.getInt(Names.PRIORITY, 0);
		CharSequence scheme = httpHeaders.get(Names.SCHEME);
		httpHeaders.remove(Names.STREAM_ID);
		httpHeaders.remove(Names.ASSOCIATED_TO_STREAM_ID);
		httpHeaders.remove(Names.PRIORITY);
		httpHeaders.remove(Names.SCHEME);
		httpHeaders.remove(HttpHeaderNames.CONNECTION);
		httpHeaders.remove("Keep-Alive");
		httpHeaders.remove("Proxy-Connection");
		httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
		SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority, this.validateHeaders);
		SpdyHeaders frameHeaders = spdySynStreamFrame.headers();
		frameHeaders.set(HttpNames.METHOD, httpRequest.method().name());
		frameHeaders.set(HttpNames.PATH, httpRequest.uri());
		frameHeaders.set(HttpNames.VERSION, httpRequest.protocolVersion().text());
		CharSequence host = httpHeaders.get(HttpHeaderNames.HOST);
		httpHeaders.remove(HttpHeaderNames.HOST);
		frameHeaders.set(HttpNames.HOST, host);
		if (scheme == null) {
			scheme = "https";
		}

		frameHeaders.set(HttpNames.SCHEME, scheme);
		Iterator<Entry<CharSequence, CharSequence>> itr = httpHeaders.iteratorCharSequence();

		while (itr.hasNext()) {
			Entry<CharSequence, CharSequence> entry = (Entry<CharSequence, CharSequence>)itr.next();
			CharSequence headerName = (CharSequence)(this.headersToLowerCase ? AsciiString.of((CharSequence)entry.getKey()).toLowerCase() : (CharSequence)entry.getKey());
			frameHeaders.add(headerName, (CharSequence)entry.getValue());
		}

		this.currentStreamId = spdySynStreamFrame.streamId();
		if (associatedToStreamId == 0) {
			spdySynStreamFrame.setLast(isLast(httpRequest));
		} else {
			spdySynStreamFrame.setUnidirectional(true);
		}

		return spdySynStreamFrame;
	}

	private SpdyHeadersFrame createHeadersFrame(HttpResponse httpResponse) throws Exception {
		HttpHeaders httpHeaders = httpResponse.headers();
		int streamId = httpHeaders.getInt(Names.STREAM_ID);
		httpHeaders.remove(Names.STREAM_ID);
		httpHeaders.remove(HttpHeaderNames.CONNECTION);
		httpHeaders.remove("Keep-Alive");
		httpHeaders.remove("Proxy-Connection");
		httpHeaders.remove(HttpHeaderNames.TRANSFER_ENCODING);
		SpdyHeadersFrame spdyHeadersFrame;
		if (SpdyCodecUtil.isServerId(streamId)) {
			spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId, this.validateHeaders);
		} else {
			spdyHeadersFrame = new DefaultSpdySynReplyFrame(streamId, this.validateHeaders);
		}

		SpdyHeaders frameHeaders = spdyHeadersFrame.headers();
		frameHeaders.set(HttpNames.STATUS, httpResponse.status().codeAsText());
		frameHeaders.set(HttpNames.VERSION, httpResponse.protocolVersion().text());
		Iterator<Entry<CharSequence, CharSequence>> itr = httpHeaders.iteratorCharSequence();

		while (itr.hasNext()) {
			Entry<CharSequence, CharSequence> entry = (Entry<CharSequence, CharSequence>)itr.next();
			CharSequence headerName = (CharSequence)(this.headersToLowerCase ? AsciiString.of((CharSequence)entry.getKey()).toLowerCase() : (CharSequence)entry.getKey());
			spdyHeadersFrame.headers().add(headerName, (CharSequence)entry.getValue());
		}

		this.currentStreamId = streamId;
		spdyHeadersFrame.setLast(isLast(httpResponse));
		return spdyHeadersFrame;
	}

	private static boolean isLast(HttpMessage httpMessage) {
		if (httpMessage instanceof FullHttpMessage) {
			FullHttpMessage fullMessage = (FullHttpMessage)httpMessage;
			if (fullMessage.trailingHeaders().isEmpty() && !fullMessage.content().isReadable()) {
				return true;
			}
		}

		return false;
	}
}
