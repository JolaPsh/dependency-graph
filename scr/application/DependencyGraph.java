package application;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
			sourceNode = nodesAndDependencies.get(source);
		} else {
			sourceNode = createNode(source);
			addVertex(source, sourceNode);
		}
		if (hasNode(target)) {
			targetNode = nodesAndDependencies.get(target);
		} else {
			targetNode = createNode(target);
			addVertex(target, targetNode);
		}
		sourceNode.addSuccessors(targetNode);
	}

	private boolean hasNode(T node) {
		return nodesAndDependencies.containsKey(node);
	}

	private void addVertex(T data, GraphNode<T> node) {
		nodesAndDependencies.put(data, node);
	}

	public GraphNode<T> createNode(T value) {
		return new GraphNode<T>(value);
	}

	public Collection<GraphNode<T>> getAllGraphNodes() {
		return nodesAndDependencies.values();
	}

	private List<GraphNode<T>> successors(GraphNode<T> node) {
		return node.getSuccessors();
	}

	public void topologicalSort() {
		Stack<GraphNode<T>> output = new Stack<>();
		for (GraphNode<T> node : getAllGraphNodes()) {
			if (!node.isProcessed()) {
				topologicalSorterHelper(node, output);
			}
		}
		while (output.empty() == false) {
			executeScript(output.pop());
		}
	}

	private void topologicalSorterHelper(GraphNode<T> vertex, Stack<GraphNode<T>> output) {
		vertex.setState(GraphNode.GraphNodeState.PROCESSED);
		for (GraphNode<T> node: successors(vertex)) {
			if (!node.isProcessed()) {
				topologicalSorterHelper(node, output);
			}
		}
		output.push(vertex);
	}

	private void executeScript(GraphNode<T> node) {
		System.out.println("echo Executing script : " + node.getData());
	}
}
