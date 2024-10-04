package ru.tadanoluka.task1;

import org.springframework.boot.SpringApplication;
import ru.tadanoluka.task1.configuration.TestcontainersConfiguration;

public class TestTask1Application {

    public static void main(String[] args) {
        SpringApplication.from(Task1Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
