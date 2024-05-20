package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.ai.BottomWalkGoal;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;
import net.thevaliantsquidward.peculiarprimordials.tag.ModTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class NeilpeartiaEntity extends EntityBaseDinosaurAnimal implements GeoEntity, IHatchableEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private Level level;

    public static final Logger LOGGER;
    private static final RawAnimation FROGFISH_WALK;
    private static final RawAnimation FROGFISH_IDLE;


    public NeilpeartiaEntity(EntityType<? extends EntityBaseDinosaurAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

@Override
public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.NEIL_FOOD);
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

    //variant code
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.BOOLEAN);


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(IS_GULPING, false);
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

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));

    }

    public void determineVariant(int variantChange){
        if(isKermit()) {
            this.setVariant(2);
        } else if (variantChange <= 30){
            this.setVariant(1);
        }else{
            this.setVariant(0);
        }
    }

    public boolean isKermit() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && s.toLowerCase().contains("kermit");
    }


    public boolean passive = false;

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == UPItems.GOLDEN_SCAU.get() && !this.passive) {

            if (!this.level().isClientSide) {

                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                this.heal(20.0F);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.passive = true;
                return InteractionResult.SUCCESS;
            }
        } else
        if (itemstack.getItem() == ModItems.CHARRED_STETHACANTHUS.get() && this.passive) {

            if (!this.level().isClientSide) {

                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                this.heal(10.0F);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.passive = false;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
@Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        int variantChange = this.random.nextInt(0, 100);
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || super.isInvulnerableTo(source);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob pOtherParent) {
        return ModEntities.NEILPEARTIA.get().create(serverLevel);
    }

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation(PeculiarPrimordials.MOD_ID, "gameplay/frogfishing");


    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.isGulping();
    }

    public int gulpNumber = 200;

    public int gulpInterval = this.random.nextInt(gulpNumber) + gulpNumber;

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && --this.gulpInterval <= 0) {
            setGulping(true);
            spawnRandomItems();
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.gulpInterval = this.random.nextInt(gulpNumber) + gulpNumber;
            playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.5F, 1.0F);
        } else if (this.gulpInterval > 0) {
            setGulping(false);
        }
    }

    private void spawnRandomItems() {
        List<ItemStack> loot = getFishingLoot(this);
            for (ItemStack itemStack : loot) {
                spawnAtLocation(itemStack, 0.0F);
            }
    }

    private static List<ItemStack> getFishingLoot(NeilpeartiaEntity entity) {
        LootTable loottable = entity.level().getServer().getLootData().getLootTable(LOOT_TABLE);
        return loottable.getRandomItems((new LootParams.Builder((ServerLevel)entity.level())).withParameter(LootContextParams.THIS_ENTITY, entity).create(LootContextParamSets.PIGLIN_BARTER));
    }


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

    private static final EntityDataAccessor<Boolean> IS_GULPING = SynchedEntityData.defineId(NeilpeartiaEntity.class, EntityDataSerializers.BOOLEAN);




    static {
     LOGGER = LogManager.getLogger();
     FROGFISH_IDLE = RawAnimation.begin().thenLoop("animation.model.idle");
     FROGFISH_WALK = RawAnimation.begin().thenLoop("animation.model.walk");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isInWater() && !this.onGround()) {
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.swim", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isGulping()) {
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.gulp", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public boolean isGulping() {
        return entityData.get(IS_GULPING);
    }

    public void setGulping(boolean gulping) {
        entityData.set(IS_GULPING, gulping);
    }

}