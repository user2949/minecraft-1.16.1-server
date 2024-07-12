package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.TreeTraverser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

@Beta
@AndroidIncompatible
@GwtIncompatible
public final class MoreFiles {
	private MoreFiles() {
	}

	public static ByteSource asByteSource(Path path, OpenOption... options) {
		return new MoreFiles.PathByteSource(path, options);
	}

	public static ByteSink asByteSink(Path path, OpenOption... options) {
		return new MoreFiles.PathByteSink(path, options);
	}

	public static CharSource asCharSource(Path path, Charset charset, OpenOption... options) {
		return asByteSource(path, options).asCharSource(charset);
	}

	public static CharSink asCharSink(Path path, Charset charset, OpenOption... options) {
		return asByteSink(path, options).asCharSink(charset);
	}

	public static ImmutableList<Path> listFiles(Path dir) throws IOException {
		try {
			DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(dir);
			Throwable var2 = null;

			ImmutableList var3;
			try {
				var3 = ImmutableList.copyOf(stream);
			} catch (Throwable var13) {
				var2 = var13;
				throw var13;
			} finally {
				if (stream != null) {
					if (var2 != null) {
						try {
							stream.close();
						} catch (Throwable var12) {
							var2.addSuppressed(var12);
						}
					} else {
						stream.close();
					}
				}
			}

			return var3;
		} catch (DirectoryIteratorException var15) {
			throw var15.getCause();
		}
	}

	public static TreeTraverser<Path> directoryTreeTraverser() {
		return MoreFiles.DirectoryTreeTraverser.INSTANCE;
	}

	public static Predicate<Path> isDirectory(LinkOption... options) {
		final LinkOption[] optionsCopy = (LinkOption[])options.clone();
		return new Predicate<Path>() {
			public boolean apply(Path input) {
				return java.nio.file.Files.isDirectory(input, optionsCopy);
			}

			public String toString() {
				return "MoreFiles.isDirectory(" + Arrays.toString(optionsCopy) + ")";
			}
		};
	}

	public static Predicate<Path> isRegularFile(LinkOption... options) {
		final LinkOption[] optionsCopy = (LinkOption[])options.clone();
		return new Predicate<Path>() {
			public boolean apply(Path input) {
				return java.nio.file.Files.isRegularFile(input, optionsCopy);
			}

			public String toString() {
				return "MoreFiles.isRegularFile(" + Arrays.toString(optionsCopy) + ")";
			}
		};
	}

	public static void touch(Path path) throws IOException {
		Preconditions.checkNotNull(path);

		try {
			java.nio.file.Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
		} catch (NoSuchFileException var4) {
			try {
				java.nio.file.Files.createFile(path);
			} catch (FileAlreadyExistsException var3) {
			}
		}
	}

	public static void createParentDirectories(Path path, FileAttribute<?>... attrs) throws IOException {
		Path normalizedAbsolutePath = path.toAbsolutePath().normalize();
		Path parent = normalizedAbsolutePath.getParent();
		if (parent != null) {
			if (!java.nio.file.Files.isDirectory(parent, new LinkOption[0])) {
				java.nio.file.Files.createDirectories(parent, attrs);
				if (!java.nio.file.Files.isDirectory(parent, new LinkOption[0])) {
					throw new IOException("Unable to create parent directories of " + path);
				}
			}
		}
	}

	public static String getFileExtension(Path path) {
		Path name = path.getFileName();
		if (name == null) {
			return "";
		} else {
			String fileName = name.toString();
			int dotIndex = fileName.lastIndexOf(46);
			return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
		}
	}

	public static String getNameWithoutExtension(Path path) {
		Path name = path.getFileName();
		if (name == null) {
			return "";
		} else {
			String fileName = name.toString();
			int dotIndex = fileName.lastIndexOf(46);
			return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
		}
	}

	public static void deleteRecursively(Path path, RecursiveDeleteOption... options) throws IOException {
		Path parentPath = getParentPath(path);
		if (parentPath == null) {
			throw new FileSystemException(path.toString(), null, "can't delete recursively");
		} else {
			Collection<IOException> exceptions = null;

			try {
				boolean sdsSupported = false;
				DirectoryStream<Path> parent = java.nio.file.Files.newDirectoryStream(parentPath);
				Throwable var6 = null;

				try {
					if (parent instanceof SecureDirectoryStream) {
						sdsSupported = true;
						exceptions = deleteRecursivelySecure((SecureDirectoryStream<Path>)parent, path.getFileName());
					}
				} catch (Throwable var16) {
					var6 = var16;
					throw var16;
				} finally {
					if (parent != null) {
						if (var6 != null) {
							try {
								parent.close();
							} catch (Throwable var15) {
								var6.addSuppressed(var15);
							}
						} else {
							parent.close();
						}
					}
				}

				if (!sdsSupported) {
					checkAllowsInsecure(path, options);
					exceptions = deleteRecursivelyInsecure(path);
				}
			} catch (IOException var18) {
				if (exceptions == null) {
					throw var18;
				}

				exceptions.add(var18);
			}

			if (exceptions != null) {
				throwDeleteFailed(path, exceptions);
			}
		}
	}

	public static void deleteDirectoryContents(Path path, RecursiveDeleteOption... options) throws IOException {
		Collection<IOException> exceptions = null;

		try {
			DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path);
			Throwable var4 = null;

			try {
				if (stream instanceof SecureDirectoryStream) {
					SecureDirectoryStream<Path> sds = (SecureDirectoryStream<Path>)stream;
					exceptions = deleteDirectoryContentsSecure(sds);
				} else {
					checkAllowsInsecure(path, options);
					exceptions = deleteDirectoryContentsInsecure(stream);
				}
			} catch (Throwable var14) {
				var4 = var14;
				throw var14;
			} finally {
				if (stream != null) {
					if (var4 != null) {
						try {
							stream.close();
						} catch (Throwable var13) {
							var4.addSuppressed(var13);
						}
					} else {
						stream.close();
					}
				}
			}
		} catch (IOException var16) {
			if (exceptions == null) {
				throw var16;
			}

			exceptions.add(var16);
		}

		if (exceptions != null) {
			throwDeleteFailed(path, exceptions);
		}
	}

	@Nullable
	private static Collection<IOException> deleteRecursivelySecure(SecureDirectoryStream<Path> dir, Path path) {
		Collection<IOException> exceptions = null;

		try {
			if (isDirectory(dir, path, LinkOption.NOFOLLOW_LINKS)) {
				SecureDirectoryStream<Path> childDir = dir.newDirectoryStream(path, LinkOption.NOFOLLOW_LINKS);
				Throwable var4 = null;

				try {
					exceptions = deleteDirectoryContentsSecure(childDir);
				} catch (Throwable var14) {
					var4 = var14;
					throw var14;
				} finally {
					if (childDir != null) {
						if (var4 != null) {
							try {
								childDir.close();
							} catch (Throwable var13) {
								var4.addSuppressed(var13);
							}
						} else {
							childDir.close();
						}
					}
				}

				if (exceptions == null) {
					dir.deleteDirectory(path);
				}
			} else {
				dir.deleteFile(path);
			}

			return exceptions;
		} catch (IOException var16) {
			return addException(exceptions, var16);
		}
	}

	@Nullable
	private static Collection<IOException> deleteDirectoryContentsSecure(SecureDirectoryStream<Path> dir) {
		Collection<IOException> exceptions = null;

		try {
			for (Path path : dir) {
				exceptions = concat(exceptions, deleteRecursivelySecure(dir, path.getFileName()));
			}

			return exceptions;
		} catch (DirectoryIteratorException var4) {
			return addException(exceptions, var4.getCause());
		}
	}

	@Nullable
	private static Collection<IOException> deleteRecursivelyInsecure(Path path) {
		Collection<IOException> exceptions = null;

		try {
			if (java.nio.file.Files.isDirectory(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
				DirectoryStream<Path> stream = java.nio.file.Files.newDirectoryStream(path);
				Throwable var3 = null;

				try {
					exceptions = deleteDirectoryContentsInsecure(stream);
				} catch (Throwable var13) {
					var3 = var13;
					throw var13;
				} finally {
					if (stream != null) {
						if (var3 != null) {
							try {
								stream.close();
							} catch (Throwable var12) {
								var3.addSuppressed(var12);
							}
						} else {
							stream.close();
						}
					}
				}
			}

			if (exceptions == null) {
				java.nio.file.Files.delete(path);
			}

			return exceptions;
		} catch (IOException var15) {
			return addException(exceptions, var15);
		}
	}

	@Nullable
	private static Collection<IOException> deleteDirectoryContentsInsecure(DirectoryStream<Path> dir) {
		Collection<IOException> exceptions = null;

		try {
			for (Path entry : dir) {
				exceptions = concat(exceptions, deleteRecursivelyInsecure(entry));
			}

			return exceptions;
		} catch (DirectoryIteratorException var4) {
			return addException(exceptions, var4.getCause());
		}
	}

	@Nullable
	private static Path getParentPath(Path path) throws IOException {
		Path parent = path.getParent();
		if (parent != null) {
			return parent;
		} else {
			return path.getNameCount() == 0 ? null : path.getFileSystem().getPath(".");
		}
	}

	private static void checkAllowsInsecure(Path path, RecursiveDeleteOption[] options) throws InsecureRecursiveDeleteException {
		if (!Arrays.asList(options).contains(RecursiveDeleteOption.ALLOW_INSECURE)) {
			throw new InsecureRecursiveDeleteException(path.toString());
		}
	}

	private static boolean isDirectory(SecureDirectoryStream<Path> dir, Path name, LinkOption... options) throws IOException {
		return ((BasicFileAttributeView)dir.getFileAttributeView(name, BasicFileAttributeView.class, options)).readAttributes().isDirectory();
	}

	private static Collection<IOException> addException(@Nullable Collection<IOException> exceptions, IOException e) {
		if (exceptions == null) {
			exceptions = new ArrayList();
		}

		exceptions.add(e);
		return exceptions;
	}

	@Nullable
	private static Collection<IOException> concat(@Nullable Collection<IOException> exceptions, @Nullable Collection<IOException> other) {
		if (exceptions == null) {
			return other;
		} else {
			if (other != null) {
				exceptions.addAll(other);
			}

			return exceptions;
		}
	}

	private static void throwDeleteFailed(Path path, Collection<IOException> exceptions) throws FileSystemException {
		FileSystemException deleteFailed = new FileSystemException(path.toString(), null, "failed to delete one or more files; see suppressed exceptions for details");

		for (IOException e : exceptions) {
			deleteFailed.addSuppressed(e);
		}

		throw deleteFailed;
	}

	private static final class DirectoryTreeTraverser extends TreeTraverser<Path> {
		private static final MoreFiles.DirectoryTreeTraverser INSTANCE = new MoreFiles.DirectoryTreeTraverser();

		public Iterable<Path> children(Path dir) {
			if (java.nio.file.Files.isDirectory(dir, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
				try {
					return MoreFiles.listFiles(dir);
				} catch (IOException var3) {
					throw new DirectoryIteratorException(var3);
				}
			} else {
				return ImmutableList.<Path>of();
			}
		}
	}

	private static final class PathByteSink extends ByteSink {
		private final Path path;
		private final OpenOption[] options;

		private PathByteSink(Path path, OpenOption... options) {
			this.path = Preconditions.checkNotNull(path);
			this.options = (OpenOption[])options.clone();
		}

		@Override
		public OutputStream openStream() throws IOException {
			return java.nio.file.Files.newOutputStream(this.path, this.options);
		}

		public String toString() {
			return "MoreFiles.asByteSink(" + this.path + ", " + Arrays.toString(this.options) + ")";
		}
	}

	private static final class PathByteSource extends ByteSource {
		private static final LinkOption[] FOLLOW_LINKS = new LinkOption[0];
		private final Path path;
		private final OpenOption[] options;
		private final boolean followLinks;

		private PathByteSource(Path path, OpenOption... options) {
			this.path = Preconditions.checkNotNull(path);
			this.options = (OpenOption[])options.clone();
			this.followLinks = followLinks(this.options);
		}

		private static boolean followLinks(OpenOption[] options) {
			for (OpenOption option : options) {
				if (option == LinkOption.NOFOLLOW_LINKS) {
					return false;
				}
			}

			return true;
		}

		@Override
		public InputStream openStream() throws IOException {
			return java.nio.file.Files.newInputStream(this.path, this.options);
		}

		private BasicFileAttributes readAttributes() throws IOException {
			return java.nio.file.Files.readAttributes(
				this.path, BasicFileAttributes.class, this.followLinks ? FOLLOW_LINKS : new LinkOption[]{LinkOption.NOFOLLOW_LINKS}
			);
		}

		@Override
		public Optional<Long> sizeIfKnown() {
			BasicFileAttributes attrs;
			try {
				attrs = this.readAttributes();
			} catch (IOException var3) {
				return Optional.absent();
			}

			return !attrs.isDirectory() && !attrs.isSymbolicLink() ? Optional.of(attrs.size()) : Optional.absent();
		}

		@Override
		public long size() throws IOException {
			BasicFileAttributes attrs = this.readAttributes();
			if (attrs.isDirectory()) {
				throw new IOException("can't read: is a directory");
			} else if (attrs.isSymbolicLink()) {
				throw new IOException("can't read: is a symbolic link");
			} else {
				return attrs.size();
			}
		}

		@Override
		public byte[] read() throws IOException {
			SeekableByteChannel channel = java.nio.file.Files.newByteChannel(this.path, this.options);
			Throwable var2 = null;

			byte[] var3;
			try {
				var3 = Files.readFile(Channels.newInputStream(channel), channel.size());
			} catch (Throwable var12) {
				var2 = var12;
				throw var12;
			} finally {
				if (channel != null) {
					if (var2 != null) {
						try {
							channel.close();
						} catch (Throwable var11) {
							var2.addSuppressed(var11);
						}
					} else {
						channel.close();
					}
				}
			}

			return var3;
		}

		public String toString() {
			return "MoreFiles.asByteSource(" + this.path + ", " + Arrays.toString(this.options) + ")";
		}
	}
}
