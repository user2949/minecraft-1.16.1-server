package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface FileRegion extends ReferenceCounted {
	long position();

	@Deprecated
	long transfered();

	long transferred();

	long count();

	long transferTo(WritableByteChannel writableByteChannel, long long2) throws IOException;

	FileRegion retain();

	FileRegion retain(int integer);

	FileRegion touch();

	FileRegion touch(Object object);
}
