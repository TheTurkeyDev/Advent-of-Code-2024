package dev.theturkey.aoc24;

import java.util.*;
import java.util.stream.Stream;

public class Day23 extends AOCPuzzle {

    private static final Map<String, List<String>> paths = new HashMap<>();

    public Day23() {
        super("23");
    }

    @Override
    void solve(List<String> input) {
        for (String s : input) {
            String[] parts = s.split("-");
            paths.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
            paths.computeIfAbsent(parts[1], k -> new ArrayList<>()).add(parts[0]);
        }

        Set<String> possible = new HashSet<>();
        for (String n1 : paths.keySet()) {
            List<String> n1Dests = paths.get(n1);
            for (int i = 0; i < n1Dests.size(); i++) {
                String n2 = n1Dests.get(i);
                boolean startWithT = n1.startsWith("t") || n2.startsWith("t");
                for (int j = i + 1; j < n1Dests.size(); j++) {
                    String n3 = n1Dests.get(j);
                    if ((!startWithT && !n3.startsWith("t")) || !paths.get(n2).contains(n3))
                        continue;

                    String key = String.join(",", Stream.of(n1, n2, n3).sorted().toList());
                    possible.add(key);
                }
            }
        }
        lap(possible.size());

        List<String> visited = new ArrayList<>();
        for (String n1 : paths.keySet()) {
            List<String> nodes = new ArrayList<>(paths.get(n1));
            nodes.add(n1);
        }
    }

    public static boolean listEquals(List<String> list1, List<String> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public List<String> getMaxShared(String node, List<String> nodes, List<String> visited) {
        if (listEquals(nodes, visited))
            return nodes;

        List<String> best = new ArrayList<>();
        for (String s : paths.get(node)) {
            if (visited.contains(s))
                continue;
            List<String> newVisited =
            List<String> union = getMaxShared(s, nodes)
        }
    }
}