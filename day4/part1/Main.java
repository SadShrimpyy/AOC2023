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

        int sum = new Day4Part1().calc(args[0]);
        if (sum >= 0) System.out.printf("Total sum of the points: %d", sum);
    }
}

class Day4Part1 {
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
            String[] game = line.split(":")[1].split("\\|");
            String wins = game[0];
            String extr = game[1];
            analyzeGame(wins.split(" "), extr.split(" "));
        }
        return this.totSum;
    }

    private void analyzeGame(String[] wins, String[] extr) {
        int pts = 0; 
        for (String w : wins) {
            for (String e : extr) {
                if (w.equals(e) && !e.equals("")) {
                    int num = Integer.parseInt(e);
                    pts = pts == 0 ? 1 : pts*2;
                }
            }
        }
        this.totSum += pts;
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
