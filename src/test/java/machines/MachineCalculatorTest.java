package machines;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MachineCalculatorTest {

    @Test
    void checkBasicCalculation() {
        Question q = Question.builder().producerSpeed(List.of(1, 1, 1)).targetAmout(3).build();
        List<Solution> result = MachineCalculator.getSolutions(q).orElseThrow();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getMachines().size());
    }


    @Test
    void checkMultipleResults() {
        Question q = Question.builder().producerSpeed(List.of(1, 2, 3)).targetAmout(3).build();
        List<Solution> result = MachineCalculator.getSolutions(q).orElseThrow();

        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    void checkImpossibleResponse() {
        Question q = Question.builder().producerSpeed(List.of(1, 2, 3)).targetAmout(7).build();
        Optional<List<Solution>> response = MachineCalculator.getSolutions(q);

        assertFalse(response.isPresent());

    }
}
