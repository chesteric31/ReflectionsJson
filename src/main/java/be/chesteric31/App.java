package be.chesteric31;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws JsonProcessingException, IllegalAccessException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/YYYY"));
        Person person = new Person();
        person.setBirthDate(new Date());
        person.setFirstName("Eric");
        person.setName("Binard");
        String json = objectMapper.writeValueAsString(person);
        System.out.println(json);
        Person otherPerson = new Person();
        Field[] declaredFields = otherPerson.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
            declaredField.setAccessible(true);
            if (!"birthDate".equals(declaredField.getName())) {
                declaredField.set(otherPerson, "$!{" + declaredField.getName() + "}");
            }
        }
        System.out.println(objectMapper.writeValueAsString(otherPerson));
    }
}
