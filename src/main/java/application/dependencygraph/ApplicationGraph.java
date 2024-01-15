package application.dependencygraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public class ApplicationGraph {

	public static void main(String[] args) {
		List<VulnerabilityScript> scriptList = new ArrayList<>();
		scriptList.add(new VulnerabilityScript(1, Arrays.asList(2, 3)));
		scriptList.add(new VulnerabilityScript(2, Arrays.asList(4, 5, 6)));
		scriptList.add(new VulnerabilityScript(3, Collections.emptyList()));
		scriptList.add(new VulnerabilityScript(4, Collections.emptyList()));
		scriptList.add(new VulnerabilityScript(5, Arrays.asList(7, 8)));
		scriptList.add(new VulnerabilityScript(6, Arrays.asList(9)));
		scriptList.add(new VulnerabilityScript(7, Collections.emptyList()));
		scriptList.add(new VulnerabilityScript(8, Collections.emptyList()));
		scriptList.add(new VulnerabilityScript(9, Collections.emptyList()));

		DependencyGraph<?> dependencyGraph = initialize(scriptList);
		dependencyGraph.topologicalSort();
	}

	private static DependencyGraph<?> initialize(List<VulnerabilityScript> scripts) {
		DependencyGraph<VulnerabilityScript> graph = new DependencyGraph<>();

		Map<Integer, VulnerabilityScript> scriptData = scripts.stream()
				.collect(Collectors.toMap(VulnerabilityScript::getScriptId, Function.identity()));
		for (VulnerabilityScript script : scripts) {
			List<VulnerabilityScript> dependantList = script.getDependencies().stream().map(v -> scriptData.get(v))
					.collect(Collectors.toList());
			graph.addDependencies(script, dependantList);
		}
		return graph;
	}
}
