// ConsoleNotifier.java - تطبيق التنبيهات عن طريق الكونسول
package notifications;

import Models.Reminder;
import java.util.List;

public class ConsoleNotifier implements Notifier {
    @Override
    public void notifyUser(List<Reminder> reminders) {
        if (reminders.isEmpty()) {
            System.out.println("\n no reminders right now ✅");
        } else {
            System.out.println("\n your reminders 🔔:");
            for (Reminder reminder : reminders) {
                System.out.println("- " + reminder);
            }
        }
    }
}
