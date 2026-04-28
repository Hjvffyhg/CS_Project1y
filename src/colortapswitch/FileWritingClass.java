package colortapswitch;

import java.io.IOException;
import java.nio.file.*;

public class FileWritingClass {

    public void fileWriteIt(String s) {
        try {
            // 1. Access the path of the score file
            Path path = Paths.get("score.txt");

            // 2. Create the file if it doesn't exist
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            // 3. Append the new score entry to the file
            Files.writeString(path, s, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }
}