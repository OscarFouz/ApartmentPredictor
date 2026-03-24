package com.example.apartment_predictor.graph;

import org.springframework.stereotype.Service;

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
        // Nodos (intersecciones)
        graph.addNode(new Node(1, 40.753182, -73.981533)); // 5th & 42nd
        graph.addNode(new Node(2, 40.754215, -73.980498)); // 5th & 43rd
        graph.addNode(new Node(3, 40.755249, -73.979463)); // 5th & 44th
        graph.addNode(new Node(4, 40.756283, -73.978428)); // 5th & 45th

        graph.addNode(new Node(5, 40.752550, -73.979130)); // Madison & 42nd
        graph.addNode(new Node(6, 40.753580, -73.978100)); // Madison & 43rd
        graph.addNode(new Node(7, 40.754610, -73.977070)); // Madison & 44th
        graph.addNode(new Node(8, 40.755640, -73.976040)); // Madison & 45th

        // Distancias aproximadas (en metros)
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
        double dist = haversine(a.lat, a.lon, b.lat, b.lon);
        graph.addEdge(from, to, dist);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return R * 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    }

    public double distance(int from, int to) {
        return aStar.shortestPath(graph, from, to);
    }
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int startNode = findClosestNode(lat1, lon1);
        int endNode = findClosestNode(lat2, lon2);
        return distance(startNode, endNode);
    }

    private int findClosestNode(double lat, double lon) {
        return graph.nodes.values().stream()
                .min((a, b) -> Double.compare(
                        haversine(a.lat, a.lon, lat, lon),
                        haversine(b.lat, b.lon, lat, lon)
                ))
                .get().id;
    }
}
