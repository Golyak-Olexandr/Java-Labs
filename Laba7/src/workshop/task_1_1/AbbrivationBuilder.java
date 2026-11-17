package workshop.task_1_1;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbbrivationBuilder {

    public static String build(List<String> list) {
        if (list == null) {
            return "";
        }

        return list.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());
    }

}