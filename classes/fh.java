import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class fh {
	private static final Logger a = LogManager.getLogger();
	private static final Map<Class<?>, fh.a<?>> b = Maps.<Class<?>, fh.a<?>>newHashMap();
	private static final Map<uh, fh.a<?>> c = Maps.<uh, fh.a<?>>newHashMap();

	public static <T extends ArgumentType<?>> void a(String string, Class<T> class2, fg<T> fg) {
		uh uh4 = new uh(string);
		if (b.containsKey(class2)) {
			throw new IllegalArgumentException("Class " + class2.getName() + " already has a serializer!");
		} else if (c.containsKey(uh4)) {
			throw new IllegalArgumentException("'" + uh4 + "' is already a registered serializer!");
		} else {
			fh.a<T> a5 = new fh.a<>(class2, fg, uh4);
			b.put(class2, a5);
			c.put(uh4, a5);
		}
	}

	public static void a() {
		fk.a();
		a("entity", dh.class, new dh.a());
		a("game_profile", dj.class, new fi(dj::a));
		a("block_pos", eh.class, new fi(eh::a));
		a("column_pos", ei.class, new fi(ei::a));
		a("vec3", eo.class, new fi(eo::a));
		a("vec2", en.class, new fi(en::a));
		a("block_state", ee.class, new fi(ee::a));
		a("block_predicate", ed.class, new fi(ed::a));
		a("item_stack", et.class, new fi(et::a));
		a("item_predicate", ew.class, new fi(ew::a));
		a("color", dc.class, new fi(dc::a));
		a("component", dd.class, new fi(dd::a));
		a("message", dl.class, new fi(dl::a));
		a("nbt_compound_tag", de.class, new fi(de::a));
		a("nbt_tag", dp.class, new fi(dp::a));
		a("nbt_path", dn.class, new fi(dn::a));
		a("objective", dq.class, new fi(dq::a));
		a("objective_criteria", dr.class, new fi(dr::a));
		a("operation", ds.class, new fi(ds::a));
		a("particle", dt.class, new fi(dt::a));
		a("rotation", el.class, new fi(el::a));
		a("scoreboard_slot", dx.class, new fi(dx::a));
		a("score_holder", dw.class, new dw.c());
		a("swizzle", em.class, new fi(em::a));
		a("team", dz.class, new fi(dz::a));
		a("item_slot", dy.class, new fi(dy::a));
		a("resource_location", dv.class, new fi(dv::a));
		a("mob_effect", dm.class, new fi(dm::a));
		a("function", es.class, new fi(es::a));
		a("entity_anchor", dg.class, new fi(dg::a));
		a("int_range", du.b.class, new du.b.a());
		a("float_range", du.a.class, new du.a.a());
		a("item_enchantment", dk.class, new fi(dk::a));
		a("entity_summon", di.class, new fi(di::a));
		a("dimension", df.class, new fi(df::a));
		a("time", ea.class, new fi(ea::a));
		a("uuid", eb.class, new fi(eb::a));
		if (u.d) {
			a("test_argument", kw.class, new fi(kw::a));
			a("test_class", kt.class, new fi(kt::a));
		}
	}

	@Nullable
	private static fh.a<?> a(uh uh) {
		return (fh.a<?>)c.get(uh);
	}

	@Nullable
	private static fh.a<?> a(ArgumentType<?> argumentType) {
		return (fh.a<?>)b.get(argumentType.getClass());
	}

	public static <T extends ArgumentType<?>> void a(mg mg, T argumentType) {
		fh.a<T> a3 = (fh.a<T>)a(argumentType);
		if (a3 == null) {
			a.error("Could not serialize {} ({}) - will not be sent to client!", argumentType, argumentType.getClass());
			mg.a(new uh(""));
		} else {
			mg.a(a3.c);
			a3.b.a(argumentType, mg);
		}
	}

	@Nullable
	public static ArgumentType<?> a(mg mg) {
		uh uh2 = mg.o();
		fh.a<?> a3 = a(uh2);
		if (a3 == null) {
			a.error("Could not deserialize {}", uh2);
			return null;
		} else {
			return a3.b.b(mg);
		}
	}

	private static <T extends ArgumentType<?>> void a(JsonObject jsonObject, T argumentType) {
		fh.a<T> a3 = (fh.a<T>)a(argumentType);
		if (a3 == null) {
			a.error("Could not serialize argument {} ({})!", argumentType, argumentType.getClass());
			jsonObject.addProperty("type", "unknown");
		} else {
			jsonObject.addProperty("type", "argument");
			jsonObject.addProperty("parser", a3.c.toString());
			JsonObject jsonObject4 = new JsonObject();
			a3.b.a(argumentType, jsonObject4);
			if (jsonObject4.size() > 0) {
				jsonObject.add("properties", jsonObject4);
			}
		}
	}

	public static <S> JsonObject a(CommandDispatcher<S> commandDispatcher, CommandNode<S> commandNode) {
		JsonObject jsonObject3 = new JsonObject();
		if (commandNode instanceof RootCommandNode) {
			jsonObject3.addProperty("type", "root");
		} else if (commandNode instanceof LiteralCommandNode) {
			jsonObject3.addProperty("type", "literal");
		} else if (commandNode instanceof ArgumentCommandNode) {
			a(jsonObject3, ((ArgumentCommandNode)commandNode).getType());
		} else {
			a.error("Could not serialize node {} ({})!", commandNode, commandNode.getClass());
			jsonObject3.addProperty("type", "unknown");
		}

		JsonObject jsonObject4 = new JsonObject();

		for (CommandNode<S> commandNode6 : commandNode.getChildren()) {
			jsonObject4.add(commandNode6.getName(), a(commandDispatcher, commandNode6));
		}

		if (jsonObject4.size() > 0) {
			jsonObject3.add("children", jsonObject4);
		}

		if (commandNode.getCommand() != null) {
			jsonObject3.addProperty("executable", true);
		}

		if (commandNode.getRedirect() != null) {
			Collection<String> collection5 = commandDispatcher.getPath(commandNode.getRedirect());
			if (!collection5.isEmpty()) {
				JsonArray jsonArray6 = new JsonArray();

				for (String string8 : collection5) {
					jsonArray6.add(string8);
				}

				jsonObject3.add("redirect", jsonArray6);
			}
		}

		return jsonObject3;
	}

	static class a<T extends ArgumentType<?>> {
		public final Class<T> a;
		public final fg<T> b;
		public final uh c;

		private a(Class<T> class1, fg<T> fg, uh uh) {
			this.a = class1;
			this.b = fg;
			this.c = uh;
		}
	}
}
