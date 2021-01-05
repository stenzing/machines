package machines;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        if (args.length<1) {
            printError();
            return;
        }
        Optional.of(args[0])
                .flatMap(Application::readInput)
                .flatMap(MachineCalculator::getSolutions)
                .ifPresentOrElse(
                        Application::printSolution,
                        Application::printError);
    }

    /**
     * Read the input by filename. If input could not be read, Optiona.empty() will be returned.
     * @param filename the filename
     * @return the question extracted from the input
     */
    private static Optional<Question> readInput(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();
            List<Integer> processingSpeeds = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .sorted(Integer::compareTo)
                    .collect(Collectors.toList());
            int targetAmount = Integer.parseInt(reader.readLine());

            return Optional.of(Question.builder()
                    .producerSpeed(processingSpeeds)
                    .targetAmout(targetAmount)
                    .build());
        } catch (FileNotFoundException e) {
            System.out.println("Input file not existing");
        } catch (IOException e) {
            System.out.println("Invalid input file");
        }
        return Optional.empty();
    }

    /**
     * Printing solution
     * @param solutions the solution to display
     */
    private static void printSolution(List<Solution> solutions) {
        System.out.println("Nr solutions="+solutions.size());
        solutions.stream()
                .map(s -> s.getMachines()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .forEach(System.out::println);
        solutions.stream()
                .findFirst()
                .ifPresent(s1 -> System.out.println("Waste="+s1.getWaste()));

    }

    /**
     * Print an error message is no solution could be provided.
     */
    private static void printError() {
        System.out.println("So solution possible");
    }

}
