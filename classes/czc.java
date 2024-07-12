import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;

public class czc extends czl {
	@Override
	public void a(bql bql, aoz aoz) {
		super.a(bql, aoz);
		this.j = aoz.a(czb.WATER);
	}

	@Override
	public void a() {
		this.b.a(czb.WATER, this.j);
		super.a();
	}

	@Override
	public czd b() {
		int integer2;
		if (this.e() && this.b.aA()) {
			integer2 = aec.c(this.b.cD());
			fu.a a3 = new fu.a(this.b.cC(), (double)integer2, this.b.cG());

			for (bvr bvr4 = this.a.d_(a3).b(); bvr4 == bvs.A; bvr4 = this.a.d_(a3).b()) {
				a3.c(this.b.cC(), (double)(++integer2), this.b.cG());
			}
		} else {
			integer2 = aec.c(this.b.cD() + 0.5);
		}

		fu fu3 = this.b.cA();
		czb czb4 = this.a(this.b, fu3.u(), integer2, fu3.w());
		if (this.b.a(czb4) < 0.0F) {
			Set<fu> set5 = Sets.<fu>newHashSet();
			set5.add(new fu(this.b.cb().a, (double)integer2, this.b.cb().c));
			set5.add(new fu(this.b.cb().a, (double)integer2, this.b.cb().f));
			set5.add(new fu(this.b.cb().d, (double)integer2, this.b.cb().c));
			set5.add(new fu(this.b.cb().d, (double)integer2, this.b.cb().f));

			for (fu fu7 : set5) {
				czb czb8 = this.a(this.b, fu7);
				if (this.b.a(czb8) >= 0.0F) {
					return super.a(fu7.u(), fu7.v(), fu7.w());
				}
			}
		}

		return super.a(fu3.u(), integer2, fu3.w());
	}

	@Override
	public czj a(double double1, double double2, double double3) {
		return new czj(super.a(aec.c(double1), aec.c(double2), aec.c(double3)));
	}

	@Override
	public int a(czd[] arr, czd czd) {
		int integer4 = 0;
		czd czd5 = this.a(czd.a, czd.b, czd.c + 1);
		if (this.b(czd5)) {
			arr[integer4++] = czd5;
		}

		czd czd6 = this.a(czd.a - 1, czd.b, czd.c);
		if (this.b(czd6)) {
			arr[integer4++] = czd6;
		}

		czd czd7 = this.a(czd.a + 1, czd.b, czd.c);
		if (this.b(czd7)) {
			arr[integer4++] = czd7;
		}

		czd czd8 = this.a(czd.a, czd.b, czd.c - 1);
		if (this.b(czd8)) {
			arr[integer4++] = czd8;
		}

		czd czd9 = this.a(czd.a, czd.b + 1, czd.c);
		if (this.b(czd9)) {
			arr[integer4++] = czd9;
		}

		czd czd10 = this.a(czd.a, czd.b - 1, czd.c);
		if (this.b(czd10)) {
			arr[integer4++] = czd10;
		}

		czd czd11 = this.a(czd.a, czd.b + 1, czd.c + 1);
		if (this.b(czd11) && this.a(czd5) && this.a(czd9)) {
			arr[integer4++] = czd11;
		}

		czd czd12 = this.a(czd.a - 1, czd.b + 1, czd.c);
		if (this.b(czd12) && this.a(czd6) && this.a(czd9)) {
			arr[integer4++] = czd12;
		}

		czd czd13 = this.a(czd.a + 1, czd.b + 1, czd.c);
		if (this.b(czd13) && this.a(czd7) && this.a(czd9)) {
			arr[integer4++] = czd13;
		}

		czd czd14 = this.a(czd.a, czd.b + 1, czd.c - 1);
		if (this.b(czd14) && this.a(czd8) && this.a(czd9)) {
			arr[integer4++] = czd14;
		}

		czd czd15 = this.a(czd.a, czd.b - 1, czd.c + 1);
		if (this.b(czd15) && this.a(czd5) && this.a(czd10)) {
			arr[integer4++] = czd15;
		}

		czd czd16 = this.a(czd.a - 1, czd.b - 1, czd.c);
		if (this.b(czd16) && this.a(czd6) && this.a(czd10)) {
			arr[integer4++] = czd16;
		}

		czd czd17 = this.a(czd.a + 1, czd.b - 1, czd.c);
		if (this.b(czd17) && this.a(czd7) && this.a(czd10)) {
			arr[integer4++] = czd17;
		}

		czd czd18 = this.a(czd.a, czd.b - 1, czd.c - 1);
		if (this.b(czd18) && this.a(czd8) && this.a(czd10)) {
			arr[integer4++] = czd18;
		}

		czd czd19 = this.a(czd.a + 1, czd.b, czd.c - 1);
		if (this.b(czd19) && this.a(czd8) && this.a(czd7)) {
			arr[integer4++] = czd19;
		}

		czd czd20 = this.a(czd.a + 1, czd.b, czd.c + 1);
		if (this.b(czd20) && this.a(czd5) && this.a(czd7)) {
			arr[integer4++] = czd20;
		}

		czd czd21 = this.a(czd.a - 1, czd.b, czd.c - 1);
		if (this.b(czd21) && this.a(czd8) && this.a(czd6)) {
			arr[integer4++] = czd21;
		}

		czd czd22 = this.a(czd.a - 1, czd.b, czd.c + 1);
		if (this.b(czd22) && this.a(czd5) && this.a(czd6)) {
			arr[integer4++] = czd22;
		}

		czd czd23 = this.a(czd.a + 1, czd.b + 1, czd.c - 1);
		if (this.b(czd23) && this.a(czd19) && this.a(czd8) && this.a(czd7) && this.a(czd9) && this.a(czd14) && this.a(czd13)) {
			arr[integer4++] = czd23;
		}

		czd czd24 = this.a(czd.a + 1, czd.b + 1, czd.c + 1);
		if (this.b(czd24) && this.a(czd20) && this.a(czd5) && this.a(czd7) && this.a(czd9) && this.a(czd11) && this.a(czd13)) {
			arr[integer4++] = czd24;
		}

		czd czd25 = this.a(czd.a - 1, czd.b + 1, czd.c - 1);
		if (this.b(czd25) && this.a(czd21) && this.a(czd8) && this.a(czd6) & this.a(czd9) && this.a(czd14) && this.a(czd12)) {
			arr[integer4++] = czd25;
		}

		czd czd26 = this.a(czd.a - 1, czd.b + 1, czd.c + 1);
		if (this.b(czd26) && this.a(czd22) && this.a(czd5) && this.a(czd6) & this.a(czd9) && this.a(czd11) && this.a(czd12)) {
			arr[integer4++] = czd26;
		}

		czd czd27 = this.a(czd.a + 1, czd.b - 1, czd.c - 1);
		if (this.b(czd27) && this.a(czd19) && this.a(czd8) && this.a(czd7) && this.a(czd10) && this.a(czd18) && this.a(czd17)) {
			arr[integer4++] = czd27;
		}

		czd czd28 = this.a(czd.a + 1, czd.b - 1, czd.c + 1);
		if (this.b(czd28) && this.a(czd20) && this.a(czd5) && this.a(czd7) && this.a(czd10) && this.a(czd15) && this.a(czd17)) {
			arr[integer4++] = czd28;
		}

		czd czd29 = this.a(czd.a - 1, czd.b - 1, czd.c - 1);
		if (this.b(czd29) && this.a(czd21) && this.a(czd8) && this.a(czd6) && this.a(czd10) && this.a(czd18) && this.a(czd16)) {
			arr[integer4++] = czd29;
		}

		czd czd30 = this.a(czd.a - 1, czd.b - 1, czd.c + 1);
		if (this.b(czd30) && this.a(czd22) && this.a(czd5) && this.a(czd6) && this.a(czd10) && this.a(czd15) && this.a(czd16)) {
			arr[integer4++] = czd30;
		}

		return integer4;
	}

	private boolean a(@Nullable czd czd) {
		return czd != null && czd.k >= 0.0F;
	}

	private boolean b(@Nullable czd czd) {
		return czd != null && !czd.i;
	}

	@Nullable
	@Override
	protected czd a(int integer1, int integer2, int integer3) {
		czd czd5 = null;
		czb czb6 = this.a(this.b, integer1, integer2, integer3);
		float float7 = this.b.a(czb6);
		if (float7 >= 0.0F) {
			czd5 = super.a(integer1, integer2, integer3);
			czd5.l = czb6;
			czd5.k = Math.max(czd5.k, float7);
			if (czb6 == czb.WALKABLE) {
				czd5.k++;
			}
		}

		return czb6 != czb.OPEN && czb6 != czb.WALKABLE ? czd5 : czd5;
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4, aoz aoz, int integer6, int integer7, int integer8, boolean boolean9, boolean boolean10) {
		EnumSet<czb> enumSet12 = EnumSet.noneOf(czb.class);
		czb czb13 = czb.BLOCKED;
		fu fu14 = aoz.cA();
		czb13 = this.a(bpg, integer2, integer3, integer4, integer6, integer7, integer8, boolean9, boolean10, enumSet12, czb13, fu14);
		if (enumSet12.contains(czb.FENCE)) {
			return czb.FENCE;
		} else {
			czb czb15 = czb.BLOCKED;

			for (czb czb17 : enumSet12) {
				if (aoz.a(czb17) < 0.0F) {
					return czb17;
				}

				if (aoz.a(czb17) >= aoz.a(czb15)) {
					czb15 = czb17;
				}
			}

			return czb13 == czb.OPEN && aoz.a(czb15) == 0.0F ? czb.OPEN : czb15;
		}
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4) {
		fu.a a6 = new fu.a();
		czb czb7 = b(bpg, a6.d(integer2, integer3, integer4));
		if (czb7 == czb.OPEN && integer3 >= 1) {
			cfj cfj8 = bpg.d_(a6.d(integer2, integer3 - 1, integer4));
			czb czb9 = b(bpg, a6.d(integer2, integer3 - 1, integer4));
			if (czb9 == czb.DAMAGE_FIRE || cfj8.a(bvs.iJ) || czb9 == czb.LAVA || cfj8.a(acx.ax)) {
				czb7 = czb.DAMAGE_FIRE;
			} else if (czb9 == czb.DAMAGE_CACTUS) {
				czb7 = czb.DAMAGE_CACTUS;
			} else if (czb9 == czb.DAMAGE_OTHER) {
				czb7 = czb.DAMAGE_OTHER;
			} else if (czb9 == czb.COCOA) {
				czb7 = czb.COCOA;
			} else if (czb9 == czb.FENCE) {
				czb7 = czb.FENCE;
			} else {
				czb7 = czb9 != czb.WALKABLE && czb9 != czb.OPEN && czb9 != czb.WATER ? czb.WALKABLE : czb.OPEN;
			}
		}

		if (czb7 == czb.WALKABLE || czb7 == czb.OPEN) {
			czb7 = a(bpg, a6.d(integer2, integer3, integer4), czb7);
		}

		return czb7;
	}

	private czb a(aoz aoz, fu fu) {
		return this.a(aoz, fu.u(), fu.v(), fu.w());
	}

	private czb a(aoz aoz, int integer2, int integer3, int integer4) {
		return this.a(this.a, integer2, integer3, integer4, aoz, this.d, this.e, this.f, this.d(), this.c());
	}
}
