package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.DomeykodactylusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import software.bernie.geckolib.model.GeoModel;

public class DomeykodactylusModel extends GeoModel<DomeykodactylusEntity> {
    @Override
    public ResourceLocation getModelResource(DomeykodactylusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/crydibird.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DomeykodactylusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/honchcrowptera.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DomeykodactylusEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/crydibird.animation.json");
    }



}