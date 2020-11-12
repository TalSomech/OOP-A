package WGraph;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private WGraph_DS wThGraph;

    /**
     * an empty constructor
     */
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
    /**
     * the algorithm finds all the connected nodes to a source node
     * the algorithm checks weighted paths the neighboring nodes until all nodes are visited
     * and sets every node's predecessor by the shortest path from src node to it
     * runTime:(O(V+E))
     * @param src node
     * @return int
     */
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
    /**
     * the algorithm finds all the connected nodes to a source node
     * the algorithm checks layer by layer the neighboring nodes until all nodes are visited
     * and sets every node's predecessor by the shortest path from src node to it
     * runTime:(O(V+E))
     * @param key node
     * @return int
     */
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
    /**
     * a function to reset all the nodes graph's tags to 0
     * used in BFS algorithm
     */
    private void resetTagZ() {
        for (node_info cur : wThGraph.getV()) {
            cur.setTag(0);
        }
    }

    /**
     * a function to reset all the nodes graph's tags,visited , and predecessor to default
     * used for dijkstra algorithm
     */
    private void resetTagI() {
        for (node_info cur : wThGraph.getV()) {
            cur.setTag(Integer.MAX_VALUE);
            wThGraph.setVisited(cur,false);
            wThGraph.setFather(null,cur);
        }
    }

}
