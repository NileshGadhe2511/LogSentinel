package com.nilesh.logsentinel.core;

import java.util.*;

public class AhoCorasick {
    private final TrieNode root = new TrieNode();

    public void addKeyword(String keyword) {
        TrieNode current = root;
        for (char ch : keyword.toCharArray()) {
            current = current.children.computeIfAbsent(ch, k -> new TrieNode());
        }
        current.matchedPattern = keyword;
    }

    public void build() {
        Queue<TrieNode> queue = new LinkedList<>();
        for (TrieNode node : root.children.values()) {
            node.failureLink = root;
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char ch = entry.getKey();
                TrieNode child = entry.getValue();
                TrieNode failure = current.failureLink;

                while (failure != root && !failure.children.containsKey(ch)) {
                    failure = failure.failureLink;
                }
                child.failureLink = failure.children.getOrDefault(ch, root);
                queue.add(child);
            }
        }
    }

    public List<String> search(String text) {
        List<String> results = new ArrayList<>();
        TrieNode current = root;
        for (char ch : text.toCharArray()) {
            while (current != root && !current.children.containsKey(ch)) {
                current = current.failureLink;
            }
            current = current.children.getOrDefault(ch, root);
            TrieNode temp = current;
            while (temp != root) {
                if (temp.matchedPattern != null) results.add(temp.matchedPattern);
                temp = temp.failureLink;
            }
        }
        return results;
    }
}