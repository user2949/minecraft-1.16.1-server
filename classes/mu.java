import java.util.Optional;

public interface mu {
	Optional<ael> b = Optional.of(ael.INSTANCE);
	mu c = new mu() {
		@Override
		public <T> Optional<T> a(mu.a<T> a) {
			return Optional.empty();
		}
	};

	<T> Optional<T> a(mu.a<T> a);

	static mu b(String string) {
		return new mu() {
			@Override
			public <T> Optional<T> a(mu.a<T> a) {
				return a.accept(string);
			}
		};
	}

	default String getString() {
		StringBuilder stringBuilder2 = new StringBuilder();
		this.a(string -> {
			stringBuilder2.append(string);
			return Optional.empty();
		});
		return stringBuilder2.toString();
	}

	public interface a<T> {
		Optional<T> accept(String string);
	}
}
