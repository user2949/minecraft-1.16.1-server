package org.apache.logging.log4j;

import java.io.Serializable;

public interface Marker extends Serializable {
	Marker addParents(Marker... arr);

	boolean equals(Object object);

	String getName();

	Marker[] getParents();

	int hashCode();

	boolean hasParents();

	boolean isInstanceOf(Marker marker);

	boolean isInstanceOf(String string);

	boolean remove(Marker marker);

	Marker setParents(Marker... arr);
}
