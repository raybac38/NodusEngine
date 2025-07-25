package engine.ecs.system;

import engine.ecs.component.Camera;
import engine.ecs.component.Component;
import engine.ecs.component.Mesh;
import utils.vector.Vector3;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderSystem implements System {
	private final List<Mesh> meshs = new ArrayList<>();
	private Camera mainCamera;
	private int defaultShaderProgram;


	private int vertexShader;
	private int fragmentShader;

	public RenderSystem() {
		createDefaultShaderProgram();
		glClearColor(0.2f, 0.3f, 1f, 1.0f);
	}

	/*
		Méthode is called by the scene to update and draw
	 */
	public void update() {
		render();
	}

	@Override
	public void notifyAddedComponent(Component component) {
		if (component instanceof Mesh) meshs.add((Mesh) component);
		if (component instanceof Camera) mainCamera = (Camera) component;
	}

	@Override
	public void notifyRemovedComponent(Component component) {
		if (component instanceof Mesh) meshs.remove((Mesh) component);
		if (component instanceof Camera) mainCamera = null;
	}

	/*
		Create the defaultShaderProgram saved in this variable
	 */
	private void createDefaultShaderProgram() {
		String vertexShaderCode = """
				      #version 330 core      \s
				      layout (location = 0) in vec3 aPos;
				      uniform mat4 projection;
				      uniform mat4 view;
				      uniform mat4 model;
				      void main()      \s
				      {         \s
					   gl_Position = projection * view * model * vec4(aPos, 1.0);
				}""";

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderCode);
		glCompileShader(vertexShader);

		String fragmentShaderCode = """
				#version 330 core      \s
				out vec4 FragColor;             \s
				void main() \s
				{          \s
				 FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);      \s
				}""";
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentShaderCode);
		glCompileShader(fragmentShader);

		/// Création d'un shader programme
		defaultShaderProgram = glCreateProgram();

		glAttachShader(defaultShaderProgram, vertexShader);
		glAttachShader(defaultShaderProgram, fragmentShader);
		glLinkProgram(defaultShaderProgram);

		/// Clean after creation
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}

	private void render() {
		if (mainCamera == null) {
			java.lang.System.out.println("No camera registered");
			return;
		}

		glUseProgram(defaultShaderProgram);

		float aspectRatio = 1.3f;
		float fov = mainCamera.fov;
		float far = mainCamera.far;
		float near = mainCamera.near;

		float f = 1.0f / (float) Math.tan(fov / 2.0f);
		float rangeInv = 1.0f / (near - far);

		int projectionLoc = glGetUniformLocation(defaultShaderProgram, "projection");
		float[] projectionMatrix = new float[]{
				f / aspectRatio, 0, 0, 0,
				0, f, 0, 0,
				0, 0, (far + near) * rangeInv, -1,
				0, 0, (2 * far * near) * rangeInv, 0
		};
		glUniformMatrix4fv(projectionLoc, false, projectionMatrix);

		Vector3 cameraPosition = mainCamera.getOwner().transform.getPosition();

		int viewLoc = glGetUniformLocation(defaultShaderProgram, "view");
		float[] viewMatrix = new float[]{
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				cameraPosition.x * -1, cameraPosition.y * -1, cameraPosition.z * -1, 1
		};
		glUniformMatrix4fv(viewLoc, false, viewMatrix);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for (Mesh mesh : meshs) {
			if (mesh.vertices == null) continue;

			Vector3 meshPosition = mesh.getOwner().transform.getPosition();

			int modelLoc = glGetUniformLocation(defaultShaderProgram, "model");
			float[] modelMatrix = new float[]
					{1, 0, 0, 0,
							0, 1, 0, 0,
							0, 0, 1, 0,
							meshPosition.x, meshPosition.y, meshPosition.z, 1};
			glUniformMatrix4fv(modelLoc, false, modelMatrix);


			int vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			float[] vertices = new float[mesh.vertices.length * 3];
			for (int i = 0; i < mesh.vertices.length; i++) {
				vertices[i * 3] = mesh.vertices[i].x;
				vertices[i * 3 + 1] = mesh.vertices[i].y;
				vertices[i * 3 + 2] = mesh.vertices[i].z;
			}
			glBufferData(GL_ARRAY_BUFFER, vertices, GL_STREAM_DRAW);

			int vao = glGenVertexArrays();

			glBindVertexArray(vao);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
			glEnableVertexAttribArray(0);

			int ebo = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, mesh.triangles, GL_STREAM_DRAW);

			///  Dessin du vao
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			glDrawElements(GL_TRIANGLES, mesh.triangles.length, GL_UNSIGNED_INT, 0);
			glBindVertexArray(0);
		}
	}
}
