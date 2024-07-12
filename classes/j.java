import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class j {
	private static final Logger a = LogManager.getLogger();
	private final String b;
	private final Throwable c;
	private final k d = new k(this, "System Details");
	private final List<k> e = Lists.<k>newArrayList();
	private File f;
	private boolean g = true;
	private StackTraceElement[] h = new StackTraceElement[0];

	public j(String string, Throwable throwable) {
		this.b = string;
		this.c = throwable;
		this.i();
	}

	private void i() {
		this.d.a("Minecraft Version", (l<String>)(() -> u.a().getName()));
		this.d.a("Minecraft Version ID", (l<String>)(() -> u.a().getId()));
		this.d
			.a(
				"Operating System",
				(l<String>)(() -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"))
			);
		this.d.a("Java Version", (l<String>)(() -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor")));
		this.d
			.a(
				"Java VM Version",
				(l<String>)(() -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"))
			);
		this.d.a("Memory", (l<String>)(() -> {
			Runtime runtime1 = Runtime.getRuntime();
			long long2 = runtime1.maxMemory();
			long long4 = runtime1.totalMemory();
			long long6 = runtime1.freeMemory();
			long long8 = long2 / 1024L / 1024L;
			long long10 = long4 / 1024L / 1024L;
			long long12 = long6 / 1024L / 1024L;
			return long6 + " bytes (" + long12 + " MB) / " + long4 + " bytes (" + long10 + " MB) up to " + long2 + " bytes (" + long8 + " MB)";
		}));
		this.d.a("CPUs", Runtime.getRuntime().availableProcessors());
		this.d.a("JVM Flags", (l<String>)(() -> {
			List<String> list1 = (List<String>)v.j().collect(Collectors.toList());
			return String.format("%d total; %s", list1.size(), list1.stream().collect(Collectors.joining(" ")));
		}));
	}

	public String a() {
		return this.b;
	}

	public Throwable b() {
		return this.c;
	}

	public void a(StringBuilder stringBuilder) {
		if ((this.h == null || this.h.length <= 0) && !this.e.isEmpty()) {
			this.h = ArrayUtils.subarray(((k)this.e.get(0)).a(), 0, 1);
		}

		if (this.h != null && this.h.length > 0) {
			stringBuilder.append("-- Head --\n");
			stringBuilder.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
			stringBuilder.append("Stacktrace:\n");

			for (StackTraceElement stackTraceElement6 : this.h) {
				stringBuilder.append("\t").append("at ").append(stackTraceElement6);
				stringBuilder.append("\n");
			}

			stringBuilder.append("\n");
		}

		for (k k4 : this.e) {
			k4.a(stringBuilder);
			stringBuilder.append("\n\n");
		}

		this.d.a(stringBuilder);
	}

	public String d() {
		StringWriter stringWriter2 = null;
		PrintWriter printWriter3 = null;
		Throwable throwable4 = this.c;
		if (throwable4.getMessage() == null) {
			if (throwable4 instanceof NullPointerException) {
				throwable4 = new NullPointerException(this.b);
			} else if (throwable4 instanceof StackOverflowError) {
				throwable4 = new StackOverflowError(this.b);
			} else if (throwable4 instanceof OutOfMemoryError) {
				throwable4 = new OutOfMemoryError(this.b);
			}

			throwable4.setStackTrace(this.c.getStackTrace());
		}

		String var4;
		try {
			stringWriter2 = new StringWriter();
			printWriter3 = new PrintWriter(stringWriter2);
			throwable4.printStackTrace(printWriter3);
			var4 = stringWriter2.toString();
		} finally {
			IOUtils.closeQuietly(stringWriter2);
			IOUtils.closeQuietly(printWriter3);
		}

		return var4;
	}

	public String e() {
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append("---- Minecraft Crash Report ----\n");
		stringBuilder2.append("// ");
		stringBuilder2.append(j());
		stringBuilder2.append("\n\n");
		stringBuilder2.append("Time: ");
		stringBuilder2.append(new SimpleDateFormat().format(new Date()));
		stringBuilder2.append("\n");
		stringBuilder2.append("Description: ");
		stringBuilder2.append(this.b);
		stringBuilder2.append("\n\n");
		stringBuilder2.append(this.d());
		stringBuilder2.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

		for (int integer3 = 0; integer3 < 87; integer3++) {
			stringBuilder2.append("-");
		}

		stringBuilder2.append("\n\n");
		this.a(stringBuilder2);
		return stringBuilder2.toString();
	}

	public boolean a(File file) {
		if (this.f != null) {
			return false;
		} else {
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}

			Writer writer3 = null;

			boolean var4;
			try {
				writer3 = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
				writer3.write(this.e());
				this.f = file;
				return true;
			} catch (Throwable var8) {
				a.error("Could not save crash report to {}", file, var8);
				var4 = false;
			} finally {
				IOUtils.closeQuietly(writer3);
			}

			return var4;
		}
	}

	public k g() {
		return this.d;
	}

	public k a(String string) {
		return this.a(string, 1);
	}

	public k a(String string, int integer) {
		k k4 = new k(this, string);
		if (this.g) {
			int integer5 = k4.a(integer);
			StackTraceElement[] arr6 = this.c.getStackTrace();
			StackTraceElement stackTraceElement7 = null;
			StackTraceElement stackTraceElement8 = null;
			int integer9 = arr6.length - integer5;
			if (integer9 < 0) {
				System.out.println("Negative index in crash report handler (" + arr6.length + "/" + integer5 + ")");
			}

			if (arr6 != null && 0 <= integer9 && integer9 < arr6.length) {
				stackTraceElement7 = arr6[integer9];
				if (arr6.length + 1 - integer5 < arr6.length) {
					stackTraceElement8 = arr6[arr6.length + 1 - integer5];
				}
			}

			this.g = k4.a(stackTraceElement7, stackTraceElement8);
			if (integer5 > 0 && !this.e.isEmpty()) {
				k k10 = (k)this.e.get(this.e.size() - 1);
				k10.b(integer5);
			} else if (arr6 != null && arr6.length >= integer5 && 0 <= integer9 && integer9 < arr6.length) {
				this.h = new StackTraceElement[integer9];
				System.arraycopy(arr6, 0, this.h, 0, this.h.length);
			} else {
				this.g = false;
			}
		}

		this.e.add(k4);
		return k4;
	}

	private static String j() {
		String[] arr1 = new String[]{
			"Who set us up the TNT?",
			"Everything's going to plan. No, really, that was supposed to happen.",
			"Uh... Did I do that?",
			"Oops.",
			"Why did you do that?",
			"I feel sad now :(",
			"My bad.",
			"I'm sorry, Dave.",
			"I let you down. Sorry :(",
			"On the bright side, I bought you a teddy bear!",
			"Daisy, daisy...",
			"Oh - I know what I did wrong!",
			"Hey, that tickles! Hehehe!",
			"I blame Dinnerbone.",
			"You should try our sister game, Minceraft!",
			"Don't be sad. I'll do better next time, I promise!",
			"Don't be sad, have a hug! <3",
			"I just don't know what went wrong :(",
			"Shall we play a game?",
			"Quite honestly, I wouldn't worry myself about that.",
			"I bet Cylons wouldn't have this problem.",
			"Sorry :(",
			"Surprise! Haha. Well, this is awkward.",
			"Would you like a cupcake?",
			"Hi. I'm Minecraft, and I'm a crashaholic.",
			"Ooh. Shiny.",
			"This doesn't make any sense!",
			"Why is it breaking :(",
			"Don't do that.",
			"Ouch. That hurt :(",
			"You're mean.",
			"This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
			"There are four lights!",
			"But it works on my machine."
		};

		try {
			return arr1[(int)(v.c() % (long)arr1.length)];
		} catch (Throwable var2) {
			return "Witty comment unavailable :(";
		}
	}

	public static j a(Throwable throwable, String string) {
		while (throwable instanceof CompletionException && throwable.getCause() != null) {
			throwable = throwable.getCause();
		}

		j j3;
		if (throwable instanceof s) {
			j3 = ((s)throwable).a();
		} else {
			j3 = new j(string, throwable);
		}

		return j3;
	}

	public static void h() {
		new j("Don't panic!", new Throwable()).e();
	}
}
