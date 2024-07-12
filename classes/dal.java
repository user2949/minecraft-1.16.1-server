import java.util.Set;
import javax.annotation.Nullable;

public interface dal {
	bpn C();

	void a(bpn bpn);

	boolean E();

	Set<String> F();

	void a(String string, boolean boolean2);

	default void a(k k) {
		k.a("Known server brands", (l<String>)(() -> String.join(", ", this.F())));
		k.a("Level was modded", (l<String>)(() -> Boolean.toString(this.E())));
		k.a("Level storage version", (l<String>)(() -> {
			int integer2 = this.y();
			return String.format("0x%05X - %s", integer2, this.i(integer2));
		}));
	}

	default String i(int integer) {
		switch (integer) {
			case 19132:
				return "McRegion";
			case 19133:
				return "Anvil";
			default:
				return "Unknown?";
		}
	}

	@Nullable
	le D();

	void b(@Nullable le le);

	dak G();

	le a(gm gm, @Nullable le le);

	boolean m();

	int y();

	String f();

	bpy l();

	void a(bpy bpy);

	boolean n();

	and r();

	void a(and and);

	boolean s();

	void d(boolean boolean1);

	bpx p();

	le x();

	le B();

	void a(le le);

	cix z();
}
