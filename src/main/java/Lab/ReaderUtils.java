package Lab;

import java.util.Scanner;

final class ReaderUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private ReaderUtils(){
        throw new UnsupportedOperationException();
    }
    public static String read(){
        return scanner.next();
    }
}