package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ForeyiaRenderer extends GeoEntityRenderer<ForeyiaEntity> {
    public ForeyiaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ForeyiaModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ForeyiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/foreyia.png");
    }

    @Override
    public void render(ForeyiaEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
