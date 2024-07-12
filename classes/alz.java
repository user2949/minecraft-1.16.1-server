import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class alz implements DynamicMBean {
	private static final Logger a = LogManager.getLogger();
	private final MinecraftServer b;
	private final MBeanInfo c;
	private final Map<String, alz.a> d = (Map<String, alz.a>)Stream.of(
			new alz.a("tickTimes", this::b, "Historical tick times (ms)", long[].class),
			new alz.a("averageTickTime", this::a, "Current average tick time (ms)", long.class)
		)
		.collect(Collectors.toMap(a -> a.a, Function.identity()));

	private alz(MinecraftServer minecraftServer) {
		this.b = minecraftServer;
		MBeanAttributeInfo[] arr3 = (MBeanAttributeInfo[])this.d.values().stream().map(object -> ((alz.a)object).a()).toArray(MBeanAttributeInfo[]::new);
		this.c = new MBeanInfo(alz.class.getSimpleName(), "metrics for dedicated server", arr3, null, null, new MBeanNotificationInfo[0]);
	}

	public static void a(MinecraftServer minecraftServer) {
		try {
			ManagementFactory.getPlatformMBeanServer().registerMBean(new alz(minecraftServer), new ObjectName("net.minecraft.server:type=Server"));
		} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException var2) {
			a.warn("Failed to initialise server as JMX bean", (Throwable)var2);
		}
	}

	private float a() {
		return this.b.aM();
	}

	private long[] b() {
		return this.b.h;
	}

	@Nullable
	public Object getAttribute(String string) {
		alz.a a3 = (alz.a)this.d.get(string);
		return a3 == null ? null : a3.b.get();
	}

	public void setAttribute(Attribute attribute) {
	}

	public AttributeList getAttributes(String[] arr) {
		List<Attribute> list3 = (List<Attribute>)Arrays.stream(arr)
			.map(this.d::get)
			.filter(Objects::nonNull)
			.map(a -> new Attribute(a.a, a.b.get()))
			.collect(Collectors.toList());
		return new AttributeList(list3);
	}

	public AttributeList setAttributes(AttributeList attributeList) {
		return new AttributeList();
	}

	@Nullable
	public Object invoke(String string, Object[] arr, String[] arr) {
		return null;
	}

	public MBeanInfo getMBeanInfo() {
		return this.c;
	}

	static final class a {
		private final String a;
		private final Supplier<Object> b;
		private final String c;
		private final Class<?> d;

		private a(String string1, Supplier<Object> supplier, String string3, Class<?> class4) {
			this.a = string1;
			this.b = supplier;
			this.c = string3;
			this.d = class4;
		}

		private MBeanAttributeInfo a() {
			return new MBeanAttributeInfo(this.a, this.d.getSimpleName(), this.c, true, false, false);
		}
	}
}
