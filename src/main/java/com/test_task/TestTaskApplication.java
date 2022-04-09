package com.test_task;

import com.test_task.controller.UserController;
import com.test_task.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public TestTaskApplication(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        UserController userController = new UserController(userRepository, mongoTemplate);
        userController.run();
    }
}