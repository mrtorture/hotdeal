package toyproject.hotdeal.urlencoding;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

public class UrlEncodingTest {

    @Test
    void urlEncoding() {
        String url = "http://localhost:port/path1/path2?param1=true&param2=false";
        System.out.println("url = " + url);

        String encodeResult = UriUtils.encode(url, StandardCharsets.UTF_8);
        System.out.println("encodeResult = " + encodeResult);

        String encodePathResult = UriUtils.encodePath(url, StandardCharsets.UTF_8);
        System.out.println("encodePathResult = " + encodePathResult);

        String encodeQueryResult = UriUtils.encodeQuery(url, StandardCharsets.UTF_8);
        System.out.println("encodeQueryResult = " + encodeQueryResult);

        String encodeQueryParamResult = UriUtils.encodeQueryParam(url, StandardCharsets.UTF_8);
        System.out.println("encodeQueryParamResult = " + encodeQueryParamResult);

    }
}
