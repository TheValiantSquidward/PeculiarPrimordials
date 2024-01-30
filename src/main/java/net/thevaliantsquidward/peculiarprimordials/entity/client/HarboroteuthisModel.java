package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.HarboroteuthisEntity;
import software.bernie.geckolib.model.GeoModel;

public class HarboroteuthisModel extends GeoModel<HarboroteuthisEntity> {
        @Override
        public ResourceLocation getModelResource(HarboroteuthisEntity animatable) {
            return new ResourceLocation(PeculiarPrimordials.MOD_ID, "geo/harboroteuthis.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(HarboroteuthisEntity animatable) {
            return new ResourceLocation(PeculiarPrimordials.MOD_ID, "textures/entity/harboroteuthis.png");
        }

        @Override
        public ResourceLocation getAnimationResource(HarboroteuthisEntity animatable) {
            return new ResourceLocation(PeculiarPrimordials.MOD_ID, "animations/harboroteuthis.animation.json");
        }
}
