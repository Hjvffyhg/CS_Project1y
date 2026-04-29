package colortapswitch;

import java.awt.Color;
import java.util.Random;

public class ColorRoundGenerator {

    public static final int TEXT_MODE = 0;
    public static final int COLOR_MODE = 1;

    private final Random random = new Random();
    private final String[] colorNames = {"YELLOW", "BLUE", "RED", "GREEN"};
    private final Color[] colorValues = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};

    public int chooseColorOrTextMode() {
        return random.nextInt(2);
    }

    public int getRandomColorIndex() {
        return random.nextInt(colorNames.length);
    }

    public String getColorNameByIndex(int index) {
        return colorNames[index];
    }

    public Color getColorValueByIndex(int index) {
        return colorValues[index];
    }
}
