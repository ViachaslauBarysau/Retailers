package by.itechart.retailers.service.interfaces;

public interface SendingCongratulationsService {
    void sendCongratulations();

    void sendSystemAdminNotification(RuntimeException ex);
}
