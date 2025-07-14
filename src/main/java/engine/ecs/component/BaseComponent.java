package engine.ecs.component;

import engine.ecs.entity.Entity;

public abstract class BaseComponent implements Component {
	protected final Entity owner;

	BaseComponent(Entity owner) {
		this.owner = owner;
	}
}
