package Lab;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Lab {

    public static void main(String[] args) {
        HttpURLConnection connection = null;
        try {
            Lab lab = new Lab();
            connection = (HttpURLConnection) new URL(lab.readURL()).openConnection();

            lab.setRequestParams(connection, lab);

            PrintUtils.printRequestHeaders(connection);

            connection.connect();

            PrintUtils.printResponseHeaders(connection);

            lab.printResponseBody(connection);

            connection.connect();
        } catch (IOException e) {
            System.out.print("Произошла ошибка во время подключения: ");
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    private void setRequestParams(HttpURLConnection connection, Lab lab) throws ProtocolException {
        connection.setRequestMethod(lab.readMethod());
        connection.setUseCaches(false);
        connection.setConnectTimeout(250);
        connection.setReadTimeout(250);
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Language", "en_US,en,q=0.5");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
        connection.setRequestProperty("Content-Type", "text/html; charset=utf-8");
    }

    private String readMethod(){
        boolean valid = false;
        String method = "";
        do {
            try {
                String parsedMethod = ReaderUtils.readMethod();
                Method.valueOf(parsedMethod);
                valid = true;
                method = parsedMethod;

            } catch (IllegalArgumentException e){
                System.out.println("Введен некорректный метод");
            }
        } while (!valid);
        return method;
    }

    private String readURL(){
        UrlValidator urlValidator = new UrlValidator();
        boolean valid = false;
        String URL = "";
        do {
            String parsedURL = ReaderUtils.readURL();
            if (urlValidator.isValid(parsedURL)){
                valid = true;
                URL = parsedURL;
            } else {
                System.out.println("Введен недействительный URL...");
            }
        } while (!valid);
        return URL;
    }

    private void printResponseBody(HttpURLConnection connection) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
            System.out.println(stringBuilder);
        }
    }


}

final class ReaderUtils {
    private ReaderUtils(){
        throw new UnsupportedOperationException();
    }

    public static String readMethod(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите метод: ");
        return scanner.next();
    }

    public static String readURL(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите url: ");
        return scanner.next();
    }
}


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

enum Method {
    GET, HEAD, POST
}
