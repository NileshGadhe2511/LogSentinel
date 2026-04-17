package com.nilesh.logsentinel.core;

import com.nilesh.logsentinel.alert.AlertManager;
import com.nilesh.logsentinel.alert.EmailAlerter;
import com.nilesh.logsentinel.io.FastFileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogProcessor {
    private final AhoCorasick engine;
    private final FastFileReader reader = new FastFileReader();
    private final AlertManager alerter = new EmailAlerter();
    // Create a thread pool so alerts don't block the main scanner
    private final ExecutorService alertThread = Executors.newFixedThreadPool(2);

    public LogProcessor(List<String> keywords) {
        this.engine = new AhoCorasick();
        keywords.forEach(engine::addKeyword);
        engine.build();
    }

    public void process(String filePath) {
        try {
            String content = reader.readNewContent(filePath);
            if (content.isEmpty()) return;

            List<String> matches = engine.search(content);
            for (String match : matches) {
                // Run the alert in a background thread
                alertThread.submit(() -> alerter.sendAlert(match, content));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}