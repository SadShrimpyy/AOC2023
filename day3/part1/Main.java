import java.io.File;
import java.io.FileNotFoundException;

import java.lang.String;
import java.lang.Integer;
import java.lang.Character;

import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Main.java input.txt");
            return;
        }

        int sum = new Day3Part1().calc(args[0]);
        if (sum >= 0) System.out.printf("Total sum of IDs: %d", sum);
    }
}

class Day3Part1 {
    private int totSum = 0;

    public int calc(String fileName) {
        LinkedList<String> lines = null;
        try {
            lines = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }

        for (int r = 0; r < lines.size(); r++) {
            String line = lines.get(r);
            char[] number = new char[lines.size()];
            for (int c = 0; c < line.length(); c++) {
                Character ch = line.charAt(c);
                if (this.isDigit(ch)) {
                    number[c] = ch;
                    if (c == line.length() - 1) foundSymbols(lines, r, c, String.valueOf(number).trim().length(), Integer.parseInt(String.valueOf(number).trim()));
                } else {
                    if (String.valueOf(number).trim().equals("")) continue;
                    foundSymbols(lines, r, c, String.valueOf(number).trim().length(), Integer.parseInt(String.valueOf(number).trim()));
                    number = new char[lines.size()];
                }
            }
        }
        return totSum;
    }

    private void foundSymbols(LinkedList<String> lines, int x1, int y1, int numLen, int num) {
        if (x1 < lines.size() - 1) x1++;
        int x2 = x1 - 1 - 1;
        if (x2 < 0) x2++;
        int y2 = y1 - numLen - 1;
        if (y2 < 0) y2++;

        for (int x = x2; x <= x1; x++) {
            for (int y = y2; y <= y1; y++) {
                Character ch = lines.get(x).charAt(y);
                if (!(this.isDigit(ch)) && !(String.valueOf(ch).equals("."))) {
                    this.totSum += num;
                    return;
                }
            }
        }
    }

    private boolean isDigit(Character ch) {
        return ((int) ch > 47 && (int) ch < 58);
    }

    private LinkedList<String> readFromFile(String fileName) throws FileNotFoundException {
        LinkedList<String> lines = new LinkedList<>();
        Scanner reader = new Scanner(new File(fileName));
        while (reader.hasNextLine())
            lines.add(reader.nextLine());
        reader.close();
        return lines;
    }
}
