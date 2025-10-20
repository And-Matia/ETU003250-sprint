package src.framework;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Router {
    public static class Handler {
        public final Object instance;
        public final Method method;
        public Handler(Object instance, Method method) {
            this.instance = instance;
            this.method = method;
        }
    }

    private final Map<String, Handler> routes = new ConcurrentHashMap<>();

    private String key(String httpMethod, String path) {
        return httpMethod.toUpperCase() + ":" + path;
    }

    public void register(String httpMethod, String path, Object instance, Method method) {
        routes.put(key(httpMethod, path), new Handler(instance, method));
    }

    public Handler findHandler(String httpMethod, String path) {
        return routes.get(key(httpMethod, path));
    }
}
