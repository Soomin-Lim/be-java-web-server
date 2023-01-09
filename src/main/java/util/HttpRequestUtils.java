package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String line) {
        String[] tokens = line.split(" ");
        String url = tokens[1];
        logger.debug("URL : {}", url);

        return url;
    }

    public static Map<String, String> parseQueryString(String query) {
        Map<String, String> result = new HashMap<>();

        for (String parameter : query.split("&")) {
            String[] info = parameter.split("=");
            result.put(info[0], info[1]);
        }

        return result;
    }
}