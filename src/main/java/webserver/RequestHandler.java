package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontController frontController;

    public RequestHandler(Socket connectionSocket, FrontController frontController) {
        this.connection = connectionSocket;
        this.frontController = frontController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = new HttpResponse();

            DataOutputStream dos = new DataOutputStream(out);

            frontController.service(httpRequest, httpResponse, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
