package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import javax.annotation.Nullable;

@Beta
public final class Parameter implements AnnotatedElement {
	private final Invokable<?, ?> declaration;
	private final int position;
	private final TypeToken<?> type;
	private final ImmutableList<Annotation> annotations;

	Parameter(Invokable<?, ?> declaration, int position, TypeToken<?> type, Annotation[] annotations) {
		this.declaration = declaration;
		this.position = position;
		this.type = type;
		this.annotations = ImmutableList.copyOf(annotations);
	}

	public TypeToken<?> getType() {
		return this.type;
	}

	public Invokable<?, ?> getDeclaringInvokable() {
		return this.declaration;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
		return this.getAnnotation(annotationType) != null;
	}

	@Nullable
	public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
		Preconditions.checkNotNull(annotationType);

		for (Annotation annotation : this.annotations) {
			if (annotationType.isInstance(annotation)) {
				return (A)annotationType.cast(annotation);
			}
		}

		return null;
	}

	public Annotation[] getAnnotations() {
		return this.getDeclaredAnnotations();
	}

	public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
		return (A[])this.getDeclaredAnnotationsByType(annotationType);
	}

	public Annotation[] getDeclaredAnnotations() {
		return this.annotations.toArray(new Annotation[this.annotations.size()]);
	}

	@Nullable
	public <A extends Annotation> A getDeclaredAnnotation(Class<A> annotationType) {
		Preconditions.checkNotNull(annotationType);
		return FluentIterable.from(this.annotations).<A>filter(annotationType).first().orNull();
	}

	public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> annotationType) {
		return (A[])FluentIterable.from(this.annotations).filter(annotationType).toArray(annotationType);
	}

	public boolean equals(@Nullable Object obj) {
		if (!(obj instanceof Parameter)) {
			return false;
		} else {
			Parameter that = (Parameter)obj;
			return this.position == that.position && this.declaration.equals(that.declaration);
		}
	}

	public int hashCode() {
		return this.position;
	}

	public String toString() {
		return this.type + " arg" + this.position;
	}
}
