public class adb {
	private static volatile adb a = new adb(acx.b(), ada.b(), acz.b(), acy.b());
	private final adg<bvr> b;
	private final adg<bke> c;
	private final adg<cwz> d;
	private final adg<aoq<?>> e;

	private adb(adg<bvr> adg1, adg<bke> adg2, adg<cwz> adg3, adg<aoq<?>> adg4) {
		this.b = adg1;
		this.c = adg2;
		this.d = adg3;
		this.e = adg4;
	}

	public adg<bvr> a() {
		return this.b;
	}

	public adg<bke> b() {
		return this.c;
	}

	public adg<cwz> c() {
		return this.d;
	}

	public adg<aoq<?>> d() {
		return this.e;
	}

	public static adb e() {
		return a;
	}

	public static void a(adg<bvr> adg1, adg<bke> adg2, adg<cwz> adg3, adg<aoq<?>> adg4) {
		a = new adb(adg1, adg2, adg3, adg4);
	}
}
