package com.test_task;

import com.test_task.controller.UserController;
import com.test_task.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TestTaskApplication {

    private final UserRepository userRepository;

    public TestTaskApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
        public void run() {
        userRepository.deleteAll();
        UserController userController = new UserController(userRepository);
        userController.run();
    }
}