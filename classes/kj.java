import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.mutable.MutableInt;

public class kj {
	public static kx a = new kp();

	public static void a(kg kg, fu fu, km km) {
		kg.a();
		km.a(kg);
		kg.a(new kh() {
			@Override
			public void a(kg kg) {
				kj.b(kg, bvs.dg);
			}

			@Override
			public void c(kg kg) {
				kj.b(kg, kg.q() ? bvs.dm : bvs.cZ);
				kj.b(kg, v.d(kg.n()));
				kj.c(kg);
			}
		});
		kg.a(fu, 2);
	}

	public static Collection<kg> a(Collection<kb> collection, fu fu, cap cap, zd zd, km km, int integer) {
		kc kc7 = new kc(collection, fu, cap, zd, km, integer);
		kc7.b();
		return kc7.a();
	}

	public static Collection<kg> b(Collection<kv> collection, fu fu, cap cap, zd zd, km km, int integer) {
		return a(a(collection), fu, cap, zd, km, integer);
	}

	public static Collection<kb> a(Collection<kv> collection) {
		Map<String, Collection<kv>> map2 = Maps.<String, Collection<kv>>newHashMap();
		collection.forEach(kv -> {
			String string3 = kv.e();
			Collection<kv> collection4 = (Collection<kv>)map2.computeIfAbsent(string3, string -> Lists.newArrayList());
			collection4.add(kv);
		});
		return (Collection<kb>)map2.keySet().stream().flatMap(string -> {
			Collection<kv> collection3 = (Collection<kv>)map2.get(string);
			Consumer<zd> consumer4 = ki.c(string);
			MutableInt mutableInt5 = new MutableInt();
			return Streams.stream(Iterables.partition(collection3, 100)).map(list -> new kb(string + ":" + mutableInt5.incrementAndGet(), collection3, consumer4));
		}).collect(Collectors.toList());
	}

	private static void c(kg kg) {
		Throwable throwable2 = kg.n();
		String string3 = (kg.q() ? "" : "(optional) ") + kg.c() + " failed! " + v.d(throwable2);
		a(kg.g(), kg.q() ? i.RED : i.YELLOW, string3);
		if (throwable2 instanceof ka) {
			ka ka4 = (ka)throwable2;
			a(kg.g(), ka4.c(), ka4.a());
		}

		a.a(kg);
	}

	private static void b(kg kg, bvr bvr) {
		zd zd3 = kg.g();
		fu fu4 = kg.d();
		fu fu5 = new fu(-1, -1, -1);
		fu fu6 = cve.a(fu4.a(fu5), bzj.NONE, kg.t(), fu4);
		zd3.a(fu6, bvs.es.n().a(kg.t()));
		fu fu7 = fu6.b(0, 1, 0);
		zd3.a(fu7, bvr.n());

		for (int integer8 = -1; integer8 <= 1; integer8++) {
			for (int integer9 = -1; integer9 <= 1; integer9++) {
				fu fu10 = fu6.b(integer8, -1, integer9);
				zd3.a(fu10, bvs.bF.n());
			}
		}
	}

	private static void b(kg kg, String string) {
		zd zd3 = kg.g();
		fu fu4 = kg.d();
		fu fu5 = new fu(-1, 1, -1);
		fu fu6 = cve.a(fu4.a(fu5), bzj.NONE, kg.t(), fu4);
		zd3.a(fu6, bvs.lY.n().a(kg.t()));
		cfj cfj7 = zd3.d_(fu6);
		bki bki8 = a(kg.c(), kg.q(), string);
		bzb.a(zd3, fu6, cfj7, bki8);
	}

	private static bki a(String string1, boolean boolean2, String string3) {
		bki bki4 = new bki(bkk.oS);
		lk lk5 = new lk();
		StringBuffer stringBuffer6 = new StringBuffer();
		Arrays.stream(string1.split("\\.")).forEach(string -> stringBuffer6.append(string).append('\n'));
		if (!boolean2) {
			stringBuffer6.append("(optional)\n");
		}

		stringBuffer6.append("-------------------\n");
		lk5.add(lt.a(stringBuffer6.toString() + string3));
		bki4.a("pages", lk5);
		return bki4;
	}

	private static void a(zd zd, i i, String string) {
		zd.a((Predicate<? super ze>)(ze -> true)).forEach(ze -> ze.a(new nd(string).a(i), v.b));
	}

	public static void a(zd zd) {
		qy.a(zd);
	}

	private static void a(zd zd, fu fu, String string) {
		qy.a(zd, fu, string, -2130771968, Integer.MAX_VALUE);
	}

	public static void a(zd zd, fu fu, km km, int integer) {
		km.a();
		fu fu5 = fu.b(-integer, 0, -integer);
		fu fu6 = fu.b(integer, 0, integer);
		fu.b(fu5, fu6).filter(fux -> zd.d_(fux).a(bvs.mY)).forEach(fux -> {
			cel cel3 = (cel)zd.c(fux);
			fu fu4 = cel3.o();
			ctd ctd5 = kr.b(cel3);
			kr.a(ctd5, fu4.v(), zd);
		});
	}
}
