package engine.core;

import engine.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	private final List<Entity> entities = new ArrayList<>();

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
}
