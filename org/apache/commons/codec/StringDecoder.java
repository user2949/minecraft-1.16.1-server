package org.apache.commons.codec;

public interface StringDecoder extends Decoder {
	String decode(String string) throws DecoderException;
}
