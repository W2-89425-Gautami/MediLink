package com.example.doctorapp.service.impl;

import com.example.doctorapp.entity.Notification;
import com.example.doctorapp.entity.User;
import com.example.doctorapp.repository.NotificationRepository;
import com.example.doctorapp.repository.UserRepository;
import com.example.doctorapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of NotificationService interface for managing user notifications.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all notifications for a specific user by user ID.
     *
     * @param userId ID of the user
     * @return List of notifications
     */
    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    /**
     * Creates a new notification for a specific user.
     *
     * @param userId  ID of the user
     * @param message The message to send
     * @return The saved Notification object
     */
    @Override
    public Notification createNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());

        return notificationRepository.save(notification);
    }
}
