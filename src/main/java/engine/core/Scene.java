package engine.core;

import engine.ecs.component.Component;
import engine.ecs.entity.Entity;
import engine.ecs.system.RenderSystem;
import engine.ecs.system.ScriptSystem;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	private final SceneManager sceneManager;
	private final ScriptSystem scriptSystem = new ScriptSystem();
	private final RenderSystem renderSystem;
	private final List<Entity> entities = new ArrayList<>();

	private final List<Component> addedComponents = new ArrayList<>();
	private final List<Component> removedComponents = new ArrayList<>();

	public Scene(SceneManager sceneManager, RenderSystem renderSystem) {
		this.sceneManager = sceneManager;
		this.renderSystem = renderSystem;
	}

	public void notifyAddedComponent(Component component) {
		addedComponents.add(component);
	}

	public void notifyRemovedComponent(Component component) {
		removedComponents.add(component);
	}

	public void handleComponentsNotification() {
		if (!addedComponents.isEmpty()) {
			for (Component component : addedComponents) {
				renderSystem.notifyAddedComponent(component);
				scriptSystem.notifyAddedComponent(component);
			}
		}
		if (!removedComponents.isEmpty()) {
			for (Component component : removedComponents) {
				renderSystem.notifyRemovedComponent(component);
				scriptSystem.notifyRemovedComponent(component);
			}
		}
		addedComponents.clear();
		removedComponents.clear();
	}

	public void update() {
		handleComponentsNotification();
		scriptSystem.update();
		renderSystem.update();
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}


}
