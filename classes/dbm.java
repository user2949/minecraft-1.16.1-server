public class dbm {
	public static final dbp a = a("empty", new dbj.a());
	public static final dbp b = a("item", new dbl.a());
	public static final dbp c = a("loot_table", new dbr.a());
	public static final dbp d = a("dynamic", new dbi.a());
	public static final dbp e = a("tag", new dbt.a());
	public static final dbp f = a("alternatives", dbh.a(dbf::new));
	public static final dbp g = a("sequence", dbh.a(dbs::new));
	public static final dbp h = a("group", dbh.a(dbk::new));

	private static dbp a(String string, dbc<? extends dbo> dbc) {
		return gl.a(gl.aY, new uh(string), new dbp(dbc));
	}

	public static Object a() {
		return dar.<dbo, dbp>a(gl.aY, "entry", "type", dbo::a).a();
	}
}
