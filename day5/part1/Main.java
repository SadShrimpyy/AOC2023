import java.io.File;
import java.io.FileNotFoundException;

import java.lang.String;
import java.lang.Long;
import java.lang.Character;
import java.lang.StringBuilder;

import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.printf("ERROR: You must provide the input file name!\n");
            System.out.printf("USAGE: java Main.java input.txt");
            return;
        }

        long loc = new Magic().calc(args[0]);
        if (loc >= 0) System.out.printf("Lowest location number: %d", loc);
    }
}
    
class Magic {
    public long calc(String fileName) {
        LinkedList<String> lines = null;
        try {
            lines = readFromFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: Could not open the file %s: %s\n", fileName, e.getMessage());
            return -1;
        }

        long[] seeds = getSeeds(lines.get(0).split(":")[1].split(" ")); 
        long[] prvs  = getSeeds(lines.get(0).split(":")[1].split(" ")); 
        LinkedList<String> maps = getMaps(lines);

        for (int i = 0; i < maps.size(); i++) {
            int dst = 0, src = 1, rng = 2;
            long[][] map = getMap(maps.get(i).split("\n"));
            boolean[] founded = new boolean[seeds.length];
            for (int m = 0; m < map.length; m++) {
                for (int s = 0; s < seeds.length; s++) {
                    if (founded[s]) continue;
                    if ((prvs[s] >= map[m][src]) && (prvs[s] < map[m][src] + map[m][rng])) { // check if ranged
                        long offset = prvs[s] - map[m][src];
                        prvs[s] = map[m][dst] + offset;
                        founded[s] = true;
                    }
                }
            }
        }

        long min = Long.MAX_VALUE;
        for (int s = 0; s < seeds.length; s++)
            min = min > prvs[s] ? prvs[s] : min;
        return min;
    }

    private long[][] getMap(String[] src) {
        long[][] dst = new long[src.length - 1][3];
        for (int i = 1; i < src.length; i++) { // shift by one - skip the header
            String[] spl = src[i].split(" ");
            dst[i - 1][0] = Long.parseLong(spl[0]); // dst
            dst[i - 1][1] = Long.parseLong(spl[1]); // src
            dst[i - 1][2] = Long.parseLong(spl[2]); // rng 
        }
        return dst;
    }

    private LinkedList<String> getMaps(LinkedList<String> src) {
        LinkedList<String> dst = new LinkedList<>();
        StringBuilder bld = new StringBuilder(200);
        for (int i = 2; i < src.size(); i++) { // skip the seeds
            if (src.get(i).equals("")) {
                dst.add(bld.toString());
                bld.delete(0, bld.length());
            } else
                bld.append(src.get(i)).append("\n");
        }
        dst.add(bld.toString());
        return dst;
    }

    private long[] getSeeds(String[] src) { 
        long[] seeds = new long[src.length - 1];
        int i = 0;
        for (String s : src)
            if (!s.trim().equals("")) {
                seeds[i] = Long.parseLong(s.trim());
                i++;
            }
        return seeds;
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
