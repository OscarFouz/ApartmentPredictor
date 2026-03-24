package com.example.apartment_predictor.graph;

import java.util.*;

public class Graph {

    public final Map<Integer, Node> nodes = new HashMap<>();
    public final Map<Integer, List<Edge>> adj = new HashMap<>();

    public void addNode(Node n) {
        nodes.put(n.id, n);
        adj.putIfAbsent(n.id, new ArrayList<>());
    }

    public void addEdge(int from, int to, double cost) {
        adj.get(from).add(new Edge(from, to, cost));
        adj.get(to).add(new Edge(to, from, cost)); // bidireccional
    }
}
