import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Predicate;
import java.lang.Comparable;

/**
*
* @author r29b821
*/
public class PrimJarnik {
    
    class W {
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
    
    class Connect {
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
    
    class D {
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
    
    class Q {
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
    
    class T {
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
    
    
    
    class Edge implements Comparable {
        String startVertex;
        String endVertex;
    
        public Edge(String startVertex, String endVertex) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
        }
    
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o instanceof Edge) {
                Edge e = (Edge) o;
                return this.startVertex == e.startVertex && this.endVertex == e.endVertex;
            } else return false;
        }
    
        public int compareTo(Object o) {
            if (o instanceof Edge) {
                Edge e = (Edge) o;
                if (this.startVertex == e.startVertex) {
                    return this.endVertex.compareTo(e.endVertex);
                } else return this.startVertex.compareTo(e.startVertex);
            } return -1;
        }
    
        public String toString() {
            return "("+this.startVertex+","+this.endVertex+")";
        }
    
        public int hashCode() {
            return this.startVertex.hashCode() ^ this.endVertex.hashCode();
        }
    
        public  String arrayToString(Edge[] edges) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Edge e : edges) {
                sb.append(e);
                sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }
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

