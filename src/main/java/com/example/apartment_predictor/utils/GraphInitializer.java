package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.graph.Graph;
import com.example.apartment_predictor.graph.Node;
import org.springframework.stereotype.Component;

@Component
public class GraphInitializer {

    private record NodeCoord(int id, double lat, double lon) {}

    private static final NodeCoord[] GRAPH_NODES = {
            new NodeCoord(1, 40.753182, -73.981533),
            new NodeCoord(2, 40.754215, -73.980498),
            new NodeCoord(3, 40.755249, -73.979463),
            new NodeCoord(4, 40.756283, -73.978428),
            new NodeCoord(5, 40.752550, -73.979130),
            new NodeCoord(6, 40.753580, -73.978100),
            new NodeCoord(7, 40.754610, -73.977070),
            new NodeCoord(8, 40.755640, -73.976040)
    };

    public int nearestNodeId(double lat, double lon) {
        double best = Double.POSITIVE_INFINITY;
        int bestId = GRAPH_NODES[0].id;

        for (NodeCoord n : GRAPH_NODES) {
            double d = DistanceCalculator.haversine(lat, lon, n.lat, n.lon);
            if (d < best) {
                best = d;
                bestId = n.id;
            }
        }
        return bestId;
    }

    public Graph createGraph() {
        Graph graph = new Graph();

        for (NodeCoord nc : GRAPH_NODES) {
            graph.addNode(new Node(nc.id, nc.lat, nc.lon));
        }

        addStreet(graph, 1, 2);
        addStreet(graph, 2, 3);
        addStreet(graph, 3, 4);

        addStreet(graph, 5, 6);
        addStreet(graph, 6, 7);
        addStreet(graph, 7, 8);

        addStreet(graph, 1, 5);
        addStreet(graph, 2, 6);
        addStreet(graph, 3, 7);
        addStreet(graph, 4, 8);

        return graph;
    }

    private void addStreet(Graph graph, int from, int to) {
        NodeCoord fromCoord = findNode(from);
        NodeCoord toCoord = findNode(to);
        double dist = DistanceCalculator.haversine(fromCoord.lat, fromCoord.lon, toCoord.lat, toCoord.lon);
        graph.addEdge(from, to, dist);
    }

    private NodeCoord findNode(int id) {
        for (NodeCoord nc : GRAPH_NODES) {
            if (nc.id == id) return nc;
        }
        return null;
    }
}
