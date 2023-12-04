package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;
import software.bernie.geckolib.model.GeoModel;

public class TapejaraModel extends GeoModel<TapejaraEntity> {
    @Override
    public ResourceLocation getModelResource(TapejaraEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/tapejara.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TapejaraEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/tapejara_gilded.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TapejaraEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/tapejara.animation.json");
    }


}