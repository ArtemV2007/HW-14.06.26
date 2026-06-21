package io.github.ArtemV2007;

import io.github.ArtemV2007.dao.UserDao;
import io.github.ArtemV2007.dao.UserDaoImpl;
import io.github.ArtemV2007.model.User;
import io.github.ArtemV2007.service.UserService;
import io.github.ArtemV2007.util.HibernateUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserService(userDao);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число от 1 до 6!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите email: ");
                    String email = scanner.nextLine();

                    // ИСПРАВЛЕНО: Безопасное чтение возраста
                    System.out.print("Введите возраст: ");
                    int age = readIntSafe(scanner);

                    userService.createUser(name, email, age);
                    System.out.println("Запрос на создание пользователя отправлен.");
                    break;

                case 2:
                    System.out.print("Введите ID: ");
                    long id = readLongSafe(scanner);
                    User user = userService.getUserById(id);
                    System.out.println(user != null ? user : "Пользователь не найден.");
                    break;

                case 3:
                    userService.getAllUsers().forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Введите ID пользователя для обновления: ");
                    long updateId = readLongSafe(scanner);
                    System.out.print("Введите новое имя: ");
                    String newName = scanner.nextLine();
                    System.out.print("Введите новый email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Введите новый возраст: ");
                    int newAge = readIntSafe(scanner);

                    userService.updateUser(updateId, newName, newEmail, newAge);
                    break;

                case 5:
                    System.out.print("Введите ID для удаления: ");
                    long deleteId = readLongSafe(scanner);
                    userService.deleteUser(deleteId);
                    System.out.println("Запрос на удаление выполнен.");
                    break;

                case 6:
                    System.out.println("Закрытие соединений с базой данных...");
                    HibernateUtil.shutdown();
                    System.out.println("Выход из программы.");
                    return;

                default:
                    System.out.println("Неверный пункт меню!");
            }
        }
    }

    // Вспомогательный метод для безопасного чтения Integer
    private static int readIntSafe(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод! Введите целое число: ");
            }
        }
    }

    // Вспомогательный метод для безопасного чтения Long
    private static long readLongSafe(Scanner scanner) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод! Введите числовой ID: ");
            }
        }
    }
}
