package net.mengie32.hexbubbles.entity.client;

import java.util.function.Function;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class BubbleEntityRenderLayer extends RenderLayer{

    public BubbleEntityRenderLayer(String name, VertexFormat vertexFormat, DrawMode drawMode, int expectedBufferSize,
            boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {

        super(name, vertexFormat, drawMode, expectedBufferSize, true, translucent, startAction, endAction);
    }

    private static final Function<Identifier, RenderLayer> BUBBLE_TRANSLUSCENT = Util.memoize(
		(Function<Identifier, RenderLayer>)(texture -> {
			RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
			.lightmap(ENABLE_LIGHTMAP)
			.program(ENTITY_TRANSLUCENT_PROGRAM)
			.texture(new RenderPhase.Texture(texture, false, false))
			.transparency(TRANSLUCENT_TRANSPARENCY)
			.target(ITEM_ENTITY_TARGET)
			.build(true);
			return of(
				"bubble_transluscent", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters
			);
		})
	);

    public static RenderLayer getBubbleRenderLayer(Identifier texture) {
		return (RenderLayer)BUBBLE_TRANSLUSCENT.apply(texture);
	}
}
