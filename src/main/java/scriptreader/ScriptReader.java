package scriptreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScriptReader  {
    public void openFile(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        
        File file = new File(classLoader.getResource("scripts/"+ filename+ ".txt").getFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        
        reader.lines().forEach(line -> {
            System.out.println(line);
        });

        System.out.println(file.getPath()); 
    }
}
