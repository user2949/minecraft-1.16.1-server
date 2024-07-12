import java.util.Random;

public class bnu extends bnw {
	protected bnu(bnw.a a, aor... arr) {
		super(a, bnx.BREAKABLE, arr);
	}

	@Override
	public int a(int integer) {
		return 5 + (integer - 1) * 8;
	}

	@Override
	public int b(int integer) {
		return super.a(integer) + 50;
	}

	@Override
	public int a() {
		return 3;
	}

	@Override
	public boolean a(bki bki) {
		return bki.e() ? true : super.a(bki);
	}

	public static boolean a(bki bki, int integer, Random random) {
		return bki.b() instanceof bid && random.nextFloat() < 0.6F ? false : random.nextInt(integer + 1) > 0;
	}
}
