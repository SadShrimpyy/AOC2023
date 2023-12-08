import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.LinkedList;
import java.lang.Integer;
import java.lang.String;
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Day2Part2.java input-day2part1.txt");
            return;
        }

        System.out.printf("Power sum of power: %d", new Day2Part1().calc(args[0]));
    }
}

public class Day2Part1 {
    private final int redMinimum;
    private final int greenMinimum;
    private final int blueMinimum;

    public Day2Part1() {
        this.redMinimum = 0;
        this.greenMinimum = 0;
        this.blueMinimum = 0;
    }

    public int calc(String fileName) {
        LinkedList<String> lines = null;
        try {
            lines = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }

        int power = 0;
        for (String l : lines) {
            int maxR = 0, maxG = 0, maxB = 0;
            for (String sets : l.split(":")[1].split(";")) {
                for (String cubes : sets.split(",")) {
                    String[] res = cubes.split(" ");
                    switch (res[2].toLowerCase()) {
                        case "red":
                            maxR = Integer.parseInt(res[1]) > maxR ? Integer.parseInt(res[1]) : maxR;
                        break;
                        case "green":
                            maxG = Integer.parseInt(res[1]) > maxG ? Integer.parseInt(res[1]) : maxG;
                        break;
                        case "blue":
                            maxB = Integer.parseInt(res[1]) > maxB ? Integer.parseInt(res[1]) : maxB;
                        break;
                    }
                }
            }
            power += (maxR * maxG * maxB);
        }
        return power;
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