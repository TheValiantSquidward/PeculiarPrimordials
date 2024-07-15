package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class NeilpeartiaGlow extends GeoRenderLayer<NeilpeartiaEntity> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/frogfish_glow.png");
    private static final ResourceLocation MODEL = new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/frogfish.geo.json");
    public NeilpeartiaGlow(GeoRenderer<NeilpeartiaEntity> entityRendererIn) {
        super(entityRendererIn);

    }

    public void render(PoseStack poseStack, NeilpeartiaEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        long roundedTime = entityLivingBaseIn.level().getDayTime() % 24000;

            RenderType cameo = RenderType.eyes(OVERLAY);
            getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);
    }

}
