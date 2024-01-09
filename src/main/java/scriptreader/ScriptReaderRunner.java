package scriptreader;

import java.io.IOException;
import java.util.Scanner;

public class ScriptReaderRunner {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        ScriptReader reader = new ScriptReader();

        System.out.println("provide script name to be opened");
        
        reader.openFile(scanner.nextLine());
    }
}
