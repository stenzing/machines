package machines;

import java.util.*;
import java.util.stream.Collectors;

public class MachineCalculator {


    public static Optional<List<Solution>> getSolutions(Question question) {
        int max = question.getProducerSpeed().stream().mapToInt(Integer::intValue).sum();
        if (question.getTargetAmout() > max)
            return Optional.empty();

        // Generate all combinations
        var possibilities = generateCombinations(new ArrayDeque<>(question.getProducerSpeed()), List.of(new ArrayList<>()))
                // Build solutions from items
                .stream().map(l -> Solution.builder()
                        .machines(l)
                        .waste(l.stream().mapToInt(Integer::intValue).sum() - question.getTargetAmout())
                        .build())
                // Filter out invalid combinations
                .filter(s -> s.getWaste() >= 0)
                // Sort by waste
                .sorted(Comparator.comparingInt(Solution::getWaste))
                .collect(Collectors.toList());

        if (possibilities.isEmpty())
            return Optional.empty();

        int minWaste = possibilities.get(0).getWaste();

        return Optional.of(possibilities.stream()
                // Take equal waste solutions
                .takeWhile(s -> s.getWaste() == minWaste)
                .collect(Collectors.toList()));
    }

    private static List<List<Integer>> generateCombinations(Queue<Integer> input, List<List<Integer>> aggregator) {
        if (input.isEmpty())
            return aggregator;

        List<List<Integer>> newAggregate = new ArrayList<>();
        aggregator.forEach(ul -> {
            var a = new ArrayList<>(ul);
            var b = new ArrayList<>(ul);
            b.add(input.peek());
            newAggregate.add(a);
            newAggregate.add(b);
        });
        input.remove();
        return generateCombinations(input, newAggregate);
    }
}
