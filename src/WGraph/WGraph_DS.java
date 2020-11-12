package WGraph;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> nodeMap;
    private int eCount;
    private int mCount;

    /**
     * an empty constructor for making a new graph
     */
    public WGraph_DS() {
        nodeMap = new HashMap<>();
        eCount = 0;
        mCount = 0;
    }

    /**
     * copy constructor which deep copy's a graph
     * @param other
     */
    public WGraph_DS(weighted_graph other) {
        this.nodeMap = new HashMap<>();
        for (node_info now : other.getV()) {
            this.addNode(new NodeInfo(now.getKey()).key);
        }
        for (node_info k : other.getV()) {
            for (node_info nei : other.getV(k.getKey())) {
                this.connect(k.getKey(), nei.getKey(), other.getEdge(nei.getKey(), k.getKey()));
            }
        }
        this.eCount = other.edgeSize();
        this.mCount = other.getMC();
    }

    @Override
    public node_info getNode(int key) {
        return nodeMap.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!(nodeMap.containsKey(node1)) || !(nodeMap.containsKey(node2)))
            return false;
        if (node1 == node2)
            return false;
        else {
            NodeInfo nodef = (NodeInfo) nodeMap.get(node1);
            return nodef.hasNi(node2);
        }
    }

    @Override
    public double getEdge(int node1, int node2) {
        node_info src = nodeMap.get(node1);
        node_info dest = nodeMap.get(node2);
        if (src == null || dest == null)
            return -1;
        if (!this.hasEdge(node1, node2))
            return -1;
        return ((NodeInfo) src).edges.get(node2);
    }

    @Override
    public void addNode(int key) {
        if (nodeMap.containsKey(key)) {
            return;
        }
        nodeMap.put(key, new NodeInfo(key));
        mCount++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (!(nodeMap.containsKey(node1) && nodeMap.containsKey(node2)) || node1 == node2 || w < 0)
            return;
        NodeInfo src = ((NodeInfo) nodeMap.get(node1));
        NodeInfo dest = ((NodeInfo) nodeMap.get(node2));
        if (src.hasNi(dest.getKey()))
            return;
        src.addNi(dest, w);
        mCount++;
        eCount++;
    }

    @Override
    public Collection<node_info> getV() {
        return nodeMap.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> neighbors = new HashSet<>();
        for (Integer integer : ((NodeInfo) nodeMap.get(node_id)).getNi()) {
            neighbors.add(nodeMap.get(integer));
        }
        return neighbors;
    }

    @Override
    public node_info removeNode(int key) {
        if (this.getNode(key) == null)
            return null;
        NodeInfo del = (NodeInfo) nodeMap.get(key);
        while (del.getNi().size() != 0) {
            NodeInfo tOne = (NodeInfo) getV(del.getKey()).stream().findFirst().get();
            removeEdge(key, tOne.getKey());
        }
        nodeMap.remove(key);
        mCount++;
        return del;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        node_info src = nodeMap.get(node1);
        node_info dest = nodeMap.get(node2);
        if (src == null || dest == null || node1 == node2 || !this.hasEdge(node1, node2))
            return;
        ((NodeInfo) src).removeNode((NodeInfo) dest);
        eCount--;
        mCount++;
    }

    @Override
    public int nodeSize() {
        return nodeMap.size();
    }

    @Override
    public int edgeSize() {
        return eCount;
    }

    @Override
    public int getMC() {
        return mCount;
    }

    /**
     * a function to set a predecessor of a specific node
     *
     * @param father: pred to be set
     * @param son: specific node
     */
    public void setFather(node_info father, node_info son) {
        ((NodeInfo) son).setMyFather((NodeInfo) father);
    }
    /**
     * a function to return a predecessor of a specific node
     * @param son
     *
     */
    public node_info getFather(node_info son) {
        return ((NodeInfo) son).getMyFather();
    }

    /**
     * a function used to check if a specific node is visited
     * used in BFS function
     * @param cur
     * @return
     */
    public boolean isVisited(node_info cur) {
        return ((NodeInfo) cur).isVisited();
    }

    /**
     * a function to set a specific node's visited field
     * @param cur
     * @param vis
     */
    public void setVisited(node_info cur, boolean vis) {
        ((NodeInfo) cur).setVisited(vis);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof weighted_graph)) {

            return false;
        }
        if(this.nodeSize()!=((weighted_graph) obj).nodeSize()||this.edgeSize()!=((weighted_graph) obj).edgeSize())
            return false;
        Iterator<node_info> it =((weighted_graph) obj).getV().iterator();
        for (node_info nd:this.getV()) {
            if(!((NodeInfo)nd).equalsB(it.next()))
                return false;
        }
        return true;
    }

    /**
     * a class which implements the node_info interface
     * used to create a node in a graph
     */
    private class NodeInfo implements node_info, Comparable<node_info>, Serializable {
        private final int key;
        HashMap<Integer, Double> edges;
        private double tag;
        private String info;
        boolean visited;
        private NodeInfo myFather; //a node pointer

        public NodeInfo(int key) {
            edges = new HashMap<>();
            this.key = key;
            this.tag = 0;
            this.info = "";
            visited = false;
            this.myFather = null;
        }

        @Override
        public int getKey() {
            return key;
        }

        /**
         * a function to return all the neighboring nodes keys connected
         * to a specific node
         * @return collection of integer
         */
        public Collection<Integer> getNi() {
            return edges.keySet();
        }

        /**
         * checks if a node is connected to current node
         * @param key
         * @return
         */
        public boolean hasNi(int key) {
            return edges.containsKey(key);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof NodeInfo)) {
                return false;
            }
            NodeInfo other = (NodeInfo) obj;
            return key == other.getKey();
        }

        /**
         * a function to compare between 2 nodes
         * @param other
         * @return
         */
        public boolean equalsB(node_info other){
            if(this.getKey()!=other.getKey()||this.edges.size()!=((NodeInfo)other).edges.size())
                return false;
            return this.edges.equals(((NodeInfo) other).edges);
        }

        /**
         * a function used to connect between current node
         * and another node
         * @param t: other node
         * @param w:weight of the edge
         */
        public void addNi(node_info t, double w) {
            if (this.hasNi(t.getKey())) {
                return;
            }
            this.edges.put(t.getKey(), w);
            ((NodeInfo) t).edges.put(this.getKey(), w);
        }

        /**
         * removes each edge from neighboring nodes and from this specific node
         *
         * @param node:
         */
        public void removeNode(NodeInfo node) {
            if (!this.hasNi(node.getKey())) {
                System.out.println("not connected");
                return;
            }
            edges.remove(node.getKey());
            node.edges.remove(this.getKey());
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {

            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public String toString() {
            return "key: " + this.key + " info: " + this.info;
        }

        /**
         * return a shallow pointer to father node,used in function bfs
         * runTime: (O(1))
         *
         * @return node_data
         */
        public node_info getMyFather() {
            return myFather;
        }

        /**
         * set a shallow pointer to a specific node
         * runTime: (O(1))
         *
         * @param myFather: a shallow pointer to another node
         */
        public void setMyFather(NodeInfo myFather) {
            this.myFather = myFather;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public int compareTo(node_info o) {
            return Double.compare(this.getTag(), o.getTag());
        }
    }

}

