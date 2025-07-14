package engine.ecs.component;

import engine.ecs.entity.Entity;

public abstract class Script extends BaseComponent {
	public Script(Entity owner) {
		super(owner);
	}
	
	/*
		This method is called every frame by the Script System
	 */
	public abstract void update();
}
