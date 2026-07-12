package org.example.app;

import java.util.List;
import java.util.Scanner;
import org.example.dao.UserDao;
import org.example.entity.User;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDao dao = new UserDao();

        boolean isRunning = true;

        while (isRunning) {
            showMenu();
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.println("Введите имя: ");
                    String name = sc.nextLine();

                    System.out.println("Введите email: ");
                    String email = sc.nextLine();

                    System.out.println("Введите возраст: ");
                    int age = sc.nextInt();

                    User user = new User(name, email, age, LocalDateTime.now());
                    dao.save(user);
                    System.out.println("Пользователь успешно сохранен");

                    break;

                case 2:
                    System.out.println("Введите ID пользователя: ");
                    Long id = sc.nextLong();
                    User foundUser = dao.findById(id);

                    if (foundUser != null) {
                        System.out.println(foundUser);
                    } else {
                        System.out.println("Пользователь не найден.");
                    }

                    break;

                case 3:
                    System.out.println("Введите ID пользователя: ");
                    Long idToUpdate = sc.nextLong();
                    User userToUpdate = dao.findById(idToUpdate);

                    if (userToUpdate == null) {
                        System.out.println("Пользователь не найден.");
                        break;
                    }

                    sc.nextLine();
                    System.out.print("Введите новое имя: ");
                    String newName = sc.nextLine();

                    System.out.print("Введите новый email: ");
                    String newEmail = sc.nextLine();

                    System.out.print("Введите новый возраст: ");
                    int newAge = sc.nextInt();

                    userToUpdate.setName(newName);
                    userToUpdate.setEmail(newEmail);
                    userToUpdate.setAge(newAge);

                    dao.update(userToUpdate);
                    System.out.println("Пользователь успешно обновлен.");

                    break;

                case 4:
                    System.out.print("Введите ID пользователя: ");
                    Long idToDelete = sc.nextLong();
                    User userToDelete = dao.findById(idToDelete);

                    if (userToDelete == null) {
                        System.out.println("Пользователь не найден.");
                        break;
                    }

                    dao.delete(idToDelete);
                    System.out.println("Пользователь успешно удален.");

                    break;

                case 5:
                    List<User> users = dao.findAll();

                    if (users.isEmpty()) {
                        System.out.println("Список пользователей пуст.");
                    } else {
                        for (User u : users) {
                            System.out.println(u);
                        }
                    }

                    break;

                case 0:
                    isRunning = false;
                    System.out.println("Работа программы завершена.");
                    break;

                default:
                    System.out.println("Данного пункта нет в меню.");
            }

        }

    }

    private static void showMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1 - Создать пользователя");
        System.out.println("2 - Найти пользователя по ID");
        System.out.println("3 - Обновить данные пользователя");
        System.out.println("4 - Удалить пользователя");
        System.out.println("5 - Вывести всех пользователей");
        System.out.println("0 - Выход");
        System.out.print("> ");

    }

}
