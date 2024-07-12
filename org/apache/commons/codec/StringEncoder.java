package org.apache.commons.codec;

public interface StringEncoder extends Encoder {
	String encode(String string) throws EncoderException;
}
