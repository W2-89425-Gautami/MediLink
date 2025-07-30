package com.example.doctorapp.service;

import com.example.doctorapp.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationDTO notificationDTO);
    List<NotificationDTO> getNotificationsForUser(Long userId);
}
