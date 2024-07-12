import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

public class ow implements ni<nl> {
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;
	private List<byte[]> g;
	private List<byte[]> h;
	private boolean i;

	public ow() {
	}

	public ow(bph bph, cwr cwr, boolean boolean3) {
		this.a = bph.b;
		this.b = bph.c;
		this.i = boolean3;
		this.g = Lists.<byte[]>newArrayList();
		this.h = Lists.<byte[]>newArrayList();

		for (int integer5 = 0; integer5 < 18; integer5++) {
			chd chd6 = cwr.a(bqi.SKY).a(go.a(bph, -1 + integer5));
			chd chd7 = cwr.a(bqi.BLOCK).a(go.a(bph, -1 + integer5));
			if (chd6 != null) {
				if (chd6.c()) {
					this.e |= 1 << integer5;
				} else {
					this.c |= 1 << integer5;
					this.g.add(chd6.a().clone());
				}
			}

			if (chd7 != null) {
				if (chd7.c()) {
					this.f |= 1 << integer5;
				} else {
					this.d |= 1 << integer5;
					this.h.add(chd7.a().clone());
				}
			}
		}
	}

	public ow(bph bph, cwr cwr, int integer3, int integer4, boolean boolean5) {
		this.a = bph.b;
		this.b = bph.c;
		this.i = boolean5;
		this.c = integer3;
		this.d = integer4;
		this.g = Lists.<byte[]>newArrayList();
		this.h = Lists.<byte[]>newArrayList();

		for (int integer7 = 0; integer7 < 18; integer7++) {
			if ((this.c & 1 << integer7) != 0) {
				chd chd8 = cwr.a(bqi.SKY).a(go.a(bph, -1 + integer7));
				if (chd8 != null && !chd8.c()) {
					this.g.add(chd8.a().clone());
				} else {
					this.c &= ~(1 << integer7);
					if (chd8 != null) {
						this.e |= 1 << integer7;
					}
				}
			}

			if ((this.d & 1 << integer7) != 0) {
				chd chd8 = cwr.a(bqi.BLOCK).a(go.a(bph, -1 + integer7));
				if (chd8 != null && !chd8.c()) {
					this.h.add(chd8.a().clone());
				} else {
					this.d &= ~(1 << integer7);
					if (chd8 != null) {
						this.f |= 1 << integer7;
					}
				}
			}
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.i();
		this.i = mg.readBoolean();
		this.c = mg.i();
		this.d = mg.i();
		this.e = mg.i();
		this.f = mg.i();
		this.g = Lists.<byte[]>newArrayList();

		for (int integer3 = 0; integer3 < 18; integer3++) {
			if ((this.c & 1 << integer3) != 0) {
				this.g.add(mg.b(2048));
			}
		}

		this.h = Lists.<byte[]>newArrayList();

		for (int integer3x = 0; integer3x < 18; integer3x++) {
			if ((this.d & 1 << integer3x) != 0) {
				this.h.add(mg.b(2048));
			}
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.d(this.b);
		mg.writeBoolean(this.i);
		mg.d(this.c);
		mg.d(this.d);
		mg.d(this.e);
		mg.d(this.f);

		for (byte[] arr4 : this.g) {
			mg.a(arr4);
		}

		for (byte[] arr4 : this.h) {
			mg.a(arr4);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
