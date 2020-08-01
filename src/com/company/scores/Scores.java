package com.company.scores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scores {

    private static final char ALPHABETS[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static void main(String[] args) throws IOException {

	validateInput(args);
	List<String> names = readFile(args[0]);
	Collections.sort(names);
	Map<String, Integer> scores = findScores(names);
	printResults(scores);

    }

    private static void printResults(Map<String, Integer> scores) {
	scores.forEach((k, v) -> {
	    System.out.println("name: " + k + ", score: " + v);
	});
	long totalScore = scores.values().stream().mapToLong(x -> x).sum();
	System.out.println("Total Score: " + totalScore);
    }

    private static Map<String, Integer> findScores(List<String> names) {
	Map<String, Integer> scores = new LinkedHashMap<>();
	int position = 0;
	for (String name : names) {
	    scores.put(name, calculateScore(name) * ++position);
	}
	return scores;
    }

    private static int calculateScore(String name) {
	int score = 0;
	for (char c : name.toCharArray()) {
	    score = score + findPosition(c);
	}
	return score;
    }

    private static int findPosition(char c) {
	for (int i = 0; i < ALPHABETS.length; i++) {
	    if (c == ALPHABETS[i])
		return i + 1;
	}
	return -1;
    }

    private static List<String> readFile(String path) throws IOException {
	return Files.readAllLines(Paths.get(path)).stream()
	    .map(line -> line.replaceAll("\"", "").split(","))
	    .flatMap(arr -> Arrays.stream(arr)).collect(Collectors.toList());
    }

    private static void validateInput(String[] args) {
	if (null == args || args.length == 0) {
	    System.err.println("Must provide file path...");
	    System.err.println("Exiting...");
	    System.exit(-1);
	}
    }
}
