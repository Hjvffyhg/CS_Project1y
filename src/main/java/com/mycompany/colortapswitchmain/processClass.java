/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colortapswitchmain;

import java.awt.Color;
import java.util.Random;

public class processClass {

    private final Random ran = new Random();
    private final String[] colors = {"YELLOW", "BLUE", "RED", "GREEN"};
    private final Color[] colorValues = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};

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