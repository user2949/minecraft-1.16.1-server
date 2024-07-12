public class dac {
	public static final dac a = new dac("advancements");
	public static final dac b = new dac("stats");
	public static final dac c = new dac("playerdata");
	public static final dac d = new dac("players");
	public static final dac e = new dac("level.dat");
	public static final dac f = new dac("generated");
	public static final dac g = new dac("datapacks");
	public static final dac h = new dac("resources.zip");
	public static final dac i = new dac(".");
	private final String j;

	private dac(String string) {
		this.j = string;
	}

	public String a() {
		return this.j;
	}

	public String toString() {
		return "/" + this.j;
	}
}
