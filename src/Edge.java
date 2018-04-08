/*
    Author:Allen Simpson
	Date: 4/5/2018
	Overview: Final Edge Class.
 */
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
        return this.startVertex+this.endVertex;
    }

    public int hashCode() {
        return this.startVertex.hashCode() ^ this.endVertex.hashCode();
    }

    public static  String arrayToString(Edge[] edges) {
        StringBuilder sb = new StringBuilder();
        for (Edge e : edges) {
            sb.append(e);
            sb.append(" ");
        }
        return sb.toString();
    }
}