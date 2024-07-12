package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

@Plugin(
	name = "KeyValuePair",
	category = "Core",
	printObject = true
)
public final class KeyValuePair {
	private final String key;
	private final String value;

	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return this.key + '=' + this.value;
	}

	@PluginBuilderFactory
	public static KeyValuePair.Builder newBuilder() {
		return new KeyValuePair.Builder();
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
		return 31 * result + (this.value == null ? 0 : this.value.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		} else {
			KeyValuePair other = (KeyValuePair)obj;
			if (this.key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!this.key.equals(other.key)) {
				return false;
			}

			if (this.value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!this.value.equals(other.value)) {
				return false;
			}

			return true;
		}
	}

	public static class Builder implements org.apache.logging.log4j.core.util.Builder<KeyValuePair> {
		@PluginBuilderAttribute
		private String key;
		@PluginBuilderAttribute
		private String value;

		public KeyValuePair.Builder setKey(String aKey) {
			this.key = aKey;
			return this;
		}

		public KeyValuePair.Builder setValue(String aValue) {
			this.value = aValue;
			return this;
		}

		public KeyValuePair build() {
			return new KeyValuePair(this.key, this.value);
		}
	}
}
