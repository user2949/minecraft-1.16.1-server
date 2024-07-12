package io.netty.channel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ChannelHandler {
	void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception;

	void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception;

	@Deprecated
	void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception;

	@Inherited
	@Documented
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Sharable {
	}
}
