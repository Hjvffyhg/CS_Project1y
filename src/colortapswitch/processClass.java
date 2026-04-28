/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package colortapswitch;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author tino
 */
public class processClass {

    private Random r = new Random();
    private String[] colors = {"YELLOW", "BLUE", "RED", "GREEN"};
    private Color[] colorValues = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
    public String user;

    // 0 = TEXT mode, 1 = COLOR mode
    public int CoT() {
        return r.nextInt(2);
    }

    public int getRandomColorIndex() {
        return r.nextInt(4);
    }

    public String getTextByIndex(int i) {
        return colors[i];
    }

    public Color getColorByIndex(int i) {
        return colorValues[i];
    }

    public void getname(String name) {
        this.user = name;
    }
}
