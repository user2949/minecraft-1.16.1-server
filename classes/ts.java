import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;

public class ts {
	private static final adm<tr<?>> t = new adm<>(16);
	public static final tr<Byte> a = new tr<Byte>() {
		public void a(mg mg, Byte byte2) {
			mg.writeByte(byte2);
		}

		public Byte a(mg mg) {
			return mg.readByte();
		}

		public Byte a(Byte byte1) {
			return byte1;
		}
	};
	public static final tr<Integer> b = new tr<Integer>() {
		public void a(mg mg, Integer integer) {
			mg.d(integer);
		}

		public Integer a(mg mg) {
			return mg.i();
		}

		public Integer a(Integer integer) {
			return integer;
		}
	};
	public static final tr<Float> c = new tr<Float>() {
		public void a(mg mg, Float float2) {
			mg.writeFloat(float2);
		}

		public Float a(mg mg) {
			return mg.readFloat();
		}

		public Float a(Float float1) {
			return float1;
		}
	};
	public static final tr<String> d = new tr<String>() {
		public void a(mg mg, String string) {
			mg.a(string);
		}

		public String a(mg mg) {
			return mg.e(32767);
		}

		public String a(String string) {
			return string;
		}
	};
	public static final tr<mr> e = new tr<mr>() {
		public void a(mg mg, mr mr) {
			mg.a(mr);
		}

		public mr a(mg mg) {
			return mg.h();
		}

		public mr a(mr mr) {
			return mr;
		}
	};
	public static final tr<Optional<mr>> f = new tr<Optional<mr>>() {
		public void a(mg mg, Optional<mr> optional) {
			if (optional.isPresent()) {
				mg.writeBoolean(true);
				mg.a((mr)optional.get());
			} else {
				mg.writeBoolean(false);
			}
		}

		public Optional<mr> a(mg mg) {
			return mg.readBoolean() ? Optional.of(mg.h()) : Optional.empty();
		}

		public Optional<mr> a(Optional<mr> optional) {
			return optional;
		}
	};
	public static final tr<bki> g = new tr<bki>() {
		public void a(mg mg, bki bki) {
			mg.a(bki);
		}

		public bki a(mg mg) {
			return mg.m();
		}

		public bki a(bki bki) {
			return bki.i();
		}
	};
	public static final tr<Optional<cfj>> h = new tr<Optional<cfj>>() {
		public void a(mg mg, Optional<cfj> optional) {
			if (optional.isPresent()) {
				mg.d(bvr.i((cfj)optional.get()));
			} else {
				mg.d(0);
			}
		}

		public Optional<cfj> a(mg mg) {
			int integer3 = mg.i();
			return integer3 == 0 ? Optional.empty() : Optional.of(bvr.a(integer3));
		}

		public Optional<cfj> a(Optional<cfj> optional) {
			return optional;
		}
	};
	public static final tr<Boolean> i = new tr<Boolean>() {
		public void a(mg mg, Boolean boolean2) {
			mg.writeBoolean(boolean2);
		}

		public Boolean a(mg mg) {
			return mg.readBoolean();
		}

		public Boolean a(Boolean boolean1) {
			return boolean1;
		}
	};
	public static final tr<hf> j = new tr<hf>() {
		public void a(mg mg, hf hf) {
			mg.d(gl.az.a(hf.b()));
			hf.a(mg);
		}

		public hf a(mg mg) {
			return this.a(mg, (hg<hf>)gl.az.a(mg.i()));
		}

		private <T extends hf> T a(mg mg, hg<T> hg) {
			return hg.d().b(hg, mg);
		}

		public hf a(hf hf) {
			return hf;
		}
	};
	public static final tr<gn> k = new tr<gn>() {
		public void a(mg mg, gn gn) {
			mg.writeFloat(gn.b());
			mg.writeFloat(gn.c());
			mg.writeFloat(gn.d());
		}

		public gn a(mg mg) {
			return new gn(mg.readFloat(), mg.readFloat(), mg.readFloat());
		}

		public gn a(gn gn) {
			return gn;
		}
	};
	public static final tr<fu> l = new tr<fu>() {
		public void a(mg mg, fu fu) {
			mg.a(fu);
		}

		public fu a(mg mg) {
			return mg.e();
		}

		public fu a(fu fu) {
			return fu;
		}
	};
	public static final tr<Optional<fu>> m = new tr<Optional<fu>>() {
		public void a(mg mg, Optional<fu> optional) {
			mg.writeBoolean(optional.isPresent());
			if (optional.isPresent()) {
				mg.a((fu)optional.get());
			}
		}

		public Optional<fu> a(mg mg) {
			return !mg.readBoolean() ? Optional.empty() : Optional.of(mg.e());
		}

		public Optional<fu> a(Optional<fu> optional) {
			return optional;
		}
	};
	public static final tr<fz> n = new tr<fz>() {
		public void a(mg mg, fz fz) {
			mg.a(fz);
		}

		public fz a(mg mg) {
			return mg.a(fz.class);
		}

		public fz a(fz fz) {
			return fz;
		}
	};
	public static final tr<Optional<UUID>> o = new tr<Optional<UUID>>() {
		public void a(mg mg, Optional<UUID> optional) {
			mg.writeBoolean(optional.isPresent());
			if (optional.isPresent()) {
				mg.a((UUID)optional.get());
			}
		}

		public Optional<UUID> a(mg mg) {
			return !mg.readBoolean() ? Optional.empty() : Optional.of(mg.k());
		}

		public Optional<UUID> a(Optional<UUID> optional) {
			return optional;
		}
	};
	public static final tr<le> p = new tr<le>() {
		public void a(mg mg, le le) {
			mg.a(le);
		}

		public le a(mg mg) {
			return mg.l();
		}

		public le a(le le) {
			return le.g();
		}
	};
	public static final tr<bdq> q = new tr<bdq>() {
		public void a(mg mg, bdq bdq) {
			mg.d(gl.aR.a(bdq.a()));
			mg.d(gl.aS.a(bdq.b()));
			mg.d(bdq.c());
		}

		public bdq a(mg mg) {
			return new bdq(gl.aR.a(mg.i()), gl.aS.a(mg.i()), mg.i());
		}

		public bdq a(bdq bdq) {
			return bdq;
		}
	};
	public static final tr<OptionalInt> r = new tr<OptionalInt>() {
		public void a(mg mg, OptionalInt optionalInt) {
			mg.d(optionalInt.orElse(-1) + 1);
		}

		public OptionalInt a(mg mg) {
			int integer3 = mg.i();
			return integer3 == 0 ? OptionalInt.empty() : OptionalInt.of(integer3 - 1);
		}

		public OptionalInt a(OptionalInt optionalInt) {
			return optionalInt;
		}
	};
	public static final tr<apj> s = new tr<apj>() {
		public void a(mg mg, apj apj) {
			mg.a(apj);
		}

		public apj a(mg mg) {
			return mg.a(apj.class);
		}

		public apj a(apj apj) {
			return apj;
		}
	};

	public static void a(tr<?> tr) {
		t.c(tr);
	}

	@Nullable
	public static tr<?> a(int integer) {
		return t.a(integer);
	}

	public static int b(tr<?> tr) {
		return t.a(tr);
	}

	static {
		a(a);
		a(b);
		a(c);
		a(d);
		a(e);
		a(f);
		a(g);
		a(i);
		a(k);
		a(l);
		a(m);
		a(n);
		a(o);
		a(h);
		a(p);
		a(j);
		a(q);
		a(r);
		a(s);
	}
}
