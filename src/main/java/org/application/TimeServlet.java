package org.application;

import org.application.engine.Engine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private Engine engine;

    @Override
    public void init() throws ServletException {
        engine = Engine.initialize();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timezone = req.getParameter("timezone");

        if(timezone != null) {
            if(timezone.contains("UTC "))
                timezone = timezone.replace(" ", "+");
            resp.addCookie(new Cookie("timezone", timezone));
        }
        else
            timezone = Arrays.stream(req.getCookies()).filter(x -> x.getName().equals("timezone")).findFirst().get().getValue();

        if(timezone == null)
            timezone = "UTC";

        resp.setContentType("text/html");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ZonedDateTime time = Instant.now().atZone(ZoneId.of(timezone));
        String fullTime = time.format(formatter) + " " + timezone;

        Context context = new Context(req.getLocale(), Map.of("time", fullTime));

        engine.getTemplateEngine().process("time", context, resp.getWriter());
        resp.getWriter().close();
    }
}
