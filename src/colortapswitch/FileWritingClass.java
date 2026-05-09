package colortapswitch;

import java.io.IOException;
import java.nio.file.*;

public class FileWritingClass { 

    public void saveUserScore(PlayerInfo playerInfo) {
        if (playerInfo == null) {
            System.err.println("saveUserScore: userInfo is null");
            return;
        }

        String name = playerInfo.getPlayerName();
        if (name == null || name.trim().isEmpty()) {
            name = "Anonymous";
        }

        int score = playerInfo.getPlayerScore();
        String line = name + " - " + score + System.lineSeparator();

        try {
            Path path = Paths.get("score.txt");
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.writeString(path, line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }
}