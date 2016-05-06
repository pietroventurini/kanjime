import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class HelloWorld {

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(1221), 0);
    server.createContext("/api/test", new MyHandler());
    server.createContext("/api/hello", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
    System.out.println("Running...");
  }

  static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
          String response = "Hello World!";
          t.sendResponseHeaders(200, response.length());
          OutputStream os = t.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
  }
  
}