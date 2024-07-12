import java.util.ArrayList;
import javax.annotation.Nullable;

public class bpa extends ArrayList<boz> {
	public bpa() {
	}

	public bpa(le le) {
		lk lk3 = le.d("Recipes", 10);

		for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
			this.add(new boz(lk3.a(integer4)));
		}
	}

	@Nullable
	public boz a(bki bki1, bki bki2, int integer) {
		if (integer > 0 && integer < this.size()) {
			boz boz5 = (boz)this.get(integer);
			return boz5.a(bki1, bki2) ? boz5 : null;
		} else {
			for (int integer5 = 0; integer5 < this.size(); integer5++) {
				boz boz6 = (boz)this.get(integer5);
				if (boz6.a(bki1, bki2)) {
					return boz6;
				}
			}

			return null;
		}
	}

	public void a(mg mg) {
		mg.writeByte((byte)(this.size() & 0xFF));

		for (int integer3 = 0; integer3 < this.size(); integer3++) {
			boz boz4 = (boz)this.get(integer3);
			mg.a(boz4.a());
			mg.a(boz4.d());
			bki bki5 = boz4.c();
			mg.writeBoolean(!bki5.a());
			if (!bki5.a()) {
				mg.a(bki5);
			}

			mg.writeBoolean(boz4.p());
			mg.writeInt(boz4.g());
			mg.writeInt(boz4.i());
			mg.writeInt(boz4.o());
			mg.writeInt(boz4.m());
			mg.writeFloat(boz4.n());
			mg.writeInt(boz4.k());
		}
	}

	public static bpa b(mg mg) {
		bpa bpa2 = new bpa();
		int integer3 = mg.readByte() & 255;

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			bki bki5 = mg.m();
			bki bki6 = mg.m();
			bki bki7 = bki.b;
			if (mg.readBoolean()) {
				bki7 = mg.m();
			}

			boolean boolean8 = mg.readBoolean();
			int integer9 = mg.readInt();
			int integer10 = mg.readInt();
			int integer11 = mg.readInt();
			int integer12 = mg.readInt();
			float float13 = mg.readFloat();
			int integer14 = mg.readInt();
			boz boz15 = new boz(bki5, bki7, bki6, integer9, integer10, integer11, float13, integer14);
			if (boolean8) {
				boz15.q();
			}

			boz15.b(integer12);
			bpa2.add(boz15);
		}

		return bpa2;
	}

	public le a() {
		le le2 = new le();
		lk lk3 = new lk();

		for (int integer4 = 0; integer4 < this.size(); integer4++) {
			boz boz5 = (boz)this.get(integer4);
			lk3.add(boz5.t());
		}

		le2.a("Recipes", lk3);
		return le2;
	}
}
