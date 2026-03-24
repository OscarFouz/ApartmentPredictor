package com.example.apartment_predictor.graph;

import java.util.*;

public class AStar {

    private double haversine(Node a, Node b) {
        double R = 6371e3;
        double dLat = Math.toRadians(b.lat - a.lat);
        double dLon = Math.toRadians(b.lon - a.lon);

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(a.lat)) * Math.cos(Math.toRadians(b.lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    }

    public double shortestPath(Graph g, int start, int goal) {

        record State(int nodeId, double fScore) {}

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s.fScore));
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Double> fScore = new HashMap<>();

        for (int id : g.nodes.keySet()) {
            dist.put(id, Double.POSITIVE_INFINITY);
            fScore.put(id, Double.POSITIVE_INFINITY);
        }

        dist.put(start, 0.0);
        fScore.put(start, haversine(g.nodes.get(start), g.nodes.get(goal)));

        pq.add(new State(start, fScore.get(start)));

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            int current = currentState.nodeId();

            if (current == goal) {
                return dist.get(goal);
            }

            for (Edge e : g.adj.getOrDefault(current, List.of())) {
                double tentative = dist.get(current) + e.cost;

                if (tentative < dist.get(e.to)) {
                    dist.put(e.to, tentative);
                    double newF = tentative + haversine(g.nodes.get(e.to), g.nodes.get(goal));
                    fScore.put(e.to, newF);
                    pq.add(new State(e.to, newF));
                }
            }
        }

        return -1;
    }
}
