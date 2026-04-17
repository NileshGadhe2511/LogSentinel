package com.nilesh.logsentinel.core;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    TrieNode failureLink;
    String matchedPattern = null; // Stores the keyword if this is an end-node
}