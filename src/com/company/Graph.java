package com.company;

import java.util.*;

public class Graph {
    private int vertices; // Akmes
    private final LinkedList<Edge> [] adjacencylist; // Adjacency List
    private Set<Node> nodes = new HashSet<>();
    private final int[][] adjacencyMatrix;  // Adjacency Matrix
    private List<String> allPaths;
    private int pathsCounter;


    public Graph(int vertices) {
        if(vertices < 0 ) throw new IllegalArgumentException("You can't enter a negative number of vertices!");
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];
        adjacencyMatrix = new int[vertices][vertices];

        Random r = new Random();
        //initialize adjacency lists for all the vertices
        // and adding colors to them
        for (int i = 0;i < vertices; i++) {
            nodes.add(new Node(i));
            adjacencylist[i] = new LinkedList<>();
        }
    }

    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        settingNewEdge(source, destination, weight);
        adjacencyMatrix[source][destination] = weight;
        adjacencylist[source].addFirst(edge); //for directed graph
    }

    // This one helps with getting the nodes of the set.
    public Node gettingNode(int nodeID){
        Node node = new Node(-1);
        for(Iterator<Node> it = nodes.iterator(); it.hasNext(); node = it.next())
            if(node.getSource() == nodeID) return node;
        if(node.getSource() == -1) System.out.println("BUGGGGGGGG\n\n");
        return node;
    }

    // This one is like adding an edge more of less.
    public void settingNewEdge(int source, int destination, int weight){
        Node sourceNode = gettingNode(source);
        Node destinationNode = gettingNode(destination);
        sourceNode.addDestination(destinationNode, weight);
    }

    public int isEdge(int source, int destination) {
        if (source >= 0 && source < vertices && destination >= 0 && destination < vertices)
            return adjacencyMatrix[source][destination];
        else return -1;
    }

    public boolean isGraphDirected(){
        for(int i = 0;i < vertices; i++)
            for(int j = 0; j < vertices; j++)
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) return true;
        return false;
    }
    /*
    public String lowestCostPath(int source, int destination){

        findAllPaths(source, destination);

        if(allPaths.size() <= 0) return null;
        String lowestCostPath = allPaths.get(0);
        ArrayList<Integer> weights = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        // For every path
        for(int i = 0;i < allPaths.size(); i++){

            // For every single character in the path
            String nextWord = allPaths.get(i);
            for(int j = 0;j < nextWord.length(); j++){
                if(nextWord.charAt(j) == '-' || nextWord.charAt(j) == '>') continue;
                else {
                   int k = j;
                   String tmp = "";
                   // This usually won't run more than 3 times ( only if we have
                   // a number of nodes/vertices higher than that).
                   while(nextWord.charAt(k) != '-' && nextWord.charAt(k) != '>'){
                       tmp += nextWord.charAt(k);
                       k++;
                   }
                    weights.add(Integer.parseInt(tmp));
                }
            }
            for()
        }
        // Keeping track if there is a red Node in the path.
        //if(colorOfVertice[i].equals("Red"))
         //   doesPathHaveRed.add(i,true);

        return null;
    }*/
    public Graph lowestCostPath(Graph graph, Node source){

        // Implementing Dijkstra's algorithm.
        // Setting all distances to "infinity".
        // (Already did that when creating the nodes)

        // Setting staring node distance to 0
        source.setWeight(0);

        // We are using Sets because compared to lists they
        // only allow unique entries.
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);
        /////////////////////////////
        // While the unsettled nodes set is not 0
        while(unsettledNodes.size() != 0){
            Node currentNode = getClosestNode(unsettledNodes);
            // getting every path from the current node to a near by node.
            for(Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()){
                // We get the node and the weight of the edge
                Node adjNode = adjacencyPair.getKey();
                int edgeWeight = adjacencyPair.getValue();

                // if the node IS NOT in the settledNodes
                // then we calculate the next minimum distance
                if(!settledNodes.contains(adjNode)){
                    calculateMinDist(adjNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjNode);
                }
            }
            settledNodes.add(currentNode);
        }

        return graph;
    }

    // It will find the lowestDistance - weight from all
    // the unsettledNodes we have atm.
    private Node getClosestNode(Set<Node> unsettledNodes){
        Node lowestWeightNode = null;
        int lowestWeight = Integer.MAX_VALUE;
        for(Node node : unsettledNodes){
            int nodeWeight = node.getWeight();
            if(nodeWeight < lowestWeight){
                lowestWeight = nodeWeight;
                lowestWeightNode = node;
            }
        }
        return lowestWeightNode;
    }

    public void calculateMinDist(Node evaluationNode, int edgeWeight, Node sourceNode){
        int tmp = sourceNode.getWeight() + edgeWeight;
        if( tmp < evaluationNode.getWeight()){
            evaluationNode.setWeight(tmp);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }


    /*
    public void lookGeitones(int removed){

        int edgeDistance = -1;
        int newDistance  = -1;

        // Gia olous tous geitones tou removed.
        for(int i = 0;i < adjacencylist[removed].size(); i++){
            int nodeID = adjacencylist[removed].get(i).destination;

            if(!settled.contains(nodes.get(nodeID).source)){
                edgeDistance =
            }
        }

     */

    public String findAllPaths(int source , int destination){

        // initializing some basic variables for to find
        // all the paths between 2 nodes.
        this.allPaths = new ArrayList<>();
        this.pathsCounter = 0;
        boolean[] visited = new boolean[vertices];
        visited[source] = true;

        // Main algorithm
        getPaths(source, destination, "", visited);

        // Returning all the paths in a String format.
        String tmp = "<html><h1 style = color:#ffcc00;>All paths </h1><p>";
        for(String path : allPaths)
            tmp += "\n"+path;
        return tmp;
    }
    public void getPaths(int source, int destination, String path, boolean[] visited){
        String newPath = path +  "->" + source;
        visited[source] = true;
        LinkedList<Edge> list = adjacencylist[source];

        for (Edge edge : list){
            if(edge.destination != destination && visited[edge.destination] == false){
                getPaths(edge.destination, destination, newPath, visited);
            }else if(edge.destination == destination){
                if(allPaths.size() != 0){
                    // If I add to my List while i am in the for
                    // sometimes it will produce a ConcurrentModificationException
                    // So I keep a "remainder" and i add the path/String later.
                    boolean concurrentSolution = false;

                    // If tmp != from the rest paths so far add it
                    // to the list
                    for(String s : allPaths){
                        String tmp = newPath + "->" + edge.destination;
                        if(tmp != s) concurrentSolution = true;
                    }
                    if(concurrentSolution)
                        allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                }else{
                    allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                }
                System.out.println(newPath + "->" + edge.destination);
            }
        }
        //remove from path
        visited[source] = false;
    }
    public String shortestPath(int source, int destination){

        // First we find all the paths.
        findAllPaths(source , destination);

        // Then we just check for their lengths.
        String shortestPath;
        if(allPaths.size() > 0){
            shortestPath = allPaths.get(0);
            for(int i = 0; i < allPaths.size(); i++)
                if(allPaths.get(i).length() < shortestPath.length())
                    shortestPath = allPaths.get(i);
        }else shortestPath = null;
        return shortestPath;
    }
    public int getVertices(){
        return vertices;
    }
    public String isGraphIsomorphic(){
        String text = "<html><h1 style = color:#057aa0;>Is Current Graph isomorphic?</h1><p>";

        boolean isIsomorphic = true;

        for(int i = 0;i < vertices; i++){
            for(int j = 0;j < vertices; j++){
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]){
                    isIsomorphic = false;
                    break;
                }
            }
        }
        text += ""+isIsomorphic;
        return text;
    }
    public String areNodesConnected(){
        String notConnected = "<html><h1 style = color:red;> Not Connected Nodes!</h1><p>";

        int count = 0;
        System.out.println("Not Connected Nodes!");
        for(int i = 0;i < vertices; i++){
            LinkedList<Edge> list = adjacencylist[i];

            if(list.size() == 0) {
                notConnected += "V"+i+"\n";
                System.out.println("V"+i);
            }else count++;
        }

        if(count == vertices)
            notConnected = "<html><h1 style = color:#06c666;>All Connected!</h1><p>All the nodes are connected to the Graph";
        return notConnected;
    }
    public String printGraph(){

        // ADJACENCY LIST PRINT
        String myText = "<html><h1 style = color:#0c6d62;>\tList and Matrix</h1><p>";
        myText += "\n\tADJACENCY LIST\n\n";
        System.out.println("\n\t ADJACENCY LIST");
        for (int i = 0;i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            myText += "V"+i;
            System.out.print("V"+i);
            for (int j = 0; j < list.size() ; j++) {
                myText += "---> " +
                        list.get(j).destination + "(" +  list.get(j).weight+"W) ";

                System.out.print("---> " +
                        list.get(j).destination + "(" +  list.get(j).weight+"W) ");
            }
            myText += "\n";
            System.out.println();
        }



        // ADJACENCY MATRIX PRINT
        myText +="\nADJACENCY MATRIX\n\n     ";
        System.out.println("\n");
        System.out.println("\tADJACENCY MATRIX");
        System.out.print("    ");
        for(int k = 0;k<vertices;k++){
            if(k < 10){
                myText += "0"+k+"  ";
                System.out.print("0"+k+"  ");
            } else {
                myText += ""+k+"  ";
                System.out.print(""+k+"  ");
            }
        }

        myText+= "\n";
        System.out.println("\n");

        for(int i = 0;i < vertices; i++){
            for(int j = 0;j < vertices; j++){
                if(j == 0){
                    if(i < 10){
                        myText += "0"+i+"|  ";
                        System.out.print("0"+i+"|  ");
                    } else {
                        myText += ""+i+"|  ";
                        System.out.print(i+"|  ");
                    }
                }
                myText += adjacencyMatrix[i][j] + "    ";
                System.out.print(adjacencyMatrix[i][j] + "   ");
            }
            myText += "\n";
            System.out.println();
        }

        return myText;
    }

    // Inner class Nodes
    static class Node {
        private int node;
        private int weight;
        Map<Node, Integer> adjacentNodes;
        private List<Node> shortestPath;
        private Random r = new Random();
        private boolean isRed;

        public Node(int node){
            this.node = node;
            this.isRed = r.nextBoolean();
            this.adjacentNodes = new HashMap<>(); // HashMap to keep info for every neighbors
            this.weight = Integer.MAX_VALUE;  // Initializing the weight to infinity
            this.shortestPath = new LinkedList<>(); // This will help to get the shortest path from the starting node
        }

        public void addDestination(Node destination, int weight){
            adjacentNodes.put(destination, weight);
        }

        // Getters Setters
        public Map<Node, Integer> getAdjacentNodes(){ return this.adjacentNodes; }
        public List<Node> getShortestPath(){ return  shortestPath;}
        public void setShortestPath(List<Node> shortestPath){ this.shortestPath = shortestPath; }
        private void setWeight(int amount){ this.weight = amount;}
        public int getSource() { return node; }
        public void setSource(int source) { this.node = source; }
        public int getWeight() { return weight; }
        public boolean isRed() { return isRed; }
    }




    // Inner class Edge
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}


