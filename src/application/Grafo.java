package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

public class Grafo {
private  DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> grafo ;
	
	public void buildGraph(){
		grafo = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		grafo.addVertex("a");
		grafo.addVertex("b");
		grafo.addVertex("c");
		grafo.addVertex("d");
		grafo.addVertex("e");
		grafo.addVertex("f");
		
		Graphs.addEdge(grafo, "a",  "b", 2);
		Graphs.addEdge(grafo, "a",  "c", 4);
		Graphs.addEdge(grafo, "b",  "c", 1);
		Graphs.addEdge(grafo, "b",  "d", 2);
		Graphs.addEdge(grafo, "c",  "e", 3);
		Graphs.addEdge(grafo, "d",  "c", 3);
		Graphs.addEdge(grafo, "d",  "f", 4);
		Graphs.addEdge(grafo, "e",  "f", 1);
		Graphs.addEdge(grafo, "f",  "a", 2);
	
		System.out.println(grafo.toString());
	}
	

	public  double getPeso(String vertice){
		double tot=0;
		Set <DefaultWeightedEdge> archi = grafo.edgesOf(vertice);
		for(DefaultWeightedEdge a : archi){
			 tot += grafo.getEdgeWeight(a);
		}
		System.out.println(tot);
		return tot;
	}
	
	public List<String> getCamminoMinimo(String start, String end){
		DijkstraShortestPath<String, DefaultWeightedEdge> di = new DijkstraShortestPath<String, DefaultWeightedEdge>(grafo, start, end);
		GraphPath <String, DefaultWeightedEdge> path= di.getPath();
		if(path==null){
			System.out.println("null");
			return null;
		}
		System.out.println(Graphs.getPathVertexList(path));
		return Graphs.getPathVertexList(path);	
	}

	public List<DefaultWeightedEdge> getCamminoMinimo2(String start, String end){
		DijkstraShortestPath<String, DefaultWeightedEdge> di = new DijkstraShortestPath<String, DefaultWeightedEdge>(grafo, start, end);
		List<DefaultWeightedEdge> pathEdgeList = di.getPathEdgeList();
		if(pathEdgeList==null){
			System.out.println("null");
			return null;
		}
		//System.out.println(pathEdgeList);
		return pathEdgeList;
	}
	
	public String getBoh(String start, String end){
		StringBuilder risu = new StringBuilder();
		risu.append("percorso : [ ");
		for(DefaultWeightedEdge arco : getCamminoMinimo2(start, end)){
			risu.append(grafo.getEdgeTarget(arco).toString());
			risu.append(", ");
		}
		risu.setLength(risu.length()-2);
		risu.append("]");
		System.out.println(risu.toString());
		return risu.toString();
	}
	
	public String getcamminoBell(String start){
		BellmanFordShortestPath<String, DefaultWeightedEdge> bellman = new BellmanFordShortestPath<String, DefaultWeightedEdge>(grafo, start) ;		
		Map<String, Double> distanze = new HashMap<>() ;	
		for( String arrivo : grafo.vertexSet() ) {
			if( ! arrivo.equals(start )) {
				double peso = bellman.getCost(arrivo) ;			
				distanze.put(arrivo, peso) ;
			}
		}	
		String testo = "" ;
		for( String v: distanze.keySet() ) {
			testo = testo + v.toString()+ " = " + distanze.get(v)+ "\n" ;
		}	
		System.out.println(testo);
		return testo ; 
	
	}
	
	public String  getStartEnd(String start, String end){    //non parte
		String testo = "-1";
		List<DefaultWeightedEdge> archi= getCamminoMinimo2(start, end);
		for(DefaultWeightedEdge a : archi){
			String inizio = grafo.getEdgeSource(a).toString();
			String fine   = grafo.getEdgeTarget(a).toString();
			testo = " " +inizio+ "  " +fine+ " \n";
		}
		return testo;
	}
	
	public String getStart(DefaultWeightedEdge arco){
		String start = grafo.getEdgeSource(arco);
		System.out.println(start);
		return start;
	}
		
	public double getCamminoMinimo3(String start, String end){
		DijkstraShortestPath<String, DefaultWeightedEdge> di = new DijkstraShortestPath<String, DefaultWeightedEdge>(grafo, start, end);
		double  path = di.getPathLength();
		System.out.println(path);
		return path;
	}
	
	public List<String> getCamminoFloyd(String start, String end){
		FloydWarshallShortestPaths<String, DefaultWeightedEdge> floy = new FloydWarshallShortestPaths<String, DefaultWeightedEdge> (grafo);
	    System.out.println( Graphs.getPathVertexList(floy.getShortestPath(start,  end)));
		return Graphs.getPathVertexList(floy.getShortestPath(start,  end));
	}
	
	public List<String> getCammFloyd2(String start, String end){
		FloydWarshallShortestPaths<String, DefaultWeightedEdge> floy = new FloydWarshallShortestPaths<String, DefaultWeightedEdge> (grafo);	
		GraphPath<String, DefaultWeightedEdge> path = floy.getShortestPath(start,  end);
		if(path==null){
			return null;
		}
		List<String> percorso = Graphs.getPathVertexList(path);
		System.out.println(percorso);
		return percorso;
	}
	
	
	
//	public List<String> getCamminoSingolaFloyd(String start){
//		for(String v : grafo.vertexSet()){
//			FloydWarshallShortestPaths<String, DefaultWeightedEdge> floy = new FloydWarshallShortestPaths<String, DefaultWeightedEdge> (grafo);	
//		    GraphPath<String, DefaultWeightedEdge> path = floy.getShortestPath(start, v);
//		  //  List<String> cammini = Graphs.successorListOf(grafo,  start);   //b c
//		  //  List<String> cammini = Graphs.predecessorListOf(grafo,  start);   //f
//		//    List<String> cammini = Graphs.
//		    System.out.println(cammini);
//			return cammini;
//		  }
//		return null;
//	}
//	
//	public List<String>getCamminoBell(String start, String end){
//		BellmanFordShortestPath<String, DefaultWeightedEdge> bellman = new BellmanFordShortestPath<String, DefaultWeightedEdge>(grafo, start, end) ;	
//	}
//	
	
	public List<String> getNonRagg(){
		List<String> non = new LinkedList<>();
		for(String s : grafo.vertexSet()){
			if(getNumeConnessioni(s)==0){
				non.add(s);
			}
		}
		System.out.println(non);
		return non;
	}
	
	private int getNumeConnessioni(String s){
		int num=0;
		num = grafo.inDegreeOf(s);
		//System.out.println(num);
		return num;
	}

	public double getArcoPiuPesante(){
		double max=0;
		for(DefaultWeightedEdge arco : grafo.edgeSet()){
			if(grafo.getEdgeWeight(arco)> max){
				max = grafo.getEdgeWeight(arco);
				
			}
		}
		System.out.println(max);
		return max;
	}
	
	public int getNumeroComponentiConnesse(){
		int num=0;
		List<String> vertici = new LinkedList<>();
		vertici.addAll(grafo.vertexSet());
		while(!vertici.isEmpty()){
			String primo= vertici.get(0);
			BreadthFirstIterator<String, DefaultWeightedEdge> visita = new BreadthFirstIterator<String, DefaultWeightedEdge>(grafo, primo);
			while(visita.hasNext()){
				String secondo = visita.next();
				vertici.remove(vertici.indexOf(secondo));
			}
			num++;
		}
		System.out.println(num);
		return num;
	}
	
	public static void main(String [] args){
		Grafo g = new Grafo();
		g.buildGraph();
		//g.getCamminoMinimo("a", "f");
		//g.getCamminoMinimo2("a",  "f");
		//g.getCamminoMinimo3("a",  "f");
		//g.getCamminoMinimo3("c", "f");
		//g.getArcoPiuPesante();
		//g.getCamminoFloyd("a",  "f");
		//g.getCammFloyd2("a",  "f");
		//g.getNonRagg();
		//g.getNumeroComponentiConnesse();
		//g.getBoh("a",  "f");
		//g.getCamminoMinimo3("a",  "f");
		//g.getcamminoBell("a");
		//g.getcamminoBell("f");
		//g.getStartEnd("a", "f");
		//g.getBoh("a",  "f");
		//g.getCamminoSingolaFloyd("a");
		g.getPeso("f");
	}
}
