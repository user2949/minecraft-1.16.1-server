public interface deq {
	deq a = (boolean1, boolean2) -> false;
	deq b = (boolean1, boolean2) -> !boolean1 && !boolean2;
	deq c = (boolean1, boolean2) -> boolean2 && !boolean1;
	deq d = (boolean1, boolean2) -> !boolean1;
	deq e = (boolean1, boolean2) -> boolean1 && !boolean2;
	deq f = (boolean1, boolean2) -> !boolean2;
	deq g = (boolean1, boolean2) -> boolean1 != boolean2;
	deq h = (boolean1, boolean2) -> !boolean1 || !boolean2;
	deq i = (boolean1, boolean2) -> boolean1 && boolean2;
	deq j = (boolean1, boolean2) -> boolean1 == boolean2;
	deq k = (boolean1, boolean2) -> boolean2;
	deq l = (boolean1, boolean2) -> !boolean1 || boolean2;
	deq m = (boolean1, boolean2) -> boolean1;
	deq n = (boolean1, boolean2) -> boolean1 || !boolean2;
	deq o = (boolean1, boolean2) -> boolean1 || boolean2;
	deq p = (boolean1, boolean2) -> true;

	boolean apply(boolean boolean1, boolean boolean2);
}
