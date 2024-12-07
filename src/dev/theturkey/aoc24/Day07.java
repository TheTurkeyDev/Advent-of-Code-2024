package dev.theturkey.aoc24;

import java.util.Arrays;
import java.util.List;

public class Day07 extends AOCPuzzle
{
	public Day07()
	{
		super("7");
	}

	public long solvePart(List<String> input, String... operands)
	{
		long partAnswer = 0;
		for(String s : input)
		{
			String[] parts = s.split(":");
			long testVolume = Long.parseLong(parts[0]);
			List<Long> testNums = Arrays.stream(parts[1].trim().split(" ")).map(Long::parseLong).toList();
			int[] operandValues = new int[testNums.size()];
			boolean possible = false;
			for(int i = 0; i < Math.pow(operands.length, testNums.size()); i++)
			{
				long answer = testNums.get(0);
				for(int j = 1; j < testNums.size(); j++)
				{
					long num = testNums.get(j);
					int operand = operandValues[j - 1];
					if(operand == 0)
						answer *= num;
					else if(operand == 1)
						answer += num;
					else
						answer = Long.parseLong(String.valueOf(answer) + num);
				}

				if(answer == testVolume)
				{
					possible = true;
					break;
				}

				for(int j = 0; j < testNums.size() - 1; j++)
				{
					operandValues[j]++;
					if(operandValues[j] == operands.length)
					{
						for(int k = 0; k <= j; k++)
							operandValues[k] = 0;
					}
					else
						break;
				}
			}

			if(possible)
				partAnswer += testVolume;
		}
		return partAnswer;
	}

	@Override
	public void solve(List<String> input)
	{
		lap(solvePart(input, "+", "*"));
		lap(solvePart(input, "+", "*", "||"));
	}
}
