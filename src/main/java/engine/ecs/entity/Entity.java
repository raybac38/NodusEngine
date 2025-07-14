package engine.ecs.entity;

import engine.core.Scene;
import engine.ecs.component.Component;
import engine.ecs.component.Transform;

import java.util.ArrayList;
import java.util.Iterator;
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
		scene.notifyAddedComponent(component);
	}

	/*
		Remove a components to this entity
		return the removed components
	 */
	public <T extends Component> T removeComponent(Class<T> type) {
		assert (type != null);
		Iterator<Component> iterator = components.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if (type.isInstance(component)) {
				iterator.remove();
				scene.notifyRemovedComponent(component);
				return type.cast(component);
			}
		}
		return null;
	}


}
