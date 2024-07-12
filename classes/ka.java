import javax.annotation.Nullable;

public class ka extends jz {
	private final fu a;
	private final fu b;
	private final long c;

	public String getMessage() {
		String string2 = "" + this.a.u() + "," + this.a.v() + "," + this.a.w() + " (relative: " + this.b.u() + "," + this.b.v() + "," + this.b.w() + ")";
		return super.getMessage() + " at " + string2 + " (t=" + this.c + ")";
	}

	@Nullable
	public String a() {
		return super.getMessage() + " here";
	}

	@Nullable
	public fu c() {
		return this.a;
	}
}
