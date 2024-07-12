import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class xg {
	private static final Dynamic4CommandExceptionType a = new Dynamic4CommandExceptionType(
		(object1, object2, object3, object4) -> new ne("commands.spreadplayers.failed.teams", object1, object2, object3, object4)
	);
	private static final Dynamic4CommandExceptionType b = new Dynamic4CommandExceptionType(
		(object1, object2, object3, object4) -> new ne("commands.spreadplayers.failed.entities", object1, object2, object3, object4)
	);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("spreadplayers")
				.requires(cz -> cz.c(2))
				.then(
					da.a("center", en.a())
						.then(
							da.a("spreadDistance", FloatArgumentType.floatArg(0.0F))
								.then(
									da.a("maxRange", FloatArgumentType.floatArg(1.0F))
										.then(
											da.a("respectTeams", BoolArgumentType.bool())
												.then(
													da.a("targets", dh.b())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	en.a(commandContext, "center"),
																	FloatArgumentType.getFloat(commandContext, "spreadDistance"),
																	FloatArgumentType.getFloat(commandContext, "maxRange"),
																	256,
																	BoolArgumentType.getBool(commandContext, "respectTeams"),
																	dh.b(commandContext, "targets")
																)
														)
												)
										)
										.then(
											da.a("under")
												.then(
													da.a("maxHeight", IntegerArgumentType.integer(0))
														.then(
															da.a("respectTeams", BoolArgumentType.bool())
																.then(
																	da.a("targets", dh.b())
																		.executes(
																			commandContext -> a(
																					commandContext.getSource(),
																					en.a(commandContext, "center"),
																					FloatArgumentType.getFloat(commandContext, "spreadDistance"),
																					FloatArgumentType.getFloat(commandContext, "maxRange"),
																					IntegerArgumentType.getInteger(commandContext, "maxHeight"),
																					BoolArgumentType.getBool(commandContext, "respectTeams"),
																					dh.b(commandContext, "targets")
																				)
																		)
																)
														)
												)
										)
								)
						)
				)
		);
	}

	private static int a(cz cz, del del, float float3, float float4, int integer, boolean boolean6, Collection<? extends aom> collection) throws CommandSyntaxException {
		Random random8 = new Random();
		double double9 = (double)(del.i - float4);
		double double11 = (double)(del.j - float4);
		double double13 = (double)(del.i + float4);
		double double15 = (double)(del.j + float4);
		xg.a[] arr17 = a(random8, boolean6 ? a(collection) : collection.size(), double9, double11, double13, double15);
		a(del, (double)float3, cz.e(), random8, double9, double11, double13, double15, integer, arr17, boolean6);
		double double18 = a(collection, cz.e(), arr17, integer, boolean6);
		cz.a(
			new ne("commands.spreadplayers.success." + (boolean6 ? "teams" : "entities"), arr17.length, del.i, del.j, String.format(Locale.ROOT, "%.2f", double18)),
			true
		);
		return arr17.length;
	}

	private static int a(Collection<? extends aom> collection) {
		Set<dfo> set2 = Sets.<dfo>newHashSet();

		for (aom aom4 : collection) {
			if (aom4 instanceof bec) {
				set2.add(aom4.bC());
			} else {
				set2.add(null);
			}
		}

		return set2.size();
	}

	private static void a(
		del del, double double2, zd zd, Random random, double double5, double double6, double double7, double double8, int integer, xg.a[] arr, boolean boolean11
	) throws CommandSyntaxException {
		boolean boolean17 = true;
		double double19 = Float.MAX_VALUE;

		int integer18;
		for (integer18 = 0; integer18 < 10000 && boolean17; integer18++) {
			boolean17 = false;
			double19 = Float.MAX_VALUE;

			for (int integer21 = 0; integer21 < arr.length; integer21++) {
				xg.a a22 = arr[integer21];
				int integer23 = 0;
				xg.a a24 = new xg.a();

				for (int integer25 = 0; integer25 < arr.length; integer25++) {
					if (integer21 != integer25) {
						xg.a a26 = arr[integer25];
						double double27 = a22.a(a26);
						double19 = Math.min(double27, double19);
						if (double27 < double2) {
							integer23++;
							a24.a = a24.a + (a26.a - a22.a);
							a24.b = a24.b + (a26.b - a22.b);
						}
					}
				}

				if (integer23 > 0) {
					a24.a = a24.a / (double)integer23;
					a24.b = a24.b / (double)integer23;
					double double25 = (double)a24.b();
					if (double25 > 0.0) {
						a24.a();
						a22.b(a24);
					} else {
						a22.a(random, double5, double6, double7, double8);
					}

					boolean17 = true;
				}

				if (a22.a(double5, double6, double7, double8)) {
					boolean17 = true;
				}
			}

			if (!boolean17) {
				for (xg.a a24 : arr) {
					if (!a24.b(zd, integer)) {
						a24.a(random, double5, double6, double7, double8);
						boolean17 = true;
					}
				}
			}
		}

		if (double19 == Float.MAX_VALUE) {
			double19 = 0.0;
		}

		if (integer18 >= 10000) {
			if (boolean11) {
				throw a.create(arr.length, del.i, del.j, String.format(Locale.ROOT, "%.2f", double19));
			} else {
				throw b.create(arr.length, del.i, del.j, String.format(Locale.ROOT, "%.2f", double19));
			}
		}
	}

	private static double a(Collection<? extends aom> collection, zd zd, xg.a[] arr, int integer, boolean boolean5) {
		double double6 = 0.0;
		int integer8 = 0;
		Map<dfo, xg.a> map9 = Maps.<dfo, xg.a>newHashMap();

		for (aom aom11 : collection) {
			xg.a a12;
			if (boolean5) {
				dfo dfo13 = aom11 instanceof bec ? aom11.bC() : null;
				if (!map9.containsKey(dfo13)) {
					map9.put(dfo13, arr[integer8++]);
				}

				a12 = (xg.a)map9.get(dfo13);
			} else {
				a12 = arr[integer8++];
			}

			aom11.l((double)aec.c(a12.a) + 0.5, (double)a12.a(zd, integer), (double)aec.c(a12.b) + 0.5);
			double double13 = Double.MAX_VALUE;

			for (xg.a a18 : arr) {
				if (a12 != a18) {
					double double19 = a12.a(a18);
					double13 = Math.min(double19, double13);
				}
			}

			double6 += double13;
		}

		return collection.size() < 2 ? 0.0 : double6 / (double)collection.size();
	}

	private static xg.a[] a(Random random, int integer, double double3, double double4, double double5, double double6) {
		xg.a[] arr11 = new xg.a[integer];

		for (int integer12 = 0; integer12 < arr11.length; integer12++) {
			xg.a a13 = new xg.a();
			a13.a(random, double3, double4, double5, double6);
			arr11[integer12] = a13;
		}

		return arr11;
	}

	static class a {
		private double a;
		private double b;

		double a(xg.a a) {
			double double3 = this.a - a.a;
			double double5 = this.b - a.b;
			return Math.sqrt(double3 * double3 + double5 * double5);
		}

		void a() {
			double double2 = (double)this.b();
			this.a /= double2;
			this.b /= double2;
		}

		float b() {
			return aec.a(this.a * this.a + this.b * this.b);
		}

		public void b(xg.a a) {
			this.a = this.a - a.a;
			this.b = this.b - a.b;
		}

		public boolean a(double double1, double double2, double double3, double double4) {
			boolean boolean10 = false;
			if (this.a < double1) {
				this.a = double1;
				boolean10 = true;
			} else if (this.a > double3) {
				this.a = double3;
				boolean10 = true;
			}

			if (this.b < double2) {
				this.b = double2;
				boolean10 = true;
			} else if (this.b > double4) {
				this.b = double4;
				boolean10 = true;
			}

			return boolean10;
		}

		public int a(bpg bpg, int integer) {
			fu.a a4 = new fu.a(this.a, (double)(integer + 1), this.b);
			boolean boolean5 = bpg.d_(a4).g();
			a4.c(fz.DOWN);
			boolean boolean6 = bpg.d_(a4).g();

			while (a4.v() > 0) {
				a4.c(fz.DOWN);
				boolean boolean7 = bpg.d_(a4).g();
				if (!boolean7 && boolean6 && boolean5) {
					return a4.v() + 1;
				}

				boolean5 = boolean6;
				boolean6 = boolean7;
			}

			return integer + 1;
		}

		public boolean b(bpg bpg, int integer) {
			fu fu4 = new fu(this.a, (double)(this.a(bpg, integer) - 1), this.b);
			cfj cfj5 = bpg.d_(fu4);
			cxd cxd6 = cfj5.c();
			return fu4.v() < integer && !cxd6.a() && cxd6 != cxd.m;
		}

		public void a(Random random, double double2, double double3, double double4, double double5) {
			this.a = aec.a(random, double2, double4);
			this.b = aec.a(random, double3, double5);
		}
	}
}
