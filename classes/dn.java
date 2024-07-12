import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class dn implements ArgumentType<dn.h> {
	private static final Collection<String> c = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
	public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("arguments.nbtpath.node.invalid"));
	public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(object -> new ne("arguments.nbtpath.nothing_found", object));

	public static dn a() {
		return new dn();
	}

	public static dn.h a(CommandContext<cz> commandContext, String string) {
		return commandContext.getArgument(string, dn.h.class);
	}

	public dn.h parse(StringReader stringReader) throws CommandSyntaxException {
		List<dn.i> list3 = Lists.<dn.i>newArrayList();
		int integer4 = stringReader.getCursor();
		Object2IntMap<dn.i> object2IntMap5 = new Object2IntOpenHashMap<>();
		boolean boolean6 = true;

		while (stringReader.canRead() && stringReader.peek() != ' ') {
			dn.i i7 = a(stringReader, boolean6);
			list3.add(i7);
			object2IntMap5.put(i7, stringReader.getCursor() - integer4);
			boolean6 = false;
			if (stringReader.canRead()) {
				char character8 = stringReader.peek();
				if (character8 != ' ' && character8 != '[' && character8 != '{') {
					stringReader.expect('.');
				}
			}
		}

		return new dn.h(stringReader.getString().substring(integer4, stringReader.getCursor()), (dn.i[])list3.toArray(new dn.i[0]), object2IntMap5);
	}

	private static dn.i a(StringReader stringReader, boolean boolean2) throws CommandSyntaxException {
		switch (stringReader.peek()) {
			case '"': {
				String string3 = stringReader.readString();
				return a(stringReader, string3);
			}
			case '[':
				stringReader.skip();
				int integer3 = stringReader.peek();
				if (integer3 == 123) {
					le le4 = new lv(stringReader).f();
					stringReader.expect(']');
					return new dn.e(le4);
				} else {
					if (integer3 == 93) {
						stringReader.skip();
						return dn.a.a;
					}

					int integer4 = stringReader.readInt();
					stringReader.expect(']');
					return new dn.c(integer4);
				}
			case '{':
				if (!boolean2) {
					throw a.createWithContext(stringReader);
				}

				le le3 = new lv(stringReader).f();
				return new dn.g(le3);
			default: {
				String string3 = b(stringReader);
				return a(stringReader, string3);
			}
		}
	}

	private static dn.i a(StringReader stringReader, String string) throws CommandSyntaxException {
		if (stringReader.canRead() && stringReader.peek() == '{') {
			le le3 = new lv(stringReader).f();
			return new dn.f(string, le3);
		} else {
			return new dn.b(string);
		}
	}

	private static String b(StringReader stringReader) throws CommandSyntaxException {
		int integer2 = stringReader.getCursor();

		while (stringReader.canRead() && a(stringReader.peek())) {
			stringReader.skip();
		}

		if (stringReader.getCursor() == integer2) {
			throw a.createWithContext(stringReader);
		} else {
			return stringReader.getString().substring(integer2, stringReader.getCursor());
		}
	}

	@Override
	public Collection<String> getExamples() {
		return c;
	}

	private static boolean a(char character) {
		return character != ' ' && character != '"' && character != '[' && character != ']' && character != '.' && character != '{' && character != '}';
	}

	private static Predicate<lu> b(le le) {
		return lu -> lq.a(le, lu, true);
	}

	static class a implements dn.i {
		public static final dn.a a = new dn.a();

		private a() {
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof ld) {
				list.addAll((ld)lu);
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			if (lu instanceof ld) {
				ld<?> ld5 = (ld<?>)lu;
				if (ld5.isEmpty()) {
					lu lu6 = (lu)supplier.get();
					if (ld5.b(0, lu6)) {
						list.add(lu6);
					}
				} else {
					list.addAll(ld5);
				}
			}
		}

		@Override
		public lu a() {
			return new lk();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			if (!(lu instanceof ld)) {
				return 0;
			} else {
				ld<?> ld4 = (ld<?>)lu;
				int integer5 = ld4.size();
				if (integer5 == 0) {
					ld4.b(0, (lu)supplier.get());
					return 1;
				} else {
					lu lu6 = (lu)supplier.get();
					int integer7 = integer5 - (int)ld4.stream().filter(lu6::equals).count();
					if (integer7 == 0) {
						return 0;
					} else {
						ld4.clear();
						if (!ld4.b(0, lu6)) {
							return 0;
						} else {
							for (int integer8 = 1; integer8 < integer5; integer8++) {
								ld4.b(integer8, (lu)supplier.get());
							}

							return integer7;
						}
					}
				}
			}
		}

		@Override
		public int a(lu lu) {
			if (lu instanceof ld) {
				ld<?> ld3 = (ld<?>)lu;
				int integer4 = ld3.size();
				if (integer4 > 0) {
					ld3.clear();
					return integer4;
				}
			}

			return 0;
		}
	}

	static class b implements dn.i {
		private final String a;

		public b(String string) {
			this.a = string;
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof le) {
				lu lu4 = ((le)lu).c(this.a);
				if (lu4 != null) {
					list.add(lu4);
				}
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			if (lu instanceof le) {
				le le5 = (le)lu;
				lu lu6;
				if (le5.e(this.a)) {
					lu6 = le5.c(this.a);
				} else {
					lu6 = (lu)supplier.get();
					le5.a(this.a, lu6);
				}

				list.add(lu6);
			}
		}

		@Override
		public lu a() {
			return new le();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			if (lu instanceof le) {
				le le4 = (le)lu;
				lu lu5 = (lu)supplier.get();
				lu lu6 = le4.a(this.a, lu5);
				if (!lu5.equals(lu6)) {
					return 1;
				}
			}

			return 0;
		}

		@Override
		public int a(lu lu) {
			if (lu instanceof le) {
				le le3 = (le)lu;
				if (le3.e(this.a)) {
					le3.r(this.a);
					return 1;
				}
			}

			return 0;
		}
	}

	static class c implements dn.i {
		private final int a;

		public c(int integer) {
			this.a = integer;
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof ld) {
				ld<?> ld4 = (ld<?>)lu;
				int integer5 = ld4.size();
				int integer6 = this.a < 0 ? integer5 + this.a : this.a;
				if (0 <= integer6 && integer6 < integer5) {
					list.add(ld4.get(integer6));
				}
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			this.a(lu, list);
		}

		@Override
		public lu a() {
			return new lk();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			if (lu instanceof ld) {
				ld<?> ld4 = (ld<?>)lu;
				int integer5 = ld4.size();
				int integer6 = this.a < 0 ? integer5 + this.a : this.a;
				if (0 <= integer6 && integer6 < integer5) {
					lu lu7 = (lu)ld4.get(integer6);
					lu lu8 = (lu)supplier.get();
					if (!lu8.equals(lu7) && ld4.a(integer6, lu8)) {
						return 1;
					}
				}
			}

			return 0;
		}

		@Override
		public int a(lu lu) {
			if (lu instanceof ld) {
				ld<?> ld3 = (ld<?>)lu;
				int integer4 = ld3.size();
				int integer5 = this.a < 0 ? integer4 + this.a : this.a;
				if (0 <= integer5 && integer5 < integer4) {
					ld3.c(integer5);
					return 1;
				}
			}

			return 0;
		}
	}

	static class e implements dn.i {
		private final le a;
		private final Predicate<lu> b;

		public e(le le) {
			this.a = le;
			this.b = dn.b(le);
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof lk) {
				lk lk4 = (lk)lu;
				lk4.stream().filter(this.b).forEach(list::add);
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			MutableBoolean mutableBoolean5 = new MutableBoolean();
			if (lu instanceof lk) {
				lk lk6 = (lk)lu;
				lk6.stream().filter(this.b).forEach(lux -> {
					list.add(lux);
					mutableBoolean5.setTrue();
				});
				if (mutableBoolean5.isFalse()) {
					le le7 = this.a.g();
					lk6.add(le7);
					list.add(le7);
				}
			}
		}

		@Override
		public lu a() {
			return new lk();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			int integer4 = 0;
			if (lu instanceof lk) {
				lk lk5 = (lk)lu;
				int integer6 = lk5.size();
				if (integer6 == 0) {
					lk5.add(supplier.get());
					integer4++;
				} else {
					for (int integer7 = 0; integer7 < integer6; integer7++) {
						lu lu8 = lk5.k(integer7);
						if (this.b.test(lu8)) {
							lu lu9 = (lu)supplier.get();
							if (!lu9.equals(lu8) && lk5.a(integer7, lu9)) {
								integer4++;
							}
						}
					}
				}
			}

			return integer4;
		}

		@Override
		public int a(lu lu) {
			int integer3 = 0;
			if (lu instanceof lk) {
				lk lk4 = (lk)lu;

				for (int integer5 = lk4.size() - 1; integer5 >= 0; integer5--) {
					if (this.b.test(lk4.k(integer5))) {
						lk4.c(integer5);
						integer3++;
					}
				}
			}

			return integer3;
		}
	}

	static class f implements dn.i {
		private final String a;
		private final le b;
		private final Predicate<lu> c;

		public f(String string, le le) {
			this.a = string;
			this.b = le;
			this.c = dn.b(le);
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof le) {
				lu lu4 = ((le)lu).c(this.a);
				if (this.c.test(lu4)) {
					list.add(lu4);
				}
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			if (lu instanceof le) {
				le le5 = (le)lu;
				lu lu6 = le5.c(this.a);
				if (lu6 == null) {
					lu var6 = this.b.g();
					le5.a(this.a, var6);
					list.add(var6);
				} else if (this.c.test(lu6)) {
					list.add(lu6);
				}
			}
		}

		@Override
		public lu a() {
			return new le();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			if (lu instanceof le) {
				le le4 = (le)lu;
				lu lu5 = le4.c(this.a);
				if (this.c.test(lu5)) {
					lu lu6 = (lu)supplier.get();
					if (!lu6.equals(lu5)) {
						le4.a(this.a, lu6);
						return 1;
					}
				}
			}

			return 0;
		}

		@Override
		public int a(lu lu) {
			if (lu instanceof le) {
				le le3 = (le)lu;
				lu lu4 = le3.c(this.a);
				if (this.c.test(lu4)) {
					le3.r(this.a);
					return 1;
				}
			}

			return 0;
		}
	}

	static class g implements dn.i {
		private final Predicate<lu> a;

		public g(le le) {
			this.a = dn.b(le);
		}

		@Override
		public void a(lu lu, List<lu> list) {
			if (lu instanceof le && this.a.test(lu)) {
				list.add(lu);
			}
		}

		@Override
		public void a(lu lu, Supplier<lu> supplier, List<lu> list) {
			this.a(lu, list);
		}

		@Override
		public lu a() {
			return new le();
		}

		@Override
		public int a(lu lu, Supplier<lu> supplier) {
			return 0;
		}

		@Override
		public int a(lu lu) {
			return 0;
		}
	}

	public static class h {
		private final String a;
		private final Object2IntMap<dn.i> b;
		private final dn.i[] c;

		public h(String string, dn.i[] arr, Object2IntMap<dn.i> object2IntMap) {
			this.a = string;
			this.c = arr;
			this.b = object2IntMap;
		}

		public List<lu> a(lu lu) throws CommandSyntaxException {
			List<lu> list3 = Collections.singletonList(lu);

			for (dn.i i7 : this.c) {
				list3 = i7.a(list3);
				if (list3.isEmpty()) {
					throw this.a(i7);
				}
			}

			return list3;
		}

		public int b(lu lu) {
			List<lu> list3 = Collections.singletonList(lu);

			for (dn.i i7 : this.c) {
				list3 = i7.a(list3);
				if (list3.isEmpty()) {
					return 0;
				}
			}

			return list3.size();
		}

		private List<lu> d(lu lu) throws CommandSyntaxException {
			List<lu> list3 = Collections.singletonList(lu);

			for (int integer4 = 0; integer4 < this.c.length - 1; integer4++) {
				dn.i i5 = this.c[integer4];
				int integer6 = integer4 + 1;
				list3 = i5.a(list3, this.c[integer6]::a);
				if (list3.isEmpty()) {
					throw this.a(i5);
				}
			}

			return list3;
		}

		public List<lu> a(lu lu, Supplier<lu> supplier) throws CommandSyntaxException {
			List<lu> list4 = this.d(lu);
			dn.i i5 = this.c[this.c.length - 1];
			return i5.a(list4, supplier);
		}

		private static int a(List<lu> list, Function<lu, Integer> function) {
			return (Integer)list.stream().map(function).reduce(0, (integer1, integer2) -> integer1 + integer2);
		}

		public int b(lu lu, Supplier<lu> supplier) throws CommandSyntaxException {
			List<lu> list4 = this.d(lu);
			dn.i i5 = this.c[this.c.length - 1];
			return a(list4, lux -> i5.a(lux, supplier));
		}

		public int c(lu lu) {
			List<lu> list3 = Collections.singletonList(lu);

			for (int integer4 = 0; integer4 < this.c.length - 1; integer4++) {
				list3 = this.c[integer4].a(list3);
			}

			dn.i i4 = this.c[this.c.length - 1];
			return a(list3, i4::a);
		}

		private CommandSyntaxException a(dn.i i) {
			int integer3 = this.b.getInt(i);
			return dn.b.create(this.a.substring(0, integer3));
		}

		public String toString() {
			return this.a;
		}
	}

	interface i {
		void a(lu lu, List<lu> list);

		void a(lu lu, Supplier<lu> supplier, List<lu> list);

		lu a();

		int a(lu lu, Supplier<lu> supplier);

		int a(lu lu);

		default List<lu> a(List<lu> list) {
			return this.a(list, this::a);
		}

		default List<lu> a(List<lu> list, Supplier<lu> supplier) {
			return this.a(list, (BiConsumer<lu, List<lu>>)((lu, listx) -> this.a(lu, supplier, listx)));
		}

		default List<lu> a(List<lu> list, BiConsumer<lu, List<lu>> biConsumer) {
			List<lu> list4 = Lists.<lu>newArrayList();

			for (lu lu6 : list) {
				biConsumer.accept(lu6, list4);
			}

			return list4;
		}
	}
}
