package net.thevaliantsquidward.peculiarprimordials.entity.projectile;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class ChainhookEntity extends Projectile {


    protected ChainhookEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            this.setHookedEntity(pResult.getEntity());
        }
    }
    private static final EntityDataAccessor<Integer> DATA_CHAIN_HOOKED_ENTITY = SynchedEntityData.defineId(ChainhookEntity.class, EntityDataSerializers.INT);
    private Entity hookedIn;
    private FishHookState currentState = FishHookState.FLYING;
    

    
    private void setHookedEntity(@Nullable Entity pHookedEntity) {
        this.hookedIn = pHookedEntity;
        this.getEntityData().set(DATA_CHAIN_HOOKED_ENTITY, pHookedEntity == null ? 0 : pHookedEntity.getId() + 1);
    }
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);



        this.discard();
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_CHAIN_HOOKED_ENTITY, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_CHAIN_HOOKED_ENTITY.equals(pKey)) {
            int i = this.getEntityData().get(DATA_CHAIN_HOOKED_ENTITY);
            this.hookedIn = i > 0 ? this.level().getEntity(i - 1) : null;
        }
        super.onSyncedDataUpdated(pKey);
    }
    private boolean shouldStopFishing(Player pPlayer) {
        ItemStack itemstack = pPlayer.getMainHandItem();
        ItemStack itemstack1 = pPlayer.getOffhandItem();
        boolean flag = itemstack.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        boolean flag1 = itemstack1.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        if (!pPlayer.isRemoved() && pPlayer.isAlive() && (flag || flag1) && !(this.distanceToSqr(pPlayer) > 1024.0D)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    public void tick() {
        super.tick();
        Player player = this.getPlayerOwner();
        if (player == null) {
            this.discard();
        } else if (this.level().isClientSide || !this.shouldStopFishing(player)) {
            if (this.onGround()) {
                    this.discard();
            }

            float f = 0.0F;

            boolean flag = f > 0.0F;
            if (this.currentState ==  FishHookState.FLYING) {
                if (this.hookedIn != null) {
                    this.setDeltaMovement(Vec3.ZERO);
                    this.currentState =  FishHookState.HOOKED_IN_ENTITY;
                    return;
                }

                this.checkCollision();
            } else {
                if (this.currentState ==  FishHookState.HOOKED_IN_ENTITY) {
                    if (this.hookedIn != null) {
                        if (!this.hookedIn.isRemoved() && this.hookedIn.level().dimension() == this.level().dimension()) {
                            this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
                        } else {
                            this.setHookedEntity((Entity)null);
                            this.currentState =  FishHookState.FLYING;
                        }
                    }

                    return;
                }


            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            if (this.currentState ==  FishHookState.FLYING && (this.onGround() || this.horizontalCollision)) {
                this.setDeltaMovement(Vec3.ZERO);
            }

            double d1 = 0.92D;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.92D));
            this.reapplyPosition();
        }
    }

    static enum FishHookState {
        FLYING,
        HOOKED_IN_ENTITY;
    }

    private void checkCollision() {
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() == HitResult.Type.MISS || !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) this.onHit(hitresult);
    }

    @Nullable
    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player ? (Player)entity : null;
    }

}
