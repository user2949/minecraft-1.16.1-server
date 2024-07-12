import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class cx extends RuntimeException {
	private final mr a;

	public cx(mr mr) {
		super(mr.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
		this.a = mr;
	}

	public mr a() {
		return this.a;
	}
}
