package joptsimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import joptsimple.internal.Reflection;
import joptsimple.internal.Strings;

public abstract class ArgumentAcceptingOptionSpec<V> extends AbstractOptionSpec<V> {
	private static final char NIL_VALUE_SEPARATOR = '\u0000';
	private final boolean argumentRequired;
	private final List<V> defaultValues = new ArrayList();
	private boolean optionRequired;
	private ValueConverter<V> converter;
	private String argumentDescription = "";
	private String valueSeparator = String.valueOf('\u0000');

	ArgumentAcceptingOptionSpec(String option, boolean argumentRequired) {
		super(option);
		this.argumentRequired = argumentRequired;
	}

	ArgumentAcceptingOptionSpec(List<String> options, boolean argumentRequired, String description) {
		super(options, description);
		this.argumentRequired = argumentRequired;
	}

	public final <T> ArgumentAcceptingOptionSpec<T> ofType(Class<T> argumentType) {
		return this.withValuesConvertedBy(Reflection.findConverter((Class<V>)argumentType));
	}

	public final <T> ArgumentAcceptingOptionSpec<T> withValuesConvertedBy(ValueConverter<T> aConverter) {
		if (aConverter == null) {
			throw new NullPointerException("illegal null converter");
		} else {
			this.converter = (ValueConverter<V>)aConverter;
			return (ArgumentAcceptingOptionSpec<T>)this;
		}
	}

	public final ArgumentAcceptingOptionSpec<V> describedAs(String description) {
		this.argumentDescription = description;
		return this;
	}

	public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(char separator) {
		if (separator == 0) {
			throw new IllegalArgumentException("cannot use U+0000 as separator");
		} else {
			this.valueSeparator = String.valueOf(separator);
			return this;
		}
	}

	public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(String separator) {
		if (separator.indexOf(0) != -1) {
			throw new IllegalArgumentException("cannot use U+0000 in separator");
		} else {
			this.valueSeparator = separator;
			return this;
		}
	}

	@SafeVarargs
	public final ArgumentAcceptingOptionSpec<V> defaultsTo(V value, V... values) {
		this.addDefaultValue(value);
		this.defaultsTo(values);
		return this;
	}

	public ArgumentAcceptingOptionSpec<V> defaultsTo(V[] values) {
		for (V each : values) {
			this.addDefaultValue(each);
		}

		return this;
	}

	public ArgumentAcceptingOptionSpec<V> required() {
		this.optionRequired = true;
		return this;
	}

	@Override
	public boolean isRequired() {
		return this.optionRequired;
	}

	private void addDefaultValue(V value) {
		Objects.requireNonNull(value);
		this.defaultValues.add(value);
	}

	@Override
	final void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
		if (Strings.isNullOrEmpty(detectedArgument)) {
			this.detectOptionArgument(parser, arguments, detectedOptions);
		} else {
			this.addArguments(detectedOptions, detectedArgument);
		}
	}

	protected void addArguments(OptionSet detectedOptions, String detectedArgument) {
		StringTokenizer lexer = new StringTokenizer(detectedArgument, this.valueSeparator);
		if (!lexer.hasMoreTokens()) {
			detectedOptions.addWithArgument(this, detectedArgument);
		} else {
			while (lexer.hasMoreTokens()) {
				detectedOptions.addWithArgument(this, lexer.nextToken());
			}
		}
	}

	protected abstract void detectOptionArgument(OptionParser optionParser, ArgumentList argumentList, OptionSet optionSet);

	@Override
	protected final V convert(String argument) {
		return this.convertWith(this.converter, argument);
	}

	protected boolean canConvertArgument(String argument) {
		StringTokenizer lexer = new StringTokenizer(argument, this.valueSeparator);

		try {
			while (lexer.hasMoreTokens()) {
				this.convert(lexer.nextToken());
			}

			return true;
		} catch (OptionException var4) {
			return false;
		}
	}

	protected boolean isArgumentOfNumberType() {
		return this.converter != null && Number.class.isAssignableFrom(this.converter.valueType());
	}

	@Override
	public boolean acceptsArguments() {
		return true;
	}

	@Override
	public boolean requiresArgument() {
		return this.argumentRequired;
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
	public List<V> defaultValues() {
		return Collections.unmodifiableList(this.defaultValues);
	}

	@Override
	public boolean equals(Object that) {
		if (!super.equals(that)) {
			return false;
		} else {
			ArgumentAcceptingOptionSpec<?> other = (ArgumentAcceptingOptionSpec<?>)that;
			return this.requiresArgument() == other.requiresArgument();
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ (this.argumentRequired ? 0 : 1);
	}
}
