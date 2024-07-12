import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ame implements amh {
	private static final Logger a = LogManager.getLogger();
	private static final amj b = new amj() {
		@Override
		public long a() {
			return 0L;
		}

		@Override
		public long b() {
			return 0L;
		}

		@Override
		public Object2LongMap<String> c() {
			return Object2LongMaps.emptyMap();
		}
	};
	private static final Splitter c = Splitter.on('\u001e');
	private static final Comparator<Entry<String, ame.a>> d = Entry.comparingByValue(Comparator.comparingLong(a -> a.b)).reversed();
	private final Map<String, ? extends amj> e;
	private final long f;
	private final int g;
	private final long h;
	private final int i;
	private final int j;

	public ame(Map<String, ? extends amj> map, long long2, int integer3, long long4, int integer5) {
		this.e = map;
		this.f = long2;
		this.g = integer3;
		this.h = long4;
		this.i = integer5;
		this.j = integer5 - integer3;
	}

	private amj c(String string) {
		amj amj3 = (amj)this.e.get(string);
		return amj3 != null ? amj3 : b;
	}

	public List<amk> a(String string) {
		String string3 = string;
		amj amj4 = this.c("root");
		long long5 = amj4.a();
		amj amj7 = this.c(string);
		long long8 = amj7.a();
		long long10 = amj7.b();
		List<amk> list12 = Lists.<amk>newArrayList();
		if (!string.isEmpty()) {
			string = string + '\u001e';
		}

		long long13 = 0L;

		for (String string16 : this.e.keySet()) {
			if (a(string, string16)) {
				long13 += this.c(string16).a();
			}
		}

		float float15 = (float)long13;
		if (long13 < long8) {
			long13 = long8;
		}

		if (long5 < long13) {
			long5 = long13;
		}

		for (String string17 : this.e.keySet()) {
			if (a(string, string17)) {
				amj amj18 = this.c(string17);
				long long19 = amj18.a();
				double double21 = (double)long19 * 100.0 / (double)long13;
				double double23 = (double)long19 * 100.0 / (double)long5;
				String string25 = string17.substring(string.length());
				list12.add(new amk(string25, double21, double23, amj18.b()));
			}
		}

		if ((float)long13 > float15) {
			list12.add(
				new amk("unspecified", (double)((float)long13 - float15) * 100.0 / (double)long13, (double)((float)long13 - float15) * 100.0 / (double)long5, long10)
			);
		}

		Collections.sort(list12);
		list12.add(0, new amk(string3, 100.0, (double)long13 * 100.0 / (double)long5, long10));
		return list12;
	}

	private static boolean a(String string1, String string2) {
		return string2.length() > string1.length() && string2.startsWith(string1) && string2.indexOf(30, string1.length() + 1) < 0;
	}

	private Map<String, ame.a> h() {
		Map<String, ame.a> map2 = Maps.newTreeMap();
		this.e.forEach((string, amj) -> {
			Object2LongMap<String> object2LongMap4 = amj.c();
			if (!object2LongMap4.isEmpty()) {
				List<String> list5 = c.splitToList(string);
				object2LongMap4.forEach((stringx, long4) -> ((ame.a)map2.computeIfAbsent(stringx, stringxx -> new ame.a())).a(list5.iterator(), long4));
			}
		});
		return map2;
	}

	@Override
	public long a() {
		return this.f;
	}

	@Override
	public int b() {
		return this.g;
	}

	@Override
	public long c() {
		return this.h;
	}

	@Override
	public int d() {
		return this.i;
	}

	@Override
	public boolean a(File file) {
		file.getParentFile().mkdirs();
		Writer writer3 = null;

		boolean var4;
		try {
			writer3 = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
			writer3.write(this.a(this.g(), this.f()));
			return true;
		} catch (Throwable var8) {
			a.error("Could not save profiler results to {}", file, var8);
			var4 = false;
		} finally {
			IOUtils.closeQuietly(writer3);
		}

		return var4;
	}

	protected String a(long long1, int integer) {
		StringBuilder stringBuilder5 = new StringBuilder();
		stringBuilder5.append("---- Minecraft Profiler Results ----\n");
		stringBuilder5.append("// ");
		stringBuilder5.append(i());
		stringBuilder5.append("\n\n");
		stringBuilder5.append("Version: ").append(u.a().getId()).append('\n');
		stringBuilder5.append("Time span: ").append(long1 / 1000000L).append(" ms\n");
		stringBuilder5.append("Tick span: ").append(integer).append(" ticks\n");
		stringBuilder5.append("// This is approximately ")
			.append(String.format(Locale.ROOT, "%.2f", (float)integer / ((float)long1 / 1.0E9F)))
			.append(" ticks per second. It should be ")
			.append(20)
			.append(" ticks per second\n\n");
		stringBuilder5.append("--- BEGIN PROFILE DUMP ---\n\n");
		this.a(0, "root", stringBuilder5);
		stringBuilder5.append("--- END PROFILE DUMP ---\n\n");
		Map<String, ame.a> map6 = this.h();
		if (!map6.isEmpty()) {
			stringBuilder5.append("--- BEGIN COUNTER DUMP ---\n\n");
			this.a(map6, stringBuilder5, integer);
			stringBuilder5.append("--- END COUNTER DUMP ---\n\n");
		}

		return stringBuilder5.toString();
	}

	private static StringBuilder a(StringBuilder stringBuilder, int integer) {
		stringBuilder.append(String.format("[%02d] ", integer));

		for (int integer3 = 0; integer3 < integer; integer3++) {
			stringBuilder.append("|   ");
		}

		return stringBuilder;
	}

	private void a(int integer, String string, StringBuilder stringBuilder) {
		List<amk> list5 = this.a(string);
		Object2LongMap<String> object2LongMap6 = ObjectUtils.firstNonNull((amj)this.e.get(string), b).c();
		object2LongMap6.forEach(
			(stringx, long4) -> a(stringBuilder, integer).append('#').append(stringx).append(' ').append(long4).append('/').append(long4 / (long)this.j).append('\n')
		);
		if (list5.size() >= 3) {
			for (int integer7 = 1; integer7 < list5.size(); integer7++) {
				amk amk8 = (amk)list5.get(integer7);
				a(stringBuilder, integer)
					.append(amk8.d)
					.append('(')
					.append(amk8.c)
					.append('/')
					.append(String.format(Locale.ROOT, "%.0f", (float)amk8.c / (float)this.j))
					.append(')')
					.append(" - ")
					.append(String.format(Locale.ROOT, "%.2f", amk8.a))
					.append("%/")
					.append(String.format(Locale.ROOT, "%.2f", amk8.b))
					.append("%\n");
				if (!"unspecified".equals(amk8.d)) {
					try {
						this.a(integer + 1, string + '\u001e' + amk8.d, stringBuilder);
					} catch (Exception var9) {
						stringBuilder.append("[[ EXCEPTION ").append(var9).append(" ]]");
					}
				}
			}
		}
	}

	private void a(int integer1, String string, ame.a a, int integer4, StringBuilder stringBuilder) {
		a(stringBuilder, integer1)
			.append(string)
			.append(" total:")
			.append(a.a)
			.append('/')
			.append(a.b)
			.append(" average: ")
			.append(a.a / (long)integer4)
			.append('/')
			.append(a.b / (long)integer4)
			.append('\n');
		a.c.entrySet().stream().sorted(d).forEach(entry -> this.a(integer1 + 1, (String)entry.getKey(), (ame.a)entry.getValue(), integer4, stringBuilder));
	}

	private void a(Map<String, ame.a> map, StringBuilder stringBuilder, int integer) {
		map.forEach((string, a) -> {
			stringBuilder.append("-- Counter: ").append(string).append(" --\n");
			this.a(0, "root", (ame.a)a.c.get("root"), integer, stringBuilder);
			stringBuilder.append("\n\n");
		});
	}

	private static String i() {
		String[] arr1 = new String[]{
			"Shiny numbers!",
			"Am I not running fast enough? :(",
			"I'm working as hard as I can!",
			"Will I ever be good enough for you? :(",
			"Speedy. Zoooooom!",
			"Hello world",
			"40% better than a crash report.",
			"Now with extra numbers",
			"Now with less numbers",
			"Now with the same numbers",
			"You should add flames to things, it makes them go faster!",
			"Do you feel the need for... optimization?",
			"*cracks redstone whip*",
			"Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
		};

		try {
			return arr1[(int)(v.c() % (long)arr1.length)];
		} catch (Throwable var2) {
			return "Witty comment unavailable :(";
		}
	}

	@Override
	public int f() {
		return this.j;
	}

	static class a {
		private long a;
		private long b;
		private final Map<String, ame.a> c = Maps.<String, ame.a>newHashMap();

		private a() {
		}

		public void a(Iterator<String> iterator, long long2) {
			this.b += long2;
			if (!iterator.hasNext()) {
				this.a += long2;
			} else {
				((ame.a)this.c.computeIfAbsent(iterator.next(), string -> new ame.a())).a(iterator, long2);
			}
		}
	}
}
