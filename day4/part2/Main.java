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

        int sum = new Magic().calc(args[0]);
        if (sum >= 0) System.out.printf("Total scratchcards: %d", sum);
    }
}

class Magic {
    private int totSum = 0;

    public int calc(String fileName) {
        LinkedList<String> lines = null;
        try {
            lines = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }

        int sz = lines.size();
        int[] cards = new int[sz];
        for (int r = 0; r < sz; r++) {
            String[] game = lines.get(r).split(":")[1].split("\\|");
            int wn = analyzeGame(game[0].split(" "), game[1].split(" "));
            cards = populateBehind(cards, r + 1, wn);
            for (int j = r; j < cards[r] + r; j++)
                cards = populateBehind(cards, r + 1, wn);
        }

        for (int i = 0; i < sz; i++)
            this.totSum += cards[i] + 1;
        return this.totSum;
    }

    private int[] populateBehind(int[] cards, int r, int wn) {
        for (int c = r; c < wn + r; c++) cards[c]++;
        return cards;
    }

    private int analyzeGame(String[] wins, String[] extr) {
        int wn = 0; 
        for (String w : wins)
            for (String e : extr)
                if (w.equals(e) && !e.equals("")) wn++;
        return wn;
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
