package net.thevaliantsquidward.peculiarprimordials.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiganhingaRenderer extends GeoEntityRenderer<GiganhingaEntity> {
    public GiganhingaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GiganhingaModel());
    }

    @Override
    public ResourceLocation getTextureLocation(GiganhingaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/anhingatexture.png");
    }

    @Override
    public void render(GiganhingaEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
