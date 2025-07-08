package engine.core.window;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class GlfwWindow implements Window {
	private final long window;
	private boolean isKilled;
	private WindowKeyListener windowKeyListener;
	private WindowMouseListener windowMouseListener;

	public GlfwWindow() {
		GLFWErrorCallback.createPrint(System.err).set();
		glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialise glfw");
		}
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(800, 600, "Silvia", 0, 0);
		if (window == 0) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
			if (windowKeyListener == null) return;
			if (action == GLFW_PRESS) {
				windowKeyListener.keyPressed(this, key);
			}
			if (action == GLFW_RELEASE) {
				windowKeyListener.keyRelease(this, key);
			}
		});

		glfwSetCursorPosCallback(window, ((window1, xpos, ypos) -> {
			if (windowMouseListener == null) return;
			windowMouseListener.mouseMoved(this, xpos, ypos);
		}));

		/*
		A quoi sers se code ? idk
		 */
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1);
		}

		GLFWVidMode vidmod = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	@Override
	public void kill() {
		glfwSetWindowShouldClose(window, true);
		closeWindow();
	}

	private void closeWindow() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	@Override
	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
		if (glfwWindowShouldClose(window)) {
			closeWindow();
		}
	}

	@Override
	public long getWindow() {
		return window;
	}

	@Override
	public void setWindowKeyListener(WindowKeyListener windowKeyListener) {
		this.windowKeyListener = windowKeyListener;
	}

	@Override
	public void setWindowMouseListener(WindowMouseListener windowMouseListener) {
		this.windowMouseListener = windowMouseListener;
	}
}
