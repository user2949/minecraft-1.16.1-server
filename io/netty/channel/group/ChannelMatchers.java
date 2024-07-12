package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ServerChannel;

public final class ChannelMatchers {
	private static final ChannelMatcher ALL_MATCHER = new ChannelMatcher() {
		@Override
		public boolean matches(Channel channel) {
			return true;
		}
	};
	private static final ChannelMatcher SERVER_CHANNEL_MATCHER = isInstanceOf(ServerChannel.class);
	private static final ChannelMatcher NON_SERVER_CHANNEL_MATCHER = isNotInstanceOf(ServerChannel.class);

	private ChannelMatchers() {
	}

	public static ChannelMatcher all() {
		return ALL_MATCHER;
	}

	public static ChannelMatcher isNot(Channel channel) {
		return invert(is(channel));
	}

	public static ChannelMatcher is(Channel channel) {
		return new ChannelMatchers.InstanceMatcher(channel);
	}

	public static ChannelMatcher isInstanceOf(Class<? extends Channel> clazz) {
		return new ChannelMatchers.ClassMatcher(clazz);
	}

	public static ChannelMatcher isNotInstanceOf(Class<? extends Channel> clazz) {
		return invert(isInstanceOf(clazz));
	}

	public static ChannelMatcher isServerChannel() {
		return SERVER_CHANNEL_MATCHER;
	}

	public static ChannelMatcher isNonServerChannel() {
		return NON_SERVER_CHANNEL_MATCHER;
	}

	public static ChannelMatcher invert(ChannelMatcher matcher) {
		return new ChannelMatchers.InvertMatcher(matcher);
	}

	public static ChannelMatcher compose(ChannelMatcher... matchers) {
		if (matchers.length < 1) {
			throw new IllegalArgumentException("matchers must at least contain one element");
		} else {
			return (ChannelMatcher)(matchers.length == 1 ? matchers[0] : new ChannelMatchers.CompositeMatcher(matchers));
		}
	}

	private static final class ClassMatcher implements ChannelMatcher {
		private final Class<? extends Channel> clazz;

		ClassMatcher(Class<? extends Channel> clazz) {
			this.clazz = clazz;
		}

		@Override
		public boolean matches(Channel ch) {
			return this.clazz.isInstance(ch);
		}
	}

	private static final class CompositeMatcher implements ChannelMatcher {
		private final ChannelMatcher[] matchers;

		CompositeMatcher(ChannelMatcher... matchers) {
			this.matchers = matchers;
		}

		@Override
		public boolean matches(Channel channel) {
			for (ChannelMatcher m : this.matchers) {
				if (!m.matches(channel)) {
					return false;
				}
			}

			return true;
		}
	}

	private static final class InstanceMatcher implements ChannelMatcher {
		private final Channel channel;

		InstanceMatcher(Channel channel) {
			this.channel = channel;
		}

		@Override
		public boolean matches(Channel ch) {
			return this.channel == ch;
		}
	}

	private static final class InvertMatcher implements ChannelMatcher {
		private final ChannelMatcher matcher;

		InvertMatcher(ChannelMatcher matcher) {
			this.matcher = matcher;
		}

		@Override
		public boolean matches(Channel channel) {
			return !this.matcher.matches(channel);
		}
	}
}
