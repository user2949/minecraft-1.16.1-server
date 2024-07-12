package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Number> {
	private static final long serialVersionUID = 512176391864L;
	private int value;

	public MutableInt() {
	}

	public MutableInt(int value) {
		this.value = value;
	}

	public MutableInt(Number value) {
		this.value = value.intValue();
	}

	public MutableInt(String value) throws NumberFormatException {
		this.value = Integer.parseInt(value);
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setValue(Number value) {
		this.value = value.intValue();
	}

	public void increment() {
		this.value++;
	}

	public int getAndIncrement() {
		return this.value++;
	}

	public int incrementAndGet() {
		this.value++;
		return this.value;
	}

	public void decrement() {
		this.value--;
	}

	public int getAndDecrement() {
		return this.value--;
	}

	public int decrementAndGet() {
		this.value--;
		return this.value;
	}

	public void add(int operand) {
		this.value += operand;
	}

	public void add(Number operand) {
		this.value = this.value + operand.intValue();
	}

	public void subtract(int operand) {
		this.value -= operand;
	}

	public void subtract(Number operand) {
		this.value = this.value - operand.intValue();
	}

	public int addAndGet(int operand) {
		this.value += operand;
		return this.value;
	}

	public int addAndGet(Number operand) {
		this.value = this.value + operand.intValue();
		return this.value;
	}

	public int getAndAdd(int operand) {
		int last = this.value;
		this.value += operand;
		return last;
	}

	public int getAndAdd(Number operand) {
		int last = this.value;
		this.value = this.value + operand.intValue();
		return last;
	}

	public int intValue() {
		return this.value;
	}

	public long longValue() {
		return (long)this.value;
	}

	public float floatValue() {
		return (float)this.value;
	}

	public double doubleValue() {
		return (double)this.value;
	}

	public Integer toInteger() {
		return this.intValue();
	}

	public boolean equals(Object obj) {
		return obj instanceof MutableInt ? this.value == ((MutableInt)obj).intValue() : false;
	}

	public int hashCode() {
		return this.value;
	}

	public int compareTo(MutableInt other) {
		return NumberUtils.compare(this.value, other.value);
	}

	public String toString() {
		return String.valueOf(this.value);
	}
}
