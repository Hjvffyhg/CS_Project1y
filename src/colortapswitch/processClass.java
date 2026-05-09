package colortapswitch;

import java.awt.Color;
import java.util.Random;

public class processClass {

    private final Random ran = new Random();
    private final String[] colors = {"YELLOW", "BLUE", "RED", "GREEN"};
    private final Color[] colorValues = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
    public String user;

    public int CoT() {
        return ran.nextInt(2);
    }

    public int getRandomColorIndex() {
        return ran.nextInt(4);
    }

    public String getTextByIndex(int i) {
        return colors[i];
    }

    public Color getColorByIndex(int i) {
        return colorValues[i];
    }
}
