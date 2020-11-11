package tests;

import WGraph.WGraph_Algo;
import WGraph.WGraph_DS;
import WGraph.node_info;
import WGraph.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void init() {
        WGraph_DS g = init(10, 20);
        WGraph_Algo h = new WGraph_Algo();
        h.init(g);
        assertEquals(h.getGraph(), g);
    }

    @Test
    void getGraph() {
        WGraph_DS g = init(10, 20);
        WGraph_Algo h = new WGraph_Algo();
        h.init(g);
        assertEquals(h.getGraph().edgeSize(), g.edgeSize());
        Assertions.assertArrayEquals(h.getGraph().getV().toArray(new node_info[0]), g.getV().toArray(new node_info[0]));
    }

    @Test
    void copy() {
        WGraph_Algo h = new WGraph_Algo();
        WGraph_DS g = init(10, 20);
        h.init(g);
        weighted_graph s = h.copy();
        assertArrayEquals(s.getV().toArray(), g.getV().toArray());
        g.removeNode(5);
        assertNotEquals(null, s.getNode(5));
        assertNull(g.getNode(5));
        assertTrue(s.hasEdge(0, 7));
        assertEquals(s.getEdge(0,7),s.getEdge(0,7));
        s.removeEdge(7, 0);
        assertTrue(g.hasEdge(0, 7));
        assertEquals(g.getEdge(0, 9), s.getEdge(9, 0));
    }

    @Test
    void isConnected() {
        WGraph_Algo h = new WGraph_Algo();
        WGraph_DS g = init(15, 20);
        h.init(g);
        assertTrue(h.isConnected());
        g.removeNode(4);
        assertFalse(h.isConnected());
        g.connect(1, 5, 5);
        assertTrue(h.isConnected());

    }

    @Test
    void shortestPathDist() {
        WGraph_Algo h = new WGraph_Algo();
        WGraph_DS g = init(15, 20);
        h.init(g);
        System.out.println(h.shortestPathDist(1, 5));
        assertEquals(14.587, h.shortestPathDist(1, 5), 0.001);
        g.removeNode(4);
        assertEquals(-1, h.shortestPathDist(1, 5));
        g.connect(1, 5, 3.2);
        assertEquals(3.2, h.shortestPathDist(1, 5));
        assertEquals(-1, h.shortestPathDist(1, 4));
    }

    @Test
    void shortestPath() {
        WGraph_Algo h = new WGraph_Algo();
        WGraph_DS g = init(15, 20);
        h.init(g);
        List<node_info> test = new LinkedList<>();
        test.add(g.getNode(5));
        test.add(g.getNode(4));
        test.add(g.getNode(1));
        List<node_info> actual = h.shortestPath(1, 5);
        boolean flag = true;
        for (int i = 0; i < actual.size(); i++) {
            if (test.get(i) != actual.get(i)) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
        g.connect(1, 5, 2);
        assertNotEquals(test.size(), h.shortestPath(1, 5).size());
        test.remove(g.getNode(4));
        assertEquals(test.size(), h.shortestPath(1, 5).size());
        for (int i = 0; i < test.size(); i++) {
            if (test.get(i) != h.shortestPath(1, 5).get(i)) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
        g.removeNode(4);
        g.removeEdge(1,5);
        assertTrue(h.shortestPath(1, 5).isEmpty());
    }

    @Test
    void saveAndLoad() {
        WGraph_Algo f = new WGraph_Algo();
        WGraph_DS k = init(15, 20);
        f.init(k);
        assertTrue(f.save("mygraph"));
        WGraph_Algo h = new WGraph_Algo();
        h.load("mygraph");
        weighted_graph g=h.getGraph();
        assertTrue(k.equals(g));
    }


    private static WGraph_DS init(int nON, int nOE) {
        Random _rnd = new Random(1);
        int rnd, rnd2;
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < nON; i++) {
            g.addNode(i);
        }
        node_info[] n = g.getV().toArray(new node_info[0]);
        while (g.edgeSize() < nOE) {
            rnd = Math.abs((_rnd.nextInt()) % nON);
            rnd2 = Math.abs((_rnd.nextInt()) % nON);
            g.connect(n[rnd].getKey(), n[rnd2].getKey(), Math.abs(_rnd.nextDouble() * 100));
        }
        return g;
    }

}