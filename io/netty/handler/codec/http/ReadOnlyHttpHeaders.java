package io.netty.handler.codec.http;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.util.AsciiString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

public final class ReadOnlyHttpHeaders extends HttpHeaders {
	private final CharSequence[] nameValuePairs;

	public ReadOnlyHttpHeaders(boolean validateHeaders, CharSequence... nameValuePairs) {
		if ((nameValuePairs.length & 1) != 0) {
			throw newInvalidArraySizeException();
		} else {
			if (validateHeaders) {
				validateHeaders(nameValuePairs);
			}

			this.nameValuePairs = nameValuePairs;
		}
	}

	private static IllegalArgumentException newInvalidArraySizeException() {
		return new IllegalArgumentException("nameValuePairs must be arrays of [name, value] pairs");
	}

	private static void validateHeaders(CharSequence... keyValuePairs) {
		for (int i = 0; i < keyValuePairs.length; i += 2) {
			DefaultHttpHeaders.HttpNameValidator.validateName(keyValuePairs[i]);
		}
	}

	private CharSequence get0(CharSequence name) {
		int nameHash = AsciiString.hashCode(name);

		for (int i = 0; i < this.nameValuePairs.length; i += 2) {
			CharSequence roName = this.nameValuePairs[i];
			if (AsciiString.hashCode(roName) == nameHash && AsciiString.contentEqualsIgnoreCase(roName, name)) {
				return this.nameValuePairs[i + 1];
			}
		}

		return null;
	}

	@Override
	public String get(String name) {
		CharSequence value = this.get0(name);
		return value == null ? null : value.toString();
	}

	@Override
	public Integer getInt(CharSequence name) {
		CharSequence value = this.get0(name);
		return value == null ? null : CharSequenceValueConverter.INSTANCE.convertToInt(value);
	}

	@Override
	public int getInt(CharSequence name, int defaultValue) {
		CharSequence value = this.get0(name);
		return value == null ? defaultValue : CharSequenceValueConverter.INSTANCE.convertToInt(value);
	}

	@Override
	public Short getShort(CharSequence name) {
		CharSequence value = this.get0(name);
		return value == null ? null : CharSequenceValueConverter.INSTANCE.convertToShort(value);
	}

	@Override
	public short getShort(CharSequence name, short defaultValue) {
		CharSequence value = this.get0(name);
		return value == null ? defaultValue : CharSequenceValueConverter.INSTANCE.convertToShort(value);
	}

	@Override
	public Long getTimeMillis(CharSequence name) {
		CharSequence value = this.get0(name);
		return value == null ? null : CharSequenceValueConverter.INSTANCE.convertToTimeMillis(value);
	}

	@Override
	public long getTimeMillis(CharSequence name, long defaultValue) {
		CharSequence value = this.get0(name);
		return value == null ? defaultValue : CharSequenceValueConverter.INSTANCE.convertToTimeMillis(value);
	}

	@Override
	public List<String> getAll(String name) {
		if (this.isEmpty()) {
			return Collections.emptyList();
		} else {
			int nameHash = AsciiString.hashCode(name);
			List<String> values = new ArrayList(4);

			for (int i = 0; i < this.nameValuePairs.length; i += 2) {
				CharSequence roName = this.nameValuePairs[i];
				if (AsciiString.hashCode(roName) == nameHash && AsciiString.contentEqualsIgnoreCase(roName, name)) {
					values.add(this.nameValuePairs[i + 1].toString());
				}
			}

			return values;
		}
	}

	@Override
	public List<Entry<String, String>> entries() {
		if (this.isEmpty()) {
			return Collections.emptyList();
		} else {
			List<Entry<String, String>> entries = new ArrayList(this.size());

			for (int i = 0; i < this.nameValuePairs.length; i += 2) {
				entries.add(new SimpleImmutableEntry(this.nameValuePairs[i].toString(), this.nameValuePairs[i + 1].toString()));
			}

			return entries;
		}
	}

	@Override
	public boolean contains(String name) {
		return this.get0(name) != null;
	}

	@Override
	public boolean contains(String name, String value, boolean ignoreCase) {
		return this.containsValue(name, value, ignoreCase);
	}

	@Override
	public boolean containsValue(CharSequence name, CharSequence value, boolean ignoreCase) {
		if (ignoreCase) {
			for (int i = 0; i < this.nameValuePairs.length; i += 2) {
				if (AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[i], name) && AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[i + 1], value)) {
					return true;
				}
			}
		} else {
			for (int ix = 0; ix < this.nameValuePairs.length; ix += 2) {
				if (AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[ix], name) && AsciiString.contentEquals(this.nameValuePairs[ix + 1], value)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Iterator<String> valueStringIterator(CharSequence name) {
		return new ReadOnlyHttpHeaders.ReadOnlyStringValueIterator(name);
	}

	@Override
	public Iterator<CharSequence> valueCharSequenceIterator(CharSequence name) {
		return new ReadOnlyHttpHeaders.ReadOnlyValueIterator(name);
	}

	@Override
	public Iterator<Entry<String, String>> iterator() {
		return new ReadOnlyHttpHeaders.ReadOnlyStringIterator();
	}

	@Override
	public Iterator<Entry<CharSequence, CharSequence>> iteratorCharSequence() {
		return new ReadOnlyHttpHeaders.ReadOnlyIterator();
	}

	@Override
	public boolean isEmpty() {
		return this.nameValuePairs.length == 0;
	}

	@Override
	public int size() {
		return this.nameValuePairs.length >>> 1;
	}

	@Override
	public Set<String> names() {
		if (this.isEmpty()) {
			return Collections.emptySet();
		} else {
			Set<String> names = new LinkedHashSet(this.size());

			for (int i = 0; i < this.nameValuePairs.length; i += 2) {
				names.add(this.nameValuePairs[i].toString());
			}

			return names;
		}
	}

	@Override
	public HttpHeaders add(String name, Object value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders add(String name, Iterable<?> values) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders addInt(CharSequence name, int value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders addShort(CharSequence name, short value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders set(String name, Object value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders set(String name, Iterable<?> values) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders setInt(CharSequence name, int value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders setShort(CharSequence name, short value) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders remove(String name) {
		throw new UnsupportedOperationException("read only");
	}

	@Override
	public HttpHeaders clear() {
		throw new UnsupportedOperationException("read only");
	}

	private final class ReadOnlyIterator implements Entry<CharSequence, CharSequence>, Iterator<Entry<CharSequence, CharSequence>> {
		private CharSequence key;
		private CharSequence value;
		private int nextNameIndex;

		private ReadOnlyIterator() {
		}

		public boolean hasNext() {
			return this.nextNameIndex != ReadOnlyHttpHeaders.this.nameValuePairs.length;
		}

		public Entry<CharSequence, CharSequence> next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.key = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex];
				this.value = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex + 1];
				this.nextNameIndex += 2;
				return this;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("read only");
		}

		public CharSequence getKey() {
			return this.key;
		}

		public CharSequence getValue() {
			return this.value;
		}

		public CharSequence setValue(CharSequence value) {
			throw new UnsupportedOperationException("read only");
		}

		public String toString() {
			return this.key.toString() + '=' + this.value.toString();
		}
	}

	private final class ReadOnlyStringIterator implements Entry<String, String>, Iterator<Entry<String, String>> {
		private String key;
		private String value;
		private int nextNameIndex;

		private ReadOnlyStringIterator() {
		}

		public boolean hasNext() {
			return this.nextNameIndex != ReadOnlyHttpHeaders.this.nameValuePairs.length;
		}

		public Entry<String, String> next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				this.key = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex].toString();
				this.value = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex + 1].toString();
				this.nextNameIndex += 2;
				return this;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("read only");
		}

		public String getKey() {
			return this.key;
		}

		public String getValue() {
			return this.value;
		}

		public String setValue(String value) {
			throw new UnsupportedOperationException("read only");
		}

		public String toString() {
			return this.key + '=' + this.value;
		}
	}

	private final class ReadOnlyStringValueIterator implements Iterator<String> {
		private final CharSequence name;
		private final int nameHash;
		private int nextNameIndex;

		ReadOnlyStringValueIterator(CharSequence name) {
			this.name = name;
			this.nameHash = AsciiString.hashCode(name);
			this.nextNameIndex = this.findNextValue();
		}

		public boolean hasNext() {
			return this.nextNameIndex != -1;
		}

		public String next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				String value = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex + 1].toString();
				this.nextNameIndex = this.findNextValue();
				return value;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("read only");
		}

		private int findNextValue() {
			for (int i = this.nextNameIndex; i < ReadOnlyHttpHeaders.this.nameValuePairs.length; i += 2) {
				CharSequence roName = ReadOnlyHttpHeaders.this.nameValuePairs[i];
				if (this.nameHash == AsciiString.hashCode(roName) && AsciiString.contentEqualsIgnoreCase(this.name, roName)) {
					return i;
				}
			}

			return -1;
		}
	}

	private final class ReadOnlyValueIterator implements Iterator<CharSequence> {
		private final CharSequence name;
		private final int nameHash;
		private int nextNameIndex;

		ReadOnlyValueIterator(CharSequence name) {
			this.name = name;
			this.nameHash = AsciiString.hashCode(name);
			this.nextNameIndex = this.findNextValue();
		}

		public boolean hasNext() {
			return this.nextNameIndex != -1;
		}

		public CharSequence next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			} else {
				CharSequence value = ReadOnlyHttpHeaders.this.nameValuePairs[this.nextNameIndex + 1];
				this.nextNameIndex = this.findNextValue();
				return value;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("read only");
		}

		private int findNextValue() {
			for (int i = this.nextNameIndex; i < ReadOnlyHttpHeaders.this.nameValuePairs.length; i += 2) {
				CharSequence roName = ReadOnlyHttpHeaders.this.nameValuePairs[i];
				if (this.nameHash == AsciiString.hashCode(roName) && AsciiString.contentEqualsIgnoreCase(this.name, roName)) {
					return i;
				}
			}

			return -1;
		}
	}
}
