package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NeilpeartiaRenderer extends GeoEntityRenderer<NeilpeartiaEntity> {
    private static final ResourceLocation TEXTURE_2FROGFISH = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/2frogfish.png");
    private static final ResourceLocation TEXTURE_GOLDEN = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/frogfish.png");
    private static final ResourceLocation TEXTURE_KERMIT = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/kermit.png");

    public NeilpeartiaRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new NeilpeartiaModel());
        this.addRenderLayer(new NeilpeartiaGlow(this));
    }

    protected void scale(NeilpeartiaEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }
    @Override
    public void render(NeilpeartiaEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    public ResourceLocation getTextureLocation(NeilpeartiaEntity entity) {
        if (entity.isKermit()) {
            return TEXTURE_KERMIT;
        }
        switch (entity.getVariant()) {
            case 1: return TEXTURE_GOLDEN;
            default: return TEXTURE_2FROGFISH;
        }
    }
}