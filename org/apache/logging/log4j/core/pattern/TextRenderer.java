package org.apache.logging.log4j.core.pattern;

public interface TextRenderer {
	void render(String string1, StringBuilder stringBuilder, String string3);

	void render(StringBuilder stringBuilder1, StringBuilder stringBuilder2);
}
