import javax.annotation.Nullable;

public interface cwp extends cws {
	@Nullable
	chd a(go go);

	int b(fu fu);

	public static enum a implements cwp {
		INSTANCE;

		@Nullable
		@Override
		public chd a(go go) {
			return null;
		}

		@Override
		public int b(fu fu) {
			return 0;
		}

		@Override
		public void a(go go, boolean boolean2) {
		}
	}
}
