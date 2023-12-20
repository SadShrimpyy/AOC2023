import java.io.File;
import java.io.FileNotFoundException;

import java.lang.String;
import java.lang.Integer;
import java.lang.Character;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Main.java input.txt");
            return;
        }

        int sum = new Day3Part2().calc(args[0]);
        if (sum >= 0) System.out.printf("Total sum of all of the gear ratios: %d", sum);
    }
}

class Day3Part2 {
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
            for (int c = 0; c < line.length(); c++) {
                Character ch = line.charAt(c);
                if (String.valueOf(ch).equals("*"))
                   foundNumbers(lines, r, c);
            }
        }
        return totSum;
    }

    private void foundNumbers(LinkedList<String> lines, int x1, int y1) {
        y1++;
        if (x1 < lines.size() - 1) x1++;
        int x2 = x1 - 1 - 1;
        if (x2 < 0) x2++;
        int y2 = y1 - 1 - 1;
        if (y2 < 0) y2++;

        HashSet<Integer> pn = new HashSet<>();
        for (int x = x2; x <= x1; x++) {
            String line = lines.get(x);
            for (int y = y2; y <= y1; y++) {
                Character ch = line.charAt(y);
                if (this.isDigit(ch)) pn.add(this.getNumber(line, y)); 
            }
        }

        if (pn.size() == 2) {
            Integer[] nums = pn.toArray(new Integer[2]);
            this.totSum += (nums[0] * nums[1]);
        }
    }

    private int getNumber(String line, int y) {
        while ( (y > 0) && (this.isDigit(line.charAt(y - 1))) ) y--;
        int pn = 0;
        do { 
            pn = (pn * 10) + Character.getNumericValue(line.charAt(y));
            y++;
        } while ( (y < line.length()) && (this.isDigit(line.charAt(y))) ); 
           
        return pn;
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
