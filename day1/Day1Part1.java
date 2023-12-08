import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Day1Part1.java day1part1-input.txt");
            return;
        }

        System.out.printf("Day1 part 1 result: %d", new Day1Part1().calc(100, args[0]));
    }
}
class Day1Part1 {
    public int calc(int dim, String fileName) {
        String[] lines;
        try {
            lines = readFile(fileName);
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

    private String[] readFile(String fileName) throws IOException {
        LinkedList<String> list = new LinkedList<>();
        Scanner reader = new Scanner(new File(fileName));
        assert reader != null;
        while (reader.hasNextLine())
            list.add(reader.nextLine());
        reader.close();
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
