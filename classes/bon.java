import java.util.Random;
import java.util.Map.Entry;

public class bon extends bnw {
	public bon(bnw.a a, aor... arr) {
		super(a, bnx.ARMOR_CHEST, arr);
	}

	@Override
	public int a(int integer) {
		return 10 + 20 * (integer - 1);
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
		return bki.b() instanceof bid ? true : super.a(bki);
	}

	@Override
	public void b(aoy aoy, aom aom, int integer) {
		Random random5 = aoy.cX();
		Entry<aor, bki> entry6 = bny.b(boa.h, aoy);
		if (a(integer, random5)) {
			if (aom != null) {
				aom.a(anw.a((aom)aoy), (float)b(integer, random5));
			}

			if (entry6 != null) {
				((bki)entry6.getValue()).a(3, aoy, aoyx -> aoyx.c((aor)entry6.getKey()));
			}
		} else if (entry6 != null) {
			((bki)entry6.getValue()).a(1, aoy, aoyx -> aoyx.c((aor)entry6.getKey()));
		}
	}

	public static boolean a(int integer, Random random) {
		return integer <= 0 ? false : random.nextFloat() < 0.15F * (float)integer;
	}

	public static int b(int integer, Random random) {
		return integer > 10 ? integer - 10 : 1 + random.nextInt(4);
	}
}
