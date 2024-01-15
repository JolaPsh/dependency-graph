package application.dependencygraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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

	public boolean removeVertex(T data) {
		return nodesAndDependencies.remove(data) != null;
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

	public Stack<T> topologicalSort() {
		Stack<T> output = new Stack<>();
		for (GraphNode<T> node : getAllGraphNodes()) {
			if (!node.isProcessed()) {
				topologicalSorterHelper(node, output);
			}
		}
		return output;
	}

	private void topologicalSorterHelper(GraphNode<T> vertex, Stack<T> output) {
		vertex.setState(GraphNode.GraphNodeState.PROCESSED);
		for (GraphNode<T> node: successors(vertex)) {
			if (!node.isProcessed()) {
				topologicalSorterHelper(node, output);
			}
		}
		output.push(vertex.getData());
	}

	public boolean hasCircularDependencies() {
		for (GraphNode<T> vertex : getAllGraphNodes()) {
			if (!vertex.isProcessed() && checkCycleUtil(vertex)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkCycleUtil(GraphNode<T> sourceNode) {
		sourceNode.setBeingVisited(true);
		for (GraphNode<T> node : successors(sourceNode)) {
			if (node.isBeingVisited()) {
				return true;
			} else if (!node.isProcessed() && checkCycleUtil(node)) {
				return true;
			}
		}
		sourceNode.setBeingVisited(false);
		sourceNode.setState(GraphNodeState.PROCESSED);
		return false;
	}
}
