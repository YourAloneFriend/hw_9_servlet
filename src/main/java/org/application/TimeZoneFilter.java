package org.application;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

@WebFilter("/time")
public class TimeZoneFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");
        if(timezone == null)
            timezone = "UTC";

        if(timezone.contains("UTC "))
            timezone = timezone.replace(" ", "+");

        try {
            ZoneId.of(timezone);
            chain.doFilter(req, resp);
        } catch (DateTimeException e){
            resp.setStatus(400);
            resp.getWriter().write("<h1>TimeServlet</h1>");
            resp.getWriter().write("<h2>Error: unknown timezone!</h2>");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("<h1>TimeServlet</h1>");
            resp.getWriter().write(e.getMessage());
        }
    }
}
