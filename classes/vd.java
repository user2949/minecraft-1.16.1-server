import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.UUID;

public class vd {
	private static final SuggestionProvider<cz> a = (commandContext, suggestionsBuilder) -> db.a(gl.aP.b(), suggestionsBuilder);
	private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("commands.attribute.failed.entity", object));
	private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
		(object1, object2) -> new ne("commands.attribute.failed.no_attribute", object1, object2)
	);
	private static final Dynamic3CommandExceptionType d = new Dynamic3CommandExceptionType(
		(object1, object2, object3) -> new ne("commands.attribute.failed.no_modifier", object2, object1, object3)
	);
	private static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType(
		(object1, object2, object3) -> new ne("commands.attribute.failed.modifier_already_present", object3, object2, object1)
	);

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("attribute")
				.requires(cz -> cz.c(2))
				.then(
					da.a("target", dh.a())
						.then(
							da.a("attribute", dv.a())
								.suggests(a)
								.then(
									da.a("get")
										.executes(commandContext -> a(commandContext.getSource(), dh.a(commandContext, "target"), dv.d(commandContext, "attribute"), 1.0))
										.then(
											da.a("scale", DoubleArgumentType.doubleArg())
												.executes(
													commandContext -> a(
															commandContext.getSource(),
															dh.a(commandContext, "target"),
															dv.d(commandContext, "attribute"),
															DoubleArgumentType.getDouble(commandContext, "scale")
														)
												)
										)
								)
								.then(
									da.a("base")
										.then(
											da.a("set")
												.then(
													da.a("value", DoubleArgumentType.doubleArg())
														.executes(
															commandContext -> c(
																	commandContext.getSource(),
																	dh.a(commandContext, "target"),
																	dv.d(commandContext, "attribute"),
																	DoubleArgumentType.getDouble(commandContext, "value")
																)
														)
												)
										)
										.then(
											da.a("get")
												.executes(commandContext -> b(commandContext.getSource(), dh.a(commandContext, "target"), dv.d(commandContext, "attribute"), 1.0))
												.then(
													da.a("scale", DoubleArgumentType.doubleArg())
														.executes(
															commandContext -> b(
																	commandContext.getSource(),
																	dh.a(commandContext, "target"),
																	dv.d(commandContext, "attribute"),
																	DoubleArgumentType.getDouble(commandContext, "scale")
																)
														)
												)
										)
								)
								.then(
									da.a("modifier")
										.then(
											da.a("add")
												.then(
													da.a("uuid", eb.a())
														.then(
															da.a("name", StringArgumentType.string())
																.then(
																	da.a("value", DoubleArgumentType.doubleArg())
																		.then(
																			da.a("add")
																				.executes(
																					commandContext -> a(
																							commandContext.getSource(),
																							dh.a(commandContext, "target"),
																							dv.d(commandContext, "attribute"),
																							eb.a(commandContext, "uuid"),
																							StringArgumentType.getString(commandContext, "name"),
																							DoubleArgumentType.getDouble(commandContext, "value"),
																							apv.a.ADDITION
																						)
																				)
																		)
																		.then(
																			da.a("multiply")
																				.executes(
																					commandContext -> a(
																							commandContext.getSource(),
																							dh.a(commandContext, "target"),
																							dv.d(commandContext, "attribute"),
																							eb.a(commandContext, "uuid"),
																							StringArgumentType.getString(commandContext, "name"),
																							DoubleArgumentType.getDouble(commandContext, "value"),
																							apv.a.MULTIPLY_TOTAL
																						)
																				)
																		)
																		.then(
																			da.a("multiply_base")
																				.executes(
																					commandContext -> a(
																							commandContext.getSource(),
																							dh.a(commandContext, "target"),
																							dv.d(commandContext, "attribute"),
																							eb.a(commandContext, "uuid"),
																							StringArgumentType.getString(commandContext, "name"),
																							DoubleArgumentType.getDouble(commandContext, "value"),
																							apv.a.MULTIPLY_BASE
																						)
																				)
																		)
																)
														)
												)
										)
										.then(
											da.a("remove")
												.then(
													da.a("uuid", eb.a())
														.executes(
															commandContext -> a(commandContext.getSource(), dh.a(commandContext, "target"), dv.d(commandContext, "attribute"), eb.a(commandContext, "uuid"))
														)
												)
										)
										.then(
											da.a("value")
												.then(
													da.a("get")
														.then(
															da.a("uuid", eb.a())
																.executes(
																	commandContext -> a(
																			commandContext.getSource(), dh.a(commandContext, "target"), dv.d(commandContext, "attribute"), eb.a(commandContext, "uuid"), 1.0
																		)
																)
																.then(
																	da.a("scale", DoubleArgumentType.doubleArg())
																		.executes(
																			commandContext -> a(
																					commandContext.getSource(),
																					dh.a(commandContext, "target"),
																					dv.d(commandContext, "attribute"),
																					eb.a(commandContext, "uuid"),
																					DoubleArgumentType.getDouble(commandContext, "scale")
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

	private static apt a(aom aom, aps aps) throws CommandSyntaxException {
		apt apt3 = a(aom).dA().a(aps);
		if (apt3 == null) {
			throw c.create(aom.P(), new ne(aps.c()));
		} else {
			return apt3;
		}
	}

	private static aoy a(aom aom) throws CommandSyntaxException {
		if (!(aom instanceof aoy)) {
			throw b.create(aom.P());
		} else {
			return (aoy)aom;
		}
	}

	private static aoy b(aom aom, aps aps) throws CommandSyntaxException {
		aoy aoy3 = a(aom);
		if (!aoy3.dA().b(aps)) {
			throw c.create(aom.P(), new ne(aps.c()));
		} else {
			return aoy3;
		}
	}

	private static int a(cz cz, aom aom, aps aps, double double4) throws CommandSyntaxException {
		aoy aoy6 = b(aom, aps);
		double double7 = aoy6.b(aps);
		cz.a(new ne("commands.attribute.value.get.success", new ne(aps.c()), aom.P(), double7), false);
		return (int)(double7 * double4);
	}

	private static int b(cz cz, aom aom, aps aps, double double4) throws CommandSyntaxException {
		aoy aoy6 = b(aom, aps);
		double double7 = aoy6.c(aps);
		cz.a(new ne("commands.attribute.base_value.get.success", new ne(aps.c()), aom.P(), double7), false);
		return (int)(double7 * double4);
	}

	private static int a(cz cz, aom aom, aps aps, UUID uUID, double double5) throws CommandSyntaxException {
		aoy aoy7 = b(aom, aps);
		apu apu8 = aoy7.dA();
		if (!apu8.a(aps, uUID)) {
			throw d.create(aom.P(), new ne(aps.c()), uUID);
		} else {
			double double9 = apu8.b(aps, uUID);
			cz.a(new ne("commands.attribute.modifier.value.get.success", uUID, new ne(aps.c()), aom.P(), double9), false);
			return (int)(double9 * double5);
		}
	}

	private static int c(cz cz, aom aom, aps aps, double double4) throws CommandSyntaxException {
		a(aom, aps).a(double4);
		cz.a(new ne("commands.attribute.base_value.set.success", new ne(aps.c()), aom.P(), double4), false);
		return 1;
	}

	private static int a(cz cz, aom aom, aps aps, UUID uUID, String string, double double6, apv.a a) throws CommandSyntaxException {
		apt apt9 = a(aom, aps);
		apv apv10 = new apv(uUID, string, double6, a);
		if (apt9.a(apv10)) {
			throw e.create(aom.P(), new ne(aps.c()), uUID);
		} else {
			apt9.c(apv10);
			cz.a(new ne("commands.attribute.modifier.add.success", uUID, new ne(aps.c()), aom.P()), false);
			return 1;
		}
	}

	private static int a(cz cz, aom aom, aps aps, UUID uUID) throws CommandSyntaxException {
		apt apt5 = a(aom, aps);
		if (apt5.c(uUID)) {
			cz.a(new ne("commands.attribute.modifier.remove.success", uUID, new ne(aps.c()), aom.P()), false);
			return 1;
		} else {
			throw d.create(aom.P(), new ne(aps.c()), uUID);
		}
	}
}
