package br.com.bank.core.api.routers;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class RouterUtils {

    public static String generateUrl(String url) {
        return "/" + url;
    }

    public static String getProtocolIdFromRequest(ServerRequest request) {
        var headersValues = request.headers().header("X-Protocol");
        return headersValues.size() > 0 && headersValues.get(0) != null ? headersValues.get(0) : UUID.randomUUID().toString();
    }

    public static HashMap<String, Object> getLogObjectForRequest(ServerRequest request) {
        var hashMap = new HashMap<String, Object>();
        hashMap.put("headers", request.headers().asHttpHeaders().toSingleValueMap());
        hashMap.put("path", request.path());
        hashMap.put("method", request.methodName());
        hashMap.put("queryParams", request.queryParams());
        hashMap.put("uri", request.uri().toString());
        hashMap.put("cookies", request.cookies());

        return hashMap;
    }

    public static HashMap<String, Object> getLogObjectForResponse(long requestStartTime) {
        long nowTime = Instant.now().toEpochMilli();
        var hashMap = new HashMap<String, Object>();
        hashMap.put("elapsedTime", nowTime - requestStartTime + "ms");
        return hashMap;
    }
}
