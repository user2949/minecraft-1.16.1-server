import java.util.UUID;
import javax.annotation.Nullable;

public class bek extends aom {
	private int b;
	private boolean c;
	private int d = 22;
	private boolean e;
	private aoy f;
	private UUID g;

	public bek(aoq<? extends bek> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bek(bqb bqb, double double2, double double3, double double4, float float5, int integer, aoy aoy) {
		this(aoq.x, bqb);
		this.b = integer;
		this.a(aoy);
		this.p = float5 * (180.0F / (float)Math.PI);
		this.d(double2, double3, double4);
	}

	@Override
	protected void e() {
	}

	public void a(@Nullable aoy aoy) {
		this.f = aoy;
		this.g = aoy == null ? null : aoy.bR();
	}

	@Nullable
	public aoy g() {
		if (this.f == null && this.g != null && this.l instanceof zd) {
			aom aom2 = ((zd)this.l).a(this.g);
			if (aom2 instanceof aoy) {
				this.f = (aoy)aom2;
			}
		}

		return this.f;
	}

	@Override
	protected void a(le le) {
		this.b = le.h("Warmup");
		if (le.b("Owner")) {
			this.g = le.a("Owner");
		}
	}

	@Override
	protected void b(le le) {
		le.b("Warmup", this.b);
		if (this.g != null) {
			le.a("Owner", this.g);
		}
	}

	@Override
	public void j() {
		// $VF: Couldn't be decompiled
		// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
		//
		// Bytecode:
		// 000: aload 0
		// 001: invokespecial aom.j ()V
		// 004: aload 0
		// 005: getfield bek.l Lbqb;
		// 008: getfield bqb.v Z
		// 00b: ifeq 0d4
		// 00e: aload 0
		// 00f: getfield bek.e Z
		// 012: ifeq 14c
		// 015: aload 0
		// 016: dup
		// 017: getfield bek.d I
		// 01a: bipush 1
		// 01b: isub
		// 01c: putfield bek.d I
		// 01f: aload 0
		// 020: getfield bek.d I
		// 023: bipush 14
		// 025: if_icmpne 14c
		// 028: bipush 0
		// 029: istore 1
		// 02a: iload 1
		// 02b: bipush 12
		// 02d: if_icmpge 0d1
		// 030: aload 0
		// 031: invokevirtual bek.cC ()D
		// 034: aload 0
		// 035: getfield bek.J Ljava/util/Random;
		// 038: invokevirtual java/util/Random.nextDouble ()D
		// 03b: ldc2_w 2.0
		// 03e: dmul
		// 03f: dconst_1
		// 040: dsub
		// 041: aload 0
		// 042: invokevirtual bek.cx ()F
		// 045: f2d
		// 046: dmul
		// 047: ldc2_w 0.5
		// 04a: dmul
		// 04b: dadd
		// 04c: dstore 2
		// 04d: aload 0
		// 04e: invokevirtual bek.cD ()D
		// 051: ldc2_w 0.05
		// 054: dadd
		// 055: aload 0
		// 056: getfield bek.J Ljava/util/Random;
		// 059: invokevirtual java/util/Random.nextDouble ()D
		// 05c: dadd
		// 05d: dstore 4
		// 05f: aload 0
		// 060: invokevirtual bek.cG ()D
		// 063: aload 0
		// 064: getfield bek.J Ljava/util/Random;
		// 067: invokevirtual java/util/Random.nextDouble ()D
		// 06a: ldc2_w 2.0
		// 06d: dmul
		// 06e: dconst_1
		// 06f: dsub
		// 070: aload 0
		// 071: invokevirtual bek.cx ()F
		// 074: f2d
		// 075: dmul
		// 076: ldc2_w 0.5
		// 079: dmul
		// 07a: dadd
		// 07b: dstore 6
		// 07d: aload 0
		// 07e: getfield bek.J Ljava/util/Random;
		// 081: invokevirtual java/util/Random.nextDouble ()D
		// 084: ldc2_w 2.0
		// 087: dmul
		// 088: dconst_1
		// 089: dsub
		// 08a: ldc2_w 0.3
		// 08d: dmul
		// 08e: dstore 8
		// 090: ldc2_w 0.3
		// 093: aload 0
		// 094: getfield bek.J Ljava/util/Random;
		// 097: invokevirtual java/util/Random.nextDouble ()D
		// 09a: ldc2_w 0.3
		// 09d: dmul
		// 09e: dadd
		// 09f: dstore 10
		// 0a1: aload 0
		// 0a2: getfield bek.J Ljava/util/Random;
		// 0a5: invokevirtual java/util/Random.nextDouble ()D
		// 0a8: ldc2_w 2.0
		// 0ab: dmul
		// 0ac: dconst_1
		// 0ad: dsub
		// 0ae: ldc2_w 0.3
		// 0b1: dmul
		// 0b2: dstore 12
		// 0b4: aload 0
		// 0b5: getfield bek.l Lbqb;
		// 0b8: getstatic hh.g Lhi;
		// 0bb: dload 2
		// 0bc: dload 4
		// 0be: dconst_1
		// 0bf: dadd
		// 0c0: dload 6
		// 0c2: dload 8
		// 0c4: dload 10
		// 0c6: dload 12
		// 0c8: invokevirtual bqb.a (Lhf;DDDDDD)V
		// 0cb: iinc 1 1
		// 0ce: goto 02a
		// 0d1: goto 14c
		// 0d4: aload 0
		// 0d5: dup
		// 0d6: getfield bek.b I
		// 0d9: bipush 1
		// 0da: isub
		// 0db: dup_x1
		// 0dc: putfield bek.b I
		// 0df: ifge 14c
		// 0e2: aload 0
		// 0e3: getfield bek.b I
		// 0e6: bipush -8
		// 0e8: if_icmpne 125
		// 0eb: aload 0
		// 0ec: getfield bek.l Lbqb;
		// 0ef: ldc aoy
		// 0f1: aload 0
		// 0f2: invokevirtual bek.cb ()Ldeg;
		// 0f5: ldc2_w 0.2
		// 0f8: dconst_0
		// 0f9: ldc2_w 0.2
		// 0fc: invokevirtual deg.c (DDD)Ldeg;
		// 0ff: invokevirtual bqb.a (Ljava/lang/Class;Ldeg;)Ljava/util/List;
		// 102: astore 1
		// 103: aload 1
		// 104: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
		// 109: astore 2
		// 10a: aload 2
		// 10b: invokeinterface java/util/Iterator.hasNext ()Z 1
		// 110: ifeq 125
		// 113: aload 2
		// 114: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
		// 119: checkcast aoy
		// 11c: astore 3
		// 11d: aload 0
		// 11e: aload 3
		// 11f: invokespecial bek.d (Laoy;)V
		// 122: goto 10a
		// 125: aload 0
		// 126: getfield bek.c Z
		// 129: ifne 13a
		// 12c: aload 0
		// 12d: getfield bek.l Lbqb;
		// 130: aload 0
		// 131: bipush 4
		// 132: invokevirtual bqb.a (Laom;B)V
		// 135: aload 0
		// 136: bipush 1
		// 137: putfield bek.c Z
		// 13a: aload 0
		// 13b: dup
		// 13c: getfield bek.d I
		// 13f: bipush 1
		// 140: isub
		// 141: dup_x1
		// 142: putfield bek.d I
		// 145: ifge 14c
		// 148: aload 0
		// 149: invokevirtual bek.aa ()V
		// 14c: return
	}

	private void d(aoy aoy) {
		aoy aoy3 = this.g();
		if (aoy.aU() && !aoy.bI() && aoy != aoy3) {
			if (aoy3 == null) {
				aoy.a(anw.o, 6.0F);
			} else {
				if (aoy3.r(aoy)) {
					return;
				}

				aoy.a(anw.c(this, aoy3), 6.0F);
			}
		}
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
