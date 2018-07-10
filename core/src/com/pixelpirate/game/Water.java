package com.pixelpirate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Water {
	private Main game;
	private Mesh waterMesh;
	
	Water(Main game) {
		this.game = game;
		waterMesh = createQuad(-1, -1, 1, -1, 1, -0.3f, -1, -0.3f);
	}
	
	public void render() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    
		Assets.Shaders.water.begin();
		Assets.Shaders.water.setUniformf("u_time", game.getXdelta());
		Assets.Shaders.water.setUniformf("u_speed", game.getSpeed());
		Assets.Shaders.water.setUniformMatrix("u_projTrans", game.getShaderMatrix());
		Assets.water.bind(1);
		Assets.waterDis.bind(2);
		Assets.Shaders.water.setUniformi("u_texture", 1);
		Assets.Shaders.water.setUniformi("u_texture2", 2);
		waterMesh.render(Assets.Shaders.water, GL20.GL_TRIANGLE_FAN);
		Assets.Shaders.water.end();
	}
	
	private Mesh createQuad(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4) {
		float[] verts = new float[20];
		int i = 0;

		verts[i++] = x1; // x1
		verts[i++] = y1; // y1
		verts[i++] = 0;
		verts[i++] = 1f; // u1
		verts[i++] = 1f; // v1

		verts[i++] = x2; // x2
		verts[i++] = y2; // y2
		verts[i++] = 0;
		verts[i++] = 0f; // u2
		verts[i++] = 1f; // v2

		verts[i++] = x3; // x3
		verts[i++] = y3; // y2
		verts[i++] = 0;
		verts[i++] = 0f; // u3
		verts[i++] = 0f; // v3

		verts[i++] = x4; // x4
		verts[i++] = y4; // y4
		verts[i++] = 0;
		verts[i++] = 1f; // u4
		verts[i++] = 0f; // v4

		Mesh mesh = new Mesh(true, 4, 0, // static mesh with 4 vertices and no
											// indices
				new VertexAttribute(Usage.Position, 3,
						ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(
						Usage.TextureCoordinates, 2,
						ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));

		mesh.setVertices(verts);
		return mesh;
	}
}
