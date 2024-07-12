import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;

public class ox implements ni<nl> {
	private int a;
	private long b;
	private boolean c;
	private bpy d;
	private bpy e;
	private Set<ug<bqb>> f;
	private gm.a g;
	private ug<cif> h;
	private ug<bqb> i;
	private int j;
	private int k;
	private boolean l;
	private boolean m;
	private boolean n;
	private boolean o;

	public ox() {
	}

	public ox(
		int integer1,
		bpy bpy2,
		bpy bpy3,
		long long4,
		boolean boolean5,
		Set<ug<bqb>> set,
		gm.a a,
		ug<cif> ug8,
		ug<bqb> ug9,
		int integer10,
		int integer11,
		boolean boolean12,
		boolean boolean13,
		boolean boolean14,
		boolean boolean15
	) {
		this.a = integer1;
		this.f = set;
		this.g = a;
		this.h = ug8;
		this.i = ug9;
		this.b = long4;
		this.d = bpy2;
		this.e = bpy3;
		this.j = integer10;
		this.c = boolean5;
		this.k = integer11;
		this.l = boolean12;
		this.m = boolean13;
		this.n = boolean14;
		this.o = boolean15;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		int integer3 = mg.readUnsignedByte();
		this.c = (integer3 & 8) == 8;
		integer3 &= -9;
		this.d = bpy.a(integer3);
		this.e = bpy.a(mg.readUnsignedByte());
		int integer4 = mg.i();
		this.f = Sets.<ug<bqb>>newHashSet();

		for (int integer5 = 0; integer5 < integer4; integer5++) {
			this.f.add(ug.a(gl.ae, mg.o()));
		}

		this.g = mg.a(gm.a.a);
		this.h = ug.a(gl.ad, mg.o());
		this.i = ug.a(gl.ae, mg.o());
		this.b = mg.readLong();
		this.j = mg.readUnsignedByte();
		this.k = mg.i();
		this.l = mg.readBoolean();
		this.m = mg.readBoolean();
		this.n = mg.readBoolean();
		this.o = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		int integer3 = this.d.a();
		if (this.c) {
			integer3 |= 8;
		}

		mg.writeByte(integer3);
		mg.writeByte(this.e.a());
		mg.d(this.f.size());

		for (ug<bqb> ug5 : this.f) {
			mg.a(ug5.a());
		}

		mg.a(gm.a.a, this.g);
		mg.a(this.h.a());
		mg.a(this.i.a());
		mg.writeLong(this.b);
		mg.writeByte(this.j);
		mg.d(this.k);
		mg.writeBoolean(this.l);
		mg.writeBoolean(this.m);
		mg.writeBoolean(this.n);
		mg.writeBoolean(this.o);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
