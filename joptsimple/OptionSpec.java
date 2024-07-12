package joptsimple;

import java.util.List;

public interface OptionSpec<V> {
	List<V> values(OptionSet optionSet);

	V value(OptionSet optionSet);

	List<String> options();

	boolean isForHelp();
}
