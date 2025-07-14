package engine.ecs.system;

import engine.ecs.component.Component;

public interface System {
	void update();

	void notifyAddedComponent(Component component);

	void notifyRemovedComponent(Component component);
}
