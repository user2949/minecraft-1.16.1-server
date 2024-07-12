public class cxe {
	public static final cxe[] a = new cxe[64];
	public static final cxe b = new cxe(0, 0);
	public static final cxe c = new cxe(1, 8368696);
	public static final cxe d = new cxe(2, 16247203);
	public static final cxe e = new cxe(3, 13092807);
	public static final cxe f = new cxe(4, 16711680);
	public static final cxe g = new cxe(5, 10526975);
	public static final cxe h = new cxe(6, 10987431);
	public static final cxe i = new cxe(7, 31744);
	public static final cxe j = new cxe(8, 16777215);
	public static final cxe k = new cxe(9, 10791096);
	public static final cxe l = new cxe(10, 9923917);
	public static final cxe m = new cxe(11, 7368816);
	public static final cxe n = new cxe(12, 4210943);
	public static final cxe o = new cxe(13, 9402184);
	public static final cxe p = new cxe(14, 16776437);
	public static final cxe q = new cxe(15, 14188339);
	public static final cxe r = new cxe(16, 11685080);
	public static final cxe s = new cxe(17, 6724056);
	public static final cxe t = new cxe(18, 15066419);
	public static final cxe u = new cxe(19, 8375321);
	public static final cxe v = new cxe(20, 15892389);
	public static final cxe w = new cxe(21, 5000268);
	public static final cxe x = new cxe(22, 10066329);
	public static final cxe y = new cxe(23, 5013401);
	public static final cxe z = new cxe(24, 8339378);
	public static final cxe A = new cxe(25, 3361970);
	public static final cxe B = new cxe(26, 6704179);
	public static final cxe C = new cxe(27, 6717235);
	public static final cxe D = new cxe(28, 10040115);
	public static final cxe E = new cxe(29, 1644825);
	public static final cxe F = new cxe(30, 16445005);
	public static final cxe G = new cxe(31, 6085589);
	public static final cxe H = new cxe(32, 4882687);
	public static final cxe I = new cxe(33, 55610);
	public static final cxe J = new cxe(34, 8476209);
	public static final cxe K = new cxe(35, 7340544);
	public static final cxe L = new cxe(36, 13742497);
	public static final cxe M = new cxe(37, 10441252);
	public static final cxe N = new cxe(38, 9787244);
	public static final cxe O = new cxe(39, 7367818);
	public static final cxe P = new cxe(40, 12223780);
	public static final cxe Q = new cxe(41, 6780213);
	public static final cxe R = new cxe(42, 10505550);
	public static final cxe S = new cxe(43, 3746083);
	public static final cxe T = new cxe(44, 8874850);
	public static final cxe U = new cxe(45, 5725276);
	public static final cxe V = new cxe(46, 8014168);
	public static final cxe W = new cxe(47, 4996700);
	public static final cxe X = new cxe(48, 4993571);
	public static final cxe Y = new cxe(49, 5001770);
	public static final cxe Z = new cxe(50, 9321518);
	public static final cxe aa = new cxe(51, 2430480);
	public static final cxe ab = new cxe(52, 12398641);
	public static final cxe ac = new cxe(53, 9715553);
	public static final cxe ad = new cxe(54, 6035741);
	public static final cxe ae = new cxe(55, 1474182);
	public static final cxe af = new cxe(56, 3837580);
	public static final cxe ag = new cxe(57, 5647422);
	public static final cxe ah = new cxe(58, 1356933);
	public final int ai;
	public final int aj;

	private cxe(int integer1, int integer2) {
		if (integer1 >= 0 && integer1 <= 63) {
			this.aj = integer1;
			this.ai = integer2;
			a[integer1] = this;
		} else {
			throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
		}
	}
}
