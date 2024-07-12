import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;

public class cgs {
	private static final Set<cgs> i = new ObjectArraySet<>();
	public static final cgs a = a(new cgs("oak"));
	public static final cgs b = a(new cgs("spruce"));
	public static final cgs c = a(new cgs("birch"));
	public static final cgs d = a(new cgs("acacia"));
	public static final cgs e = a(new cgs("jungle"));
	public static final cgs f = a(new cgs("dark_oak"));
	public static final cgs g = a(new cgs("crimson"));
	public static final cgs h = a(new cgs("warped"));
	private final String j;

	protected cgs(String string) {
		this.j = string;
	}

	private static cgs a(cgs cgs) {
		i.add(cgs);
		return cgs;
	}
}
