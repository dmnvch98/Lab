package Lab;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

final class PrintUtils {
    private PrintUtils(){
        throw new UnsupportedOperationException();
    }

    public static void printRequestHeaders(HttpURLConnection connection){
        System.out.println("---REQUEST---");
        print(connection.getRequestProperties());
    }

    public static void printResponseHeaders(HttpURLConnection connection) throws IOException {
        System.out.println("---RESPONSE---");
        System.out.println("Status code: " + connection.getResponseCode() + " : " + connection.getResponseMessage());
        print(connection.getHeaderFields());
    }

    private static void print(Map<String, List<String>> map) {
        for(String k : map.keySet()) {
            System.out.println( k + " : " + map.get(k).toString());
        }
    }
}