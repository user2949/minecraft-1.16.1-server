import java.util.function.UnaryOperator;

public interface mx extends mr {
	mx a(nb nb);

	default mx c(String string) {
		return this.a(new nd(string));
	}

	mx a(mr mr);

	default mx a(UnaryOperator<nb> unaryOperator) {
		this.a((nb)unaryOperator.apply(this.c()));
		return this;
	}

	default mx c(nb nb) {
		this.a(nb.a(this.c()));
		return this;
	}

	default mx a(i... arr) {
		this.a(this.c().a(arr));
		return this;
	}

	default mx a(i i) {
		this.a(this.c().b(i));
		return this;
	}
}
