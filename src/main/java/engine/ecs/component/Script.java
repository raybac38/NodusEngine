package engine.ecs.component;

import engine.ecs.entity.Entity;

public abstract class Script extends BaseComponent {
	public Script(Entity owner) {
		super(owner);
	}

	/*
		This method is called every frame by the Script System
	 */
	public abstract void onUpdate();

	/*
		This method is called at the first update
	 */
	public abstract void onStart();

	/*
		This method is called after the last update
	 */
	public abstract void onStop();
}
