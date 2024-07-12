package org.apache.logging.log4j.core.layout;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

@Plugin(
	name = "PatternMatch",
	category = "Core",
	printObject = true
)
public final class PatternMatch {
	private final String key;
	private final String pattern;

	public PatternMatch(String key, String pattern) {
		this.key = key;
		this.pattern = pattern;
	}

	public String getKey() {
		return this.key;
	}

	public String getPattern() {
		return this.pattern;
	}

	public String toString() {
		return this.key + '=' + this.pattern;
	}

	@PluginBuilderFactory
	public static PatternMatch.Builder newBuilder() {
		return new PatternMatch.Builder();
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
		return 31 * result + (this.pattern == null ? 0 : this.pattern.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			PatternMatch other = (PatternMatch)obj;
			if (this.key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!this.key.equals(other.key)) {
				return false;
			}

			if (this.pattern == null) {
				if (other.pattern != null) {
					return false;
				}
			} else if (!this.pattern.equals(other.pattern)) {
				return false;
			}

			return true;
		}
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<PatternMatch>, Serializable {
		private static final long serialVersionUID = 1L;
		@PluginBuilderAttribute
		private String key;
		@PluginBuilderAttribute
		private String pattern;

		public PatternMatch.Builder setKey(String key) {
			this.key = key;
			return this;
		}

		public PatternMatch.Builder setPattern(String pattern) {
			this.pattern = pattern;
			return this;
		}

		public PatternMatch build() {
			return new PatternMatch(this.key, this.pattern);
		}

		protected Object readResolve() throws ObjectStreamException {
			return new PatternMatch(this.key, this.pattern);
		}
	}
}
