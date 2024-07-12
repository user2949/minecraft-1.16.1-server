package io.netty.util.internal;

import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.Recycler.Handle;
import io.netty.util.concurrent.Promise;

public final class PendingWrite {
	private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
		protected PendingWrite newObject(Handle<PendingWrite> handle) {
			return new PendingWrite(handle);
		}
	};
	private final Handle<PendingWrite> handle;
	private Object msg;
	private Promise<Void> promise;

	public static PendingWrite newInstance(Object msg, Promise<Void> promise) {
		PendingWrite pending = RECYCLER.get();
		pending.msg = msg;
		pending.promise = promise;
		return pending;
	}

	private PendingWrite(Handle<PendingWrite> handle) {
		this.handle = handle;
	}

	public boolean recycle() {
		this.msg = null;
		this.promise = null;
		this.handle.recycle(this);
		return true;
	}

	public boolean failAndRecycle(Throwable cause) {
		ReferenceCountUtil.release(this.msg);
		if (this.promise != null) {
			this.promise.setFailure(cause);
		}

		return this.recycle();
	}

	public boolean successAndRecycle() {
		if (this.promise != null) {
			this.promise.setSuccess(null);
		}

		return this.recycle();
	}

	public Object msg() {
		return this.msg;
	}

	public Promise<Void> promise() {
		return this.promise;
	}

	public Promise<Void> recycleAndGet() {
		Promise<Void> promise = this.promise;
		this.recycle();
		return promise;
	}
}
