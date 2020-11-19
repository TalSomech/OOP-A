
# An implemantation of an weighted Undirectional graph in java.

**Some Info:** 
the graph is built by 3 layer

__The Node layer which includes:__

-the edges are in a hashmap which includes the connected node
and the weight of the edge


__getKey__ function which getting the key

__equalsB__ function to compare between 2 diffrent nodes

__getNi__ function which returns all the neighboring nodes

__hasNi__ which checks by a key if a specific node is connected to a current node

__addNi__ which is used to connect a node the current node

__removeNode__ which disconnects a node from the current node

__Get__ and Set info and tag which used to return or change the current node's info or tag

__Get__ and Set for a father node , used only for BFS algorithm

##The Graph layer which includes:

-2 Diffrent constructors:

**One made for creating a new graph**

**One made for deep copying a graph**

__equals__ funcion to compare between 2 diffrent graphs

__getNode__ which returns a node in the graph

__hasEdge__ which checks if 2 nodes are connected

__addNode__ adds a node to the graph

__connect__ which connects the 2 nodes

__getV__ which returns all the nodes in the graph

__getV__ with a integer paramater to return the neighboring nodes to a specific nodes

__removeNode__ function to remove a node from the graph

__removeEdge__ function to disconnect 2 diffrent nodes

__NodeSize__ , edgeSize and getMC which returns the node size , edge size , and the number of changes made to the graph


##The Graph Algorithms which includes:

__init__ which initialize the graph

__copy__ , to deep copy the graph

__isConnected__ to check if the graph is connected using BFS algorithm

__shortestPathDist__ which checks the shortest number of edges needed to get from a specific node to a diffrent node using dijkstra algorithm

__shortestPath__ which returns a shallow pointer to all the nodes on the shortest path between 2 nodes using dijkstra algorithm

__save__ function to save the graph using Objectoutputwriter onto harddrive

__load__ function to load a graph from the harddrive using objectinputreader

##How to use the program:

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
