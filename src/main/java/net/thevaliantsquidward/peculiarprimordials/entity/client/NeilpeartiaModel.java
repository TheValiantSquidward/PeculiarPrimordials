package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import software.bernie.geckolib.model.GeoModel;

public class NeilpeartiaModel extends GeoModel<NeilpeartiaEntity> {
    @Override
    public ResourceLocation getModelResource(NeilpeartiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/frogfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NeilpeartiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/2frogfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NeilpeartiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/model.animation.json");
    }


}