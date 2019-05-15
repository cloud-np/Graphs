package com.company;

import javax.swing.*;
import java.util.Scanner;

public class Main extends JFrame {


    public Main(){

    }
    public static void main(String[] args) {
        //int vertices = 6; Akmes
        Gui gui = new Gui();
       // mainProgram();

        /*
        DEBUGGING
        // Poio grhgoros tropos na dwsoume apla enan grafo..
//        Graph graph = new Graph(6);
//        graph.addEgde(0, 1, 4);
//        graph.addEgde(0, 2, 3);
//        graph.addEgde(1, 3, 2);
//        graph.addEgde(1, 2, 5);
//        graph.addEgde(2, 3, 7);
//        graph.addEgde(2, 0, 10);
//        graph.addEgde(3, 4, 2);
//        graph.addEgde(4, 0, 4);
//        graph.addEgde(4, 1, 4);
//        graph.addEgde(4, 5, 6);
//        graph.printGraph();
        //System.out.println("Is the given graph directed: "+graph.isGraphDirected());
        //System.out.println("For 0-->4 we have: "+graph.getPaths(4, 0));
        //graph.findAllPaths(4, 0);

         */

    }

    private static void mainProgram(){

        Graph graph = null;

        boolean quit = false;
        while(!quit){
            System.out.println("\nPress: \n"+
                    "0. Make a new Graph\n" +
                    "1. Add an Edge.\n" +
                    "2. Print the current graph.\n" +
                    "3. Enter 2 nodes (source - destination).\n" +
                    "4. Is the graph directed\n" +
                    "5. Load a pre-made graph\n" +
                    "6. Quit.");
            int pick = readNum();

            switch (pick){

                case 0:
                    System.out.print("Enter number of vertices: ");
                    int V = readNum();
                    graph = new Graph(V);
                    break;
                case 1:
                    if(graph != null ) {
                        System.out.print("\nSource: ");
                        int source = readNum();
                        System.out.print("\nDestination: ");
                        int destination = readNum();
                        System.out.print("\nWeight: ");
                        int weight = readNum();
                        graph.addEgde(source, destination, weight);
                    }else System.out.println("\nYou have to make a graph first!");
                    break;
                case 2:
                    if(graph != null) graph.printGraph();
                    else System.out.println("\nYou have to make a graph first!");
                    break;
                case 3:
                    System.out.print("\nGive Source node: ");
                    int src  = readNum();
                    System.out.println("\nGive Destination node: ");
                    int dest = readNum();

                    if(src > graph.getVertices() || dest > graph.getVertices()) throw new IllegalArgumentException("Not a valid number!");
                    System.out.println("All the paths from "+src+" to destination " +dest);

                    graph.findAllPaths(src, dest);
                    break;
                case 4:
                    if(graph != null) System.out.println("Is the given graph directed: "+graph.isGraphDirected());
                    else System.out.println("\nYou have to make a graph first!");
                    break;
                case 5:
                    graph = loadGraph();
                    break;
                case 6:
                    quit = true;
                    //System.exit(1);
                    break;
            }
        }
    }


    private static Graph loadGraph(){

       // /*
        Graph graph = new Graph(6);
        graph.addEgde(0, 1, 4);
        graph.addEgde(1, 2, 3);
        graph.addEgde(4, 3, 2);
        graph.addEgde(1, 2, 5);
        graph.addEgde(2, 3, 7);
        graph.addEgde(2, 0, 10);
        graph.addEgde(3, 4, 2);
        graph.addEgde(3, 0, 4);
        graph.addEgde(0, 5, 4);
        graph.addEgde(4, 5, 6);
        //graph.printGraph();

        // */

        return graph;
    }

    private static int readNum(){
        Scanner in = new Scanner(System.in);
        int n;
        do{

            n = in.nextInt();
            in.nextLine();
            if(n < 0) System.out.println("Enter a psotive number!");
        }while(n < 99999999);
        return n;
    }


}