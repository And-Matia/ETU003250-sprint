package src.controllers;

import src.framework.annotations.MyMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExampleController {

    @MyMapping(value = "/hello", method = "GET")
    public void hello(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<html><body>");
        resp.getWriter().println("<h3>Hello from ExampleController</h3>");
        resp.getWriter().println("</body></html>");
    }

    @MyMapping(value = "/submit", method = "POST")
    public void submit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String name = req.getParameter("name");
        resp.getWriter().println("<html><body>");
        resp.getWriter().println("<h3>Submitted: " + (name == null ? "(empty)" : name) + "</h3>");
        resp.getWriter().println("</body></html>");
    }
}

