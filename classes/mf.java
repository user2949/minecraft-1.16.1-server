import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;

public enum mf {
	HANDSHAKING(-1, b().a(nj.SERVERBOUND, new mf.a().a(st.class, st::new))),
	PLAY(
		0,
		b()
			.a(
				nj.CLIENTBOUND,
				new mf.a()
					.a(nm.class, nm::new)
					.a(nn.class, nn::new)
					.a(no.class, no::new)
					.a(np.class, np::new)
					.a(nq.class, nq::new)
					.a(nr.class, nr::new)
					.a(ns.class, ns::new)
					.a(nt.class, nt::new)
					.a(nu.class, nu::new)
					.a(nv.class, nv::new)
					.a(nw.class, nw::new)
					.a(nx.class, nx::new)
					.a(ny.class, ny::new)
					.a(nz.class, nz::new)
					.a(oa.class, oa::new)
					.a(ob.class, ob::new)
					.a(oc.class, oc::new)
					.a(od.class, od::new)
					.a(oe.class, oe::new)
					.a(of.class, of::new)
					.a(og.class, og::new)
					.a(oh.class, oh::new)
					.a(oi.class, oi::new)
					.a(oj.class, oj::new)
					.a(ok.class, ok::new)
					.a(ol.class, ol::new)
					.a(om.class, om::new)
					.a(on.class, on::new)
					.a(oo.class, oo::new)
					.a(op.class, op::new)
					.a(oq.class, oq::new)
					.a(or.class, or::new)
					.a(os.class, os::new)
					.a(ot.class, ot::new)
					.a(ou.class, ou::new)
					.a(ov.class, ov::new)
					.a(ow.class, ow::new)
					.a(ox.class, ox::new)
					.a(oy.class, oy::new)
					.a(oz.class, oz::new)
					.a(pa.a.class, pa.a::new)
					.a(pa.b.class, pa.b::new)
					.a(pa.c.class, pa.c::new)
					.a(pa.class, pa::new)
					.a(pb.class, pb::new)
					.a(pc.class, pc::new)
					.a(pd.class, pd::new)
					.a(pe.class, pe::new)
					.a(pf.class, pf::new)
					.a(pg.class, pg::new)
					.a(ph.class, ph::new)
					.a(pi.class, pi::new)
					.a(pj.class, pj::new)
					.a(pk.class, pk::new)
					.a(pl.class, pl::new)
					.a(pm.class, pm::new)
					.a(pn.class, pn::new)
					.a(po.class, po::new)
					.a(pp.class, pp::new)
					.a(pq.class, pq::new)
					.a(pr.class, pr::new)
					.a(ps.class, ps::new)
					.a(pt.class, pt::new)
					.a(pu.class, pu::new)
					.a(pv.class, pv::new)
					.a(pw.class, pw::new)
					.a(px.class, px::new)
					.a(py.class, py::new)
					.a(pz.class, pz::new)
					.a(qa.class, qa::new)
					.a(qb.class, qb::new)
					.a(qc.class, qc::new)
					.a(qd.class, qd::new)
					.a(qe.class, qe::new)
					.a(qf.class, qf::new)
					.a(qg.class, qg::new)
					.a(qh.class, qh::new)
					.a(qi.class, qi::new)
					.a(qj.class, qj::new)
					.a(qk.class, qk::new)
					.a(ql.class, ql::new)
					.a(qm.class, qm::new)
					.a(qn.class, qn::new)
					.a(qo.class, qo::new)
					.a(qp.class, qp::new)
					.a(qq.class, qq::new)
					.a(qr.class, qr::new)
					.a(qs.class, qs::new)
					.a(qt.class, qt::new)
					.a(qu.class, qu::new)
					.a(qv.class, qv::new)
					.a(qw.class, qw::new)
			)
			.a(
				nj.SERVERBOUND,
				new mf.a()
					.a(ra.class, ra::new)
					.a(rb.class, rb::new)
					.a(rc.class, rc::new)
					.a(rd.class, rd::new)
					.a(re.class, re::new)
					.a(rf.class, rf::new)
					.a(rg.class, rg::new)
					.a(rh.class, rh::new)
					.a(ri.class, ri::new)
					.a(rj.class, rj::new)
					.a(rk.class, rk::new)
					.a(rl.class, rl::new)
					.a(rm.class, rm::new)
					.a(rn.class, rn::new)
					.a(ro.class, ro::new)
					.a(rp.class, rp::new)
					.a(rq.class, rq::new)
					.a(rr.class, rr::new)
					.a(rs.a.class, rs.a::new)
					.a(rs.b.class, rs.b::new)
					.a(rs.c.class, rs.c::new)
					.a(rs.class, rs::new)
					.a(rt.class, rt::new)
					.a(ru.class, ru::new)
					.a(rv.class, rv::new)
					.a(rw.class, rw::new)
					.a(rx.class, rx::new)
					.a(ry.class, ry::new)
					.a(rz.class, rz::new)
					.a(sa.class, sa::new)
					.a(sb.class, sb::new)
					.a(sc.class, sc::new)
					.a(sd.class, sd::new)
					.a(se.class, se::new)
					.a(sf.class, sf::new)
					.a(sg.class, sg::new)
					.a(sh.class, sh::new)
					.a(si.class, si::new)
					.a(sj.class, sj::new)
					.a(sk.class, sk::new)
					.a(sl.class, sl::new)
					.a(sm.class, sm::new)
					.a(sn.class, sn::new)
					.a(so.class, so::new)
					.a(sp.class, sp::new)
					.a(sq.class, sq::new)
					.a(sr.class, sr::new)
			)
	),
	STATUS(1, b().a(nj.SERVERBOUND, new mf.a().a(to.class, to::new).a(tn.class, tn::new)).a(nj.CLIENTBOUND, new mf.a().a(tk.class, tk::new).a(tj.class, tj::new))),
	LOGIN(
		2,
		b()
			.a(nj.CLIENTBOUND, new mf.a().a(tb.class, tb::new).a(sz.class, sz::new).a(sy.class, sy::new).a(ta.class, ta::new).a(sx.class, sx::new))
			.a(nj.SERVERBOUND, new mf.a().a(te.class, te::new).a(tf.class, tf::new).a(td.class, td::new))
	);

	private static final mf[] e = new mf[4];
	private static final Map<Class<? extends ni<?>>, mf> f = Maps.<Class<? extends ni<?>>, mf>newHashMap();
	private final int g;
	private final Map<nj, ? extends mf.a<?>> h;

	private static mf.b b() {
		return new mf.b();
	}

	private mf(int integer3, mf.b b) {
		this.g = integer3;
		this.h = b.a;
	}

	@Nullable
	public Integer a(nj nj, ni<?> ni) {
		return ((mf.a)this.h.get(nj)).a(ni.getClass());
	}

	@Nullable
	public ni<?> a(nj nj, int integer) {
		return ((mf.a)this.h.get(nj)).a(integer);
	}

	public int a() {
		return this.g;
	}

	@Nullable
	public static mf a(int integer) {
		return integer >= -1 && integer <= 2 ? e[integer - -1] : null;
	}

	public static mf a(ni<?> ni) {
		return (mf)f.get(ni.getClass());
	}

	static {
		for (mf mf4 : values()) {
			int integer5 = mf4.a();
			if (integer5 < -1 || integer5 > 2) {
				throw new Error("Invalid protocol ID " + Integer.toString(integer5));
			}

			e[integer5 - -1] = mf4;
			mf4.h.forEach((nj, a) -> a.a().forEach(class2 -> {
					if (f.containsKey(class2) && f.get(class2) != mf4) {
						throw new IllegalStateException("Packet " + class2 + " is already assigned to protocol " + f.get(class2) + " - can't reassign to " + mf4);
					} else {
						f.put(class2, mf4);
					}
				}));
		}
	}

	static class a<T extends mj> {
		private final Object2IntMap<Class<? extends ni<T>>> a = v.a(
			new Object2IntOpenHashMap<>(), object2IntOpenHashMap -> object2IntOpenHashMap.defaultReturnValue(-1)
		);
		private final List<Supplier<? extends ni<T>>> b = Lists.<Supplier<? extends ni<T>>>newArrayList();

		private a() {
		}

		public <P extends ni<T>> mf.a<T> a(Class<P> class1, Supplier<P> supplier) {
			int integer4 = this.b.size();
			int integer5 = this.a.put(class1, integer4);
			if (integer5 != -1) {
				String string6 = "Packet " + class1 + " is already registered to ID " + integer5;
				LogManager.getLogger().fatal(string6);
				throw new IllegalArgumentException(string6);
			} else {
				this.b.add(supplier);
				return this;
			}
		}

		@Nullable
		public Integer a(Class<?> class1) {
			int integer3 = this.a.getInt(class1);
			return integer3 == -1 ? null : integer3;
		}

		@Nullable
		public ni<?> a(int integer) {
			Supplier<? extends ni<T>> supplier3 = (Supplier<? extends ni<T>>)this.b.get(integer);
			return supplier3 != null ? (ni)supplier3.get() : null;
		}

		public Iterable<Class<? extends ni<?>>> a() {
			return Iterables.unmodifiableIterable(this.a.keySet());
		}
	}

	static class b {
		private final Map<nj, mf.a<?>> a = Maps.newEnumMap(nj.class);

		private b() {
		}

		public <T extends mj> mf.b a(nj nj, mf.a<T> a) {
			this.a.put(nj, a);
			return this;
		}
	}
}
