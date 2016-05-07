import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
/**
 *
 * @author Pietro Venturini
 */
public class Markov {
    static final int MAXGEN = 10000; // maximum words generated
    static final String filePath = "text-files/Alice.txt";
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(1221), 0);
        server.createContext("/api/test", new MarkovHandler());
        server.createContext("/api/hello", new MarkovHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Running...");
    }

    static class MarkovHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Chain chain = new Chain();
            int nwords = MAXGEN;
            chain.build(filePath);
            chain.generate(nwords); 
        
            String response = "Hello World!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}