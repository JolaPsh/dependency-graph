package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public final  class GraphNode<T> {

	public enum GraphNodeState {
		NOT_PROCESSED, PROCESSED;
	}

	private final T data;
	private final List<GraphNode<T>> successors;
	private GraphNodeState state = GraphNodeState.NOT_PROCESSED;
	private boolean isBeingVisited;

	public GraphNode(T data) {
		this.data = data;
		this.successors = new ArrayList<>();
	}

	public T getData() {
		return this.data;
	}

	public void addSuccessors(GraphNode<T> node) {
		successors.add(node);
	}

	public List<GraphNode<T>> getSuccessors() {
		return Collections.unmodifiableList(successors);
	}

	public void setState(GraphNodeState state) {
		this.state = state;
	}

	public boolean isProcessed() {
		return state == GraphNodeState.PROCESSED;
	}

	public boolean isBeingVisited() {
		return isBeingVisited;
	}

	public void setBeingVisited(boolean isBeingVisited) {
		this.isBeingVisited = isBeingVisited;
	}
}