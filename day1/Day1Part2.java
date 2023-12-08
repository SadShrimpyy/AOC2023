import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Day1Part2.java day2part2-input.txt");
            return;
        }

        int num = new Day1Part2().calc(args[0]);
        if (num > -1)
        System.out.printf("Day 1 part 2 result: %d", num);
    }
}

class Day1Part2 {
    public int calc(String fileName) {
        LinkedList<String> input;
        try {
            input = loadFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }
        HashMap<String, Integer> table = loadTable();

        int tot = 0;
        for (String line : input) {
            tot += findFirst(line, table) * 10 + findLast(line, table);
            System.out.printf("%40s - %d - %d\n", line, findFirst(line, table), findLast(line, table));
        }
        return tot;
    }

    private int findFirst(String line, HashMap<String, Integer> table) {
        for (int i = 0; i <= line.length() - 1; i++) {
            if (Character.isDigit(line.charAt(i))) return Character.getNumericValue(line.charAt(i));
            for (String num : table.keySet()) {
                if (i + num.length() > line.length()) continue;
                if (line.substring(i, i + num.length()).equals(num)) return table.get(num);
            }
        }
        return -1;
    }

    private int findLast(String line, HashMap<String, Integer> table) {
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) return Character.getNumericValue(line.charAt(i));
            for (String num : table.keySet()) {
                if (i + num.length() > line.length()) continue;
                if (line.substring(i, i + num.length()).equals(num)) return table.get(num);
            }
        }
        return -1;
    }

    private LinkedList<String> loadFromFile(String fileName) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(fileName));
        LinkedList<String> in = new LinkedList<>();
        while (reader.hasNextLine())
            in.add(reader.nextLine());
        reader.close();
        return in;
    }

    private HashMap<String, Integer> loadTable() {
        HashMap<String, Integer> table = new HashMap<>();
//        table.put("zero",  0);
        table.put("one",   1);
        table.put("two",   2);
        table.put("three", 3);
        table.put("four",  4);
        table.put("five",  5);
        table.put("six",   6);
        table.put("seven", 7);
        table.put("eight", 8);
        table.put("nine",  9);
        table.put("ten",  10);
        return table;
    }
}
