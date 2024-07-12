package joptsimple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import joptsimple.internal.Reflection;

public class NonOptionArgumentSpec<V> extends AbstractOptionSpec<V> {
	static final String NAME = "[arguments]";
	private ValueConverter<V> converter;
	private String argumentDescription = "";

	NonOptionArgumentSpec() {
		this("");
	}

	NonOptionArgumentSpec(String description) {
		super(Arrays.asList("[arguments]"), description);
	}

	public <T> NonOptionArgumentSpec<T> ofType(Class<T> argumentType) {
		this.converter = Reflection.findConverter((Class<V>)argumentType);
		return (NonOptionArgumentSpec<T>)this;
	}

	public final <T> NonOptionArgumentSpec<T> withValuesConvertedBy(ValueConverter<T> aConverter) {
		if (aConverter == null) {
			throw new NullPointerException("illegal null converter");
		} else {
			this.converter = (ValueConverter<V>)aConverter;
			return (NonOptionArgumentSpec<T>)this;
		}
	}

	public NonOptionArgumentSpec<V> describedAs(String description) {
		this.argumentDescription = description;
		return this;
	}

	@Override
	protected final V convert(String argument) {
		return this.convertWith(this.converter, argument);
	}

	@Override
	void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
		detectedOptions.addWithArgument(this, detectedArgument);
	}

	@Override
	public List<?> defaultValues() {
		return Collections.emptyList();
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean acceptsArguments() {
		return false;
	}

	@Override
	public boolean requiresArgument() {
		return false;
	}

	@Override
	public String argumentDescription() {
		return this.argumentDescription;
	}

	@Override
	public String argumentTypeIndicator() {
		return this.argumentTypeIndicatorFrom(this.converter);
	}

	@Override
	public boolean representsNonOptions() {
		return true;
	}
}
