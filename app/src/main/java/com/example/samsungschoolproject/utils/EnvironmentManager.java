package com.example.samsungschoolproject.utils;

import android.util.Log; // Импорт для использования Log

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Утилитарный класс для управления переменными окружения из файла .env.
 * не используется в проекте
 */
public class EnvironmentManager {

    private static final String ENV_FILE_PATH = ".env"; // Путь к файлу .env

    private String envFilePath; // Путь к файлу .env

    /**
     * Конструктор класса, инициализирует путь к файлу .env по умолчанию.
     */
    public EnvironmentManager() {
        this.envFilePath = ENV_FILE_PATH;
    }

    /**
     * Метод для получения значения переменной IP_SERVER из файла .env.
     *
     * @return Значение переменной IP_SERVER или пустая строка, если не найдено или произошла ошибка.
     */
    public String getIpServer() {
        String ipServer = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(envFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("IP_SERVER")) {
                    // Найдена строка с переменной IP_SERVER
                    // Парсим значение переменной, предполагая формат "IP_SERVER=value"
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        ipServer = parts[1].trim(); // Получаем значение и убираем пробелы
                        break; // Выходим из цикла, если нашли значение
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Выводим ошибку в случае исключения
        }
        Log.d("EnvironmentManager", ipServer); // Выводим значение IP_SERVER в лог для отладки
        return ipServer;
    }

    /**
     * Пример использования класса EnvironmentManager.
     *
     * @param args Аргументы командной строки (не используются в данном примере).
     */
    public static void main(String[] args) {
        EnvironmentManager manager = new EnvironmentManager();
        String ipServer = manager.getIpServer();
        System.out.println("IP сервера: " + ipServer); // Выводим значение IP сервера в консоль
    }
}
