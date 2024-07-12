import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;

public class wc {
	private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ne("commands.help.failed"));

	public static void a(CommandDispatcher<cz> commandDispatcher) {
		commandDispatcher.register(
			da.a("help")
				.executes(commandContext -> {
					Map<CommandNode<cz>, String> map3 = commandDispatcher.getSmartUsage(commandDispatcher.getRoot(), commandContext.getSource());
		
					for (String string5 : map3.values()) {
						commandContext.getSource().a(new nd("/" + string5), false);
					}
		
					return map3.size();
				})
				.then(
					da.a("command", StringArgumentType.greedyString())
						.executes(
							commandContext -> {
								ParseResults<cz> parseResults3 = commandDispatcher.parse(StringArgumentType.getString(commandContext, "command"), commandContext.getSource());
								if (parseResults3.getContext().getNodes().isEmpty()) {
									throw a.create();
								} else {
									Map<CommandNode<cz>, String> map4 = commandDispatcher.getSmartUsage(
										Iterables.<ParsedCommandNode<cz>>getLast(parseResults3.getContext().getNodes()).getNode(), commandContext.getSource()
									);
					
									for (String string6 : map4.values()) {
										commandContext.getSource().a(new nd("/" + parseResults3.getReader().getString() + " " + string6), false);
									}
					
									return map4.size();
								}
							}
						)
				)
		);
	}
}
