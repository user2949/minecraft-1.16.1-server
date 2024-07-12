package org.apache.logging.log4j.core.config.plugins.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PluginCache {
	private final Map<String, Map<String, PluginEntry>> categories = new LinkedHashMap();

	public Map<String, Map<String, PluginEntry>> getAllCategories() {
		return this.categories;
	}

	public Map<String, PluginEntry> getCategory(String category) {
		String key = category.toLowerCase();
		if (!this.categories.containsKey(key)) {
			this.categories.put(key, new LinkedHashMap());
		}

		return (Map<String, PluginEntry>)this.categories.get(key);
	}

	public void writeCache(OutputStream os) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(os));
		Throwable var3 = null;

		try {
			out.writeInt(this.categories.size());

			for (Entry<String, Map<String, PluginEntry>> category : this.categories.entrySet()) {
				out.writeUTF((String)category.getKey());
				Map<String, PluginEntry> m = (Map<String, PluginEntry>)category.getValue();
				out.writeInt(m.size());

				for (Entry<String, PluginEntry> entry : m.entrySet()) {
					PluginEntry plugin = (PluginEntry)entry.getValue();
					out.writeUTF(plugin.getKey());
					out.writeUTF(plugin.getClassName());
					out.writeUTF(plugin.getName());
					out.writeBoolean(plugin.isPrintable());
					out.writeBoolean(plugin.isDefer());
				}
			}
		} catch (Throwable var17) {
			var3 = var17;
			throw var17;
		} finally {
			if (out != null) {
				if (var3 != null) {
					try {
						out.close();
					} catch (Throwable var16) {
						var3.addSuppressed(var16);
					}
				} else {
					out.close();
				}
			}
		}
	}

	public void loadCacheFiles(Enumeration<URL> resources) throws IOException {
		this.categories.clear();

		while (resources.hasMoreElements()) {
			URL url = (URL)resources.nextElement();
			DataInputStream in = new DataInputStream(new BufferedInputStream(url.openStream()));
			Throwable var4 = null;

			try {
				int count = in.readInt();

				for (int i = 0; i < count; i++) {
					String category = in.readUTF();
					Map<String, PluginEntry> m = this.getCategory(category);
					int entries = in.readInt();

					for (int j = 0; j < entries; j++) {
						PluginEntry entry = new PluginEntry();
						entry.setKey(in.readUTF());
						entry.setClassName(in.readUTF());
						entry.setName(in.readUTF());
						entry.setPrintable(in.readBoolean());
						entry.setDefer(in.readBoolean());
						entry.setCategory(category);
						if (!m.containsKey(entry.getKey())) {
							m.put(entry.getKey(), entry);
						}
					}
				}
			} catch (Throwable var19) {
				var4 = var19;
				throw var19;
			} finally {
				if (in != null) {
					if (var4 != null) {
						try {
							in.close();
						} catch (Throwable var18) {
							var4.addSuppressed(var18);
						}
					} else {
						in.close();
					}
				}
			}
		}
	}

	public int size() {
		return this.categories.size();
	}
}
