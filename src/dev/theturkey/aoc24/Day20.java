package dev.theturkey.aoc24;

import java.util.ArrayList;
import java.util.List;

public class Day20 extends AOCPuzzle
{
	public Day20()
	{
		super("20");
	}

	private int adjPaths(char[][] map, int row, int col)
	{
		if(map[row][col] != '#')
			return 0;
		return (map[row - 1][col] != '#' ? 1 : 0) +
				(map[row + 1][col] != '#' ? 1 : 0) +
				(map[row][col - 1] != '#' ? 1 : 0) +
				(map[row][col + 1] != '#' ? 1 : 0);
	}

	@Override
	public void solve(List<String> input)
	{
		char[][] map = new char[input.size()][input.get(0).length()];
		Point start = new Point(0, 0);
		for(int row = 0; row < input.size(); row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < rowStr.length(); col++)
			{
				char c = rowStr.charAt(col);
				if(c == 'S')
					start = new Point(row, col);
				map[row][col] = rowStr.charAt(col);
			}
		}

		PossiblePath normal = getTimeToSolve(start, map, Integer.MAX_VALUE);
		if(normal == null)
		{
			System.out.println("Failed to find normal path...");
			return;
		}
		int normalTime = normal.score;

		int validCheatPaths = 0;
		for(int row = 1; row < map.length - 1; row++)
		{
			for(int col = 1; col < map[row].length - 1; col++)
			{
				if(adjPaths(map, row, col) < 2)
					continue;
				map[row][col] = '.';
				PossiblePath cheatPath = getTimeToSolve(start, map, normalTime - 99);
				if(cheatPath != null)
					validCheatPaths++;
				map[row][col] = '#';
			}
		}

		lap(validCheatPaths);
	}

	private PossiblePath getTimeToSolve(Point startPoint, char[][] map, long maxTime)
	{
		List<Point> visited = new ArrayList<>();
		List<PossiblePath> queue = new ArrayList<>();
		queue.add(new PossiblePath(startPoint, 0));
		while(!queue.isEmpty())
		{
			PossiblePath pp = queue.remove(0);
			if(visited.contains(pp.p))
				continue;
			visited.add(pp.p);

			for(Direction d : Direction.values())
			{
				Point offsetPoint = pp.p.directionOffset(d);
				char c = map[offsetPoint.row()][offsetPoint.col()];
				if(c == '#' || pp.score + 1 >= maxTime || visited.contains(offsetPoint))
					continue;

				if(c == 'E')
					return new PossiblePath(offsetPoint, pp.score + 1);
				queue.add(new PossiblePath(offsetPoint, pp.score + 1));
			}
		}

		return null;
	}

	private record PossiblePath(Point p, int score)
	{

	}
}