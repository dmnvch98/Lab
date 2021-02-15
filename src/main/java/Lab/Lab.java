package Lab;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Lab {
    public static void main(String[] args) {
        new Lab().execute();
    }

    public void execute(){
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(readURL()).openConnection();

            setRequestParams(connection);

            PrintUtils.printRequestHeaders(connection);

            connection.connect();

            PrintUtils.printResponseHeaders(connection);

            printResponseBody(connection);

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

    private void setRequestParams(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(readMethod());
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
                System.out.println("Введите метод: ");
                String parsedMethod = ReaderUtils.read();
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
            System.out.println("Введите url: ");
            String parsedURL = ReaderUtils.read();
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








