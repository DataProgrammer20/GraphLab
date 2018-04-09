/*
    Author: Allen Simpson
	Date: 4/4/2018
	Overview: PrimJarnik Algorithm Implementation.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Comparable;

public class PrimJarnik {
    //The various classes below are designed to allow for the algorithm (at the bottom of the file)
    //to resemble the pseudocode as closely as possible
    class W { // The Array of Weighted Edges
        HashMap startingVertex = new HashMap();
        public W(WEdge[] edges) {
            for(WEdge edge : edges) {
                HashMap endingVertex = (HashMap)startingVertex.get(edge.startVertex);
                if (endingVertex == null) {
                    endingVertex = new HashMap();
                    endingVertex.put(edge.endVertex,edge.weight);
                    startingVertex.put(edge.startVertex, endingVertex);
                } else {
                    endingVertex.put(edge.endVertex,edge.weight);
                }
            }
        }
    
        public int get(String u, String v) {
            HashMap ev = (HashMap)startingVertex.get(u);
            if (ev==null) return Integer.MAX_VALUE;
           Object w = ev.get(v);
            if (w == null) return Integer.MAX_VALUE;
            return ((Integer)w).intValue();
        }
    }
    
    class Connect { //Implementation of the "connect" function
        HashMap map = new HashMap();
        public Connect() {
            this.map = new HashMap();
        }
        public Edge get(String e) {
           return (Edge)map.get(e);
        }
    
        public void add(String v, String u) {
            map.put(v,new Edge(u,v));
        }
    }
    
    class D { //Hashmap of all Vertex Weighted Edge pairs
        HashMap map = new HashMap();
        public D() {
            this.map = new HashMap();
        }
    
        public int get(String v) {
            Object o = map.get(v);
            if (o==null) return Integer.MAX_VALUE;
            return ((Integer)o).intValue();
        }
    
        public void add(String v, int w) {
            map.put(v,w);
        }
    }
    
    class Q { //Priority Que Implementation (This que has a resort method defined in it)
        class Tuple implements Comparable {
            int weight;
            String vertex;
            public Tuple(String vertex, int weight) {
                this.vertex = vertex;
                this.weight = weight;
            }
            public int compareTo(Object o) {
                if (o instanceof Tuple) {
                    Tuple t = (Tuple) o;
                    return this.weight - t.weight;
                } return -1;
            }
        }
        ArrayList list = new ArrayList();
        public Q() {
        }
    
        public String dequeue() {
            if (list.isEmpty()) return null;
            return ((Tuple)list.remove(0)).vertex;
        }
    
        public boolean isEmpty() {
            return list.isEmpty();
        }
    
        public void enqueue(String v, int w) {
            list.add(new Tuple(v,w));
        }
    
        public void updateTuple(String v, int w) {
            for(int i = 0; i < list.size(); i++) {
                Tuple t = (Tuple)list.get(i);
                if (t.vertex == v) {
                    t.weight = w;
                    return;
                }
            }
        }
    
        public void resort() {
            list.sort(null);
        }
    
        public int size() {
            return list.size();
        }
    
        public String get(int i) {
            return ((Tuple)list.get(i)).vertex;
        }
    }
    
    class T {   //The Tree "T" Implementation
        ArrayList list = new ArrayList();
        public T() {
    
        }
        public void add(String u, String v) {
            list.add(new Edge(u,v));
        }
        public Edge[] toArray() {
            return (Edge[])list.toArray(new Edge[list.size()]);
        }
    }
    //The PrimJarnik Algorithm'm actual implementation
    public  Edge[] primJarnik(AdjacencyMatrix adjacencyMatrix, String startVertex) {
        W w = new W(adjacencyMatrix.edges);
        D d = new D();
        for(String v : adjacencyMatrix.vertices) {
            d.add(v, Integer.MAX_VALUE);
        }
        d.add(startVertex,0);
        T t = new T();
        Q q = new Q();
        Connect connect = new Connect();
        for(String v : adjacencyMatrix.vertices) {
            q.enqueue(v, d.get(v));
        }
        q.resort();
        while (!q.isEmpty()) {
            String u = q.dequeue();
            Edge edge = connect.get(u);
            if (edge!=null) {
                t.add(edge.startVertex, edge.endVertex);
            }
            for(int j = 0; j < q.size(); j++) {
                String v = q.get(j);
                int weight = w.get(u,v);
                if (weight < d.get(v)) {
                    d.add(v, weight);
                    connect.add(v, u);
                    q.updateTuple(v, weight);
                }
            }
            q.resort();
        }
        return t.toArray();
    }    
    
}