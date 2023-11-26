package net.thevaliantsquidward.peculiarprimordials.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ItemFishBucket extends MobBucketItem {
    public ItemFishBucket(Supplier<? extends EntityType<?>> fishTypeIn, Fluid fluid, Item.Properties builder) {
        super(fishTypeIn, () -> fluid, () -> SoundEvents.BUCKET_EMPTY_FISH, builder.stacksTo(1));
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        EntityType fishType = getFishType();
    }
}