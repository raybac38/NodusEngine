package engine.system;

import engine.component.Component;
import engine.component.Mesh;

public class SystemManager {
	private final RenderSystem renderSystem;

	SystemManager(RenderSystem renderSystem) {
		this.renderSystem = renderSystem;
	}

	public void registerComponent(Component component) {
		if (component instanceof Mesh) renderSystem.addMesh((Mesh) component);
	}

	public void unregisterComponent(Component component) {
		if (component instanceof Mesh) renderSystem.removeMesh((Mesh) component);
	}


}
