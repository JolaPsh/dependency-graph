package application.dependencygraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.dependencygraph.GraphNode.GraphNodeState;;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public class DependencyGraph<T> {

	private Map<T, GraphNode<T>> nodesAndDependencies = new HashMap<>();

	public DependencyGraph() { }

	public void addDependencies(T source, List<T> targets) {
		for (T target : targets) {
			addDependency(source, target);
		}
	}

	private void addDependency(T source, T target) {
		GraphNode<T> sourceNode = null;
		GraphNode<T> targetNode = null;
		if (hasNode(source)) {
			sourceNode = getNode(source);
		} else {
			sourceNode = createNode(source);
			addVertex(source, sourceNode);
		}
		if (hasNode(target)) {
			targetNode = getNode(target);
		} else {
			targetNode = createNode(target);
			addVertex(target, targetNode);
		}
		sourceNode.addSuccessors(targetNode);
	}

	public GraphNode<T> getNode(T data){
		return nodesAndDependencies.get(data);
	}

	public boolean hasNode(T node) {
		return nodesAndDependencies.containsKey(node);
	}

	private void addVertex(T data, GraphNode<T> node) {
		nodesAndDependencies.put(data, node);
	}

	public void removeVertex(T data) {
		GraphNode<T> vertex = getNode(data);
		if (vertex == null) {
			throw new NotFoundException();
		}
		nodesAndDependencies.remove(data);		
		Set<T> allNodes = nodesAndDependencies.keySet();
		Iterator<T> iter = allNodes.iterator();
		while (iter.hasNext()) {
			GraphNode<T> node = getNode(iter.next());
			if (successors(node).contains(vertex)) {
				node.removeSuccessors(vertex);
			}
		}
	}

	public GraphNode<T> createNode(T value) {
		return new GraphNode<T>(value);
	}

	public Collection<GraphNode<T>> getAllGraphNodes() {
		return nodesAndDependencies.values();
	}

	public List<GraphNode<T>> successors(GraphNode<T> node) {
		return node.getSuccessors();
	}

	public List<T> topologicalSort() {
		List<T> output = new ArrayList<>();
		for (GraphNode<T> node : getAllGraphNodes()) {
			if (!node.isVisited()) {
				topologicalSorterHelper(node, output);
			}
		}
		return output;
	}

	private void topologicalSorterHelper(GraphNode<T> vertex, List<T> output) {
		vertex.setState(GraphNode.GraphNodeState.VISITED);
		for (GraphNode<T> node: successors(vertex)) {
			if (!node.isVisited()) {
				topologicalSorterHelper(node, output);
			}
		}
		output.add(vertex.getData());
	}

	public boolean hasCircularDependencies() {
		for (GraphNode<T> vertex : getAllGraphNodes()) {
			if (!vertex.isVisited() && checkCycleUtil(vertex)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkCycleUtil(GraphNode<T> sourceNode) {
		sourceNode.setState(GraphNodeState.BEING_VISITED);
		for (GraphNode<T> node : successors(sourceNode)) {
			if (node.isBeingVisited()) {
				return true;
			} else if (!node.isVisited() && checkCycleUtil(node)) {
				return true;
			}
		}
		sourceNode.setState(GraphNodeState.NOT_BEING_VISITED);
		sourceNode.setState(GraphNodeState.VISITED);
		return false;
	}
}
