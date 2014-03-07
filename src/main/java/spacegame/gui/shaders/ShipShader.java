package spacegame.gui.shaders;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import spacegame.gui.Assets;

public class ShipShader implements Shader {
    ShaderProgram program;
    Camera camera;
    RenderContext context;
    int u_projTrans;
    int u_worldTrans;
    int u_color;

    @Override
    public void init() {
        program = Assets.shaders.get(Assets.Shader.DEFAULT);

        if (!program.isCompiled()) {
            throw new GdxRuntimeException(program.getLog());
        }

        u_projTrans = program.getUniformLocation("u_projTrans");
        u_worldTrans = program.getUniformLocation("u_worldTrans");
        u_color = program.getUniformLocation("u_color");
    }

    @Override
    public void dispose() {
        program.dispose();
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        this.camera = camera;
        this.context = context;
        program.begin();
        program.setUniformMatrix(u_projTrans, camera.combined);
        context.setDepthTest(GL30.GL_LEQUAL);
        context.setCullFace(GL30.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
        program.setUniformf(u_color, MathUtils.random(), MathUtils.random(), MathUtils.random());
        renderable.mesh.render(program, renderable.primitiveType, renderable.meshPartOffset, renderable.meshPartSize);
    }

    @Override
    public void end() {
        program.end();
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return true;
    }
}
