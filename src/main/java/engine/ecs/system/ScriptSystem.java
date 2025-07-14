package engine.ecs.system;

import engine.ecs.component.Component;
import engine.ecs.component.Script;

import java.util.ArrayList;
import java.util.List;

public class ScriptSystem implements System {
	private final List<Script> scripts = new ArrayList<>();

	@Override
	public void update() {
		for (Script script : scripts) {
			script.update();
		}
	}

	@Override
	public void notifyAddedComponent(Component component) {
		if (component instanceof Script) scripts.add((Script) component);
	}

	@Override
	public void notifyRemovedComponent(Component component) {
		if (component instanceof Script) scripts.remove((Script) component);
	}
}
