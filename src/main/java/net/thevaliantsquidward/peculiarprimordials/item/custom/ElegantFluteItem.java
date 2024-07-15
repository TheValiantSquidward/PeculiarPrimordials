package net.thevaliantsquidward.peculiarprimordials.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;
import com.mojang.datafixers.util.Pair;

import java.util.Optional;

public class ElegantFluteItem extends Item {



    public ElegantFluteItem(Properties properties) {
        super(properties);

    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity holder, int duration) {
        if (holder instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            if (level instanceof ServerLevel sl && !player.isCreative()) {
                    stack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(hand));
                }
            }
        }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(level.dimension() == Level.OVERWORLD && !player.isUsingItem() && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.playSound(ModSounds.ELEGANT_TOOT.get(), 1.0F, 1.0F);
        }
        if (player instanceof ServerPlayer serverPlayer && !player.isUsingItem() && !player.getCooldowns().isOnCooldown(stack.getItem())) {

            if(level.dimension() == Level.OVERWORLD) {
                player.startUsingItem(hand);
                player.getCooldowns().addCooldown(this, getUseDuration(stack));
                teleportToSpawnPosition(serverPlayer);
                return InteractionResultHolder.success(stack);
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
        return InteractionResultHolder.success(stack);
    }

    public static Pair<Vec3, ServerLevel> getSpawnData(ServerPlayer player) {
        BlockPos respawnPosition = player.getRespawnPosition();
        ServerLevel serverLevel = player.server.getLevel(player.getRespawnDimension());
        Vec3 exactSpawnPosition = null;
        if (serverLevel == null)
            return new Pair<>(Vec3.ZERO, null);

        if (respawnPosition != null) {
            float angle = player.getRespawnAngle();
            Optional< Vec3 > spawnPosition = Player.findRespawnPositionAndUseSpawnBlock(serverLevel, respawnPosition, angle, true, true);
            if (spawnPosition.isPresent())
                exactSpawnPosition = spawnPosition.get();
        }
        if (exactSpawnPosition == null) {
            serverLevel = player.server.getLevel(Level.OVERWORLD);
            assert serverLevel != null;
            exactSpawnPosition = Vec3.atCenterOf(serverLevel.getSharedSpawnPos());
        }
        return new Pair<>(exactSpawnPosition, serverLevel);
    }


    public static void teleportToSpawnPosition(ServerPlayer player) {
        Pair<Vec3, ServerLevel> spawnData = getSpawnData(player);
        Vec3 spawnPosition = spawnData.getFirst();
        ServerLevel serverLevel = spawnData.getSecond();

        player.teleportTo(serverLevel, spawnPosition.x, spawnPosition.y, spawnPosition.z, player.getYRot(), player.getXRot());
        player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 5*20;
    }

}

