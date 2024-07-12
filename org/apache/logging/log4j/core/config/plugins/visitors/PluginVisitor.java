package org.apache.logging.log4j.core.config.plugins.visitors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

public interface PluginVisitor<A extends Annotation> {
	PluginVisitor<A> setAnnotation(Annotation annotation);

	PluginVisitor<A> setAliases(String... arr);

	PluginVisitor<A> setConversionType(Class<?> class1);

	PluginVisitor<A> setStrSubstitutor(StrSubstitutor strSubstitutor);

	PluginVisitor<A> setMember(Member member);

	Object visit(Configuration configuration, Node node, LogEvent logEvent, StringBuilder stringBuilder);
}
