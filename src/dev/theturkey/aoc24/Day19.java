package dev.theturkey.aoc24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends AOCPuzzle
{
	public Day19()
	{
		super("19");
	}

	private List<String> getTowelComposition(Map<Integer, List<String>> routes, int index, List<String> towelsUsed)
	{
		if(index == routes.size() - 1)
			return new ArrayList<>();

		for(String route : routes.getOrDefault(index, new ArrayList<>()))
		{
			List<String> newPath = new ArrayList<>(towelsUsed);
			newPath.add(route);
			List<String> possiblePath = getTowelComposition(routes, index + route.length(), newPath);
			if(possiblePath != null)
				return possiblePath;
		}
		return null;
	}

	@Override
	public void solve(List<String> input)
	{
		List<String> towels = Arrays.stream(input.get(0).split(", ")).toList();

		int part1 = 0;
		for(String pattern : input.subList(2, input.size()))
		{
			Map<Integer, List<String>> routes = new HashMap<>();
			for(int i = 0; i < pattern.length(); i++)
			{
				String patternSub = pattern.substring(i);
				for(String towel : towels)
					if(patternSub.startsWith(towel))
						routes.computeIfAbsent(i, k -> new ArrayList<>()).add(towel);
			}

			List<String> path = getTowelComposition(routes, 0, new ArrayList<>());
			if(path != null)
				part1++;
		}
		lap(part1);
	}
}