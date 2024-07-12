package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {
	public static void main(String[] arr) throws IOException {
		OptionParser optionParser2 = new OptionParser();
		OptionSpec<Void> optionSpec3 = optionParser2.accepts("help", "Show the help menu").forHelp();
		OptionSpec<Void> optionSpec4 = optionParser2.accepts("server", "Include server generators");
		OptionSpec<Void> optionSpec5 = optionParser2.accepts("client", "Include client generators");
		OptionSpec<Void> optionSpec6 = optionParser2.accepts("dev", "Include development tools");
		OptionSpec<Void> optionSpec7 = optionParser2.accepts("reports", "Include data reports");
		OptionSpec<Void> optionSpec8 = optionParser2.accepts("validate", "Validate inputs");
		OptionSpec<Void> optionSpec9 = optionParser2.accepts("all", "Include all generators");
		OptionSpec<String> optionSpec10 = optionParser2.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
		OptionSpec<String> optionSpec11 = optionParser2.accepts("input", "Input folder").withRequiredArg();
		OptionSet optionSet12 = optionParser2.parse(arr);
		if (!optionSet12.has(optionSpec3) && optionSet12.hasOptions()) {
			Path path13 = Paths.get(optionSpec10.value(optionSet12));
			boolean boolean14 = optionSet12.has(optionSpec9);
			boolean boolean15 = boolean14 || optionSet12.has(optionSpec5);
			boolean boolean16 = boolean14 || optionSet12.has(optionSpec4);
			boolean boolean17 = boolean14 || optionSet12.has(optionSpec6);
			boolean boolean18 = boolean14 || optionSet12.has(optionSpec7);
			boolean boolean19 = boolean14 || optionSet12.has(optionSpec8);
			hk hk20 = a(
				path13,
				(Collection<Path>)optionSet12.valuesOf(optionSpec11).stream().map(string -> Paths.get(string)).collect(Collectors.toList()),
				boolean15,
				boolean16,
				boolean17,
				boolean18,
				boolean19
			);
			hk20.c();
		} else {
			optionParser2.printHelpOn(System.out);
		}
	}

	public static hk a(Path path, Collection<Path> collection, boolean boolean3, boolean boolean4, boolean boolean5, boolean boolean6, boolean boolean7) {
		hk hk8 = new hk(path, collection);
		if (boolean3 || boolean4) {
			hk8.a(new jo(hk8).a(new jp()));
		}

		if (boolean3) {
			hk8.a(new ij(hk8));
		}

		if (boolean4) {
			hk8.a(new jt(hk8));
			jr jr9 = new jr(hk8);
			hk8.a(jr9);
			hk8.a(new ju(hk8, jr9));
			hk8.a(new js(hk8));
			hk8.a(new jf(hk8));
			hk8.a(new hn(hk8));
			hk8.a(new id(hk8));
		}

		if (boolean5) {
			hk8.a(new jn(hk8));
		}

		if (boolean6) {
			hk8.a(new hu(hk8));
			hk8.a(new hw(hk8));
			hk8.a(new hv(hk8));
		}

		return hk8;
	}
}
