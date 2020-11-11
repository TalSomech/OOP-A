package WGraph;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private WGraph_DS wThGraph;

    public WGraph_Algo() {
        wThGraph = new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
      wThGraph = (WGraph_DS)g;
    }

    @Override
    public weighted_graph getGraph() {
        return wThGraph;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS(this.wThGraph);
    }

    @Override
    public boolean isConnected() {
        if (wThGraph.nodeSize() == 0 || wThGraph.nodeSize() == 1)
            return true;
        node_info n = wThGraph.getV().stream().findAny().get();
        return BFS(n) == wThGraph.nodeSize();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest)
            return 0;
        //TODO: add exception
        if(wThGraph.getNode(dest)==null||wThGraph.getNode(src)==null)
            return -1;
        node_info desti = wThGraph.getNode(dest);
        dijkstra(wThGraph.getNode(src));
        double path=desti.getTag();
        if (desti.getTag()==Integer.MAX_VALUE){
            return -1;
        }
        return path;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> path = new LinkedList<>();
        if (src == dest)
            return path;
        if(wThGraph.getNode(dest)==null||wThGraph.getNode(src)==null)
            return path;
        //TODO: add exception
        dijkstra(wThGraph.getNode(src));
        node_info desti = wThGraph.getNode(dest);
        while (wThGraph.getFather(desti) != null) {
            path.add(desti);
            desti = wThGraph.getFather(desti);
        }
        path.add(desti);
        if (!path.contains(wThGraph.getNode(src))) {
            path.clear();
            return path;
        }
        return path;
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fw= new FileOutputStream(file);
            ObjectOutputStream savd=new ObjectOutputStream(fw);
            savd.writeObject(wThGraph);
            savd.close();
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream fr=new FileInputStream(file);
            ObjectInputStream lad=new ObjectInputStream(fr);
            this.wThGraph=(WGraph_DS) lad.readObject();
            lad.close();
            fr.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void dijkstra(node_info src) {
        resetTagI();
        src.setTag(0);
        PriorityQueue<node_info> queue = new PriorityQueue<>(wThGraph.getV());
        while (!queue.isEmpty()) {
            node_info cur = queue.poll();
            for (node_info neighbs : wThGraph.getV(cur.getKey())) {
                if (!(wThGraph.isVisited(neighbs))) {
                    double dis = cur.getTag() + wThGraph.getEdge(cur.getKey(), neighbs.getKey());
                    if (neighbs.getTag() > dis) {
                        neighbs.setTag(dis);
                        wThGraph.setFather(cur, neighbs);
                        queue.remove(neighbs);
                        queue.add(neighbs);
                    }
                }
            }
            wThGraph.setVisited(cur, true);
        }

    }

    private int BFS(node_info key) {
        this.resetTagZ();
        Queue<node_info> que = new LinkedList<>();
        int tyCom = 1;//tying component
        key.setTag(1);
        que.add(key);
        node_info s;
        while (!que.isEmpty()) {
            s = que.poll();
            for (node_info now : wThGraph.getV(s.getKey())) {
                if (now.getTag() == 0) {
                    now.setTag(1);
                    que.add(now);
                    tyCom++;
                }
            }

        }
        return tyCom;
    }

    private void resetTagZ() {
        for (node_info cur : wThGraph.getV()) {
            cur.setTag(0);
        }
    }

    private void resetTagI() {
        for (node_info cur : wThGraph.getV()) {
            cur.setTag(Integer.MAX_VALUE);
            wThGraph.setVisited(cur,false);
            wThGraph.setFather(null,cur);
        }
    }

    public static void main(String[] args) {
        WGraph_DS g = new WGraph_DS();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(2);
        System.out.println(g.getV().toString());
        g.connect(1, 2, 6);
        g.connect(4, 5, 12);
        g.connect(2, 4, 10);
        g.connect(3, 6, 5);
        g.connect(5, 7, 7);
        g.connect(1, 3, 8);
        g.connect(3, 5, 0);
        System.out.println(g.getV(7));
        System.out.println(g.getV(2));
        System.out.println(g.getNode(5));
        System.out.println(g.hasEdge(12, 4));
        System.out.println(g.nodeSize());
        System.out.println(g.getV(5));
        System.out.println(g.getEdge(1, 2));
        WGraph_Algo h = new WGraph_Algo();
        h.init(g);
        System.out.println(h.shortestPath(2, 5));
        System.out.println(h.shortestPathDist(2,5));
        String file="myfile";
        h.save(file);
        WGraph_Algo k=new WGraph_Algo();
        k.load(file);
        System.out.println(k.getGraph().getV().toString());
    }
}
