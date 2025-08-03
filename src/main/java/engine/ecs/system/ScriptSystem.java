package engine.ecs.system;

import engine.ecs.component.Component;
import engine.ecs.component.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptSystem implements System {
	private final List<Script> scripts = new ArrayList<>();
	private final List<Script> scriptsToUpdate = new ArrayList<>();
	private final List<Script> scriptsToStart = new ArrayList<>();
	private final List<Script> scriptToStop = new ArrayList<>();

	@Override
	public void update() {

		/// Updating all script
		for (Script script : scriptToStop) {
			script.onStop();
		}
		for (Script script : scriptsToStart) {
			script.onStart();
		}
		for (Script script : scriptsToUpdate) {
			script.onUpdate();
		}

		/// Passing all starting script to update
		scriptsToUpdate.addAll(scriptsToStart);
		scriptsToStart.clear();
		scriptToStop.clear();
	}

	@Override
	public void notifyAddedComponent(Component component) {
		if (component instanceof Script script) {
			scripts.add(script);
			scriptsToStart.add(script);
		}
	}

	@Override
	public void notifyRemovedComponent(Component component) {
		if (component instanceof Script script) {
			scripts.remove(component);
			scriptsToUpdate.remove(script);
			scriptToStop.add(script);
		}
	}
}
