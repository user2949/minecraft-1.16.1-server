package com.mojang.brigadier;

import com.mojang.brigadier.tree.CommandNode;
import java.util.Collection;

@FunctionalInterface
public interface AmbiguityConsumer<S> {
	void ambiguous(CommandNode<S> commandNode1, CommandNode<S> commandNode2, CommandNode<S> commandNode3, Collection<String> collection);
}
