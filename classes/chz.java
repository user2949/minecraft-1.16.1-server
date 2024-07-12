import java.util.BitSet;

public class chz {
	private final BitSet a = new BitSet();

	public void a(int integer1, int integer2) {
		this.a.set(integer1, integer1 + integer2);
	}

	public void b(int integer1, int integer2) {
		this.a.clear(integer1, integer1 + integer2);
	}

	public int a(int integer) {
		int integer3 = 0;

		while (true) {
			int integer4 = this.a.nextClearBit(integer3);
			int integer5 = this.a.nextSetBit(integer4);
			if (integer5 == -1 || integer5 - integer4 >= integer) {
				this.a(integer4, integer);
				return integer4;
			}

			integer3 = integer5;
		}
	}
}
