
import WGraph.WGraph_DS;
import WGraph.node_info;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {


    @Test
    void getNode() {
         WGraph_DS g=init(5,10);
        g.getNode(1).setInfo("true?");
        node_info temp=g.getNode(1);
        node_info temp2=g.getNode(10);
        assertEquals("true?",temp.getInfo());
        assertNull(temp2);
    }

    @Test
    void hasEdge() {
        WGraph_DS g=init(5,5);
        assertTrue(g.hasEdge(1,0));
        assertFalse(g.hasEdge(0,3));
        assertFalse(g.hasEdge(3,0));
        assertFalse(g.hasEdge(0,10));
    }

    @Test
    void getEdge() {
        WGraph_DS g=init(10,20);
        assertEquals(g.getEdge(0,7),20.4913,0.001);
        assertEquals(g.getEdge(7,0),20.4913,0.001);
       g.getEdge(0,5);
        if(g.getEdge(0,5)!=-1){
            fail("should not exist,then should not be equal");
        }
        if(g.getEdge(0,30)!=-1){//dosent exist
            fail("should not exist");
        }
    }
    @Test
    void getV() {
        int nON=10;
        int nOE=20;
        WGraph_DS g=init(nON,nOE);
        WGraph_DS s=new WGraph_DS();
        for (int i = 0; i < nON; i++) {
            s.addNode(i);
        }
        assertArrayEquals(s.getV().toArray(new node_info[0]), g.getV().toArray(new node_info[0]));
        if(g.getV().stream().findAny().isPresent())
        g.removeNode(g.getV().stream().findAny().get().getKey());
        if(Arrays.equals(s.getV().toArray(new node_info[0]), g.getV().toArray(new node_info[0]))){
            fail("should not be equal");
        }
    }

    @Test
    void testGetV() {
        WGraph_DS g=init(10,20);
        node_info[] neighbors=new node_info[6];
        neighbors[0]=g.getNode(1);
        neighbors[1]=g.getNode(2);
        neighbors[2]=g.getNode(3);
        neighbors[3]=g.getNode(6);
        neighbors[4]=g.getNode(8);
        neighbors[5]=g.getNode(9);
        assertArrayEquals(neighbors,g.getV(5).toArray());
        g.removeEdge(5,3);
        g.removeEdge(5,4);
        if(Arrays.equals(neighbors, g.getV(5).toArray())){
            fail("should not be equals");
        }

    }

    @Test
    void removeNode() {
        WGraph_DS g=init(10,20);
        WGraph_DS s=new WGraph_DS(g);
        int sON=g.nodeSize();
        int sOE=g.edgeSize();
        assertArrayEquals(g.getV().toArray(),s.getV().toArray());
        g.removeNode(5);
        g.removeNode(2590);
        assertEquals(sON-1,g.nodeSize());
        assertNotEquals(sOE,g.edgeSize());
        if(Arrays.equals(g.getV().toArray(), s.getV().toArray())){
            fail("should not be equal");
        }
    }

    @Test
    void removeEdge() {
        WGraph_DS h= new WGraph_DS();
        WGraph_DS g=init(10,20);
        int nOE=g.edgeSize();
        removeCheck(g, nOE);
    }

    @Test
    void nodeSize() {
        WGraph_DS g=init(10,5);
        int nON=g.nodeSize();
        assertEquals(10,g.nodeSize());
        g.removeNode(5);
        nON--;
        assertEquals(nON,g.nodeSize());
        g.removeNode(2509);//doesn't exist
        assertEquals(nON,g.nodeSize());
        g.removeNode(2);
        nON--;
        assertEquals(nON,g.nodeSize());
        g.removeNode(5);//should not exist
        assertEquals(nON,g.nodeSize());
    }

    @Test
    void edgeSize() {
       WGraph_DS g=  init(10,20);
       int nOE=g.edgeSize();
        assertEquals(20,g.edgeSize());
        removeCheck(g, nOE);
    }



    @Test
    void getMC() {
        WGraph_DS g =init(10,20);
        int mC=g.getMC();
        g.removeEdge(5,2);
        mC++;
        g.removeEdge(2,5);
        mC=mC+g.getV(5).size()+1;
        g.removeNode(5);
        g.removeNode(5);
        assertEquals(mC,g.getMC());
        g.addNode(5);
        mC++;
        assertEquals(mC,g.getMC());
        g.addNode(5);
        assertEquals(mC,g.getMC());
    }
/////////////////// Private Methods ///////////////////////////////////////////////////////////////////////////
    private static WGraph_DS init(int nON,int nOE) {
        Random _rnd = new Random(1);
        int rnd;
        int rnd2;
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < nON; i++) {
            g.addNode(i);
        }
        while(g.edgeSize()<nOE){
            rnd = Math.abs((_rnd.nextInt()) % nON);
            rnd2 = Math.abs((_rnd.nextInt()) % nON);
            g.connect(rnd, rnd2, _rnd.nextDouble()*100);
        }
        return g;
    }
    private void removeCheck(WGraph_DS g, int nOE) {

        g.removeEdge(5,g.getV(5).stream().findAny().get().getKey());
        g.removeEdge(g.getV(5).stream().findAny().get().getKey(),5);
        g.removeEdge(236593265,5);//doesn't exist
        assertFalse(g.hasEdge(2,5));
        nOE-=2;
        assertEquals(nOE,g.edgeSize());
        nOE-=g.getV(5).size();
        g.removeNode(5);
        assertEquals(nOE,g.edgeSize());
    }
}