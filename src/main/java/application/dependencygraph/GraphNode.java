package application.dependencygraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public final  class GraphNode<T> {

	public enum GraphNodeState {
		NOT_VISITED, VISITED, NOT_BEING_VISITED, BEING_VISITED
	}

	private final T data;
	private final List<GraphNode<T>> successors;
	private final EnumSet<GraphNodeState> state;

	public GraphNode(T data) {
		this.data = data;
		this.successors = new ArrayList<>();
		this.state = EnumSet.of(GraphNodeState.NOT_VISITED);
	}

	public T getData() {
		return this.data;
	}

	public void addSuccessors(GraphNode<T> node) {
		successors.add(node);
	}

	public void removeSuccessors(GraphNode<T> node) {
		successors.remove(node);
	}

	public List<GraphNode<T>> getSuccessors() {
		return Collections.unmodifiableList(successors);
	}

	public void setState(GraphNodeState state) {
		switch (state) {
			case VISITED:
				this.state.add(GraphNodeState.VISITED);
				this.state.remove(GraphNodeState.NOT_VISITED);
				break;
			case BEING_VISITED:
				this.state.add(GraphNodeState.BEING_VISITED);
				this.state.remove(GraphNodeState.NOT_BEING_VISITED);
				break;
			case NOT_BEING_VISITED:
				this.state.add(GraphNodeState.NOT_BEING_VISITED);
				this.state.remove(GraphNodeState.BEING_VISITED);
				break;
			default:
				break;
		}
	}

	public boolean isVisited() {
		return state.contains(GraphNodeState.VISITED);
	}

	public boolean isBeingVisited() {
		return state.contains(GraphNodeState.BEING_VISITED);
	}
}