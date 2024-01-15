package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static application.ScriptTestData.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Test;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public class DependencyGraphTest {

	@Test
	public void testDetermineSuccessors01() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		List<GraphNode<VulnerabilityScript>> expectedSuccessors = new ArrayList<>();
		expectedSuccessors.add(graph.getNode(SCRIPT7));
		expectedSuccessors.add(graph.getNode(SCRIPT8));
		List<GraphNode<VulnerabilityScript>> currentSuccessors1 = graph.successors(graph.getNode(SCRIPT5));
		assertEquals(expectedSuccessors, currentSuccessors1);
	}

	@Test
	public void testDetermineSuccessors02() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		List<GraphNode<VulnerabilityScript>> currentSuccessors = graph.successors(graph.getNode(SCRIPT3));
		assertEquals(Collections.emptyList(), currentSuccessors);
	}

	@Test
	public void testVertexExists() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		assertTrue(graph.hasNode(SCRIPT5));

		graph.removeVertex(SCRIPT8);
		assertFalse(graph.hasNode(SCRIPT8));
	}

	@Test
	public void testHasCircularDependencies01() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		assertFalse(graph.hasCircularDependencies());
	}

	@Test
	public void testHasCircularDependencies02() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS2);
		assertTrue(graph.hasCircularDependencies());
	}

	@Test
	public void testCheckTopologicalOrder01() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		Stack<VulnerabilityScript> orderedScripts = graph.topologicalSort();
		// check if script3 is executed before script1
		int beforeElementIdx = orderedScripts.indexOf(SCRIPT3);
		int afterElementIdx = orderedScripts.indexOf(SCRIPT1);
		assertTrue(afterElementIdx > beforeElementIdx);
	}

	@Test
	public void testCheckTopologicalOrder02() {
		DependencyGraph<VulnerabilityScript> graph = createDependencyGraph(SCRIPTS1);
		Stack<VulnerabilityScript> orderedScripts = graph.topologicalSort();
		// check if script8 is executed before script5
		int beforeElementIdx = orderedScripts.indexOf(SCRIPT8);
		int afterElementIdx = orderedScripts.indexOf(SCRIPT5);
		assertTrue(afterElementIdx > beforeElementIdx);
	}
}
