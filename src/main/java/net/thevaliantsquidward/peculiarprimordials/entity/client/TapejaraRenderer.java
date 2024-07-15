package net.thevaliantsquidward.peculiarprimordials.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TapejaraRenderer extends GeoEntityRenderer<TapejaraEntity> {


    private static final ResourceLocation TEXTURE_GILDED = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/tapejara_gilded.png");
    private static final ResourceLocation TEXTURE_FLAMBOYANT = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/tapejara_flamboyant.png");
    private static final ResourceLocation TEXTURE_ELEGANT = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/tapejara_elegant.png");

    public TapejaraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TapejaraModel());
    }
    @Override
    public void render(TapejaraEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
    public ResourceLocation getTextureLocation(TapejaraEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_FLAMBOYANT;
            case 2 -> TEXTURE_ELEGANT;
           default -> TEXTURE_GILDED;
       };
   }
}