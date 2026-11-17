package workshop.task_2_1;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperService {

    public static List<String> getLanguages(List<Developer> team) {
        if (team == null) {
            return Collections.emptyList();
        }

        return team.stream()
                .map(Developer::getLanguages)
                .flatMap(Set::stream)
                .distinct()
                .collect(Collectors.toList());
    }

}