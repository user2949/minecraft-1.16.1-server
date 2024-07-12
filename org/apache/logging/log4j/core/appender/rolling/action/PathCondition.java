package org.apache.logging.log4j.core.appender.rolling.action;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public interface PathCondition {
	void beforeFileTreeWalk();

	boolean accept(Path path1, Path path2, BasicFileAttributes basicFileAttributes);
}
