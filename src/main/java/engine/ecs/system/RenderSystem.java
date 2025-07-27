package engine.ecs.system;

import engine.ecs.component.Camera;
import engine.ecs.component.Component;
import engine.ecs.component.Mesh;
import engine.ecs.component.Transform;
import utils.vector.Vector3;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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


	private float[] multiplyMatrix(float[] matrixA, float[] matrixB) {
		assert (matrixA != null && matrixB != null);
		int length = (int) Math.sqrt(matrixA.length);
		assert (length * length == matrixA.length);

		float[] res = new float[matrixA.length];

		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				float sum = 0;
				for (int k = 0; k < length; k++) {
					sum += matrixA[row * length + k] * matrixB[k * length + col];
				}
				res[row * length + col] = sum;
			}
		}
		return res;
	}

	private float[] rotationMatrixInv(Vector3 angle) {
		float cosX = (float) Math.cos(Math.toRadians(angle.x));
		float sinX = (float) Math.sin(Math.toRadians(angle.x));

		float[] rotationX = new float[]{
				1f, 0f, 0f, 0f,
				0f, cosX, sinX, 0f,
				0f, sinX * -1f, cosX, 0f,
				0f, 0f, 0f, 1f};

		float cosY = (float) Math.cos(Math.toRadians(angle.y));
		float sinY = (float) Math.sin(Math.toRadians(angle.y));

		float[] rotationY = new float[]{
				cosY, 0f, sinY * -1f, 0f,
				0f, 1f, 0f, 0f,
				sinY, 0f, cosY, 0f,
				0f, 0f, 0f, 1f};

		float cosZ = (float) Math.cos(Math.toRadians(angle.z));
		float sinZ = (float) Math.sin(Math.toRadians(angle.z));

		float[] rotationZ = new float[]{
				cosZ, sinZ, 0f, 0f,
				-1f * sinZ, cosZ, 0f, 0f,
				0f, 0f, 1f, 0f,
				0f, 0f, 0f, 1f};

		return multiplyMatrix(rotationX, multiplyMatrix(rotationY, rotationZ));
	}

	private float[] rotationMatrix(Vector3 angle) {
		float cosX = (float) Math.cos(Math.toRadians(angle.x));
		float sinX = (float) Math.sin(Math.toRadians(angle.x));

		float[] rotationX = new float[]{
				1f, 0f, 0f, 0f,
				0f, cosX, -1f * sinX, 0f,
				0f, sinX, cosX, 0f,
				0f, 0f, 0f, 1f};

		float cosY = (float) Math.cos(Math.toRadians(angle.y));
		float sinY = (float) Math.sin(Math.toRadians(angle.y));

		float[] rotationY = new float[]{
				cosY, 0f, sinY, 0f,
				0f, 1f, 0f, 0f,
				-1f * sinY, 0f, cosY, 0f,
				0f, 0f, 0f, 1f};

		float cosZ = (float) Math.cos(Math.toRadians(angle.z));
		float sinZ = (float) Math.sin(Math.toRadians(angle.z));

		float[] rotationZ = new float[]{
				cosZ, -1f * sinZ, 0f, 0f,
				sinZ, cosZ, 0f, 0f,
				0f, 0f, 1f, 0f,
				0f, 0f, 0f, 1f};

		return multiplyMatrix(rotationZ, multiplyMatrix(rotationY, rotationX));
	}

	private float[] computeViewMatrix(Transform transform) {
		Vector3 position = transform.getPosition();
		float[] translationMatrix = new float[]{
				1f, 0f, 0f, 0f,
				0f, 1f, 0f, 0f,
				0f, 0f, 1f, 0f,
				position.x * -1f, position.y * -1f, position.z * -1f, 1f};

		Vector3 angle = transform.getRotation();

		return multiplyMatrix(rotationMatrixInv(angle), translationMatrix);
	}

	private float[] computeModelMatrix(Transform transform) {
		Vector3 position = transform.getPosition();
		float[] translationMatrix = new float[]{
				1f, 0f, 0f, 0f,
				0f, 1f, 0f, 0f,
				0f, 0f, 1f, 0f,
				position.x, position.y, position.z, 1f};

		Vector3 angle = transform.getRotation();

		return multiplyMatrix(translationMatrix, rotationMatrix(angle));
	}

	/*
		Create the defaultShaderProgram saved in this variable
	 */
	private void createDefaultShaderProgram() {
		String vertexShaderCode;
		String fragmentShaderCode;
		try {
			vertexShaderCode = Files.readString(Path.of("src/main/resources/shaders/defaultVertexShader.vert"), StandardCharsets.UTF_8);
			fragmentShaderCode = Files.readString(Path.of("src/main/resources/shaders/defaultFragmentShader.frag"), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderCode);
		glCompileShader(vertexShader);

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

		float aspectRatio = 16f / 9f;
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

		int viewLoc = glGetUniformLocation(defaultShaderProgram, "view");
		float[] viewMatrix = computeViewMatrix(mainCamera.getOwner().transform);
		glUniformMatrix4fv(viewLoc, false, viewMatrix);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for (Mesh mesh : meshs) {
			if (mesh.vertices == null) continue;

			Transform meshTransform = mesh.getOwner().transform;
			int modelLoc = glGetUniformLocation(defaultShaderProgram, "model");
			glUniformMatrix4fv(modelLoc, false, computeModelMatrix(meshTransform));
			
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
