package net.thevaliantsquidward.peculiarprimordials.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NeilpeartiaRenderer extends GeoEntityRenderer<NeilpeartiaEntity> {
    private static final ResourceLocation TEXTURE_2FROGFISH = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/2frogfish.png");
    private static final ResourceLocation TEXTURE_GOLDEN = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/frogfish.png");
    private static final ResourceLocation TEXTURE_KERMIT = new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/kermit.png");

    public NeilpeartiaRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new NeilpeartiaModel());
    }

    protected void scale(NeilpeartiaEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    public ResourceLocation getTextureLocation(NeilpeartiaEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_GOLDEN;
            case 2 -> TEXTURE_KERMIT;
            default -> TEXTURE_2FROGFISH;
        };
    }
}