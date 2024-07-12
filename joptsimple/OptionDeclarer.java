package joptsimple;

import java.util.List;

public interface OptionDeclarer {
	OptionSpecBuilder accepts(String string);

	OptionSpecBuilder accepts(String string1, String string2);

	OptionSpecBuilder acceptsAll(List<String> list);

	OptionSpecBuilder acceptsAll(List<String> list, String string);

	NonOptionArgumentSpec<String> nonOptions();

	NonOptionArgumentSpec<String> nonOptions(String string);

	void posixlyCorrect(boolean boolean1);

	void allowsUnrecognizedOptions();

	void recognizeAlternativeLongOptions(boolean boolean1);
}
