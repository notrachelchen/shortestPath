Edge class represents an edge with 3 characteristics: edgeId(unique identifier for the edge), weight(represent the distance of traversing the edge ) and nodes(an array of two nodes that the edge connects)

Node class represents the vertex in a graph. This file holds the data of the vertex through a string and a array of edges that the vertex connects with 

Graph class represents the graph data structure with Nodes, Edges and adjacency matrix. All the road network data 
is stored in this class and it provides the fuctionality of building a graph data structure from the road data 
The class contains different characteristics such as V(array of nodes), adj(adjacency matrix that stores edges), numNodes(number of nodes in the graph), nodeMap(hashMap that maps a node ID to the node object) and region(contains all the road network data.).
The class also contains a constructor that tales a Region object as an input and intializes the Graph obect based on the road data. 
The buildGraph method is used to build the graph data structure by creating a new node for each intersection and connection the intersection to form edges
The buildAdjacency method fills the adj matrix with the edges between nodes 
The getWeight method returns the distance between the corresponding intersection in the road data
The getRoadId method returns the road ID for the given intersection IDs
The dijkstra method perfoms dijkstra's algorithim on the graph in order to find the shortest path between 2 nodes. 
It uses the relax method which updates the distance and parent characteristics of a node during running. The initalize_single_source method 
initializes the distance of all nodes before the algorithim runs. The buildQueue method creates a priority queue which the algorithim uses 
to select the node with the minimum distance.

Intersection class, this class defines and stores information about the intersection on a map by using ID, latitude and longitude and it returns the intersection ID as a string 

Key class represents a custom key that can be used to store and retrieve values in a HashMap with 2 keys. The class has two instance variable: key1 and key2 that are of 
generic type K1 and K2. It creates a HashMap with Key as the key type and then adds two key-value pair to the hashmap with keys that are two different keys each.
The program then prints the HashMpa and retrieves a value using one of the keys

MapPanel class represents the panel where the map is displayed and it contains the functionality to draw the map and 
highlight paths found by the dijkstra algorithim. The setParams(Region region) method sets the values for the minLat, minLong, maxLat, maxLong, roads, and intersectionMap variables based on a Region object passed as an argument. The paintComponent(Graphics page) method is responsible for rendering the map and paths using 2D graphics, and the scale() method updates the scales of the map based on the panel size and the latitude and longitude values of the map

The region class represents a region on a map and contains information about the intersections and raods within that region.
The class contains 4 instance variables: intersectionMap(map that maps intersection IDs to intersection objects), roadMap(map that maps road IDs to Road objects), roads(list of all the Road objects in that region), intersectionsToRoadMap(map that maps a pait of intersection IDs to a road ID)
The insertToRoadMap adds a new road to the region by creating a new Road object and adding it to the roadMap, adding the road ID to the intersectionsToRoadMap map, and updating the distance of the road using the setDistance() method.
The insert method adds a new Intersection object to the intersectionMap and updates the max and min latutide and longitude of the region
The setDistance method calculates the distance between 2 intersection which is the edge by using the calcDist() method
The calcDist() method calculates the distance between 2 points using the Haversine formula 

The Road class represents a road that connects two intersections (represented by their IDs) and stores its ID and distance.

The StreetMap class loads and displays the map of a region. The readFile method reads data from a file contaning information about the regions intersections and roads 
The doShow method creates a new MapPanel object with the current region, adds it  to the frame object and displays the frame 
The doDirection methods sets the startIntersection and endIntersection instance variable
Inside the main method, a new StreetMap object is created and reads the name of the file to load from the command line argumenets. It processes the arguments to display directions 
between two intersections. This class provides the interface for interacting with the region's map and finding directions between intersections.


