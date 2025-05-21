import com.fasterxml.jackson.databind.ObjectMapper;
import items.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class JsonFileParsing {

    private static final Logger log = LoggerFactory.getLogger(JsonFileParsing.class);
    private final ClassLoader cl = JsonFileParsing.class.getClassLoader();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Проверка json файла")
    public void checkStudentFromJsonTest() throws Exception {
        File jsonFile = new File("src/test/resources/anketa.json");
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(jsonFile, Student.class);

        Assertions.assertEquals("John", student.getName());
        Assertions.assertEquals(30, student.getAge());
        Assertions.assertTrue(student.isStudent());
        Assertions.assertEquals(List.of("Math", "History"), student.getCourses());
        Assertions.assertEquals("Moskov", student.getAddress().getCity());
        Assertions.assertEquals(10501, student.getAddress().getIndex());
    }
}
