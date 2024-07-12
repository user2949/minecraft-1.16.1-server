package org.apache.logging.log4j.message;

import java.util.ResourceBundle;

public class LocalizedMessageFactory extends AbstractMessageFactory {
	private static final long serialVersionUID = -1996295808703146741L;
	private final transient ResourceBundle resourceBundle;
	private final String baseName;

	public LocalizedMessageFactory(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.baseName = null;
	}

	public LocalizedMessageFactory(String baseName) {
		this.resourceBundle = null;
		this.baseName = baseName;
	}

	public String getBaseName() {
		return this.baseName;
	}

	public ResourceBundle getResourceBundle() {
		return this.resourceBundle;
	}

	@Override
	public Message newMessage(String key) {
		return this.resourceBundle == null ? new LocalizedMessage(this.baseName, key) : new LocalizedMessage(this.resourceBundle, key);
	}

	@Override
	public Message newMessage(String key, Object... params) {
		return this.resourceBundle == null ? new LocalizedMessage(this.baseName, key, params) : new LocalizedMessage(this.resourceBundle, key, params);
	}
}