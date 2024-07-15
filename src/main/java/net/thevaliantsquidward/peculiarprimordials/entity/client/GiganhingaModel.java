package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
    @Override
    public void setCustomAnimations(GiganhingaEntity animatable, long instanceId, AnimationState<GiganhingaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("bone");
        // CoreGeoBone body = this.getAnimationProcessor().getBone("bone11");
        // if (animatable.isBaby()) {
        //     head.setScaleX(1.50F);
        //     head.setScaleY(1.50F);
        //     head.setScaleZ(1.50F);
//
        //     //body.setScaleX(0.5F);
        //     //body.setScaleY(0.5F);
        //     //body.setScaleZ(0.5F);
//
        // } else {
        //     head.setScaleX(1.0F);
        //     head.setScaleY(1.0F);
        //     head.setScaleZ(1.0F);
//
        //     //body.setScaleX(1.0F);
        //     //body.setScaleY(1.0F);
        //     //body.setScaleZ(1.0F);
        // }
        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

}