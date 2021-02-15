package Lab;

import java.util.Scanner;

final class ReaderUtils {
    private ReaderUtils(){
        throw new UnsupportedOperationException();
    }

    public static String read(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}