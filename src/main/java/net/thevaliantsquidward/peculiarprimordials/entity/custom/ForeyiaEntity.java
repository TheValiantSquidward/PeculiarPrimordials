package net.thevaliantsquidward.peculiarprimordials.entity.custom;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.npc.InventoryCarrier;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ForeyiaEntity extends WaterAnimal implements GeoEntity, IPicksUpItems {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation DUNK_BEACHED;
    private static final RawAnimation DUNK_SWIM;
    private static final RawAnimation DUNK_IDLE;
    public UUID feederUUID = null;
    public int fishFeedings = 0;
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(ForeyiaEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ENTITY_STATE;
private static final EntityDataAccessor<Integer> ANIMATION_STATE;



    public int tickertimer = 0;
public boolean produceItem = false;
public boolean passive = false;
    public ForeyiaEntity(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
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
        this.goalSelector.addGoal(0, new ForeyiaBreakCoralsGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityDunkleosteus.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityTyrannosaurusRex.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.targetSelector.addGoal(1, new TargetItems<>(this, false));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);

    }

    @Override
    public void tick() {
super.tick();

if (produceItem) {

    tickertimer++;
    if (tickertimer >= 100) {
        tickertimer = 0;
        this.spawnAtLocation(Items.SAND);
        this.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
        produceItem = false;
    }
}
    }
    @Override
    @Nonnull
  public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      Item item = itemstack.getItem();

      if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

      if (itemstack.is(ModTags.FOREYIA_FOOD) && !produceItem) {

          int size = itemstack.getCount();

          if (!player.isCreative()) {

              itemstack.shrink(1);
          }

              this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

              produceItem = true;
              this.heal(10);
              return InteractionResult.SUCCESS;
      } else if(itemstack.getItem() == UPItems.GOLDEN_SCAU.get()) {


          if (!this.level().isClientSide) {
              if (!player.isCreative()) {
                  itemstack.shrink(1);
              }
              this.setTarget((LivingEntity)null);
              this.heal(20.0F);
              this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

              this.passive = true;

          }
          return InteractionResult.SUCCESS;
      } else {
          return InteractionResult.PASS;
                }

  }
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("FishFeedings", this.fishFeedings);


        if(feederUUID != null){
            compound.putUUID("FeederUUID", feederUUID);
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.fishFeedings = compound.getInt("FishFeedings");


        if(compound.hasUUID("FeederUUID")){
            this.feederUUID = compound.getUUID("FeederUUID");
        }
    }


    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.ARMOR, 1)
                .build();
    }


    @Override
    public boolean canTargetItem(ItemStack stack) {
        return stack.is(ModTags.FOREYIA_FOOD);
    }

    @Override
    public void onGetItem(ItemEntity e) {
        if (e.getItem().is(ModTags.FOREYIA_FOOD)) {
            fishFeedings++;
            this.gameEvent(GameEvent.EAT);
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            produceItem = true;
            Entity itemThrower = e.getOwner();
                if(itemThrower != null){
                    feederUUID = itemThrower.getUUID();
                }
        } else {
            feederUUID = null;
        }
        this.heal(10);
    }
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(travelVector);
        }

    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }


    public class ForeyiaBreakCoralsGoal extends Goal {
        private final ForeyiaEntity foreyia;
        private int idleAtFlowerTime = 0;
        private int timeoutCounter = 0;
        private int searchCooldown = 0;
        private boolean isAboveDestinationBear;
        private BlockPos destinationBlock;
        private final BlockSorter targetSorter;



        public ForeyiaBreakCoralsGoal(ForeyiaEntity foreyia) {
            super();
            this.foreyia = foreyia;
            this.targetSorter = new BlockSorter(foreyia);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
        }

        public void start() {
            super.start();
        }

        public boolean canUse() {

            if (!foreyia.isBaby()  && !foreyia.passive &&  (foreyia.getTarget() == null || !foreyia.getTarget().isAlive())) {
                if(searchCooldown <= 0){
                    resetTarget();
                    searchCooldown = 100 + foreyia.getRandom().nextInt(200);
                    return destinationBlock != null;
                }else{
                    searchCooldown--;
                }
            }
            return false;
        }

        public boolean canContinueToUse() {
            return destinationBlock != null && timeoutCounter < 1200 && (foreyia.getTarget() == null || !foreyia.getTarget().isAlive());
        }

        public void stop() {
            searchCooldown = 50;
            timeoutCounter = 0;
            destinationBlock = null;
        }

        public double getTargetDistanceSq() {
            return 2.3D;
        }

        public void tick() {
            BlockPos blockpos = destinationBlock;
            float yDist = (float) Math.abs(blockpos.getY() - foreyia.getY() - foreyia.getBbHeight()/2);
            this.foreyia.getNavigation().moveTo((double) ((float) blockpos.getX()) + 0.5D, blockpos.getY() + 0.5D, (double) ((float) blockpos.getZ()) + 0.5D, 1);
            if (!isWithinXZDist(blockpos, foreyia.position(), this.getTargetDistanceSq()) || yDist > 2F) {
                this.isAboveDestinationBear = false;
                ++this.timeoutCounter;
            } else {
                this.isAboveDestinationBear = true;
                --this.timeoutCounter;
            }
            if(timeoutCounter > 2400){
                stop();
            }
            if (this.getIsAboveDestination()) {
                foreyia.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(destinationBlock.getX() + 0.5D, destinationBlock.getY(), destinationBlock.getZ() + 0.5));
                if (this.idleAtFlowerTime >= 2) {
                    idleAtFlowerTime = 0;
                    this.breakBlock();
                    playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                    this.stop();
                } else {
                    ++this.idleAtFlowerTime;
                }
            }

            super.tick();

            if (produceItem) {
                tickertimer++;
                if (tickertimer >= 100) {
                    tickertimer = 0;

                    produceItem = false;
                }
            }
        }

        private void resetTarget() {
            List<BlockPos> allBlocks = new ArrayList<>();
            int radius = 16;
            for (BlockPos pos : BlockPos.betweenClosedStream(this.foreyia.blockPosition().offset(-radius, -radius, -radius), this.foreyia.blockPosition().offset(radius, radius, radius)).map(BlockPos::immutable).collect(Collectors.toList())) {
                if (!foreyia.level().isEmptyBlock(pos) && shouldMoveTo(foreyia.level(), pos)) {
                    if(!foreyia.isInWater() || isBlockTouchingWater(pos)){
                        allBlocks.add(pos);
                    }
                }
            }
            if (!allBlocks.isEmpty()) {
                allBlocks.sort(this.targetSorter);
                for(BlockPos pos : allBlocks){
                    if(hasLineOfSightBlock(pos)){
                        this.destinationBlock = pos;
                        return;
                    }
                }
            }
            destinationBlock = null;
        }

        private boolean isBlockTouchingWater(BlockPos pos) {
            for(Direction dir : Direction.values()){
                if(foreyia.level().getFluidState(pos.relative(dir)).is(FluidTags.WATER)){
                    return true;
                }
            }
            return false;
        }

        private boolean isWithinXZDist(BlockPos blockpos, Vec3 positionVec, double distance) {
            return blockpos.distSqr(ModBlockPos.fromCoords(positionVec.x(), blockpos.getY(), positionVec.z())) < distance * distance;
        }

        protected boolean getIsAboveDestination() {
            return this.isAboveDestinationBear;
        }

        private void breakBlock() {
            if (shouldMoveTo(foreyia.level(), destinationBlock)) {
                BlockState state = foreyia.level().getBlockState(destinationBlock);
                if(!foreyia.level().isEmptyBlock(destinationBlock) && net.minecraftforge.common.ForgeHooks.canEntityDestroy(foreyia.level(), destinationBlock, foreyia) && state.getDestroySpeed(foreyia.level(), destinationBlock) >= 0){
                    foreyia.level().destroyBlock(destinationBlock, true);
                    produceItem = true;
                }
            }
        }

        private boolean hasLineOfSightBlock(BlockPos destinationBlock) {
            Vec3 Vector3d = new Vec3(foreyia.getX(), foreyia.getEyeY(), foreyia.getZ());
            Vec3 blockVec = net.minecraft.world.phys.Vec3.atCenterOf(destinationBlock);
            BlockHitResult result = foreyia.level().clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, foreyia));
            return result.getBlockPos().equals(destinationBlock);
        }


        protected boolean shouldMoveTo(LevelReader worldIn, BlockPos pos) {
            Item blockItem = worldIn.getBlockState(pos).getBlock().asItem();
            return worldIn.getBlockState(pos).is(BlockTags.CORALS);
        }

        public record BlockSorter(Entity entity) implements Comparator<BlockPos> {
            @Override
            public int compare(BlockPos pos1, BlockPos pos2) {
                final double distance1 = this.getDistance(pos1);
                final double distance2 = this.getDistance(pos2);
                return Double.compare(distance1, distance2);
            }

            private double getDistance(BlockPos pos) {
                final double deltaX = this.entity.getX() - (pos.getX() + 0.5);
                final double deltaY = this.entity.getY() + this.entity.getEyeHeight() - (pos.getY() + 0.5);
                final double deltaZ = this.entity.getZ() - (pos.getZ() + 0.5);
                return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            }
        }

    }

    public int getAnimationState() {
        return (Integer)this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
    }
    public int getEntityState() {
        return (Integer)this.entityData.get(ENTITY_STATE);
    }

    public void setEntityState(int anim) {
        this.entityData.set(ENTITY_STATE, anim);
    }

    protected <E extends ForeyiaEntity> PlayState Controller(AnimationState<E> event) {
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
                       ANIMATION_STATE = SynchedEntityData.defineId(ForeyiaEntity.class, EntityDataSerializers.INT);
              ENTITY_STATE = SynchedEntityData.defineId(ForeyiaEntity.class, EntityDataSerializers.INT);
         DUNK_SWIM = RawAnimation.begin().thenLoop("animation.model.new");
        DUNK_IDLE = RawAnimation.begin().thenLoop("animation.model.new");
               DUNK_BEACHED = RawAnimation.begin().thenLoop("animation.model.flop");
    }



    static class MoveHelperController extends MoveControl {
        private final ForeyiaEntity dolphin;

        public MoveHelperController(ForeyiaEntity dolphinIn) {
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
