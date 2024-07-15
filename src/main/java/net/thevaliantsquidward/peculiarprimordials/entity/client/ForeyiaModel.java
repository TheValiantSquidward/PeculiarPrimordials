package net.thevaliantsquidward.peculiarprimordials.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.ForeyiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.GiganhingaEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
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

    @Override
    public void setCustomAnimations(ForeyiaEntity animatable, long instanceId, AnimationState<ForeyiaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if (animatable.onGround()) return;
        float speed = 1.2f;
        float degree = 1.5f;
        CoreGeoBone body = this.getAnimationProcessor().getBone("bone");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
//7
        CoreGeoBone root = this.getAnimationProcessor().getBone("bone");
        root.setRotX(extraDataOfType.headPitch() * (Mth.DEG_TO_RAD / 5));
        //root.setRotZ(Mth.clamp(Mth.lerp(0.1F, Mth.cos(animatable.yBodyRot * 0.1F) * 0.1F, 1.0F), -15F, 15F));

//220
        root.setRotX(root.getRotX() + extraDataOfType.headPitch() * ((float) Math.PI / 180F));
        root.setRotY(root.getRotY() + extraDataOfType.netHeadYaw() * ((float) Math.PI / 180F));
    }

}