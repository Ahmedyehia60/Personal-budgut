// Reminder.java - يمثل التذكير الفردي
package Models;

import java.time.LocalDateTime;

public class Reminder {
    private String username;
    private String message;
    private LocalDateTime dateTime;

    public Reminder(String username, String message, LocalDateTime dateTime) {
        this.username = username;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "[" + username + "] [" + dateTime.toString() + "] " + message;
    }
}
