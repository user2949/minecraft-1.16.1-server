import java.util.EnumSet;

public abstract class aug {
	private final EnumSet<aug.a> a = EnumSet.noneOf(aug.a.class);

	public abstract boolean a();

	public boolean b() {
		return this.a();
	}

	public boolean D_() {
		return true;
	}

	public void c() {
	}

	public void d() {
	}

	public void e() {
	}

	public void a(EnumSet<aug.a> enumSet) {
		this.a.clear();
		this.a.addAll(enumSet);
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	public EnumSet<aug.a> i() {
		return this.a;
	}

	public static enum a {
		MOVE,
		LOOK,
		JUMP,
		TARGET;
	}
}
