package com.example.springboot_module4.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest class DemoApplicationTests {

    @Test void contextLoads() {
    }

    @Test void testEqualsAndHashCode() {
        //		i need to test equals and double equals

        String x = "hello";
        String y = "hello";
        String z = "hello";

        System.out.println(String.valueOf(x == y).concat(" ").concat(String.valueOf(y == z)).concat(" ")
                                 .concat(String.valueOf(x == z)));
        System.out.println(y.equals(z));
    }

    

}
