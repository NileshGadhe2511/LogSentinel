package com.nilesh.logsentinel;

import com.nilesh.logsentinel.core.LogProcessor;
import com.nilesh.logsentinel.io.LogWatcher;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 1. Define keywords to look for
        LogProcessor processor = new LogProcessor(Arrays.asList("ERROR", "FATAL", "CRITICAL", "404"));

        // 2. Pass the processor into the watcher
        LogWatcher watcher = new LogWatcher("logs_to_monitor", processor);
        
        // 3. Start the loop
        watcher.startWatching();
    }
}