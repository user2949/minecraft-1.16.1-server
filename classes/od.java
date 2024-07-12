import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class od implements ni<nl> {
	private RootCommandNode<db> a;

	public od() {
	}

	public od(RootCommandNode<db> rootCommandNode) {
		this.a = rootCommandNode;
	}

	@Override
	public void a(mg mg) throws IOException {
		od.a[] arr3 = new od.a[mg.i()];
		Deque<od.a> deque4 = new ArrayDeque(arr3.length);

		for (int integer5 = 0; integer5 < arr3.length; integer5++) {
			arr3[integer5] = this.c(mg);
			deque4.add(arr3[integer5]);
		}

		while (!deque4.isEmpty()) {
			boolean boolean5 = false;
			Iterator<od.a> iterator6 = deque4.iterator();

			while (iterator6.hasNext()) {
				od.a a7 = (od.a)iterator6.next();
				if (a7.a(arr3)) {
					iterator6.remove();
					boolean5 = true;
				}
			}

			if (!boolean5) {
				throw new IllegalStateException("Server sent an impossible command tree");
			}
		}

		this.a = (RootCommandNode<db>)arr3[mg.i()].e;
	}

	@Override
	public void b(mg mg) throws IOException {
		Map<CommandNode<db>, Integer> map3 = Maps.<CommandNode<db>, Integer>newHashMap();
		Deque<CommandNode<db>> deque4 = new ArrayDeque();
		deque4.add(this.a);

		while (!deque4.isEmpty()) {
			CommandNode<db> commandNode5 = (CommandNode<db>)deque4.pollFirst();
			if (!map3.containsKey(commandNode5)) {
				int integer6 = map3.size();
				map3.put(commandNode5, integer6);
				deque4.addAll(commandNode5.getChildren());
				if (commandNode5.getRedirect() != null) {
					deque4.add(commandNode5.getRedirect());
				}
			}
		}

		CommandNode<db>[] arr5 = new CommandNode[map3.size()];

		for (Entry<CommandNode<db>, Integer> entry7 : map3.entrySet()) {
			arr5[entry7.getValue()] = (CommandNode<db>)entry7.getKey();
		}

		mg.d(arr5.length);

		for (CommandNode<db> commandNode9 : arr5) {
			this.a(mg, commandNode9, map3);
		}

		mg.d((Integer)map3.get(this.a));
	}

	private od.a c(mg mg) {
		byte byte3 = mg.readByte();
		int[] arr4 = mg.b();
		int integer5 = (byte3 & 8) != 0 ? mg.i() : 0;
		ArgumentBuilder<db, ?> argumentBuilder6 = this.a(mg, byte3);
		return new od.a(argumentBuilder6, byte3, integer5, arr4);
	}

	@Nullable
	private ArgumentBuilder<db, ?> a(mg mg, byte byte2) {
		int integer4 = byte2 & 3;
		if (integer4 == 2) {
			String string5 = mg.e(32767);
			ArgumentType<?> argumentType6 = fh.a(mg);
			if (argumentType6 == null) {
				return null;
			} else {
				RequiredArgumentBuilder<db, ?> requiredArgumentBuilder7 = RequiredArgumentBuilder.argument(string5, argumentType6);
				if ((byte2 & 16) != 0) {
					requiredArgumentBuilder7.suggests(fj.a(mg.o()));
				}

				return requiredArgumentBuilder7;
			}
		} else {
			return integer4 == 1 ? LiteralArgumentBuilder.literal(mg.e(32767)) : null;
		}
	}

	private void a(mg mg, CommandNode<db> commandNode, Map<CommandNode<db>, Integer> map) {
		byte byte5 = 0;
		if (commandNode.getRedirect() != null) {
			byte5 = (byte)(byte5 | 8);
		}

		if (commandNode.getCommand() != null) {
			byte5 = (byte)(byte5 | 4);
		}

		if (commandNode instanceof RootCommandNode) {
			byte5 = (byte)(byte5 | 0);
		} else if (commandNode instanceof ArgumentCommandNode) {
			byte5 = (byte)(byte5 | 2);
			if (((ArgumentCommandNode)commandNode).getCustomSuggestions() != null) {
				byte5 = (byte)(byte5 | 16);
			}
		} else {
			if (!(commandNode instanceof LiteralCommandNode)) {
				throw new UnsupportedOperationException("Unknown node type " + commandNode);
			}

			byte5 = (byte)(byte5 | 1);
		}

		mg.writeByte(byte5);
		mg.d(commandNode.getChildren().size());

		for (CommandNode<db> commandNode7 : commandNode.getChildren()) {
			mg.d((Integer)map.get(commandNode7));
		}

		if (commandNode.getRedirect() != null) {
			mg.d((Integer)map.get(commandNode.getRedirect()));
		}

		if (commandNode instanceof ArgumentCommandNode) {
			ArgumentCommandNode<db, ?> argumentCommandNode6 = (ArgumentCommandNode<db, ?>)commandNode;
			mg.a(argumentCommandNode6.getName());
			fh.a(mg, argumentCommandNode6.getType());
			if (argumentCommandNode6.getCustomSuggestions() != null) {
				mg.a(fj.a(argumentCommandNode6.getCustomSuggestions()));
			}
		} else if (commandNode instanceof LiteralCommandNode) {
			mg.a(((LiteralCommandNode)commandNode).getLiteral());
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}

	static class a {
		@Nullable
		private final ArgumentBuilder<db, ?> a;
		private final byte b;
		private final int c;
		private final int[] d;
		private CommandNode<db> e;

		private a(@Nullable ArgumentBuilder<db, ?> argumentBuilder, byte byte2, int integer, int[] arr) {
			this.a = argumentBuilder;
			this.b = byte2;
			this.c = integer;
			this.d = arr;
		}

		public boolean a(od.a[] arr) {
			if (this.e == null) {
				if (this.a == null) {
					this.e = new RootCommandNode<>();
				} else {
					if ((this.b & 8) != 0) {
						if (arr[this.c].e == null) {
							return false;
						}

						this.a.redirect(arr[this.c].e);
					}

					if ((this.b & 4) != 0) {
						this.a.executes(commandContext -> 0);
					}

					this.e = this.a.build();
				}
			}

			for (int integer6 : this.d) {
				if (arr[integer6].e == null) {
					return false;
				}
			}

			for (int integer6x : this.d) {
				CommandNode<db> commandNode7 = arr[integer6x].e;
				if (!(commandNode7 instanceof RootCommandNode)) {
					this.e.addChild(commandNode7);
				}
			}

			return true;
		}
	}
}
