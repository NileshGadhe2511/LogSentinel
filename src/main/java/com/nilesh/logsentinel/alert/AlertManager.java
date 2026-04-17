package com.nilesh.logsentinel.alert;

public interface AlertManager {
    void sendAlert(String keyword, String context);
}