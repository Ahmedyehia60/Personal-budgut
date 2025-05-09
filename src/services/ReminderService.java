// ReminderService.java
package services;

import Models.Reminder;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReminderService {
    private static final String FILE_PATH = "src/data/reminders.txt";

    public void addReminder(Reminder reminder) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(reminder.getUsername() + "|" + reminder.getDateTime() + "|" + reminder.getMessage());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reminder> getRemindersForUser(String username) {
        List<Reminder> reminders = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3 && parts[0].equals(username)) {
                    Reminder reminder = new Reminder(parts[0], parts[2], LocalDateTime.parse(parts[1]));
                    reminders.add(reminder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reminders;
    }
}
