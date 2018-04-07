/*
Author: William Kingsley, Allen Simpson
	Date: 4/7/2018
	Overview: Kruskal Algorithm Final Implementation.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Kruskalv2 {
    class Cluster{
        HashMap<String,HashSet<String>> sets = new HashMap<String,HashSet<String>>();
        
        public Cluster (String[] verticies) {
            for (String s : verticies) {
                HashSet<String> hS = new HashSet<String>();
                hS.add(s);
                sets.put(s, hS);
            }
        }        
        public HashSet<String> get(String vertex) {
            return (HashSet<String>)sets.get(vertex);
        }
        public void merge (HashSet<String> A, HashSet<String> B) {
            HashSet<String> merged = new HashSet<String>();
            for (String s : A) {
                merged.add(s);
            }
            for (String s : B) {
                merged.add(s);
            }
            for (String e : merged){
                sets.put(e, merged);
            }
        }
    }
    public Edge[] kruskal(AdjacencyMatrix am){
        ArrayList<Edge> t = new ArrayList<Edge> ();
        PriorityQueue<WEdge> q= new PriorityQueue<WEdge>();
        Cluster clusters = new Cluster(am.vertices);
        for (WEdge we : am.edges){
            q.add(we);
        }
        while (t.size()<am.vertices.length-1) {
            WEdge we = q.poll();
            HashSet<String> cU = clusters.get(we.startVertex);     
            HashSet<String> cV = clusters.get(we.endVertex);
            if (!cU.equals(cV)) {
                t.add(new Edge(we.startVertex,we.endVertex));
                clusters.merge(cU, cV);
            }
        }
        return (Edge[])t.toArray(new Edge[t.size()]);
    }
}