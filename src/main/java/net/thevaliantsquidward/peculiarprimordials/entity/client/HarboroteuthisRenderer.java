package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.HarboroteuthisEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HarboroteuthisRenderer extends GeoEntityRenderer<HarboroteuthisEntity> {
    public HarboroteuthisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HarboroteuthisModel());
    }

    @Override
    public ResourceLocation getTextureLocation(HarboroteuthisEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/harboroteuthis.png");
    }

    @Override
    public void render(HarboroteuthisEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
