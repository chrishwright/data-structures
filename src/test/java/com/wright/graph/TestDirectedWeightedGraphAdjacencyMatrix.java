package com.wright.graph;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TestDirectedWeightedGraphAdjacencyMatrix {
    @Test
    public void testAddVertex() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");
        graph.addVertex("Hartford");
        graph.addVertex("Dover");

        assertEquals(5, graph.getGraphSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddVertex2() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");
        graph.addVertex("Hartford");
        graph.addVertex("Dover");

        assertEquals(3, graph.getGraphSize());
    }

    @Test
    public void testGetVertex() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        assertEquals(3, graph.getGraphSize());
        assertEquals("Boston", graph.getVertex(0));
        assertEquals("Philadelphia", graph.getVertex(1));
        assertEquals("Baltimore", graph.getVertex(2));
    }

    @Test
    public void testAddEdge() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(4);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Jersey City");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1, 2);
        graph.addEdge(2, 3, 9);
        assertEquals(4, graph.getGraphSize());
        assertEquals(2, graph.getWeightForEdge(0, 1));
        assertEquals(9, graph.getWeightForEdge(2, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithError() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 4, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithError2() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(3);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 2, 45);
    }

    @Test
    public void testGetNeighbors() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(3);

        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");

        graph.addEdge(0, 1, 5);

        Set<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(Set.of(1), neighbors);
    }

    @Test
    public void testGetNeighbors2() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 5);
        graph.addEdge(0, 3, 1);

        Set<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(Set.of(1, 2, 3), neighbors);
    }

    @Test
    public void testGetNeighbors3() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 2);
        graph.addEdge(0, 3, 2);
        graph.addEdge(1, 0, 2);
        graph.addEdge(2, 0, 2);
        graph.addEdge(4, 2, 2);
        graph.addEdge(4, 3, 7);

        assertEquals(Set.of(1, 2, 3), graph.getNeighbors(0));
        assertEquals(Set.of(0), graph.getNeighbors(1));
        assertEquals(Set.of(2, 3), graph.getNeighbors(4));
    }

    @Test
    public void testGetNextSmallestDistance() {
        Set<Integer> allowed = Set.of();
        int[] distances = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 0};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(2, actual);
    }

    @Test
    public void testGetNextSmallestDistance2() {
        Set<Integer> allowed = Set.of();
        int[] distances = new int[]{1, 0, 2};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(1, actual);
    }

    @Test
    public void testGetNextSmallestDistance3() {
        Set<Integer> allowed = Set.of();
        int[] distances = new int[]{1, 1, 1};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(2, actual);
    }

    @Test
    public void testGetNextSmallestDistance4() {
        Set<Integer> allowed = Set.of();
        int[] distances = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Baltimore");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(2, actual);
    }

    @Test
    public void testGetNextSmallestDistance5() {
        Set<Integer> allowed = Set.of(0, 1);
        int[] distances = new int[]{0, 2, 10, 17, Integer.MAX_VALUE, 8};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(6);
        graph.addVertex("V0");
        graph.addVertex("V1");
        graph.addVertex("V2");
        graph.addVertex("V3");
        graph.addVertex("V4");
        graph.addVertex("V5");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(5, actual);
    }

    @Test
    public void testGetNextSmallestDistance6() {
        Set<Integer> allowed = Set.of(0, 1, 5);
        int[] distances = new int[]{0, 2, 10, 17, Integer.MAX_VALUE, 8};

        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(6);
        graph.addVertex("V0");
        graph.addVertex("V1");
        graph.addVertex("V2");
        graph.addVertex("V3");
        graph.addVertex("V4");
        graph.addVertex("V5");

        int actual = graph.getNextSmallestDistanceIndex(distances, allowed);
        assertEquals(2, actual);
    }

    @Test
    public void testCalculateAllShortestPaths() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(6);
        graph.addVertex("V0");
        graph.addVertex("V1");
        graph.addVertex("V2");
        graph.addVertex("V3");
        graph.addVertex("V4");
        graph.addVertex("V5");

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 5, 9);
        graph.addEdge(1, 5, 6);
        graph.addEdge(1, 3, 15);
        graph.addEdge(1, 2, 8);
        graph.addEdge(2, 3, 1);
        graph.addEdge(4, 2, 7);
        graph.addEdge(4, 3, 3);
        graph.addEdge(5, 4, 3);

        DirectedWeightedGraphAdjacencyMatrix.ShortestPathHelper helper = graph.calculateAllShortestPaths(0);
        assertArrayEquals(new int[]{0, 2, 10, 11, 11, 8}, helper.getDistances());
        assertArrayEquals(new int[]{0, 0, 1, 2, 5, 1}, helper.getPredecessors());

        List<Integer> shortestPath = helper.getShortestPathToTarget(4);
        assertEquals(List.of(4, 5, 1, 0), shortestPath);
    }

    @Test
    public void testDfs() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 7);

        Set<String> processed = graph.depthFirstTraversal(0);

        assertTrue(processed.contains("Boston"));
        assertTrue(processed.contains("Philadelphia"));
        assertTrue(processed.contains("Hartford"));
        assertTrue(processed.contains("Newark"));
        assertTrue(processed.contains("Baltimore"));
        assertEquals(processed.size(), 5);
    }

    @Test
    public void testDfs2() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(6);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");
        graph.addVertex("Palm Springs");

        graph.addEdge(0, 1, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(4, 5, 1);

        Set<String> processed1 = graph.depthFirstTraversal(0);
        Set<String> processed2 = graph.depthFirstTraversal(2);
        Set<String> processed3 = graph.depthFirstTraversal(4);

        assertEquals(processed1.size(), 2);
        assertTrue(processed1.contains("Boston"));
        assertTrue(processed1.contains("Philadelphia"));

        assertEquals(processed2.size(), 2);
        assertTrue(processed2.contains("Hartford"));
        assertTrue(processed2.contains("Newark"));

        assertEquals(processed3.size(), 2);
        assertTrue(processed3.contains("Baltimore"));
        assertTrue(processed3.contains("Palm Springs"));
    }

    @Test
    public void testDfs3() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(7);
        graph.addVertex("V0");
        graph.addVertex("V1");
        graph.addVertex("V2");
        graph.addVertex("V3");
        graph.addVertex("V4");
        graph.addVertex("V5");
        graph.addVertex("V6");

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 4, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(3, 6, 1);
        graph.addEdge(3, 5, 1);
        graph.addEdge(3, 0, 1);
        graph.addEdge(6, 1, 1);

        Set<String> processed = graph.depthFirstTraversal(0);
        assertEquals(processed.size(), 6);
        assertTrue(processed.contains("V0"));
        assertTrue(processed.contains("V1"));
        assertTrue(processed.contains("V3"));
        assertTrue(processed.contains("V4"));
        assertTrue(processed.contains("V5"));
        assertTrue(processed.contains("V6"));

        assertFalse(processed.contains("V2"));
    }

    @Test
    public void testDfs4() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(7);
        graph.addVertex("V0");
        graph.addVertex("V1");
        graph.addVertex("V2");
        graph.addVertex("V3");
        graph.addVertex("V4");
        graph.addVertex("V5");
        graph.addVertex("V6");

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 4, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(3, 6, 1);
        graph.addEdge(3, 5, 1);
        graph.addEdge(3, 0, 1);
        graph.addEdge(6, 1, 1);

        Set<String> processed = graph.depthFirstTraversal(2);
        assertEquals(processed.size(), 7);
        assertTrue(processed.contains("V0"));
        assertTrue(processed.contains("V1"));
        assertTrue(processed.contains("V2"));
        assertTrue(processed.contains("V3"));
        assertTrue(processed.contains("V4"));
        assertTrue(processed.contains("V5"));
        assertTrue(processed.contains("V6"));
    }

    @Test
    public void testBfs() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(5);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 1);

        Set<String> processed = graph.breadthFirstTraversal(0);
        assertEquals(processed.size(), 5);
        assertTrue(processed.contains("Boston"));
        assertTrue(processed.contains("Philadelphia"));
        assertTrue(processed.contains("Hartford"));
        assertTrue(processed.contains("Newark"));
        assertTrue(processed.contains("Baltimore"));
    }

    @Test
    public void testBfs2() {
        DirectedWeightedGraphAdjacencyMatrix<String> graph = new DirectedWeightedGraphAdjacencyMatrix<>(6);
        graph.addVertex("Boston");
        graph.addVertex("Philadelphia");
        graph.addVertex("Hartford");
        graph.addVertex("Newark");
        graph.addVertex("Baltimore");
        graph.addVertex("Palm Springs");

        graph.addEdge(0, 1, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(4, 5, 1);

        Set<String> processed1 = graph.breadthFirstTraversal(0);
        Set<String> processed2 = graph.breadthFirstTraversal(2);
        Set<String> processed3 = graph.breadthFirstTraversal(4);

        assertEquals(processed1.size(), 2);
        assertTrue(processed1.contains("Boston"));
        assertTrue(processed1.contains("Philadelphia"));

        assertEquals(processed2.size(), 2);
        assertTrue(processed2.contains("Hartford"));
        assertTrue(processed2.contains("Newark"));

        assertEquals(processed3.size(), 2);
        assertTrue(processed3.contains("Baltimore"));
        assertTrue(processed3.contains("Palm Springs"));
    }
}
