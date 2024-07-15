package net.thevaliantsquidward.peculiarprimordials.item.custom;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;

public class GildedHornItem extends Item {



    public GildedHornItem(Properties properties) {
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
        if( !player.isUsingItem() && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.playSound(ModSounds.GILDED_TOOT.get(), 1.0F, 1.0F);
        }
        if (level instanceof ServerLevel sl && !player.isUsingItem() && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.startUsingItem(hand);

            player.getCooldowns().addCooldown(this, getUseDuration(stack));
            long roundedTime = level.getDayTime() % 24000;
            boolean night = roundedTime >= 13000 && roundedTime <= 22000;

            if(night) {
                sl.setDayTime(0);
            } else {
                sl.setDayTime(13000);
            }
        }



        return InteractionResultHolder.success(stack);
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

