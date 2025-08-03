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
		java.lang.System.out.println("update");

		/// Updating all script
		for (Script script : scriptToStop) {
			java.lang.System.out.println("all stop script");
			script.onStop();
		}
		for (Script script : scriptsToStart) {
			java.lang.System.out.println("all start script");
			script.onStart();
		}
		for (Script script : scriptsToUpdate) {
			java.lang.System.out.println("all update script");
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
