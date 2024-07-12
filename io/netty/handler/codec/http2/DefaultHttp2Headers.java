package io.netty.handler.codec.http2;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeaders.HeaderEntry;
import io.netty.handler.codec.DefaultHeaders.NameValidator;
import io.netty.handler.codec.http2.Http2Headers.PseudoHeaderName;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2Headers extends DefaultHeaders<CharSequence, CharSequence, Http2Headers> implements Http2Headers {
	private static final ByteProcessor HTTP2_NAME_VALIDATOR_PROCESSOR = new ByteProcessor() {
		@Override
		public boolean process(byte value) {
			return !AsciiString.isUpperCase(value);
		}
	};
	static final NameValidator<CharSequence> HTTP2_NAME_VALIDATOR = new NameValidator<CharSequence>() {
		public void validateName(CharSequence name) {
			if (name == null || name.length() == 0) {
				PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "empty headers are not allowed [%s]", name));
			}

			if (name instanceof AsciiString) {
				int index;
				try {
					index = ((AsciiString)name).forEachByte(DefaultHttp2Headers.HTTP2_NAME_VALIDATOR_PROCESSOR);
				} catch (Http2Exception var4) {
					PlatformDependent.throwException(var4);
					return;
				} catch (Throwable var5) {
					PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, var5, "unexpected error. invalid header name [%s]", name));
					return;
				}

				if (index != -1) {
					PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", name));
				}
			} else {
				for (int i = 0; i < name.length(); i++) {
					if (AsciiString.isUpperCase(name.charAt(i))) {
						PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", name));
					}
				}
			}
		}
	};
	private HeaderEntry<CharSequence, CharSequence> firstNonPseudo = this.head;

	public DefaultHttp2Headers() {
		this(true);
	}

	public DefaultHttp2Headers(boolean validate) {
		super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, validate ? HTTP2_NAME_VALIDATOR : NameValidator.NOT_NULL);
	}

	public DefaultHttp2Headers(boolean validate, int arraySizeHint) {
		super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, validate ? HTTP2_NAME_VALIDATOR : NameValidator.NOT_NULL, arraySizeHint);
	}

	public Http2Headers clear() {
		this.firstNonPseudo = this.head;
		return (Http2Headers)super.clear();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Http2Headers && this.equals((Http2Headers)o, AsciiString.CASE_SENSITIVE_HASHER);
	}

	@Override
	public int hashCode() {
		return this.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
	}

	@Override
	public Http2Headers method(CharSequence value) {
		this.set(PseudoHeaderName.METHOD.value(), value);
		return this;
	}

	@Override
	public Http2Headers scheme(CharSequence value) {
		this.set(PseudoHeaderName.SCHEME.value(), value);
		return this;
	}

	@Override
	public Http2Headers authority(CharSequence value) {
		this.set(PseudoHeaderName.AUTHORITY.value(), value);
		return this;
	}

	@Override
	public Http2Headers path(CharSequence value) {
		this.set(PseudoHeaderName.PATH.value(), value);
		return this;
	}

	@Override
	public Http2Headers status(CharSequence value) {
		this.set(PseudoHeaderName.STATUS.value(), value);
		return this;
	}

	@Override
	public CharSequence method() {
		return this.get(PseudoHeaderName.METHOD.value());
	}

	@Override
	public CharSequence scheme() {
		return this.get(PseudoHeaderName.SCHEME.value());
	}

	@Override
	public CharSequence authority() {
		return this.get(PseudoHeaderName.AUTHORITY.value());
	}

	@Override
	public CharSequence path() {
		return this.get(PseudoHeaderName.PATH.value());
	}

	@Override
	public CharSequence status() {
		return this.get(PseudoHeaderName.STATUS.value());
	}

	public boolean contains(CharSequence name, CharSequence value) {
		return this.contains(name, value, false);
	}

	@Override
	public boolean contains(CharSequence name, CharSequence value, boolean caseInsensitive) {
		return this.contains(name, value, caseInsensitive ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
	}

	protected final HeaderEntry<CharSequence, CharSequence> newHeaderEntry(
		int h, CharSequence name, CharSequence value, HeaderEntry<CharSequence, CharSequence> next
	) {
		return new DefaultHttp2Headers.Http2HeaderEntry(h, name, value, next);
	}

	private final class Http2HeaderEntry extends HeaderEntry<CharSequence, CharSequence> {
		protected Http2HeaderEntry(int hash, CharSequence key, CharSequence value, HeaderEntry<CharSequence, CharSequence> next) {
			super(hash, key);
			this.value = value;
			this.next = next;
			if (PseudoHeaderName.hasPseudoHeaderFormat(key)) {
				this.after = DefaultHttp2Headers.this.firstNonPseudo;
				this.before = DefaultHttp2Headers.this.firstNonPseudo.before();
			} else {
				this.after = DefaultHttp2Headers.this.head;
				this.before = DefaultHttp2Headers.this.head.before();
				if (DefaultHttp2Headers.this.firstNonPseudo == DefaultHttp2Headers.this.head) {
					DefaultHttp2Headers.this.firstNonPseudo = this;
				}
			}

			this.pointNeighborsToThis();
		}

		@Override
		protected void remove() {
			if (this == DefaultHttp2Headers.this.firstNonPseudo) {
				DefaultHttp2Headers.this.firstNonPseudo = DefaultHttp2Headers.this.firstNonPseudo.after();
			}

			super.remove();
		}
	}
}
