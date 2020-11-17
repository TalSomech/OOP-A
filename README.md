
	An implemantation of an weighted Undirectional graph in java.

Some Info: 
the graph is built by 3 layer

The Node layer which includes:

-the edges are in a hashmap which includes the connected node
and the weight of the edge


-getKey function which getting the key

-equalsB function to compare between 2 diffrent nodes

-getNi function which returns all the neighboring nodes

-hasNi which checks by a key if a specific node is connected to a current node

-addNi which is used to connect a node the current node

-removeNode which disconnects a node from the current node

-Get and Set info and tag which used to return or change the current node's info or tag

-Get and Set for a father node , used only for BFS algorithm

The Graph layer which includes:

-2 Diffrent constructors:

*One made for creating a new graph

*One made for deep copying a graph

-equals funcion to compare between 2 diffrent graphs

-getNode which returns a node in the graph

-hasEdge which checks if 2 nodes are connected

-addNode adds a node to the graph

-connect which connects the 2 nodes

-getV which returns all the nodes in the graph

-getV with a integer paramater to return the neighboring nodes to a specific nodes

-removeNode function to remove a node from the graph

-removeEdge function to disconnect 2 diffrent nodes

-NodeSize , edgeSize and getMC which returns the node size , edge size , and the number of changes made to the graph


The Graph Algorithms which includes:

-init which initialize the graph

-copy , to deep copy the graph

-isConnected to check if the graph is connected using BFS algorithm

-shortestPathDist which checks the shortest number of edges needed to get from a specific node to a diffrent node using dijkstra algorithm

-shortestPath which returns a shallow pointer to all the nodes on the shortest path between 2 nodes using dijkstra algorithm

-save function to save the graph using Objectoutputwriter onto harddrive

-load function to load a graph from the harddrive using objectinputreader

How to use the program:

WGraph_Algo h = new WGraph_Algo();

WGraph_DS g =new WGraph_DS();

h.init(g);

g.addnode(5);

g.addnode(6);

g.addnode(7);

g.addnode(8);

g.connect(5,6,2);

g.connect(7,6,3);

g.connect(8,7,4);

h.isConnected;//true

h.shortestPathDistance(6,8)//3+4=7

g.remove(6,7);

h.isConnected//false
