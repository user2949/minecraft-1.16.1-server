import java.util.Iterator;

public interface tx<T> {
	default void a(int integer1, int integer2, int integer3, bmu<?> bmu, Iterator<T> iterator, int integer6) {
		int integer8 = integer1;
		int integer9 = integer2;
		if (bmu instanceof bmz) {
			bmz bmz10 = (bmz)bmu;
			integer8 = bmz10.i();
			integer9 = bmz10.j();
		}

		int integer10 = 0;

		for (int integer11 = 0; integer11 < integer2; integer11++) {
			if (integer10 == integer3) {
				integer10++;
			}

			boolean boolean12 = (float)integer9 < (float)integer2 / 2.0F;
			int integer13 = aec.d((float)integer2 / 2.0F - (float)integer9 / 2.0F);
			if (boolean12 && integer13 > integer11) {
				integer10 += integer1;
				integer11++;
			}

			for (int integer14 = 0; integer14 < integer1; integer14++) {
				if (!iterator.hasNext()) {
					return;
				}

				boolean12 = (float)integer8 < (float)integer1 / 2.0F;
				integer13 = aec.d((float)integer1 / 2.0F - (float)integer8 / 2.0F);
				int integer15 = integer8;
				boolean boolean16 = integer14 < integer8;
				if (boolean12) {
					integer15 = integer13 + integer8;
					boolean16 = integer13 <= integer14 && integer14 < integer13 + integer8;
				}

				if (boolean16) {
					this.a(iterator, integer10, integer6, integer11, integer14);
				} else if (integer15 == integer14) {
					integer10 += integer1 - integer14;
					break;
				}

				integer10++;
			}
		}
	}

	void a(Iterator<T> iterator, int integer2, int integer3, int integer4, int integer5);
}
