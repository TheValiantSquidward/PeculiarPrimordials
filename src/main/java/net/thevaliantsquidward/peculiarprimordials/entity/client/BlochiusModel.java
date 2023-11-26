package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import software.bernie.geckolib.model.GeoModel;

public class BlochiusModel extends GeoModel<BlochiusEntity> {
    @Override
    public ResourceLocation getModelResource(BlochiusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/blochius.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlochiusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/needlefish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlochiusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/blochius.animation.json");
    }


}