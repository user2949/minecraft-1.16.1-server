import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

public class oo implements ni<nl> {
	private double a;
	private double b;
	private double c;
	private float d;
	private List<fu> e;
	private float f;
	private float g;
	private float h;

	public oo() {
	}

	public oo(double double1, double double2, double double3, float float4, List<fu> list, dem dem) {
		this.a = double1;
		this.b = double2;
		this.c = double3;
		this.d = float4;
		this.e = Lists.<fu>newArrayList(list);
		if (dem != null) {
			this.f = (float)dem.b;
			this.g = (float)dem.c;
			this.h = (float)dem.d;
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = (double)mg.readFloat();
		this.b = (double)mg.readFloat();
		this.c = (double)mg.readFloat();
		this.d = mg.readFloat();
		int integer3 = mg.readInt();
		this.e = Lists.<fu>newArrayListWithCapacity(integer3);
		int integer4 = aec.c(this.a);
		int integer5 = aec.c(this.b);
		int integer6 = aec.c(this.c);

		for (int integer7 = 0; integer7 < integer3; integer7++) {
			int integer8 = mg.readByte() + integer4;
			int integer9 = mg.readByte() + integer5;
			int integer10 = mg.readByte() + integer6;
			this.e.add(new fu(integer8, integer9, integer10));
		}

		this.f = mg.readFloat();
		this.g = mg.readFloat();
		this.h = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeFloat((float)this.a);
		mg.writeFloat((float)this.b);
		mg.writeFloat((float)this.c);
		mg.writeFloat(this.d);
		mg.writeInt(this.e.size());
		int integer3 = aec.c(this.a);
		int integer4 = aec.c(this.b);
		int integer5 = aec.c(this.c);

		for (fu fu7 : this.e) {
			int integer8 = fu7.u() - integer3;
			int integer9 = fu7.v() - integer4;
			int integer10 = fu7.w() - integer5;
			mg.writeByte(integer8);
			mg.writeByte(integer9);
			mg.writeByte(integer10);
		}

		mg.writeFloat(this.f);
		mg.writeFloat(this.g);
		mg.writeFloat(this.h);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
