package Controller;

import db.UserDatabase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.HomeLoginView;
import webserver.session.Session;
import webserver.session.SessionConst;

import java.io.IOException;

public class DynamicResourceController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(DynamicResourceController.class);
    private static final DynamicResourceController instance = new DynamicResourceController();

    public static DynamicResourceController getInstance() {
        return instance;
    }

    private DynamicResourceController() {
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) {

        if (!request.isLogin()) {
            StaticResourceController.getInstance().process(request, response);
            return;
        }

        String url = request.getUrl();
        if (url.equals("/")) {
            url = "/index.html";
        }

        Session session = request.getSession();
        String userId = session.getAttribute(SessionConst.USER_ID);

        User loginUser = UserDatabase.findUserById(userId);

        try {
            String body = HomeLoginView.render(loginUser.getName(), url);

            response.ok(request);
            response.setBodyMessage(body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            response.notFound(request);
        }
    }
}
