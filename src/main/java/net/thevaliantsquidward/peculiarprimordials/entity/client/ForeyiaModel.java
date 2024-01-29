package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ForeyiaModel extends GeoModel<ForeyiaEntity> {
    @Override
    public ResourceLocation getModelResource(ForeyiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/foreyia.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForeyiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/foreyia.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ForeyiaEntity animatable) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/foreyia.animation.json");
    }



}