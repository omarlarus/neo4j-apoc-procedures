package apoc.algo;

import apoc.Description;
import apoc.path.PathExplorer;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Algo {

    @Procedure
    @Description("")
    public Stream<WeightedPathContainer> dijekstra(
            @Name("startNode") Node startNode,
            @Name("endNode") Node endNode,
            @Name("weightPropertyName") String weightPropertyName) {

        Iterable<WeightedPath> allPaths = GraphAlgoFactory.dijkstra(
                PathExpanders.allTypesAndDirections(),
                weightPropertyName
        ).findAllPaths(startNode, endNode);
        return StreamSupport.stream(allPaths.spliterator(), false)
                .map( weightedPath -> new WeightedPathContainer(weightedPath) );
    }

    public static class WeightedPathContainer {
        public Path path;
        public double weight;

        public WeightedPathContainer(WeightedPath weightedPath) {
            this.path = weightedPath;
            this.weight = weightedPath.weight();
        }
    }
}
