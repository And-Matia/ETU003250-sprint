package src;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class FrontServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String uri = req.getRequestURI();

        resp.getWriter().println("<html><body>");
        resp.getWriter().println("<h2>Mini Framework - FrontServlet</h2>");
        resp.getWriter().println("<p>URL demandée : " + uri + "</p>");
        resp.getWriter().println("</body></html>");
    }
    
}
