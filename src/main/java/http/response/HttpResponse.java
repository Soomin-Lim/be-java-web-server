package http.response;

import http.HttpHeader;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatusLine httpStatusLine;
    private final HttpHeader httpHeader = new HttpHeader();
    private byte[] body;

    public void ok(HttpRequest request) {
        setHttpStatusLine(request, HttpStatusCode.OK);
        addHttpHeader("Content-Type", request.getHttpHeader("Accept"));
    }

    public void redirect(HttpRequest request, String redirectUrl) {
        setHttpStatusLine(request, HttpStatusCode.FOUND);
        addHttpHeader("Location", redirectUrl);
        setEmptyBody();
    }

    public void notFound(HttpRequest request) {
        setHttpStatusLine(request, HttpStatusCode.NOT_FOUND);
        addHttpHeader("Content-Type", request.getHttpHeader("Accept"));
        setBodyMessage("요청하신 URL을 찾을 수 없습니다.");
    }

    public void setHttpStatusLine(HttpRequest request, HttpStatusCode statusCode) {
        this.httpStatusLine = HttpStatusLine.of(request.getHttpVersion(), statusCode);
    }

    public void addHttpHeader(String name, String values) {
        String value = values.split(",")[0];
        if (name.equals("Content-Type") && value.contains("text")) {
            value += "; charset=UTF-8";
        }
        logger.debug("HttpResponse.addHttpHeader(): value = {}", value);

        httpHeader.addHeader(name, value);
    }

    public void setEmptyBody() {
        this.body = new byte[0];
        addHttpHeader("Content-Length", String.valueOf(body.length));
    }

    public void setBodyMessage(String message) {
        this.body = message.getBytes(StandardCharsets.UTF_8);
        addHttpHeader("Content-Length", String.valueOf(body.length));
    }

    public void setBodyMessage(byte[] body) {
        this.body = body;
        addHttpHeader("Content-Length", String.valueOf(body.length));
    }

    public void send(DataOutputStream dos) {
        try {
            logger.debug("httpStatusLine : {}", httpStatusLine);
            logger.debug("httpHeader : {}", httpHeader);

            dos.writeBytes(httpStatusLine + "\r\n");
            dos.writeBytes(httpHeader + "\r\n");

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
