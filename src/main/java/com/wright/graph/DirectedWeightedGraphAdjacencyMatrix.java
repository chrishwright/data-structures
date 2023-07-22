package com.wright.graph;

import java.util.*;

public class DirectedWeightedGraphAdjacencyMatrix<T> {
    private static final int INFINITY = Integer.MAX_VALUE;

    private final int maxGraphCapacity;
    private final int[][] edges;
    private final Object[] vertexLabels;

    private int graphSize = 0;

    public DirectedWeightedGraphAdjacencyMatrix(int maxGraphCapacity) {
        this.maxGraphCapacity = maxGraphCapacity;
        this.edges = new int[maxGraphCapacity][maxGraphCapacity];
        this.vertexLabels = new Object[maxGraphCapacity];

        for (int[] row : edges) {
            Arrays.fill(row, INFINITY);
        }
    }

    public void addVertex(T data) {
        if (graphSize >= maxGraphCapacity) {
            throw new IllegalArgumentException("Graph is full");
        }
        vertexLabels[graphSize++] = data;
    }

    @SuppressWarnings("unchecked")
    public T getVertex(int vertex) {
        if (vertex >= graphSize) {
            throw new IllegalArgumentException("Invalid vertex - must not be greater than graph size");
        }
        return (T) vertexLabels[vertex];
    }

    public void addEdge(int sourceVertex, int targetVertex, int weight) {
        validateEdge(sourceVertex, targetVertex);
        edges[sourceVertex][targetVertex] = weight;
    }

    public Set<Integer> getNeighbors(int sourceVertex) {
        validateEdge(sourceVertex, sourceVertex);

        Set<Integer> neighbors = new HashSet<>();
        for (int i = 0; i < graphSize; i++) {
            if (edges[sourceVertex][i] != INFINITY) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public int getWeightForEdge(int sourceVertex, int targetVertex) {
        validateEdge(sourceVertex, targetVertex);
        return edges[sourceVertex][targetVertex];
    }

    private void validateEdge(int sourceVertex, int targetVertex) {
        if (sourceVertex >= graphSize || targetVertex >= graphSize) {
            throw new IllegalArgumentException("Source and target must be within the size of the graph");
        }
    }

    public ShortestPathHelper calculateAllShortestPaths(int sourceVertex) {
        Set<Integer> allowedVertices = new HashSet<>();
        int[] distances = new int[graphSize];
        int[] predecessors = new int[graphSize];

        Arrays.fill(distances, INFINITY);
        Arrays.fill(predecessors, INFINITY);

        distances[sourceVertex] = 0;
        predecessors[sourceVertex] = 0;

        for (int i = 1; i <= graphSize; i++) {
            int next = getNextSmallestDistanceIndex(distances, allowedVertices);
            allowedVertices.add(next);

            for (int neighbor : getNeighbors(next)) {
                int weight = getWeightForEdge(next, neighbor);
                int sum = distances[next] + weight;
                if (sum < distances[neighbor]) {
                    predecessors[neighbor] = next;
                    distances[neighbor] = sum;
                }
            }
        }

        return new ShortestPathHelper(sourceVertex, distances, predecessors);
    }

    protected int getNextSmallestDistanceIndex(int[] distances, Set<Integer> allowedVertices) {
        int smallestWeight = INFINITY;
        int smallestWeightIndex = INFINITY;
        for (int i = 0; i < distances.length; i++) {
            if (!allowedVertices.contains(i) && distances[i] <= smallestWeight) {
                smallestWeight = distances[i];
                smallestWeightIndex = i;
            }
        }
        return smallestWeightIndex;
    }

    public Set<T> depthFirstTraversal(int sourceVertex) {
        Set<Integer> visited = new HashSet<>();
        Set<T> processed = new HashSet<>();
        return depthFirstRecursive(sourceVertex, visited, processed);
    }

    @SuppressWarnings("unchecked")
    private Set<T> depthFirstRecursive(int vertex, Set<Integer> visited, Set<T> processed) {
        processed.add((T) vertexLabels[vertex]);
        visited.add(vertex);
        getNeighbors(vertex).stream()
                .filter(neighbor -> !visited.contains(neighbor))
                .forEach(neighbor -> depthFirstRecursive(neighbor, visited, processed));
        return processed;
    }

    @SuppressWarnings("unchecked")
    public Set<T> breadthFirstTraversal(int sourceVertex) {
        validateEdge(sourceVertex, sourceVertex);

        Set<T> processed = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> toVisit = new ArrayDeque<>();
        toVisit.add(sourceVertex);

        while (!toVisit.isEmpty()) {
            int next = toVisit.poll();
            processed.add((T) vertexLabels[next]);
            visited.add(next);
            getNeighbors(next).stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .forEach(toVisit::add);
        }
        return processed;
    }

    public int getGraphSize() {
        return graphSize;
    }

    public static final class ShortestPathHelper {
        private final int source;
        private final int[] distances;
        private final int[] predecessors;

        private ShortestPathHelper(int source, int[] distances, int[] predecessors) {
            this.source = source;
            this.distances = distances;
            this.predecessors = predecessors;
        }

        public int[] getDistances() {
            return Arrays.copyOf(distances, distances.length);
        }

        public int[] getPredecessors() {
            return Arrays.copyOf(predecessors, predecessors.length);
        }

        public List<Integer> getShortestPathToTarget(int targetVertex) {
            if (targetVertex >= distances.length) {
                throw new IllegalArgumentException("targetVertex must be less than the size of the graph");
            }
            List<Integer> response = new ArrayList<>();
            response.add(targetVertex);

            int current = targetVertex;
            while (current != source) {
                current = predecessors[current];
                response.add(current);
            }

            return response;
        }
    }
}
