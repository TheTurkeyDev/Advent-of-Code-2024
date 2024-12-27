package dev.theturkey.aoc24;

import java.util.*;

public class Day21 extends AOCPuzzle {

    private static final char[][] keypad = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'}
    };

    private static final char[][] controller = {
            {' ', '^', 'A'},
            {'<', 'v', '>'}
    };

    public Day21() {
        super("21");
    }

    @Override
    void solve(List<String> input) {
        long answer = 0;
        for (String inp : input) {
            StringBuilder answer1 = new StringBuilder();
            StringBuilder answer2 = new StringBuilder();
            StringBuilder answer3 = new StringBuilder();
            Point[] robots = {
                    new Point(3, 2),
                    new Point(0, 2),
                    new Point(0, 2)
            };
            for (char c : inp.toCharArray()) {
                ReturnInfo afterMove = getMovesForKeyPadBtn(robots[0], c, 2);

            }

            long num = Long.parseLong(inp.substring(0, inp.length() - 1));
            answer += num * answer3.length();
            System.out.println(inp + ": " + num + "*" + answer3.length() + " | " + answer3);
        }
        lap(answer);
    }

    //281260 High
    //v<<A>>^AvA^Av<<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^AA<A>Av<<A>A>^AAAvA<^A>A
    //<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A

    private ReturnInfo getMovesForKeyPadBtn(Point p, char b, int depth) {
        char[][] keys = depth == 3 ? keypad : controller;
        Point dest = new Point(-1, -1);
        for (int row = 0; row < keys.length; row++) {
            for (int col = 0; col < keys[0].length; col++) {
                if (keys[row][col] == b)
                    dest = new Point(row, col);
            }
        }

        int colDiff = p.col() - dest.col();
        int rowDiff = p.row() - dest.row();
        String udBtns = (rowDiff > 0 ? "^" : "v").repeat(Math.abs(rowDiff));
        String lrBtns = (colDiff > 0 ? "<" : ">").repeat(Math.abs(colDiff));

        if (depth == 0)
            return new ReturnInfo(dest, udBtns + lrBtns + "A");

        List<ReturnInfo> shortestReturns = new ArrayList<>();
        for (String s : permutation(udBtns + lrBtns)) {
            for (char c : (s + "A").toCharArray()) {
                ReturnInfo ri = getMovesForKeyPadBtn(p, c, depth - 1);
            }
        }

        shortestReturns.stream().min((ri, ri1) -> ri1.buttonsPressed.length() - ri.buttonsPressed.length()).get();

        return null;
    }

    private Set<String> permutation(String s) {
        if (s.length() == 1) {
            Set<String> res = new HashSet<>();
            res.add(s);
            return res;
        }

        int lastIndex = s.length() - 1;
        String last = s.substring(lastIndex);
        String rest = s.substring(0, lastIndex);
        return merge(permutation(rest), last);
    }

    private Set<String> merge(Set<String> list, String c) {
        Set<String> res = new HashSet<>();
        for (String s : list)
            for (int i = 0; i <= s.length(); ++i)
                res.add(new StringBuffer(s).insert(i, c).toString());
        return res;
    }

    private record ReturnInfo(Point p, String buttonsPressed) {

    }
}