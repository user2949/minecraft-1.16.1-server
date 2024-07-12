package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpRequest;
import java.nio.charset.Charset;

public interface HttpDataFactory {
	void setMaxLimit(long long1);

	Attribute createAttribute(HttpRequest httpRequest, String string);

	Attribute createAttribute(HttpRequest httpRequest, String string, long long3);

	Attribute createAttribute(HttpRequest httpRequest, String string2, String string3);

	FileUpload createFileUpload(HttpRequest httpRequest, String string2, String string3, String string4, String string5, Charset charset, long long7);

	void removeHttpDataFromClean(HttpRequest httpRequest, InterfaceHttpData interfaceHttpData);

	void cleanRequestHttpData(HttpRequest httpRequest);

	void cleanAllHttpData();

	@Deprecated
	void cleanRequestHttpDatas(HttpRequest httpRequest);

	@Deprecated
	void cleanAllHttpDatas();
}
