package com.mojang.brigadier;

import com.mojang.brigadier.context.CommandContext;

@FunctionalInterface
public interface ResultConsumer<S> {
	void onCommandComplete(CommandContext<S> commandContext, boolean boolean2, int integer);
}
