package iot.sh.util.http;

import org.apache.http.client.HttpClient;

public abstract class HttpPool {
	public abstract  HttpClient getHttpClient();
}
