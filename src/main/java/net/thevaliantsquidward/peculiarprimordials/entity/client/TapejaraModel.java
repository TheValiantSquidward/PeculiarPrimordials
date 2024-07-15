package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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

    @Override
    public void setCustomAnimations(TapejaraEntity animatable, long instanceId, AnimationState<TapejaraEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("bone8");
        if (animatable.isBaby()) {
            head.setScaleX(1.50F);
            head.setScaleY(1.50F);
            head.setScaleZ(1.50F);


        } else {
            head.setScaleX(1.0F);
            head.setScaleY(1.0F);
            head.setScaleZ(1.0F);

        }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}