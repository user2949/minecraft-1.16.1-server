public class awr {
	private final arn a;
	private final float b;
	private final int c;

	public awr(fu fu, float float2, int integer) {
		this(new aqj(fu), float2, integer);
	}

	public awr(dem dem, float float2, int integer) {
		this(new aqj(new fu(dem)), float2, integer);
	}

	public awr(arn arn, float float2, int integer) {
		this.a = arn;
		this.b = float2;
		this.c = integer;
	}

	public arn a() {
		return this.a;
	}

	public float b() {
		return this.b;
	}

	public int c() {
		return this.c;
	}
}
