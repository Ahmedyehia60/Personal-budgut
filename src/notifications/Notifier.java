// Notifier.java - واجهة التنبيهات
package notifications;

import Models.Reminder;
import java.util.List;

public interface Notifier {
    void notifyUser(List<Reminder> reminders);
}
