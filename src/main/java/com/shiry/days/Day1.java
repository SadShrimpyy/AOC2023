package com.shiry.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Day1 {

    public int calc(int dim) {
        String[] lines;
        try {
            lines = readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int x = 0, y = 0;

        int[][] digits = new int[lines.length][dim];
        for (int[] row : digits)
            Arrays.fill(row, -1);

        for (String line : lines) {
            char[] chars = new char[line.length()];
            line.getChars(0, line.length(), chars, 0);
            for (int i = 0; i < chars.length; i++) {
                if ((int) chars[i] < 48 || (int) chars[i] > 57) continue;
                digits[x][y] = Character.getNumericValue(chars[i]);
                y++;
            }
            x++;
            y = 0;
        }
        return getTotal(digits, -1, 0);
    }

    private String[] readFile() throws IOException {
        LinkedList<String> list = new LinkedList<>();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("day1-input.txt");
        assert stream != null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null)
            list.add(line);
        stream.close();
        return list.toArray(new String[0]);
    }

    private int getTotal(int[][] digits, int x, int tot) {
        x++;
        int calc = tot + digits[x][0] * 10 + getLastPositive(digits, x);
        if (x == digits.length - 1)
            return calc;
        return getTotal(digits, x, calc);
    }

    private int getLastPositive(int[][] digits, int x) {
        for (int i = digits[x].length - 1; i >= 0; i--)
            if (digits[x][i] > -1) return digits[x][i];
        return 10;
    }
}
