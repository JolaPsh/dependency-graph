package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Joanna Pakosh, 01/2024
 */
public class ScriptTestData {

	public static final VulnerabilityScript SCRIPT1 = new VulnerabilityScript(1, Arrays.asList(2, 3));
	public static final VulnerabilityScript SCRIPT2 = new VulnerabilityScript(2, Arrays.asList(4, 5, 6));
	public static final VulnerabilityScript SCRIPT3 = new VulnerabilityScript(3, Collections.emptyList());
	public static final VulnerabilityScript SCRIPT4 = new VulnerabilityScript(4, Collections.emptyList());
	public static final VulnerabilityScript SCRIPT5 = new VulnerabilityScript(5, Arrays.asList(7, 8));
	public static final VulnerabilityScript SCRIPT6 = new VulnerabilityScript(6, Arrays.asList(9));
	public static final VulnerabilityScript SCRIPT7 = new VulnerabilityScript(7, Collections.emptyList());
	public static final VulnerabilityScript SCRIPT8 = new VulnerabilityScript(8, Collections.emptyList());
	public static final VulnerabilityScript SCRIPT9 = new VulnerabilityScript(9, Collections.emptyList());

	public static final List<VulnerabilityScript> SCRIPTS1 = Arrays.asList(SCRIPT1, SCRIPT2, SCRIPT3, SCRIPT4, SCRIPT5,
			SCRIPT6, SCRIPT7, SCRIPT8, SCRIPT9);
	
	public static final VulnerabilityScript SCRIPT10 = new VulnerabilityScript(1, Arrays.asList(2));
	public static final VulnerabilityScript SCRIPT11 = new VulnerabilityScript(2, Arrays.asList(3));
	public static final VulnerabilityScript SCRIPT12 = new VulnerabilityScript(3, Arrays.asList(1));
	
	public static final List<VulnerabilityScript> SCRIPTS2 = Arrays.asList(SCRIPT10, SCRIPT11, SCRIPT12);

	public static DependencyGraph<VulnerabilityScript> createDependencyGraph(List<VulnerabilityScript> scripts) {
		DependencyGraph<VulnerabilityScript> dependencyGraph = new DependencyGraph<>();
		Map<Integer, VulnerabilityScript> scriptData = scripts.stream()
				.collect(Collectors.toMap(VulnerabilityScript::getScriptId, Function.identity()));
		for (VulnerabilityScript script : scripts) {
			List<VulnerabilityScript> dependantList = script.getDependencies().stream().map(v -> scriptData.get(v))
					.collect(Collectors.toList());
			dependencyGraph.addDependencies(script, dependantList);
		}
		return dependencyGraph;
	}
}
