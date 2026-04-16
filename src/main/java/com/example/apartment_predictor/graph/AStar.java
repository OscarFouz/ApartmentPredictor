package com.example.apartment_predictor.graph;

import com.example.apartment_predictor.utils.DistanceCalculator;

import java.util.*;

public class AStar {

    public Optional<Double> shortestPath(Graph g, int start, int goal) {

        record State(int nodeId, double fScore) {}

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s.fScore));
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Double> fScore = new HashMap<>();

        for (int id : g.nodes.keySet()) {
            dist.put(id, Double.POSITIVE_INFINITY);
            fScore.put(id, Double.POSITIVE_INFINITY);
        }

        dist.put(start, 0.0);
        fScore.put(start, DistanceCalculator.haversine(
                g.nodes.get(start).lat, g.nodes.get(start).lon,
                g.nodes.get(goal).lat, g.nodes.get(goal).lon));

        pq.add(new State(start, fScore.get(start)));

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            int current = currentState.nodeId();

            if (current == goal) {
                return Optional.of(dist.get(goal));
            }

            for (Edge e : g.adj.getOrDefault(current, List.of())) {
                double tentative = dist.get(current) + e.cost;

                if (tentative < dist.get(e.to)) {
                    dist.put(e.to, tentative);
                    double newF = tentative + DistanceCalculator.haversine(
                            g.nodes.get(e.to).lat, g.nodes.get(e.to).lon,
                            g.nodes.get(goal).lat, g.nodes.get(goal).lon);
                    fScore.put(e.to, newF);
                    pq.add(new State(e.to, newF));
                }
            }
        }

        return Optional.empty();
    }
}
