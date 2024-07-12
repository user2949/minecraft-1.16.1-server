package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

public class DefaultHttpObject implements HttpObject {
	private static final int HASH_CODE_PRIME = 31;
	private DecoderResult decoderResult = DecoderResult.SUCCESS;

	protected DefaultHttpObject() {
	}

	@Override
	public DecoderResult decoderResult() {
		return this.decoderResult;
	}

	@Deprecated
	@Override
	public DecoderResult getDecoderResult() {
		return this.decoderResult();
	}

	@Override
	public void setDecoderResult(DecoderResult decoderResult) {
		if (decoderResult == null) {
			throw new NullPointerException("decoderResult");
		} else {
			this.decoderResult = decoderResult;
		}
	}

	public int hashCode() {
		int result = 1;
		return 31 * result + this.decoderResult.hashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof DefaultHttpObject)) {
			return false;
		} else {
			DefaultHttpObject other = (DefaultHttpObject)o;
			return this.decoderResult().equals(other.decoderResult());
		}
	}
}
