package com.wright.graph;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestDirectedGraphAdjacencyMatrix {
    @Test
    public void testAddVertex() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");
        graph.addVertex("Hartford");
        graph.addVertex("Dover");

        assertEquals(5, graph.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVertex2() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");
        graph.addVertex("Hartford");
        graph.addVertex("Dover");

        assertEquals(3, graph.size());
    }

    @Test
    public void testGetVertex() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        assertEquals(3, graph.size());
        assertEquals("Boston", graph.getVertex(0));
        assertEquals("Philadelphia", graph.getVertex(1));
        assertEquals("Baltimore", graph.getVertex(2));
    }

    @Test
    public void testAddEdge() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 1);
        assertEquals(2, graph.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithError() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithError2() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 2);
    }

    @Test
    public void testGetNeighbors() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 1);

        Set<Integer> neighbors = graph.getNeighborsForVertex(0);
        assertEquals(Set.of(1), neighbors);
    }

    @Test
    public void testGetNeighbors2() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);

        Set<Integer> neighbors = graph.getNeighborsForVertex(0);
        assertEquals(Set.of(1, 2, 3), neighbors);
    }

    @Test
    public void testGetNeighbors3() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(1, 0);
        graph.addEdge(2, 0);
        graph.addEdge(4, 2);
        graph.addEdge(4, 3);

        assertEquals(Set.of(1, 2, 3), graph.getNeighborsForVertex(0));
        assertEquals(Set.of(0), graph.getNeighborsForVertex(1));
        assertEquals(Set.of(2, 3), graph.getNeighborsForVertex(4));
    }

    @Test
    public void testDfs() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.dfs(0, System.out::println);
    }

    @Test
    public void testDfs2() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(6);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");
        graph.addVertex("Palm Springs");

        graph.addEdge(0, 1);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);

        graph.dfs(0, System.out::println);
        graph.dfs(2, System.out::println);
        graph.dfs(4, System.out::println);
    }

    @Test
    public void testBfs() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.bfs(0, System.out::println);
    }

    @Test
    public void testDBfs2() {
        DirectedGraphAdjacencyMatrix<String> graph = new DirectedGraphAdjacencyMatrix<>(6);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");
        graph.addVertex("Palm Springs");

        graph.addEdge(0, 1);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);

        graph.bfs(0, System.out::println);
        graph.bfs(2, System.out::println);
        graph.bfs(4, System.out::println);
    }
}
