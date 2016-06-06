import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author Pietro Venturini
 */
public class Markov {
    static final int MAXGEN = 10000; // maximum words generated
    static final String FILEPATH = "text-files/file_2.txt";
    static final String TITLESPATH = "text-files/titles.json";
    static final String FILE_PATH = "text-files/";
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(1221), 0);
        server.createContext("/api/titles", new TitlesHandler());
        server.createContext("/api/markov", new MarkovHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Running...");
    }

    static class MarkovHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            
            Chain chain = new Chain();            
            //chain.build(FILEPATH);  //to build a chain from a file 
            chain.build(isr); //to build a chain from an InputStream (http POST requests)
            
            String response = chain.generate(MAXGEN);
            
            t.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class TitlesHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            
            String response = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = t.getRequestURI(); //Returns request's URI (next to '?')
            String query = requestedUri.getRawQuery(); 
            
            if(query != null) {
                parseQuery(query, parameters);
                String id = parameters.get("id").toString();
                if(id.equals("1") || id.equals("2")) {
                       response = new Scanner(new File(FILE_PATH + "file_" + id + ".txt"), "UTF-8").useDelimiter("\\A").next();                                          
                } else {
                    response = "No file found";
                }
                t.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            } else {
                response = new Scanner(new File(TITLESPATH), "UTF-8").useDelimiter("\\A").next();
                t.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            }
            
            t.sendResponseHeaders(200, response.getBytes().length); //Needed length of getBytes because of japanese characters
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

         if (query != null) {
                 String pairs[] = query.split("[&]");
                 for (String pair : pairs) {
                          String param[] = pair.split("[=]");
                          String key = null;
                          String value = null;
                          if (param.length > 0) {
                          key = URLDecoder.decode(param[0], 
                          	System.getProperty("file.encoding"));
                          }

                          if (param.length > 1) {
                                   value = URLDecoder.decode(param[1], 
                                   System.getProperty("file.encoding"));
                          }

                          if (parameters.containsKey(key)) {
                                   Object obj = parameters.get(key);
                                   System.out.println("parameters.containsKey(key)");
                                   if (obj instanceof List<?>) {
                                            List<String> values = (List<String>) obj;
                                            values.add(value);

                                   } else if (obj instanceof String) {
                                            System.out.println("obj instanceof String");
                                            List<String> values = new ArrayList<String>();
                                            values.add((String) obj);
                                            values.add(value);
                                            parameters.put(key, values);
                                   }
                          } else {
                                   parameters.put(key, value);
                          }
                 }
         }
}
}
