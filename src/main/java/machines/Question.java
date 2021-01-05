package machines;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Question {
    List<Integer> producerSpeed;
    int targetAmout;
}
