package org.apache.commons.io.monitor;

import java.io.File;

public class FileAlterationListenerAdaptor implements FileAlterationListener {
	@Override
	public void onStart(FileAlterationObserver observer) {
	}

	@Override
	public void onDirectoryCreate(File directory) {
	}

	@Override
	public void onDirectoryChange(File directory) {
	}

	@Override
	public void onDirectoryDelete(File directory) {
	}

	@Override
	public void onFileCreate(File file) {
	}

	@Override
	public void onFileChange(File file) {
	}

	@Override
	public void onFileDelete(File file) {
	}

	@Override
	public void onStop(FileAlterationObserver observer) {
	}
}
