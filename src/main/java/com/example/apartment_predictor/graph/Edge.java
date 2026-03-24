package com.example.apartment_predictor.graph;

public class Edge {
    public final int from;
    public final int to;
    public final double cost;

    public Edge(int from, int to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}
