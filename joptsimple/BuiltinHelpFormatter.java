package joptsimple;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import joptsimple.internal.Classes;
import joptsimple.internal.Messages;
import joptsimple.internal.Rows;
import joptsimple.internal.Strings;

public class BuiltinHelpFormatter implements HelpFormatter {
	private final Rows nonOptionRows;
	private final Rows optionRows;

	BuiltinHelpFormatter() {
		this(80, 2);
	}

	public BuiltinHelpFormatter(int desiredOverallWidth, int desiredColumnSeparatorWidth) {
		this.nonOptionRows = new Rows(desiredOverallWidth * 2, 0);
		this.optionRows = new Rows(desiredOverallWidth, desiredColumnSeparatorWidth);
	}

	@Override
	public String format(Map<String, ? extends OptionDescriptor> options) {
		this.optionRows.reset();
		this.nonOptionRows.reset();
		Comparator<OptionDescriptor> comparator = new Comparator<OptionDescriptor>() {
			public int compare(OptionDescriptor first, OptionDescriptor second) {
				return ((String)first.options().iterator().next()).compareTo((String)second.options().iterator().next());
			}
		};
		Set<OptionDescriptor> sorted = new TreeSet(comparator);
		sorted.addAll(options.values());
		this.addRows(sorted);
		return this.formattedHelpOutput();
	}

	protected void addOptionRow(String single) {
		this.addOptionRow(single, "");
	}

	protected void addOptionRow(String left, String right) {
		this.optionRows.add(left, right);
	}

	protected void addNonOptionRow(String single) {
		this.nonOptionRows.add(single, "");
	}

	protected void fitRowsToWidth() {
		this.nonOptionRows.fitToWidth();
		this.optionRows.fitToWidth();
	}

	protected String nonOptionOutput() {
		return this.nonOptionRows.render();
	}

	protected String optionOutput() {
		return this.optionRows.render();
	}

	protected String formattedHelpOutput() {
		StringBuilder formatted = new StringBuilder();
		String nonOptionDisplay = this.nonOptionOutput();
		if (!Strings.isNullOrEmpty(nonOptionDisplay)) {
			formatted.append(nonOptionDisplay).append(Strings.LINE_SEPARATOR);
		}

		formatted.append(this.optionOutput());
		return formatted.toString();
	}

	protected void addRows(Collection<? extends OptionDescriptor> options) {
		this.addNonOptionsDescription(options);
		if (options.isEmpty()) {
			this.addOptionRow(this.message("no.options.specified"));
		} else {
			this.addHeaders(options);
			this.addOptions(options);
		}

		this.fitRowsToWidth();
	}

	protected void addNonOptionsDescription(Collection<? extends OptionDescriptor> options) {
		OptionDescriptor nonOptions = this.findAndRemoveNonOptionsSpec(options);
		if (this.shouldShowNonOptionArgumentDisplay(nonOptions)) {
			this.addNonOptionRow(this.message("non.option.arguments.header"));
			this.addNonOptionRow(this.createNonOptionArgumentsDisplay(nonOptions));
		}
	}

	protected boolean shouldShowNonOptionArgumentDisplay(OptionDescriptor nonOptionDescriptor) {
		return !Strings.isNullOrEmpty(nonOptionDescriptor.description())
			|| !Strings.isNullOrEmpty(nonOptionDescriptor.argumentTypeIndicator())
			|| !Strings.isNullOrEmpty(nonOptionDescriptor.argumentDescription());
	}

	protected String createNonOptionArgumentsDisplay(OptionDescriptor nonOptionDescriptor) {
		StringBuilder buffer = new StringBuilder();
		this.maybeAppendOptionInfo(buffer, nonOptionDescriptor);
		this.maybeAppendNonOptionsDescription(buffer, nonOptionDescriptor);
		return buffer.toString();
	}

	protected void maybeAppendNonOptionsDescription(StringBuilder buffer, OptionDescriptor nonOptions) {
		buffer.append(buffer.length() > 0 && !Strings.isNullOrEmpty(nonOptions.description()) ? " -- " : "").append(nonOptions.description());
	}

	protected OptionDescriptor findAndRemoveNonOptionsSpec(Collection<? extends OptionDescriptor> options) {
		Iterator<? extends OptionDescriptor> it = options.iterator();

		while (it.hasNext()) {
			OptionDescriptor next = (OptionDescriptor)it.next();
			if (next.representsNonOptions()) {
				it.remove();
				return next;
			}
		}

		throw new AssertionError("no non-options argument spec");
	}

	protected void addHeaders(Collection<? extends OptionDescriptor> options) {
		if (this.hasRequiredOption(options)) {
			this.addOptionRow(this.message("option.header.with.required.indicator"), this.message("description.header"));
			this.addOptionRow(this.message("option.divider.with.required.indicator"), this.message("description.divider"));
		} else {
			this.addOptionRow(this.message("option.header"), this.message("description.header"));
			this.addOptionRow(this.message("option.divider"), this.message("description.divider"));
		}
	}

	protected final boolean hasRequiredOption(Collection<? extends OptionDescriptor> options) {
		for (OptionDescriptor each : options) {
			if (each.isRequired()) {
				return true;
			}
		}

		return false;
	}

	protected void addOptions(Collection<? extends OptionDescriptor> options) {
		for (OptionDescriptor each : options) {
			if (!each.representsNonOptions()) {
				this.addOptionRow(this.createOptionDisplay(each), this.createDescriptionDisplay(each));
			}
		}
	}

	protected String createOptionDisplay(OptionDescriptor descriptor) {
		StringBuilder buffer = new StringBuilder(descriptor.isRequired() ? "* " : "");
		Iterator<String> i = descriptor.options().iterator();

		while (i.hasNext()) {
			String option = (String)i.next();
			buffer.append(this.optionLeader(option));
			buffer.append(option);
			if (i.hasNext()) {
				buffer.append(", ");
			}
		}

		this.maybeAppendOptionInfo(buffer, descriptor);
		return buffer.toString();
	}

	protected String optionLeader(String option) {
		return option.length() > 1 ? "--" : ParserRules.HYPHEN;
	}

	protected void maybeAppendOptionInfo(StringBuilder buffer, OptionDescriptor descriptor) {
		String indicator = this.extractTypeIndicator(descriptor);
		String description = descriptor.argumentDescription();
		if (descriptor.acceptsArguments() || !Strings.isNullOrEmpty(description) || descriptor.representsNonOptions()) {
			this.appendOptionHelp(buffer, indicator, description, descriptor.requiresArgument());
		}
	}

	protected String extractTypeIndicator(OptionDescriptor descriptor) {
		String indicator = descriptor.argumentTypeIndicator();
		return !Strings.isNullOrEmpty(indicator) && !String.class.getName().equals(indicator) ? Classes.shortNameOf(indicator) : "String";
	}

	protected void appendOptionHelp(StringBuilder buffer, String typeIndicator, String description, boolean required) {
		if (required) {
			this.appendTypeIndicator(buffer, typeIndicator, description, '<', '>');
		} else {
			this.appendTypeIndicator(buffer, typeIndicator, description, '[', ']');
		}
	}

	protected void appendTypeIndicator(StringBuilder buffer, String typeIndicator, String description, char start, char end) {
		buffer.append(' ').append(start);
		if (typeIndicator != null) {
			buffer.append(typeIndicator);
		}

		if (!Strings.isNullOrEmpty(description)) {
			if (typeIndicator != null) {
				buffer.append(": ");
			}

			buffer.append(description);
		}

		buffer.append(end);
	}

	protected String createDescriptionDisplay(OptionDescriptor descriptor) {
		List<?> defaultValues = descriptor.defaultValues();
		if (defaultValues.isEmpty()) {
			return descriptor.description();
		} else {
			String defaultValuesDisplay = this.createDefaultValuesDisplay(defaultValues);
			return (descriptor.description() + ' ' + Strings.surround(this.message("default.value.header") + ' ' + defaultValuesDisplay, '(', ')')).trim();
		}
	}

	protected String createDefaultValuesDisplay(List<?> defaultValues) {
		return defaultValues.size() == 1 ? defaultValues.get(0).toString() : defaultValues.toString();
	}

	protected String message(String keySuffix, Object... args) {
		return Messages.message(Locale.getDefault(), "joptsimple.HelpFormatterMessages", BuiltinHelpFormatter.class, keySuffix, args);
	}
}
