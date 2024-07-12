public interface cyv extends cyw {
	@Override
	default int a(int integer) {
		return integer - 1;
	}

	@Override
	default int b(int integer) {
		return integer - 1;
	}
}
