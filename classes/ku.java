import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class ku {
	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("test")
				.then(da.a("runthis").executes(commandContext -> a(commandContext.getSource())))
				.then(da.a("runthese").executes(commandContext -> b(commandContext.getSource())))
				.then(
					da.a("runfailed")
						.executes(commandContext -> a(commandContext.getSource(), false, 0, 8))
						.then(
							da.a("onlyRequiredTests", BoolArgumentType.bool())
								.executes(commandContext -> a(commandContext.getSource(), BoolArgumentType.getBool(commandContext, "onlyRequiredTests"), 0, 8))
								.then(
									da.a("rotationSteps", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													BoolArgumentType.getBool(commandContext, "onlyRequiredTests"),
													IntegerArgumentType.getInteger(commandContext, "rotationSteps"),
													8
												)
										)
										.then(
											da.a("testsPerRow", IntegerArgumentType.integer())
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															BoolArgumentType.getBool(commandContext, "onlyRequiredTests"),
															IntegerArgumentType.getInteger(commandContext, "rotationSteps"),
															IntegerArgumentType.getInteger(commandContext, "testsPerRow")
														)
												)
										)
								)
						)
				)
				.then(
					da.a("run")
						.then(
							da.a("testName", kw.a())
								.executes(commandContext -> a(commandContext.getSource(), kw.a(commandContext, "testName"), 0))
								.then(
									da.a("rotationSteps", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(commandContext.getSource(), kw.a(commandContext, "testName"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"))
										)
								)
						)
				)
				.then(
					da.a("runall")
						.executes(commandContext -> a(commandContext.getSource(), 0, 8))
						.then(
							da.a("testClassName", kt.a())
								.executes(commandContext -> a(commandContext.getSource(), kt.a(commandContext, "testClassName"), 0, 8))
								.then(
									da.a("rotationSteps", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(), kt.a(commandContext, "testClassName"), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), 8
												)
										)
										.then(
											da.a("testsPerRow", IntegerArgumentType.integer())
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															kt.a(commandContext, "testClassName"),
															IntegerArgumentType.getInteger(commandContext, "rotationSteps"),
															IntegerArgumentType.getInteger(commandContext, "testsPerRow")
														)
												)
										)
								)
						)
						.then(
							da.a("rotationSteps", IntegerArgumentType.integer())
								.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "rotationSteps"), 8))
								.then(
									da.a("testsPerRow", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													IntegerArgumentType.getInteger(commandContext, "rotationSteps"),
													IntegerArgumentType.getInteger(commandContext, "testsPerRow")
												)
										)
								)
						)
				)
				.then(
					da.a("export")
						.then(
							da.a("testName", StringArgumentType.word())
								.executes(commandContext -> c(commandContext.getSource(), StringArgumentType.getString(commandContext, "testName")))
						)
				)
				.then(da.a("exportthis").executes(commandContext -> c(commandContext.getSource())))
				.then(
					da.a("import")
						.then(
							da.a("testName", StringArgumentType.word())
								.executes(commandContext -> d(commandContext.getSource(), StringArgumentType.getString(commandContext, "testName")))
						)
				)
				.then(
					da.a("pos")
						.executes(commandContext -> a(commandContext.getSource(), "pos"))
						.then(
							da.a("var", StringArgumentType.word()).executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "var")))
						)
				)
				.then(
					da.a("create")
						.then(
							da.a("testName", StringArgumentType.word())
								.executes(commandContext -> a(commandContext.getSource(), StringArgumentType.getString(commandContext, "testName"), 5, 5, 5))
								.then(
									da.a("width", IntegerArgumentType.integer())
										.executes(
											commandContext -> a(
													commandContext.getSource(),
													StringArgumentType.getString(commandContext, "testName"),
													IntegerArgumentType.getInteger(commandContext, "width"),
													IntegerArgumentType.getInteger(commandContext, "width"),
													IntegerArgumentType.getInteger(commandContext, "width")
												)
										)
										.then(
											da.a("height", IntegerArgumentType.integer())
												.then(
													da.a("depth", IntegerArgumentType.integer())
														.executes(
															commandContext -> a(
																	commandContext.getSource(),
																	StringArgumentType.getString(commandContext, "testName"),
																	IntegerArgumentType.getInteger(commandContext, "width"),
																	IntegerArgumentType.getInteger(commandContext, "height"),
																	IntegerArgumentType.getInteger(commandContext, "depth")
																)
														)
												)
										)
								)
						)
				)
				.then(
					da.a("clearall")
						.executes(commandContext -> a(commandContext.getSource(), 200))
						.then(
							da.a("radius", IntegerArgumentType.integer())
								.executes(commandContext -> a(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "radius")))
						)
				)
		);
	}

	private static int a(cz cz, String string, int integer3, int integer4, int integer5) {
		if (integer3 <= 48 && integer4 <= 48 && integer5 <= 48) {
			zd zd6 = cz.e();
			fu fu7 = new fu(cz.d());
			fu fu8 = new fu(fu7.u(), cz.e().a(cio.a.WORLD_SURFACE, fu7).v(), fu7.w() + 3);
			kr.a(string.toLowerCase(), fu8, new fu(integer3, integer4, integer5), cap.NONE, zd6);

			for (int integer9 = 0; integer9 < integer3; integer9++) {
				for (int integer10 = 0; integer10 < integer5; integer10++) {
					fu fu11 = new fu(fu8.u() + integer9, fu8.v() + 1, fu8.w() + integer10);
					bvr bvr12 = bvs.h;
					ec ec13 = new ec(bvr12.n(), Collections.EMPTY_SET, null);
					ec13.a(zd6, fu11, 2);
				}
			}

			kr.a(fu8, new fu(1, 0, -1), cap.NONE, zd6);
			return 0;
		} else {
			throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
		}
	}

	private static int a(cz cz, String string) throws CommandSyntaxException {
		deh deh3 = (deh)cz.h().a(10.0, 1.0F, false);
		fu fu4 = deh3.a();
		zd zd5 = cz.e();
		Optional<fu> optional6 = kr.a(fu4, 15, zd5);
		if (!optional6.isPresent()) {
			optional6 = kr.a(fu4, 200, zd5);
		}

		if (!optional6.isPresent()) {
			cz.a(new nd("Can't find a structure block that contains the targeted pos " + fu4));
			return 0;
		} else {
			cel cel7 = (cel)zd5.c((fu)optional6.get());
			fu fu8 = fu4.b((gr)optional6.get());
			String string9 = fu8.u() + ", " + fu8.v() + ", " + fu8.w();
			String string10 = cel7.f();
			mr mr11 = new nd(string9)
				.a(
					nb.b
						.a(true)
						.a(i.GREEN)
						.a(new mv(mv.a.a, new nd("Click to copy to clipboard")))
						.a(new mp(mp.a.COPY_TO_CLIPBOARD, "final BlockPos " + string + " = new BlockPos(" + string9 + ");"))
				);
			cz.a(new nd("Position relative to " + string10 + ": ").a(mr11), false);
			qy.a(zd5, new fu(fu4), string9, -2147418368, 10000);
			return 1;
		}
	}

	private static int a(cz cz) {
		fu fu2 = new fu(cz.d());
		zd zd3 = cz.e();
		fu fu4 = kr.b(fu2, 15, zd3);
		if (fu4 == null) {
			a(zd3, "Couldn't find any structure block within 15 radius", i.RED);
			return 0;
		} else {
			kj.a(zd3);
			a(zd3, fu4, null);
			return 1;
		}
	}

	private static int b(cz cz) {
		fu fu2 = new fu(cz.d());
		zd zd3 = cz.e();
		Collection<fu> collection4 = kr.c(fu2, 200, zd3);
		if (collection4.isEmpty()) {
			a(zd3, "Couldn't find any structure blocks within 200 block radius", i.RED);
			return 1;
		} else {
			kj.a(zd3);
			b(cz, "Running " + collection4.size() + " tests...");
			kq kq5 = new kq();
			collection4.forEach(fu -> a(zd3, fu, kq5));
			return 1;
		}
	}

	private static void a(zd zd, fu fu, @Nullable kq kq) {
		cel cel4 = (cel)zd.c(fu);
		String string5 = cel4.f();
		kv kv6 = ki.e(string5);
		kg kg7 = new kg(kv6, cel4.l(), zd);
		if (kq != null) {
			kq.a(kg7);
			kg7.a(new ku.a(zd, kq));
		}

		a(kv6, zd);
		deg deg8 = kr.a(cel4);
		fu fu9 = new fu(deg8.a, deg8.b, deg8.c);
		kj.a(kg7, fu9, km.a);
	}

	private static void b(zd zd, kq kq) {
		if (kq.i()) {
			a(zd, "GameTest done! " + kq.h() + " tests were run", i.WHITE);
			if (kq.d()) {
				a(zd, "" + kq.a() + " required tests failed :(", i.RED);
			} else {
				a(zd, "All required tests passed :)", i.GREEN);
			}

			if (kq.e()) {
				a(zd, "" + kq.b() + " optional tests failed", i.GRAY);
			}
		}
	}

	private static int a(cz cz, int integer) {
		zd zd3 = cz.e();
		kj.a(zd3);
		fu fu4 = new fu(cz.d().b, (double)cz.e().a(cio.a.WORLD_SURFACE, new fu(cz.d())).v(), cz.d().d);
		kj.a(zd3, fu4, km.a, aec.a(integer, 0, 1024));
		return 1;
	}

	private static int a(cz cz, kv kv, int integer) {
		zd zd4 = cz.e();
		fu fu5 = new fu(cz.d());
		int integer6 = cz.e().a(cio.a.WORLD_SURFACE, fu5).v();
		fu fu7 = new fu(fu5.u(), integer6, fu5.w() + 3);
		kj.a(zd4);
		a(kv, zd4);
		cap cap8 = kr.a(integer);
		kg kg9 = new kg(kv, cap8, zd4);
		kj.a(kg9, fu7, km.a);
		return 1;
	}

	private static void a(kv kv, zd zd) {
		Consumer<zd> consumer3 = ki.c(kv.e());
		if (consumer3 != null) {
			consumer3.accept(zd);
		}
	}

	private static int a(cz cz, int integer2, int integer3) {
		kj.a(cz.e());
		Collection<kv> collection4 = ki.a();
		b(cz, "Running all " + collection4.size() + " tests...");
		ki.d();
		a(cz, collection4, integer2, integer3);
		return 1;
	}

	private static int a(cz cz, String string, int integer3, int integer4) {
		Collection<kv> collection5 = ki.a(string);
		kj.a(cz.e());
		b(cz, "Running " + collection5.size() + " tests from " + string + "...");
		ki.d();
		a(cz, collection5, integer3, integer4);
		return 1;
	}

	private static int a(cz cz, boolean boolean2, int integer3, int integer4) {
		Collection<kv> collection5;
		if (boolean2) {
			collection5 = (Collection<kv>)ki.c().stream().filter(kv::d).collect(Collectors.toList());
		} else {
			collection5 = ki.c();
		}

		if (collection5.isEmpty()) {
			b(cz, "No failed tests to rerun");
			return 0;
		} else {
			kj.a(cz.e());
			b(cz, "Rerunning " + collection5.size() + " failed tests (" + (boolean2 ? "only required tests" : "including optional tests") + ")");
			a(cz, collection5, integer3, integer4);
			return 1;
		}
	}

	private static void a(cz cz, Collection<kv> collection, int integer3, int integer4) {
		fu fu5 = new fu(cz.d());
		fu fu6 = new fu(fu5.u(), cz.e().a(cio.a.WORLD_SURFACE, fu5).v(), fu5.w() + 3);
		zd zd7 = cz.e();
		cap cap8 = kr.a(integer3);
		Collection<kg> collection9 = kj.b(collection, fu6, cap8, zd7, km.a, integer4);
		kq kq10 = new kq(collection9);
		kq10.a(new ku.a(zd7, kq10));
		kq10.a(kg -> ki.a(kg.u()));
	}

	private static void b(cz cz, String string) {
		cz.a(new nd(string), false);
	}

	private static int c(cz cz) {
		fu fu2 = new fu(cz.d());
		zd zd3 = cz.e();
		fu fu4 = kr.b(fu2, 15, zd3);
		if (fu4 == null) {
			a(zd3, "Couldn't find any structure block within 15 radius", i.RED);
			return 0;
		} else {
			cel cel5 = (cel)zd3.c(fu4);
			String string6 = cel5.f();
			return c(cz, string6);
		}
	}

	private static int c(cz cz, String string) {
		Path path3 = Paths.get(kr.a);
		uh uh4 = new uh("minecraft", string);
		Path path5 = cz.e().r_().a(uh4, ".nbt");
		Path path6 = jn.a(path5, string, path3);
		if (path6 == null) {
			b(cz, "Failed to export " + path5);
			return 1;
		} else {
			try {
				Files.createDirectories(path6.getParent());
			} catch (IOException var7) {
				b(cz, "Could not create folder " + path6.getParent());
				var7.printStackTrace();
				return 1;
			}

			b(cz, "Exported " + string + " to " + path6.toAbsolutePath());
			return 0;
		}
	}

	private static int d(cz cz, String string) {
		Path path3 = Paths.get(kr.a, string + ".snbt");
		uh uh4 = new uh("minecraft", string);
		Path path5 = cz.e().r_().a(uh4, ".nbt");

		try {
			BufferedReader bufferedReader6 = Files.newBufferedReader(path3);
			String string7 = IOUtils.toString(bufferedReader6);
			Files.createDirectories(path5.getParent());
			OutputStream outputStream8 = Files.newOutputStream(path5);
			lo.a(lv.a(string7), outputStream8);
			b(cz, "Imported to " + path5.toAbsolutePath());
			return 0;
		} catch (CommandSyntaxException | IOException var8) {
			System.err.println("Failed to load structure " + string);
			var8.printStackTrace();
			return 1;
		}
	}

	private static void a(zd zd, String string, i i) {
		zd.a((Predicate<? super ze>)(ze -> true)).forEach(ze -> ze.a(new nd(i + string), v.b));
	}

	static class a implements kh {
		private final zd a;
		private final kq b;

		public a(zd zd, kq kq) {
			this.a = zd;
			this.b = kq;
		}

		@Override
		public void a(kg kg) {
		}

		@Override
		public void c(kg kg) {
			ku.b(this.a, this.b);
		}
	}
}
