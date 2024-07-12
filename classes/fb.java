import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class fb {
	private static final Map<String, fb.b> i = Maps.<String, fb.b>newHashMap();
	public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.unknown", object));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.inapplicable", object));
	public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ne("argument.entity.options.distance.negative"));
	public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ne("argument.entity.options.level.negative"));
	public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ne("argument.entity.options.limit.toosmall"));
	public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.sort.irreversible", object));
	public static final DynamicCommandExceptionType g = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.mode.invalid", object));
	public static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(object -> new ne("argument.entity.options.type.invalid", object));

	private static void a(String string, fb.a a, Predicate<fa> predicate, mr mr) {
		i.put(string, new fb.b(a, predicate, mr));
	}

	public static void a() {
		if (i.isEmpty()) {
			a("name", fa -> {
				int integer2 = fa.g().getCursor();
				boolean boolean3 = fa.e();
				String string4 = fa.g().readString();
				if (fa.w() && !boolean3) {
					fa.g().setCursor(integer2);
					throw b.createWithContext(fa.g(), "name");
				} else {
					if (boolean3) {
						fa.c(true);
					} else {
						fa.b(true);
					}

					fa.a((Predicate<aom>)(aom -> aom.P().getString().equals(string4) != boolean3));
				}
			}, fa -> !fa.v(), new ne("argument.entity.options.name.description"));
			a("distance", fa -> {
				int integer2 = fa.g().getCursor();
				bx.c c3 = bx.c.a(fa.g());
				if ((c3.a() == null || !(c3.a() < 0.0F)) && (c3.b() == null || !(c3.b() < 0.0F))) {
					fa.a(c3);
					fa.h();
				} else {
					fa.g().setCursor(integer2);
					throw c.createWithContext(fa.g());
				}
			}, fa -> fa.i().c(), new ne("argument.entity.options.distance.description"));
			a("level", fa -> {
				int integer2 = fa.g().getCursor();
				bx.d d3 = bx.d.a(fa.g());
				if ((d3.a() == null || d3.a() >= 0) && (d3.b() == null || d3.b() >= 0)) {
					fa.a(d3);
					fa.a(false);
				} else {
					fa.g().setCursor(integer2);
					throw d.createWithContext(fa.g());
				}
			}, fa -> fa.j().c(), new ne("argument.entity.options.level.description"));
			a("x", fa -> {
				fa.h();
				fa.a(fa.g().readDouble());
			}, fa -> fa.m() == null, new ne("argument.entity.options.x.description"));
			a("y", fa -> {
				fa.h();
				fa.b(fa.g().readDouble());
			}, fa -> fa.n() == null, new ne("argument.entity.options.y.description"));
			a("z", fa -> {
				fa.h();
				fa.c(fa.g().readDouble());
			}, fa -> fa.o() == null, new ne("argument.entity.options.z.description"));
			a("dx", fa -> {
				fa.h();
				fa.d(fa.g().readDouble());
			}, fa -> fa.p() == null, new ne("argument.entity.options.dx.description"));
			a("dy", fa -> {
				fa.h();
				fa.e(fa.g().readDouble());
			}, fa -> fa.q() == null, new ne("argument.entity.options.dy.description"));
			a("dz", fa -> {
				fa.h();
				fa.f(fa.g().readDouble());
			}, fa -> fa.r() == null, new ne("argument.entity.options.dz.description"));
			a("x_rotation", fa -> fa.a(cs.a(fa.g(), true, aec::g)), fa -> fa.k() == cs.a, new ne("argument.entity.options.x_rotation.description"));
			a("y_rotation", fa -> fa.b(cs.a(fa.g(), true, aec::g)), fa -> fa.l() == cs.a, new ne("argument.entity.options.y_rotation.description"));
			a("limit", fa -> {
				int integer2 = fa.g().getCursor();
				int integer3 = fa.g().readInt();
				if (integer3 < 1) {
					fa.g().setCursor(integer2);
					throw e.createWithContext(fa.g());
				} else {
					fa.a(integer3);
					fa.d(true);
				}
			}, fa -> !fa.u() && !fa.x(), new ne("argument.entity.options.limit.description"));
			a(
				"sort",
				fa -> {
					int integer2 = fa.g().getCursor();
					String string3 = fa.g().readUnquotedString();
					fa.a(
						(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>>)((suggestionsBuilder, consumer) -> db.b(
								Arrays.asList("nearest", "furthest", "random", "arbitrary"), suggestionsBuilder
							))
					);
					BiConsumer<dem, List<? extends aom>> biConsumer4;
					switch (string3) {
						case "nearest":
							biConsumer4 = fa.h;
							break;
						case "furthest":
							biConsumer4 = fa.i;
							break;
						case "random":
							biConsumer4 = fa.j;
							break;
						case "arbitrary":
							biConsumer4 = fa.g;
							break;
						default:
							fa.g().setCursor(integer2);
							throw f.createWithContext(fa.g(), string3);
					}
	
					fa.a(biConsumer4);
					fa.e(true);
				},
				fa -> !fa.u() && !fa.y(),
				new ne("argument.entity.options.sort.description")
			);
			a("gamemode", fa -> {
				fa.a((BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>>)((suggestionsBuilder, consumer) -> {
					String string4x = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
					boolean boolean5 = !fa.A();
					boolean boolean6 = true;
					if (!string4x.isEmpty()) {
						if (string4x.charAt(0) == '!') {
							boolean5 = false;
							string4x = string4x.substring(1);
						} else {
							boolean6 = false;
						}
					}

					for (bpy bpy10 : bpy.values()) {
						if (bpy10 != bpy.NOT_SET && bpy10.b().toLowerCase(Locale.ROOT).startsWith(string4x)) {
							if (boolean6) {
								suggestionsBuilder.suggest('!' + bpy10.b());
							}

							if (boolean5) {
								suggestionsBuilder.suggest(bpy10.b());
							}
						}
					}

					return suggestionsBuilder.buildFuture();
				}));
				int integer2 = fa.g().getCursor();
				boolean boolean3 = fa.e();
				if (fa.A() && !boolean3) {
					fa.g().setCursor(integer2);
					throw b.createWithContext(fa.g(), "gamemode");
				} else {
					String string4 = fa.g().readUnquotedString();
					bpy bpy5 = bpy.a(string4, bpy.NOT_SET);
					if (bpy5 == bpy.NOT_SET) {
						fa.g().setCursor(integer2);
						throw g.createWithContext(fa.g(), string4);
					} else {
						fa.a(false);
						fa.a((Predicate<aom>)(aom -> {
							if (!(aom instanceof ze)) {
								return false;
							} else {
								bpy bpy4 = ((ze)aom).d.b();
								return boolean3 ? bpy4 != bpy5 : bpy4 == bpy5;
							}
						}));
						if (boolean3) {
							fa.g(true);
						} else {
							fa.f(true);
						}
					}
				}
			}, fa -> !fa.z(), new ne("argument.entity.options.gamemode.description"));
			a("team", fa -> {
				boolean boolean2 = fa.e();
				String string3 = fa.g().readUnquotedString();
				fa.a((Predicate<aom>)(aom -> {
					if (!(aom instanceof aoy)) {
						return false;
					} else {
						dfo dfo4 = aom.bC();
						String string5 = dfo4 == null ? "" : dfo4.b();
						return string5.equals(string3) != boolean2;
					}
				}));
				if (boolean2) {
					fa.i(true);
				} else {
					fa.h(true);
				}
			}, fa -> !fa.B(), new ne("argument.entity.options.team.description"));
			a("type", fa -> {
				fa.a((BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>>)((suggestionsBuilder, consumer) -> {
					db.a(gl.al.b(), suggestionsBuilder, String.valueOf('!'));
					db.a(acy.b().a(), suggestionsBuilder, "!#");
					if (!fa.F()) {
						db.a(gl.al.b(), suggestionsBuilder);
						db.a(acy.b().a(), suggestionsBuilder, String.valueOf('#'));
					}

					return suggestionsBuilder.buildFuture();
				}));
				int integer2 = fa.g().getCursor();
				boolean boolean3 = fa.e();
				if (fa.F() && !boolean3) {
					fa.g().setCursor(integer2);
					throw b.createWithContext(fa.g(), "type");
				} else {
					if (boolean3) {
						fa.D();
					}

					if (fa.f()) {
						uh uh4 = uh.a(fa.g());
						fa.a((Predicate<aom>)(aom -> aom.cg().aE().e().b(uh4).a(aom.U()) != boolean3));
					} else {
						uh uh4 = uh.a(fa.g());
						aoq<?> aoq5 = (aoq<?>)gl.al.b(uh4).orElseThrow(() -> {
							fa.g().setCursor(integer2);
							return h.createWithContext(fa.g(), uh4.toString());
						});
						if (Objects.equals(aoq.bb, aoq5) && !boolean3) {
							fa.a(false);
						}

						fa.a((Predicate<aom>)(aom -> Objects.equals(aoq5, aom.U()) != boolean3));
						if (!boolean3) {
							fa.a(aoq5);
						}
					}
				}
			}, fa -> !fa.E(), new ne("argument.entity.options.type.description"));
			a("tag", fa -> {
				boolean boolean2 = fa.e();
				String string3 = fa.g().readUnquotedString();
				fa.a((Predicate<aom>)(aom -> "".equals(string3) ? aom.W().isEmpty() != boolean2 : aom.W().contains(string3) != boolean2));
			}, fa -> true, new ne("argument.entity.options.tag.description"));
			a("nbt", fa -> {
				boolean boolean2 = fa.e();
				le le3 = new lv(fa.g()).f();
				fa.a((Predicate<aom>)(aom -> {
					le le4 = aom.e(new le());
					if (aom instanceof ze) {
						bki bki5 = ((ze)aom).bt.f();
						if (!bki5.a()) {
							le4.a("SelectedItem", bki5.b(new le()));
						}
					}

					return lq.a(le3, le4, true) != boolean2;
				}));
			}, fa -> true, new ne("argument.entity.options.nbt.description"));
			a("scores", fa -> {
				StringReader stringReader2 = fa.g();
				Map<String, bx.d> map3 = Maps.<String, bx.d>newHashMap();
				stringReader2.expect('{');
				stringReader2.skipWhitespace();

				while (stringReader2.canRead() && stringReader2.peek() != '}') {
					stringReader2.skipWhitespace();
					String string4 = stringReader2.readUnquotedString();
					stringReader2.skipWhitespace();
					stringReader2.expect('=');
					stringReader2.skipWhitespace();
					bx.d d5 = bx.d.a(stringReader2);
					map3.put(string4, d5);
					stringReader2.skipWhitespace();
					if (stringReader2.canRead() && stringReader2.peek() == ',') {
						stringReader2.skip();
					}
				}

				stringReader2.expect('}');
				if (!map3.isEmpty()) {
					fa.a((Predicate<aom>)(aom -> {
						dfm dfm3 = aom.cg().aF();
						String string4x = aom.bT();

						for (Entry<String, bx.d> entry6 : map3.entrySet()) {
							dfj dfj7 = dfm3.d((String)entry6.getKey());
							if (dfj7 == null) {
								return false;
							}

							if (!dfm3.b(string4x, dfj7)) {
								return false;
							}

							dfl dfl8 = dfm3.c(string4x, dfj7);
							int integer9 = dfl8.b();
							if (!((bx.d)entry6.getValue()).d(integer9)) {
								return false;
							}
						}

						return true;
					}));
				}

				fa.j(true);
			}, fa -> !fa.G(), new ne("argument.entity.options.scores.description"));
			a("advancements", fa -> {
				StringReader stringReader2 = fa.g();
				Map<uh, Predicate<y>> map3 = Maps.<uh, Predicate<y>>newHashMap();
				stringReader2.expect('{');
				stringReader2.skipWhitespace();

				while (stringReader2.canRead() && stringReader2.peek() != '}') {
					stringReader2.skipWhitespace();
					uh uh4 = uh.a(stringReader2);
					stringReader2.skipWhitespace();
					stringReader2.expect('=');
					stringReader2.skipWhitespace();
					if (stringReader2.canRead() && stringReader2.peek() == '{') {
						Map<String, Predicate<ac>> map5 = Maps.<String, Predicate<ac>>newHashMap();
						stringReader2.skipWhitespace();
						stringReader2.expect('{');
						stringReader2.skipWhitespace();

						while (stringReader2.canRead() && stringReader2.peek() != '}') {
							stringReader2.skipWhitespace();
							String string6 = stringReader2.readUnquotedString();
							stringReader2.skipWhitespace();
							stringReader2.expect('=');
							stringReader2.skipWhitespace();
							boolean boolean7 = stringReader2.readBoolean();
							map5.put(string6, (Predicate)ac -> ac.a() == boolean7);
							stringReader2.skipWhitespace();
							if (stringReader2.canRead() && stringReader2.peek() == ',') {
								stringReader2.skip();
							}
						}

						stringReader2.skipWhitespace();
						stringReader2.expect('}');
						stringReader2.skipWhitespace();
						map3.put(uh4, (Predicate)y -> {
							for (Entry<String, Predicate<ac>> entry4 : map5.entrySet()) {
								ac ac5 = y.c((String)entry4.getKey());
								if (ac5 == null || !((Predicate)entry4.getValue()).test(ac5)) {
									return false;
								}
							}

							return true;
						});
					} else {
						boolean boolean5 = stringReader2.readBoolean();
						map3.put(uh4, (Predicate)y -> y.a() == boolean5);
					}

					stringReader2.skipWhitespace();
					if (stringReader2.canRead() && stringReader2.peek() == ',') {
						stringReader2.skip();
					}
				}

				stringReader2.expect('}');
				if (!map3.isEmpty()) {
					fa.a((Predicate<aom>)(aom -> {
						if (!(aom instanceof ze)) {
							return false;
						} else {
							ze ze3 = (ze)aom;
							uq uq4 = ze3.J();
							us us5 = ze3.cg().ay();

							for (Entry<uh, Predicate<y>> entry7 : map3.entrySet()) {
								w w8 = us5.a((uh)entry7.getKey());
								if (w8 == null || !((Predicate)entry7.getValue()).test(uq4.b(w8))) {
									return false;
								}
							}

							return true;
						}
					}));
					fa.a(false);
				}

				fa.k(true);
			}, fa -> !fa.H(), new ne("argument.entity.options.advancements.description"));
			a("predicate", fa -> {
				boolean boolean2 = fa.e();
				uh uh3 = uh.a(fa.g());
				fa.a((Predicate<aom>)(aom -> {
					if (!(aom.l instanceof zd)) {
						return false;
					} else {
						zd zd4 = (zd)aom.l;
						ddm ddm5 = zd4.l().aI().a(uh3);
						if (ddm5 == null) {
							return false;
						} else {
							dat dat6 = new dat.a(zd4).a(dda.a, aom).a(dda.f, aom.cA()).a(dcz.d);
							return boolean2 ^ ddm5.test(dat6);
						}
					}
				}));
			}, fa -> true, new ne("argument.entity.options.predicate.description"));
		}
	}

	public static fb.a a(fa fa, String string, int integer) throws CommandSyntaxException {
		fb.b b4 = (fb.b)i.get(string);
		if (b4 != null) {
			if (b4.b.test(fa)) {
				return b4.a;
			} else {
				throw b.createWithContext(fa.g(), string);
			}
		} else {
			fa.g().setCursor(integer);
			throw a.createWithContext(fa.g(), string);
		}
	}

	public static void a(fa fa, SuggestionsBuilder suggestionsBuilder) {
		String string3 = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);

		for (Entry<String, fb.b> entry5 : i.entrySet()) {
			if (((fb.b)entry5.getValue()).b.test(fa) && ((String)entry5.getKey()).toLowerCase(Locale.ROOT).startsWith(string3)) {
				suggestionsBuilder.suggest((String)entry5.getKey() + '=', ((fb.b)entry5.getValue()).c);
			}
		}
	}

	public interface a {
		void handle(fa fa) throws CommandSyntaxException;
	}

	static class b {
		public final fb.a a;
		public final Predicate<fa> b;
		public final mr c;

		private b(fb.a a, Predicate<fa> predicate, mr mr) {
			this.a = a;
			this.b = predicate;
			this.c = mr;
		}
	}
}
