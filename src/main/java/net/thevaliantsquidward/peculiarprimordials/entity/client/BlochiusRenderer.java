package net.thevaliantsquidward.peculiarprimordials.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlochiusRenderer extends GeoEntityRenderer<BlochiusEntity> {
    public BlochiusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlochiusModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BlochiusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/needlefish.png");
    }

    @Override
    public void render(BlochiusEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
