package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.ai.BottomWalkGoal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class NeilpeartiaEntity extends EntityBaseDinosaurAnimal implements GeoEntity, IHatchableEntity {
    private static final EntityDataAccessor<Integer> GULPING_TIME;
    private static final EntityDataAccessor<Boolean> GULPING;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Level level;

    public float prevGulpProgress;
    public float gulpProgress;
    public float gulpSoundTimer;
    public static final Logger LOGGER;
    private static final RawAnimation FROGFISH_WALK;
    private static final RawAnimation FROGFISH_IDLE;
    private static final RawAnimation FROGFISH_GULP;

    public NeilpeartiaEntity(EntityType<? extends EntityBaseDinosaurAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.gulpSoundTimer = 100.0F;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    //goal code
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new BottomWalkGoal(this, 1.0D, 10, 50));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
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

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.MAX_HEALTH, 16.0D).build();
    }
    public boolean getIsGulping() {
        return (Boolean)this.entityData.get(GULPING);
    }

    public void setIsGulping(boolean preening) {
        this.entityData.set(GULPING, preening);
    }

    public int getGulpingTime() {
        return (Integer)this.entityData.get(GULPING_TIME);
    }

    public void setGulpingTime(int shaking) {
        this.entityData.set(GULPING_TIME, shaking);
    }

    //variant code
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(GULPING_TIME, 0);
        this.entityData.define(GULPING, false);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Integer.valueOf(variant));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putInt("gulpingTime", this.getGulpingTime());
        compound.putBoolean("isGulping", this.getIsGulping());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setGulpingTime(compound.getInt("gulpingTime"));
        this.setIsGulping(compound.getBoolean("isGulping"));
    }

    public void determineVariant(int variantChange){
        if(variantChange <= 1){
            this.setVariant(2);
        }else if(variantChange <= 30){
            this.setVariant(1);
        }else{
            this.setVariant(0);
        }
    }
@Override
@Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        int variantChange = this.random.nextInt(0, 100);
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob pOtherParent) {
        return ModEntities.NEILPEARTIA.get().create(serverLevel);
    }

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation(PeculiarPrimordials.MOD_ID, "gameplay/frogfishing");
 private long lastSpawnTime = 0;
 //4800
 private final long spawnInterval = 4800;

 @Override
 public void customServerAiStep() {
     this.prevGulpProgress = this.gulpProgress;
     super.customServerAiStep();
     if (isInWater()) {
         long currentTime = level().getGameTime();
         long timeSinceLastSpawn = currentTime - lastSpawnTime;

         if (timeSinceLastSpawn >= spawnInterval) {
             this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 1.0F, 1.0F);
             spawnRandomItems();
             lastSpawnTime = currentTime;
         }
     }
 }

    private void spawnRandomItems() {
        List<ItemStack> loot = getDigLoot(this);
            for (ItemStack itemStack : loot) {
                spawnAtLocation(itemStack, 0.0F);
            }
    }
    private static List<ItemStack> getDigLoot(NeilpeartiaEntity entity) {
        LootTable loottable = entity.level().getServer().getLootData().getLootTable(LOOT_TABLE);
        return loottable.getRandomItems((new LootParams.Builder((ServerLevel)entity.level())).withParameter(LootContextParams.THIS_ENTITY, entity).create(LootContextParamSets.PIGLIN_BARTER));
    }

//  public void tick() {
//      super.tick();


//      if (this.getGulpingTime() <= 0 && this.getIsGulping()) {
//          this.setIsGulping(false);
//          this.goalSelector.getRunningGoals().forEach(WrappedGoal::start);
//      }

//      if (this.getIsGulping()) {
//          this.setGulpingTime(this.getGulpingTime() - 1);
//          this.getNavigation().stop();
//          this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
//          if (this.gulpSoundTimer <= 0.0F) {
//              this.gulpSoundTimer = 100.0F;
//          }

//          if (this.gulpSoundTimer >= 100.0F) {
//              this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 1.0F, 1.0F);            }

//          --this.gulpSoundTimer;
//          if (this.random.nextInt(90) == 0) {
//              spawnRandomItems();
//          }
//      }

//  }



    //sound code
    protected SoundEvent getAmbientSound() {
        return SoundEvents.TROPICAL_FISH_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.TROPICAL_FISH_FLOP, 0.15F, 1.0F);
    }


    //animations code

    static {
        GULPING_TIME = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.INT);
        GULPING = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.BOOLEAN);
        LOGGER = LogManager.getLogger();
        FROGFISH_GULP = RawAnimation.begin().thenPlay("animation.model.gulp");
        FROGFISH_IDLE = RawAnimation.begin().thenLoop("animation.model.idle");
        FROGFISH_WALK = RawAnimation.begin().thenLoop("animation.model.walk");
    }
    protected <E extends NeilpeartiaEntity> PlayState gulpController(AnimationState<E> event) {

     if (this.getIsGulping()) {
            event.setAndContinue(FROGFISH_GULP);
            return PlayState.CONTINUE;
        } else {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController[]{new AnimationController(this, "Normal", 5, this::Controller)});
        controllers.add(new AnimationController[]{new AnimationController(this, "Gulp", 5, this::gulpController)});
    }

    public double getTick(Object o) {
        return (double)this.tickCount;
    }

    protected <E extends NeilpeartiaEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
     if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.walk"));
                event.getController().setAnimationSpeed(1.5D);
            }
        } else {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.model.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}