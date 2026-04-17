package com.nilesh.logsentinel.io;

import com.nilesh.logsentinel.core.LogProcessor;
import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;

public class LogWatcher {
    private final Path logDir;
    private WatchService watchService;
    private final LogProcessor processor; // Added processor reference

    public LogWatcher(String dirPath, LogProcessor processor) {
        this.logDir = Paths.get(dirPath);
        this.processor = processor;
    }

    public void startWatching() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
            // Register for Modify and Create events
            logDir.register(watchService, ENTRY_MODIFY, ENTRY_CREATE);

            System.out.println("🚀 LogSentinel is active and watching: " + logDir.toAbsolutePath());

            while (true) {
                WatchKey key = watchService.take(); 
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changedFile = (Path) event.context();
                    
                    // Construct the full path to the file
                    Path fullPath = logDir.resolve(changedFile);
                    
                    // Filter out non-log files if necessary (e.g., .txt or .log)
                    if (Files.isRegularFile(fullPath)) {
                        System.out.println("🔍 Scanning change in: " + changedFile);
                        processor.process(fullPath.toString()); // Trigger the engine
                    }
                }
                if (!key.reset()) break;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Watcher Error: " + e.getMessage());
        }
    }
}   