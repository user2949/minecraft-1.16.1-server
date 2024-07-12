package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ExtendedStackTraceElement;
import org.apache.logging.log4j.core.impl.ThrowableProxy;

class Initializers {
	static class SetupContextInitializer {
		void setupModule(SetupContext context, boolean includeStacktrace) {
			context.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
			context.setMixInAnnotations(Marker.class, MarkerMixIn.class);
			context.setMixInAnnotations(Level.class, LevelMixIn.class);
			context.setMixInAnnotations(LogEvent.class, LogEventWithContextListMixIn.class);
			context.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
			context.setMixInAnnotations(ThrowableProxy.class, includeStacktrace ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
		}
	}

	static class SetupContextJsonInitializer {
		void setupModule(SetupContext context, boolean includeStacktrace) {
			context.setMixInAnnotations(StackTraceElement.class, StackTraceElementMixIn.class);
			context.setMixInAnnotations(Marker.class, MarkerMixIn.class);
			context.setMixInAnnotations(Level.class, LevelMixIn.class);
			context.setMixInAnnotations(LogEvent.class, LogEventJsonMixIn.class);
			context.setMixInAnnotations(ExtendedStackTraceElement.class, ExtendedStackTraceElementMixIn.class);
			context.setMixInAnnotations(ThrowableProxy.class, includeStacktrace ? ThrowableProxyMixIn.class : ThrowableProxyWithoutStacktraceMixIn.class);
		}
	}

	static class SimpleModuleInitializer {
		void initialize(SimpleModule simpleModule) {
			simpleModule.addDeserializer(StackTraceElement.class, new Log4jStackTraceElementDeserializer());
			simpleModule.addDeserializer(ContextStack.class, new MutableThreadContextStackDeserializer());
		}
	}
}
