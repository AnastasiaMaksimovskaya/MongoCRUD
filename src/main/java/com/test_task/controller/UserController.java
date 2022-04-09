package com.test_task.controller;

import com.test_task.entity.User;
import com.test_task.repository.UserRepository;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                go(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    System.exit(0);
                }
                go(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("if you want add user, please enter 1");
        System.out.println("if you want update user, please enter 2");
        System.out.println("if you want delete user, please enter 3");
        System.out.println("if you want find all users, please enter 4");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    private void go(String position, BufferedReader reader) {
        try {
            switch (position) {
                case "1" -> create(reader);
                case "2" -> {
                    System.out.println("Enter email");
                    update(reader);
                }
                case "3" -> delete(reader);
                case "4" -> findAll();
                case "0" -> System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        runNavigation();
    }

    private void create(BufferedReader reader) throws IOException {
        System.out.println("Please, enter name");
        String name = reader.readLine();
        System.out.println("Please, enter email");
        String email = reader.readLine();
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        userRepository.insert(user);
    }

    private void update(BufferedReader reader) throws IOException {
        String prevEmail = reader.readLine();
        if (!userRepository.existsByEmail(prevEmail)) {
            System.out.println("user doesn't exist");
            return;
        }
        User user = userRepository.findByEmail(prevEmail);
        System.out.println("To edit name press 1");
        System.out.println("To edit email press 2");
        System.out.println("To edit both press 3");
        String opt = reader.readLine();
        switch (opt) {
            case "1" -> {
                System.out.println("Please, enter new name");
                String name = reader.readLine();
                user.setName(name);
                userRepository.save(user);
            }
            case "2" -> {
                System.out.println("Please, enter new email");
                String email = reader.readLine();
                if (userRepository.existsByEmail(email)) {
                    System.out.println("user already exists");
                    return;
                }
                user.setEmail(email);
                userRepository.save(user);
            }
            case "3" -> {
                System.out.println("Please, enter new name");
                String nameEdit = reader.readLine();
                System.out.println("Please, enter new email");
                String emailEdit = reader.readLine();
                if (userRepository.existsByEmail(emailEdit)) {
                    System.out.println("user already exists");
                    return;
                }
                user.setEmail(emailEdit);
                user.setName(nameEdit);
                userRepository.save(user);
            }
        }
    }

    private void delete(BufferedReader reader) throws IOException {
        System.out.println("Please, enter email");
        String email = reader.readLine();
        if (!userRepository.existsByEmail(email)) {
            System.out.println("user doesn't exist");
            return;
        }
        userRepository.delete(userRepository.findByEmail(email));
    }

    private void findAll() {
        List<User> users = userRepository.findAll();
        if (users.size() != 0) {
            for (User user : users) {
                System.out.println("User = " + user);
            }
        } else {
            System.out.println("User list is empty");
        }
    }
}
