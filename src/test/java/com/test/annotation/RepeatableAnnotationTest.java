package com.test.annotation;

import org.junit.jupiter.api.Test;

@Person(role = "ceo")
@Person(role = "husband")
@Person(role = "faster")
@Person(role = "son")
public class RepeatableAnnotationTest {

    @Test
    public void test() {
        // false
        System.out.println(RepeatableAnnotationTest.class.isAnnotationPresent(Person.class));
        // true
        if (RepeatableAnnotationTest.class.isAnnotationPresent(Persons.class)) {
            Persons persons = RepeatableAnnotationTest.class.getAnnotation(Persons.class);
            for (Person person : persons.value()) {
                System.out.println(person.role());
            }
        }
    }
}
