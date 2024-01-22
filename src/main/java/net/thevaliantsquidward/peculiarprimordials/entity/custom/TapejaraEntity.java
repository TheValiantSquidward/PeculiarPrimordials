package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.EntityMajungasaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import com.peeko32213.unusualprehistory.common.entity.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.msc.util.navigator.PPFlyingMoveController;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class TapejaraEntity extends EntityBaseDinosaurAnimal implements GeoEntity, NeutralMob, IBookEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private UUID persistentAngerTarget;
    private static final EntityDataAccessor<Boolean> FLYING;
    private static final EntityDataAccessor<Integer> CROPS_POLLINATED;
    private static final EntityDataAccessor<Boolean> FROM_BOOK;
    private static final UniformInt PERSISTENT_ANGER_TIME;
    private static final UniformInt ALERT_INTERVAL;
    private static final int ALERT_RANGE_Y = 10;
    private int remainingPersistentAngerTime;
    private boolean isSchool = true;
    private int ticksUntilNextAlert;
    public int pollinateCooldown = 0;
    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public int ringBufferIndex = -1;
    private boolean isLandNavigator;
    private int timeFlying;
    private static final RawAnimation ANURO_WALK;
    private static final RawAnimation ANURO_IDLE;
    private static final RawAnimation ANURO_FLY;
    private static final RawAnimation ANURO_BITE;

    public TapejaraEntity(EntityType<? extends EntityBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }



    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.MAX_HEALTH, 16.0D).build();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.0));
        this.goalSelector.addGoal(1, new AIFlyIdle());
        GoalSelector var10000 = this.goalSelector;
        Predicate var10009 = EntitySelector.NO_SPECTATORS;
        Objects.requireNonNull(var10009);
        var10000.addGoal(2, new AvoidEntityGoal(this, EntityMajungasaurus.class, 8.0F, 1.6, 1.4, var10009::test));
        var10000 = this.goalSelector;
        var10009 = EntitySelector.NO_SPECTATORS;
        Objects.requireNonNull(var10009);
        var10000.addGoal(2, new AvoidEntityGoal(this, EntityTyrannosaurusRex.class, 8.0F, 1.6, 1.4, var10009::test));
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }

    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PPFlyingMoveController(this, 0.6F, false, true);
            this.navigation = new FlyingPathNavigation(this, this.level()) {
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below(2)).isAir();
                }
            };
            this.navigation.setCanFloat(false);
            this.isLandNavigator = false;
        }

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
        this.entityData.define(CROPS_POLLINATED, 0);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
    }

    public void tick() {
        super.tick();
        this.prevFlyProgress = this.flyProgress;
        if (this.isFlying() && this.flyProgress < 5.0F) {
            ++this.flyProgress;
        }

        if (!this.isFlying() && this.flyProgress > 0.0F) {
            --this.flyProgress;
        }

        if (this.ringBufferIndex < 0) {
            for(int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = 15.0F;
            }
        }

        if (this.pollinateCooldown > 0) {
            --this.pollinateCooldown;
        }

        ++this.ringBufferIndex;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }

        if (!this.level().isClientSide) {
            if (this.isFlying() && this.isLandNavigator) {
                this.switchNavigator(false);
            }

            if (!this.isFlying() && !this.isLandNavigator) {
                this.switchNavigator(true);
            }

            if (this.isFlying()) {
                if (this.isFlying() && !this.onGround() && !this.isInWaterOrBubble()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6000000238418579, 1.0));
                }

                if (this.onGround() && this.timeFlying > 20) {
                    this.setFlying(false);
                }

                ++this.timeFlying;
            } else {
                this.timeFlying = 0;
            }
        }

    }

    public boolean hurt(DamageSource source, float amount) {
        boolean prev = super.hurt(source, amount);
        return prev;
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
    }

    public boolean isFlying() {
        return (Boolean)this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if (!flying || !this.isBaby()) {
            this.entityData.set(FLYING, flying);
        }
    }

    public int getCropsPollinated() {
        return (Integer)this.entityData.get(CROPS_POLLINATED);
    }

    public void setCropsPollinated(int crops) {
        this.entityData.set(CROPS_POLLINATED, crops);
    }

    public boolean canBlockBeSeen(BlockPos pos) {
        double x = (double)((float)pos.getX() + 0.5F);
        double y = (double)((float)pos.getY() + 0.5F);
        double z = (double)((float)pos.getZ() + 0.5F);
        HitResult result = this.level().clip(new ClipContext(new Vec3(this.getX(), this.getY() + (double)this.getEyeHeight(), this.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        double dist = result.getLocation().distanceToSqr(x, y, z);
        return dist <= 1.0 || result.getType() == HitResult.Type.MISS;
    }
//variant code

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.INT);



    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Integer.valueOf(variant));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("CropsPollinated", this.getCropsPollinated());
        compound.putInt("PollinateCooldown", this.pollinateCooldown);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setCropsPollinated(compound.getInt("CropsPollinated"));
        this.pollinateCooldown = compound.getInt("PollinateCooldown");
        this.setVariant(compound.getInt("Variant"));
    }

    public void determineVariant(int variantChange){
        if(variantChange <= 33){
            this.setVariant(2);
        }else if(variantChange <= 66){
            this.setVariant(1);
        }else{
            this.setVariant(0);
        }
    }
    @Override
    @org.jetbrains.annotations.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnDataIn, @org.jetbrains.annotations.Nullable CompoundTag dataTag) {
        int variantChange = this.random.nextInt(0, 100);
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob pOtherParent) {
        return ModEntities.TAPEJARA.get().create(serverLevel);
    }

    protected SoundEvent getAmbientSound() {
        return (SoundEvent)UPSounds.ANURO_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return (SoundEvent)UPSounds.ANURO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return (SoundEvent)UPSounds.ANURO_DEATH.get();
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(10.0F);
    }

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        float radius = -9.450001F - (float)this.getRandom().nextInt(24);
        float neg = this.getRandom().nextBoolean() ? 1.0F : -1.0F;
        float renderYawOffset = this.yBodyRot;
        float angle = 0.017453292F * renderYawOffset + 3.15F + this.getRandom().nextFloat() * neg;
        double extraX = (double)(radius * Mth.sin(3.1415927F + angle));
        double extraZ = (double)(radius * Mth.cos(angle));
        BlockPos radialPos = new BlockPos((int)(fleePos.x() + extraX), (int)this.getY(), (int)(fleePos.z() + extraZ));
        BlockPos ground = this.getAnuroGround(radialPos);
        if (ground.getY() == -64) {
            return this.position();
        } else {
            for(ground = this.blockPosition(); ground.getY() > -64 && !this.level().getBlockState(ground).isSolid(); ground = ground.below()) {
            }

            return !this.isTargetBlocked(Vec3.atCenterOf(ground.above())) ? Vec3.atCenterOf(ground) : null;
        }
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        float radius = 5.0F + radiusAdd + (float)this.getRandom().nextInt(5);
        float neg = this.getRandom().nextBoolean() ? 1.0F : -1.0F;
        float renderYawOffset = this.yBodyRot;
        float angle = 0.017453292F * renderYawOffset + 3.15F + this.getRandom().nextFloat() * neg;
        double extraX = (double)(radius * Mth.sin((float)(Math.PI + (double)angle)));
        double extraZ = (double)(radius * Mth.cos(angle));
        BlockPos radialPos = new BlockPos((int)(fleePos.x() + extraX), (int)this.getY(), (int)(fleePos.z() + extraZ));
        BlockPos ground = this.getAnuroGround(radialPos);
        int distFromGround = (int)this.getY() - ground.getY();
        int flightHeight = 5 + this.getRandom().nextInt(5);
        int j = this.getRandom().nextInt(5) + 5;
        BlockPos newPos = ground.above(distFromGround > 5 ? flightHeight : j);
        return !this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1.0 ? Vec3.atCenterOf(newPos) : null;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position;
        for(position = this.blockPosition(); position.getY() > -65 && this.level().isEmptyBlock(position); position = position.below()) {
        }

        return !this.level().getFluidState(position).isEmpty() || this.level().getBlockState(position).is(Blocks.VINE) || position.getY() <= -65;
    }

    public BlockPos getAnuroGround(BlockPos in) {
        BlockPos position;
        for(position = new BlockPos(in.getX(), (int)this.getY(), in.getZ()); position.getY() > -64 && !this.level().getBlockState(position).isSolid() && this.level().getFluidState(position).isEmpty(); position = position.below()) {
        }

        return position;
    }

    public boolean doHurtTarget(Entity target) {
        float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity livingEntity) {
            damage += livingEntity.getMobType().equals(MobType.ARTHROPOD) ? damage : 0.0F;
            knockback += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        boolean shouldHurt;
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback((double)(knockback * 0.5F), (double)Mth.sin(this.getYRot() * 0.017453292F), (double)(-Mth.cos(this.getYRot() * 0.017453292F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }

        return shouldHurt;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return null;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
        controllers.add(new AnimationController[]{new AnimationController(this, "Attack", 0, this::attackController)});
    }

    protected <E extends TapejaraEntity> PlayState Controller(AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        } else if (event.isMoving() && this.onGround() && this.onGround()) {
            return event.setAndContinue(ANURO_WALK);
        } else {
            return !event.isMoving() && this.onGround() && this.onGround() ? event.setAndContinue(ANURO_IDLE) : event.setAndContinue(ANURO_FLY);
        }
    }

    protected <E extends TapejaraEntity> PlayState attackController(AnimationState<E> event) {
        return this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED) ? event.setAndContinue(ANURO_BITE) : PlayState.CONTINUE;
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public double getTick(Object o) {
        return (double)this.tickCount;
    }

    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setRemainingPersistentAngerTime(int p_21673_) {
        this.remainingPersistentAngerTime = p_21673_;
    }

    public @org.jetbrains.annotations.Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID p_21672_) {
        this.persistentAngerTarget = p_21672_;
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public boolean isFromBook() {
        return (Boolean)this.entityData.get(FROM_BOOK);
    }

    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }




    static {
        FLYING = SynchedEntityData.defineId( TapejaraEntity.class, EntityDataSerializers.BOOLEAN);
        CROPS_POLLINATED = SynchedEntityData.defineId( TapejaraEntity.class, EntityDataSerializers.INT);
        FROM_BOOK = SynchedEntityData.defineId( TapejaraEntity.class, EntityDataSerializers.BOOLEAN);
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
        ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
        ANURO_WALK = RawAnimation.begin().thenLoop("animation.model.new5");
        ANURO_IDLE = RawAnimation.begin().thenLoop("animation.model.new4");
        ANURO_FLY = RawAnimation.begin().thenLoop("animation.model.fly");
        ANURO_BITE = RawAnimation.begin().thenPlay("animation.anuro.bite");
    }

    private class AIFlyIdle extends Goal {
        protected double x;
        protected double y;
        protected double z;

        public AIFlyIdle() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (! TapejaraEntity.this.isVehicle() && ( TapejaraEntity.this.getTarget() == null || ! TapejaraEntity.this.getTarget().isAlive()) && ! TapejaraEntity.this.isPassenger()) {
                if ( TapejaraEntity.this.getRandom().nextInt(45) != 0 && ! TapejaraEntity.this.isFlying()) {
                    return false;
                } else {
                    Vec3 lvt_1_1_ = this.getPosition();
                    if (lvt_1_1_ == null) {
                        return false;
                    } else {
                        this.x = lvt_1_1_.x;
                        this.y = lvt_1_1_.y;
                        this.z = lvt_1_1_.z;
                        return true;
                    }
                }
            } else {
                return false;
            }
        }

        public void tick() {
             TapejaraEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1.0);
            if ( TapejaraEntity.this.isFlying() &&  TapejaraEntity.this.onGround() &&  TapejaraEntity.this.timeFlying > 10) {
                 TapejaraEntity.this.setFlying(false);
            }

        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d =  TapejaraEntity.this.position();
            return  TapejaraEntity.this.timeFlying >= 200 && ! TapejaraEntity.this.isOverWaterOrVoid() ?  TapejaraEntity.this.getBlockGrounding(vector3d) :  TapejaraEntity.this.getBlockInViewAway(vector3d, 0.0F);
        }

        public boolean canContinueToUse() {
            return  TapejaraEntity.this.isFlying() &&  TapejaraEntity.this.distanceToSqr(this.x, this.y, this.z) > 5.0;
        }

        public void start() {
             TapejaraEntity.this.setFlying(true);
             TapejaraEntity.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1.0);
        }

        public void stop() {
             TapejaraEntity.this.getNavigation().stop();
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            super.stop();
        }
    }
}
