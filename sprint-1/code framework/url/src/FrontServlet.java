package src;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URL;
import java.lang.reflect.Method;
import src.framework.Router;
import src.framework.annotations.MyMapping;

public class FrontServlet extends HttpServlet {
    private Router router;

    @Override
    public void init() throws ServletException {
        super.init();
        router = new Router();
        // Enregistrer manuellement les controllers connus (ex: controllers.ExampleController)
        try {
            Class<?> ctrlClass = Class.forName("controllers.ExampleController");
            Object instance = ctrlClass.getDeclaredConstructor().newInstance();
            for (Method m : ctrlClass.getMethods()) {
                if (m.isAnnotationPresent(MyMapping.class)) {
                    MyMapping ann = m.getAnnotation(MyMapping.class);
                    String p = ann.value();
                    String http = ann.method().toUpperCase();
                    router.register(http, p, instance, m);
                }
            }
        } catch (ClassNotFoundException cnf) {
            // pas d'exemple de controller présent, on continue
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.substring(context.length());

        // Vérifier si une méthode contrôleur correspond
        if (router != null) {
            Router.Handler handler = router.findHandler(req.getMethod(), path);
            if (handler != null) {
                try {
                    handler.method.invoke(handler.instance, req, resp);
                    return;
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }
        }

        // Vérifier si la ressource existe dans le contexte web (fallback statique)
        URL resource;
        try {
            resource = getServletContext().getResource(path);
        } catch (Exception e) {
            resource = null;
        }

        if (resource != null) {
            // La ressource existe : forward vers elle
            RequestDispatcher rd = req.getRequestDispatcher(path);
            rd.forward(req, resp);
            return;
        } else {
            // Pas de ressource : afficher le lien et message "not found"
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Mini Framework - FrontServlet</h2>");
            resp.getWriter().println("<p>URL demandée : <a href=\"" + uri + "\">" + uri + "</a></p>");
            resp.getWriter().println("<p style=\"color:red;\">Ressource non trouvée (URL not found)</p>");
            resp.getWriter().println("</body></html>");
        }
    }

}
