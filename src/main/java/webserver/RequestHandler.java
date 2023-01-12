package webserver;

import Controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllerMapper;
import view.ViewResolver;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(in);
            HttpResponse httpResponse = new HttpResponse();
            Controller controller = ControllerMapper.getController(httpRequest);

            String viewName = controller.process(httpRequest, httpResponse);
            String viewPath = ViewResolver.process(viewName);

            sendResponse(out, httpResponse, viewPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse, String viewPath) throws IOException {
        httpResponse.setBody(viewPath);

        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.send(dos);
    }
}
