package com.example.apartment_predictor.graph;

import com.example.apartment_predictor.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManhattanGraphService {

    private final Graph graph;
    private final AStar aStar;

    public ManhattanGraphService() {
        this.graph = new Graph();
        this.aStar = new AStar();
        initGraph();
    }

    private void initGraph() {
        graph.addNode(new Node(1, 40.753182, -73.981533));
        graph.addNode(new Node(2, 40.754215, -73.980498));
        graph.addNode(new Node(3, 40.755249, -73.979463));
        graph.addNode(new Node(4, 40.756283, -73.978428));

        graph.addNode(new Node(5, 40.752550, -73.979130));
        graph.addNode(new Node(6, 40.753580, -73.978100));
        graph.addNode(new Node(7, 40.754610, -73.977070));
        graph.addNode(new Node(8, 40.755640, -73.976040));

        addStreet(1, 2);
        addStreet(2, 3);
        addStreet(3, 4);

        addStreet(5, 6);
        addStreet(6, 7);
        addStreet(7, 8);

        addStreet(1, 5);
        addStreet(2, 6);
        addStreet(3, 7);
        addStreet(4, 8);
    }

    private void addStreet(int from, int to) {
        Node a = graph.nodes.get(from);
        Node b = graph.nodes.get(to);
        double dist = DistanceCalculator.haversine(a.lat, a.lon, b.lat, b.lon);
        graph.addEdge(from, to, dist);
    }

    public Optional<Double> distance(int from, int to) {
        return aStar.shortestPath(graph, from, to);
    }

    public Optional<Double> calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int startNode = findClosestNode(lat1, lon1);
        int endNode = findClosestNode(lat2, lon2);
        return distance(startNode, endNode);
    }

    private int findClosestNode(double lat, double lon) {
        return graph.nodes.values().stream()
                .min((a, b) -> Double.compare(
                        DistanceCalculator.haversine(a.lat, a.lon, lat, lon),
                        DistanceCalculator.haversine(b.lat, b.lon, lat, lon)
                ))
                .get().id;
    }
}
