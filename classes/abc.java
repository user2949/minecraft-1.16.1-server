import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface abc {
	abb a(uh uh) throws IOException;

	List<abb> c(uh uh) throws IOException;

	Collection<uh> a(uh uh, Predicate<String> predicate);

	Collection<uh> a(String string, Predicate<String> predicate);

	public static enum a implements abc {
		INSTANCE;

		@Override
		public abb a(uh uh) throws IOException {
			throw new FileNotFoundException(uh.toString());
		}

		@Override
		public List<abb> c(uh uh) {
			return ImmutableList.of();
		}

		@Override
		public Collection<uh> a(uh uh, Predicate<String> predicate) {
			return ImmutableSet.<uh>of();
		}

		@Override
		public Collection<uh> a(String string, Predicate<String> predicate) {
			return ImmutableSet.<uh>of();
		}
	}
}
