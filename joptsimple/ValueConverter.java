package joptsimple;

public interface ValueConverter<V> {
	V convert(String string);

	Class<? extends V> valueType();

	String valuePattern();
}
