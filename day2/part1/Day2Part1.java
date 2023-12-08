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
            System.out.printf("USAGE: java Day2Part1.java input-day2part1.txt");
            return;
        }

        System.out.printf("Total sum of IDs: %d", new Day2Part1(12, 13, 14).calc(args[0]));
    }
}

public class Day2Part1 {
    private final int redLimit;
    private final int greenLimit;
    private final int blueLimit;

    public Day2Part1(int r, int g, int b) {
        this.redLimit = r;
        this.greenLimit = g;
        this.blueLimit = b;
    }

    public int calc(String fileName) {
        LinkedList<String> lines = null;
        try {
            lines = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }

        int IDs = 0;
        for (String l : lines) {
            boolean status = true; // true = valid && false = invalid
            for (String sets : l.split(":")[1].split(";")) {
                for (String cubes : sets.split(",")) {
                    String[] res = cubes.split(" ");
                    switch (res[2].toLowerCase()) {
                        case "red":
                            if (Integer.parseInt(res[1]) > redLimit) status = false;
                        break;
                        case "green":
                            if (Integer.parseInt(res[1]) > greenLimit) status = false;
                        break;
                        case "blue":
                            if (Integer.parseInt(res[1]) > blueLimit) status = false;
                        break;
                    }
                }
            }
            if (status) IDs += Integer.parseInt(l.split(":")[0].replace("Game ", ""));
        }
        return IDs;
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