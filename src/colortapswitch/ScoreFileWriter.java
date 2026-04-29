package colortapswitch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScoreFileWriter {

    public void appendScore(String scoreEntry) {
        try {
            Path scoreFilePath = Paths.get("score.txt");

            if (Files.notExists(scoreFilePath)) {
                Files.createFile(scoreFilePath);
            }

            // Keep one line per finished game so the score history is easy to read.
            Files.writeString(scoreFilePath, scoreEntry, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }
}
