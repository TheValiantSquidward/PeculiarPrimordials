package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class GiganhingaEntity extends EntityBaseDinosaurAnimal implements GeoEntity {

    private static final RawAnimation ANHINGA_WALK = RawAnimation.begin().thenLoop("animation.model.walk");
    private static final RawAnimation ANHINGA_IDLE = RawAnimation.begin().thenLoop("animation.model.idle");
    private static final RawAnimation ANHINGA_SWIM = RawAnimation.begin().thenLoop("animation.model.idle");

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public GiganhingaEntity(EntityType<? extends EntityBaseDinosaurAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public int eggTime = this.random.nextInt(6000) + 6000;
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && --this.eggTime <= 0) {
            spawnRandomItems();
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }
    }

    private void spawnRandomItems() {
        List<ItemStack> loot = getDigLoot(this);

        if (!loot.isEmpty()) {
            for (ItemStack itemStack : loot) {
                spawnAtLocation(itemStack, 0.0F);
            }
            playSound(SoundEvents.CHICKEN_EGG, 1.0F, 1.0F);
        }
    }

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation(PeculiarPrimordials.MOD_ID, "gameplay/anhingaegglay");
    private Level level;
    private static List<ItemStack> getDigLoot(GiganhingaEntity entity) {
        LootTable lootTable = Objects.requireNonNull(entity.level().getServer()).getLootData().getLootTable(LOOT_TABLE);
        ServerLevel serverLevel = (ServerLevel) entity.level;
        return lootTable.getRandomItems((new LootParams.Builder((ServerLevel) entity.level())).withParameter(LootContextParams.THIS_ENTITY, entity).create(LootContextParamSets.PIGLIN_BARTER));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return ModEntities.GIGANHINGA.get().create(serverLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 7D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 3.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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




    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    protected <E extends GiganhingaEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater()) {
            {
                event.setAndContinue(ANHINGA_WALK);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
        }
        if (this.isInWater()) {
            event.setAndContinue(ANHINGA_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else if (!this.isInWater()) {
            event.setAndContinue(ANHINGA_IDLE);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


}