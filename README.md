# LogSentinel: Real-Time Log Analysis & Alerting System

LogSentinel is an event-driven Java application designed for high-speed log monitoring and keyword detection.

### 🚀 Key Features
* **Near-Zero Latency Monitoring:** Uses `Java NIO WatchService` to detect filesystem changes instantly.
* **High-Speed Pattern Matching:** Implements the **Aho-Corasick algorithm** to find multiple keywords in $O(n)$ time complexity.
* **Instant Alerts:** Automated email notifications via **Jakarta Mail (SMTP/TLS)** for critical logs.

### 🛠️ Tech Stack
* **Language:** Java (JDK 25)
* **Build Tool:** Maven
* **Core APIs:** Java NIO, Jakarta Mail

### 📖 How it Works
The system builds a Trie-based Finite State Machine (Aho-Corasick) to scan incoming log streams. Unlike standard search methods, LogSentinel scans the entire line only once, regardless of how many keywords it is looking for.
