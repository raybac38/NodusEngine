package engine.ecs.component;

import engine.ecs.entity.Entity;

public interface Component {
	Entity getOwner();
}
