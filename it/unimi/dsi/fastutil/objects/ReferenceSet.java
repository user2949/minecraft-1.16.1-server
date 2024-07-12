package it.unimi.dsi.fastutil.objects;

import java.util.Set;

public interface ReferenceSet<K> extends ReferenceCollection<K>, Set<K> {
	@Override
	ObjectIterator<K> iterator();
}
