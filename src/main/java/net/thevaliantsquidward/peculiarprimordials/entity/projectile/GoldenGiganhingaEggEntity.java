package net.thevaliantsquidward.peculiarprimordials.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;

public class GoldenGiganhingaEggEntity extends ThrowableItemProjectile {
    public GoldenGiganhingaEggEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GoldenGiganhingaEggEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.INFERTILE_EGG.get(), pShooter, pLevel);
    }

    public GoldenGiganhingaEggEntity(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.INFERTILE_EGG.get(), pX, pY, pZ, pLevel);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 1.0F);
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide)
            this.level().broadcastEntityEvent(this, (byte)3);
        this.playSound(ModSounds.SPLAT.get(), 1.0F, 1.0F);
        int i = 3 + this.level().random.nextInt(10) + this.level().random.nextInt(10);
        ExperienceOrb.award((ServerLevel)this.level(), this.position(), i);
        this.discard();
    }

    protected Item getDefaultItem() {
        return ModItems.INFERTILE_EGG.get();
    }
}
