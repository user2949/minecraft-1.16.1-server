import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class cgd extends cgg<fz> {
	protected cgd(String string, Collection<fz> collection) {
		super(string, fz.class, collection);
	}

	public static cgd a(String string, Predicate<fz> predicate) {
		return a(string, (Collection<fz>)Arrays.stream(fz.values()).filter(predicate).collect(Collectors.toList()));
	}

	public static cgd a(String string, fz... arr) {
		return a(string, Lists.<fz>newArrayList(arr));
	}

	public static cgd a(String string, Collection<fz> collection) {
		return new cgd(string, collection);
	}
}
