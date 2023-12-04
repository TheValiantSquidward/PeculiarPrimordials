package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;
import software.bernie.geckolib.model.GeoModel;

public class GiganhingaModel extends GeoModel<GiganhingaEntity> {
    @Override
    public ResourceLocation getModelResource(GiganhingaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/giganhinga.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GiganhingaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/anhingatexture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GiganhingaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/anhinga.animation.json");
    }


}