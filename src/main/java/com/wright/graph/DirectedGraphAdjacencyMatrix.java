package com.wright.graph;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DirectedGraphAdjacencyMatrix<T> {
    private final int max;
    private final Object[] labels;
    private final boolean[][] edges;
    private int size = 0;

    public DirectedGraphAdjacencyMatrix(int max) {
        this.max = max;
        labels = new Object[max];
        edges = new boolean[max][max];
    }

    public void addVertex(T data) {
        if (size == max) {
            throw new IllegalArgumentException("Graph is full");
        }

        labels[size++] = data;
    }

    public void addEdge(int sourceVertex, int targetVertex) {
        if (!isValidEdge(sourceVertex, targetVertex)) {
            throw new IllegalArgumentException("Invalid edge index");
        }

        edges[sourceVertex][targetVertex] = true;
    }

    /**
     * Checks that the edge is valid.  This method assumes that an edge
     * cannot be pointed to an edge that does not exist.  Therefore, the
     * edge source and target must be within the range of vertices that exist.
     *
     * @param sourceVertex the source vertex index, must be > 0 && < size
     * @param targetVertex the target vertex index, must be > 0 && < size
     * @return <code>true</code> if the edge is valid.
     */
    private boolean isValidEdge(int sourceVertex, int targetVertex) {
        return sourceVertex >= 0 &&
                sourceVertex < size &&
                targetVertex >= 0 &&
                targetVertex < size;
    }

    @SuppressWarnings("unchecked")
    public T getVertex(int index) {
        if (size == 0) {
            throw new IllegalArgumentException("Graph is empty");
        }

        return (T) labels[index];
    }

    public Set<Integer> getNeighborsForVertex(int vertex) {
        Set<Integer> neighbors = new HashSet<>();
        for (int i = 0; i < edges.length; i++) {
            if (edges[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public void dfs(int startVertex, Consumer<T> consumer) {
        boolean[] marked = new boolean[size];
        dfsRecursive(marked, consumer, startVertex);
    }

    @SuppressWarnings("unchecked")
    private void dfsRecursive(boolean[] marked, Consumer<T> consumer, int vertex) {
        if (!marked[vertex]) {
            consumer.accept((T) labels[vertex]);
            marked[vertex] = true;
            Set<Integer> neighbors = getNeighborsForVertex(vertex);
            neighbors.stream()
                    .filter((neighbor) -> !marked[neighbor])
                    .forEach((neighbor) -> dfsRecursive(marked, consumer, neighbor));
        }
    }

    @SuppressWarnings("unchecked")
    public void bfs(int startVertex, Consumer<T> consumer) {
        boolean[] marked = new boolean[size];
        Queue<Integer> queue = new ArrayDeque<>();

        consumer.accept((T) labels[startVertex]);
        marked[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Set<Integer> unseenNeighbors = getNeighborsForVertex(queue.poll()).stream()
                    .filter((neighbor) -> !marked[neighbor])
                    .collect(Collectors.toSet());
            unseenNeighbors.forEach((neighbor) -> {
                queue.add(neighbor);
                consumer.accept((T) labels[neighbor]);
            });
        }
    }

    public int size() {
        return size;
    }
}
