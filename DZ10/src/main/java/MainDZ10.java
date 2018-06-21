import person.Person;
import streams.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class MainDZ10 {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>(Arrays.asList(
                new Person("Николай", 31),
                new Person("Юлия", 20)));

        StringBuilder sb = new StringBuilder();
        sb.append("Исходный список:\n")
                .append(persons);

        Map<String, Integer> personsMap = Streams.of(persons)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p.getAge());

        sb.append("\n\nРезультат Streams:\n")
                .append(personsMap);

        sb.append("\n\nИсходный список не изменен:\n")
                .append(persons);

        System.out.println(sb);
    }
}