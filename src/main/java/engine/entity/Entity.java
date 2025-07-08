package engine.entity;

import engine.component.Component;
import engine.component.Transform;
import engine.core.Scene;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	public final Scene scene;
	private final List<Component> components = new ArrayList<>();
	private final Component transform = new Transform(this);

	public Entity(Scene scene) {
		this.scene = scene;
		scene.addEntity(this);
	}

	/*
		Return the component matching the given type.
		Return null if not find
	 */
	public <T extends Component> T getComponent(Class<T> type) {
		assert (type != null);
		for (Component component : components) {
			if (type.isInstance(component)) {
				return type.cast(component);
			}
		}
		return null;
	}


	/*
		Attaching a new components to this entity
	 */
	public <T extends Component> void addComponent(T component) {
		assert (!components.contains(component));
		assert (!(component instanceof Transform));
		components.add(component);
	}

}
