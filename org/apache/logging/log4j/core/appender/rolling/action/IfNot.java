package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(
	name = "IfNot",
	category = "Core",
	printObject = true
)
public final class IfNot implements PathCondition {
	private final PathCondition negate;

	private IfNot(PathCondition negate) {
		this.negate = (PathCondition)Objects.requireNonNull(negate, "filter");
	}

	public PathCondition getWrappedFilter() {
		return this.negate;
	}

	@Override
	public boolean accept(Path baseDir, Path relativePath, BasicFileAttributes attrs) {
		return !this.negate.accept(baseDir, relativePath, attrs);
	}

	@Override
	public void beforeFileTreeWalk() {
		this.negate.beforeFileTreeWalk();
	}

	@PluginFactory
	public static IfNot createNotCondition(@PluginElement("PathConditions") PathCondition condition) {
		return new IfNot(condition);
	}

	public String toString() {
		return "IfNot(" + this.negate + ")";
	}
}
