public interface axw {
	axw a = a("zombie_villager_cured");
	axw b = a("golem_killed");
	axw c = a("villager_hurt");
	axw d = a("villager_killed");
	axw e = a("trade");

	static axw a(String string) {
		return new axw() {
			public String toString() {
				return string;
			}
		};
	}
}
