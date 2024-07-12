package org.apache.commons.lang3.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class DiffBuilder implements Builder<DiffResult> {
	private final List<Diff<?>> diffs;
	private final boolean objectsTriviallyEqual;
	private final Object left;
	private final Object right;
	private final ToStringStyle style;

	public DiffBuilder(Object lhs, Object rhs, ToStringStyle style, boolean testTriviallyEqual) {
		if (lhs == null) {
			throw new IllegalArgumentException("lhs cannot be null");
		} else if (rhs == null) {
			throw new IllegalArgumentException("rhs cannot be null");
		} else {
			this.diffs = new ArrayList();
			this.left = lhs;
			this.right = rhs;
			this.style = style;
			this.objectsTriviallyEqual = testTriviallyEqual && (lhs == rhs || lhs.equals(rhs));
		}
	}

	public DiffBuilder(Object lhs, Object rhs, ToStringStyle style) {
		this(lhs, rhs, style, true);
	}

	public DiffBuilder append(String fieldName, boolean lhs, boolean rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Boolean>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Boolean getLeft() {
						return lhs;
					}

					public Boolean getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, boolean[] lhs, boolean[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Boolean[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Boolean[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Boolean[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, byte lhs, byte rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Byte>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Byte getLeft() {
						return lhs;
					}

					public Byte getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, byte[] lhs, byte[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Byte[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Byte[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Byte[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, char lhs, char rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Character>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Character getLeft() {
						return lhs;
					}

					public Character getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, char[] lhs, char[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Character[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Character[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Character[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, double lhs, double rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (Double.doubleToLongBits(lhs) != Double.doubleToLongBits(rhs)) {
				this.diffs.add(new Diff<Double>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Double getLeft() {
						return lhs;
					}

					public Double getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, double[] lhs, double[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Double[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Double[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Double[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, float lhs, float rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (Float.floatToIntBits(lhs) != Float.floatToIntBits(rhs)) {
				this.diffs.add(new Diff<Float>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Float getLeft() {
						return lhs;
					}

					public Float getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, float[] lhs, float[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Float[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Float[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Float[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, int lhs, int rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Integer>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Integer getLeft() {
						return lhs;
					}

					public Integer getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, int[] lhs, int[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Integer[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Integer[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Integer[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, long lhs, long rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Long>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Long getLeft() {
						return lhs;
					}

					public Long getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, long[] lhs, long[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Long[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Long[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Long[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, short lhs, short rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (lhs != rhs) {
				this.diffs.add(new Diff<Short>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Short getLeft() {
						return lhs;
					}

					public Short getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, short[] lhs, short[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Short[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Short[] getLeft() {
						return ArrayUtils.toObject(lhs);
					}

					public Short[] getRight() {
						return ArrayUtils.toObject(rhs);
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, Object lhs, Object rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else if (lhs == rhs) {
			return this;
		} else {
			Object objectToTest;
			if (lhs != null) {
				objectToTest = lhs;
			} else {
				objectToTest = rhs;
			}

			if (objectToTest.getClass().isArray()) {
				if (objectToTest instanceof boolean[]) {
					return this.append(fieldName, (boolean[])lhs, (boolean[])rhs);
				} else if (objectToTest instanceof byte[]) {
					return this.append(fieldName, (byte[])lhs, (byte[])rhs);
				} else if (objectToTest instanceof char[]) {
					return this.append(fieldName, (char[])lhs, (char[])rhs);
				} else if (objectToTest instanceof double[]) {
					return this.append(fieldName, (double[])lhs, (double[])rhs);
				} else if (objectToTest instanceof float[]) {
					return this.append(fieldName, (float[])lhs, (float[])rhs);
				} else if (objectToTest instanceof int[]) {
					return this.append(fieldName, (int[])lhs, (int[])rhs);
				} else if (objectToTest instanceof long[]) {
					return this.append(fieldName, (long[])lhs, (long[])rhs);
				} else {
					return objectToTest instanceof short[] ? this.append(fieldName, (short[])lhs, (short[])rhs) : this.append(fieldName, (Object[])lhs, (Object[])rhs);
				}
			} else if (lhs != null && lhs.equals(rhs)) {
				return this;
			} else {
				this.diffs.add(new Diff<Object>(fieldName) {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getLeft() {
						return lhs;
					}

					@Override
					public Object getRight() {
						return rhs;
					}
				});
				return this;
			}
		}
	}

	public DiffBuilder append(String fieldName, Object[] lhs, Object[] rhs) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			if (!Arrays.equals(lhs, rhs)) {
				this.diffs.add(new Diff<Object[]>(fieldName) {
					private static final long serialVersionUID = 1L;

					public Object[] getLeft() {
						return lhs;
					}

					public Object[] getRight() {
						return rhs;
					}
				});
			}

			return this;
		}
	}

	public DiffBuilder append(String fieldName, DiffResult diffResult) {
		if (fieldName == null) {
			throw new IllegalArgumentException("Field name cannot be null");
		} else if (diffResult == null) {
			throw new IllegalArgumentException("Diff result cannot be null");
		} else if (this.objectsTriviallyEqual) {
			return this;
		} else {
			for (Diff<?> diff : diffResult.getDiffs()) {
				this.append(fieldName + "." + diff.getFieldName(), diff.getLeft(), diff.getRight());
			}

			return this;
		}
	}

	public DiffResult build() {
		return new DiffResult(this.left, this.right, this.diffs, this.style);
	}
}
