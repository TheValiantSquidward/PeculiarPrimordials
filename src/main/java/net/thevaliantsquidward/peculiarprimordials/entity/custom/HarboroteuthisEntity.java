package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.thevaliantsquidward.peculiarprimordials.entity.IPicksUpItems;
import net.thevaliantsquidward.peculiarprimordials.entity.ai.ModBlockPos;
import net.thevaliantsquidward.peculiarprimordials.entity.ai.TargetItems;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;
import net.thevaliantsquidward.peculiarprimordials.tag.ModTags;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class HarboroteuthisEntity extends WaterAnimal implements GeoEntity, IPicksUpItems {
    public int harborotickertimer = 0;
    public boolean producePearlItem = false;
    public boolean produceUrchinItem = false;
    public boolean passive = false;

    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(HarboroteuthisEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation DUNK_BEACHED;
    private static final RawAnimation DUNK_SWIM;
    private static final RawAnimation DUNK_IDLE;
    private static final EntityDataAccessor<Integer> ANIMATION_STATE;
    public HarboroteuthisEntity(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 45, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.moveControl = new MoveHelperController(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityDunkleosteus.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityTyrannosaurusRex.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.targetSelector.addGoal(1, new TargetItems<>(this, false));
    }
    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ARMOR, 0.5)
                .build();
    }
    @Override
    public void tick() {
        super.tick();

        if (producePearlItem) {

            harborotickertimer++;
            if (harborotickertimer >= 100 && !produceUrchinItem) {
                harborotickertimer = 0;
                this.spawnAtLocation(Items.CHICKEN);
                this.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
                producePearlItem = false;
            }
        }
        if (produceUrchinItem) {

            harborotickertimer++;
            if (harborotickertimer >= 100 && !producePearlItem) {
                harborotickertimer = 0;
                this.spawnAtLocation(Items.MUSIC_DISC_CHIRP);
                this.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
                produceUrchinItem = false;
            }
        }
    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }


    public int getAnimationState() {
        return (Integer)this.entityData.get(ANIMATION_STATE);
    }
    protected <E extends HarboroteuthisEntity> PlayState Controller(AnimationState<E> event) {
        int animState = this.getAnimationState();

        if ((!(event.getLimbSwingAmount() > -0.06F) || !(event.getLimbSwingAmount() < 0.06F)) && this.isInWater()) {
            event.setAndContinue(DUNK_SWIM);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            event.setAndContinue(DUNK_BEACHED);
            event.getController().setAnimationSpeed(2.0);
            return PlayState.CONTINUE;
        }

        if (this.isInWater()) {
            event.setAndContinue(DUNK_IDLE);
            return PlayState.CONTINUE;
        }

        return PlayState.CONTINUE;
    }
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
    }
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    static {
        ANIMATION_STATE = SynchedEntityData.defineId(HarboroteuthisEntity.class, EntityDataSerializers.INT);
        DUNK_SWIM = RawAnimation.begin().thenLoop("animation.model.idle");
        DUNK_IDLE = RawAnimation.begin().thenLoop("animation.model.idle");
        DUNK_BEACHED = RawAnimation.begin().thenLoop("animation.model.flop");
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(ANIMATION_STATE, 0);

    }

    @Override
    public boolean canTargetItem(ItemStack stack) {
        return stack.is(ModTags.FOREYIA_FOOD);
    }
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (super.hurt(pSource, pAmount) && this.getLastHurtByMob() != null) {
            if (!this.level().isClientSide) {
                this.spawnInk();
            }

            return true;
        } else {
            return false;
        }
    }
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;

    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;

    }

    private Vec3 rotateVector(Vec3 pVector) {
        Vec3 vec3 = pVector.xRot(this.xBodyRotO * ((float)Math.PI / 180F));
        return vec3.yRot(-this.yBodyRotO * ((float)Math.PI / 180F));
    }
    protected SoundEvent getSquirtSound() {
        return SoundEvents.SQUID_SQUIRT;
    }
    private void spawnInk() {
        this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
        Vec3 vec3 = this.rotateVector(new Vec3(0.0D, -1.0D, 0.0D)).add(this.getX(), this.getY(), this.getZ());

        for(int i = 0; i < 30; ++i) {
            Vec3 vec31 = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6D - 0.3D, -1.0D, (double)this.random.nextFloat() * 0.6D - 0.3D));
            Vec3 vec32 = vec31.scale(0.4D + (double)(this.random.nextFloat() * 2.0F));
            ((ServerLevel)this.level()).sendParticles(this.getInkParticle(), vec3.x, vec3.y + 0.5D, vec3.z, 0, vec32.x, vec32.y, vec32.z, (double)0.1F);
        }

    }
    protected ParticleOptions getInkParticle() {
        return ParticleTypes.SQUID_INK;
    }
    @Override
    public void onGetItem(ItemEntity e) {

    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SQUID_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SQUID_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SQUID_DEATH;
    }
    //  @Override
//  public void onGetItem(ItemEntity e) {
//      if (e.getItem().is(ModItems.COOKED_GIGANHINGA.get())) {
//          this.gameEvent(GameEvent.EAT);
//          this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
//          producePearlItem = true;
//          Entity itemThrower = e.getOwner();
//          if(itemThrower != null){
//              feederUUID = itemThrower.getUUID();
//          }
//      } else if (e.getItem().is(ModItems.TAPEJARA_FLASK.get())) {
//          this.gameEvent(GameEvent.EAT);
//          this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
//          produceUrchinItem = true;
//          Entity itemThrower = e.getOwner();
//          if(itemThrower != null){
//              feederUUID = itemThrower.getUUID();
//          }
//      } else {
//          feederUUID = null;
//      }
//      this.heal(10);
//  }
    static class MoveHelperController extends MoveControl {
        private final HarboroteuthisEntity dolphin;

        public MoveHelperController(HarboroteuthisEntity dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                double d0 = this.wantedX - this.dolphin.getX();
                double d1 = this.wantedY - this.dolphin.getY();
                double d2 = this.wantedZ - this.dolphin.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < 2.500000277905201E-7) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float)(Mth.atan2(d2, d0) * 57.2957763671875) - 90.0F;
                    this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                    this.dolphin.yBodyRot = this.dolphin.getYRot();
                    this.dolphin.yHeadRot = this.dolphin.getYRot();
                    float f1 = (float)(this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater()) {
                        this.dolphin.setSpeed(f1 * 0.02F);
                        float f2 = -((float)(Mth.atan2(d1, (double)Mth.sqrt((float)(d0 * d0 + d2 * d2))) * 57.2957763671875));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                        float f3 = Mth.cos(this.dolphin.getXRot() * 0.017453292F);
                        float f4 = Mth.sin(this.dolphin.getXRot() * 0.017453292F);
                        this.dolphin.zza = f3 * f1;
                        this.dolphin.yya = -f4 * f1;
                    } else {
                        this.dolphin.setSpeed(f1 * 0.1F);
                    }
                }
            } else {
                this.dolphin.setSpeed(0.0F);
                this.dolphin.setXxa(0.0F);
                this.dolphin.setYya(0.0F);
                this.dolphin.setZza(0.0F);
            }

        }
    }
}
