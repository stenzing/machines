package machines;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Solution {
    List<Integer> machines;
    int waste;
}
