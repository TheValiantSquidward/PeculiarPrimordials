package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.DomeykodactylusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DomeykodactylusRenderer extends GeoEntityRenderer<DomeykodactylusEntity> {
    public DomeykodactylusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DomeykodactylusModel());
    }

    @Override
    public ResourceLocation getTextureLocation(DomeykodactylusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/honchcrowptera.png");
    }

    @Override
    public void render(DomeykodactylusEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
