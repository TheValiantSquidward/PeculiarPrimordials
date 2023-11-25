package net.thevaliantsquidward.peculiarprimordials.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PeculiarPrimordials.MOD_ID);

    public static final RegistryObject<Item> NEILPEARTIA_SPAWN_EGG = ITEMS.register("neilpeartia_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.NEILPEARTIA, 0x876e36, 0x4e442c, new Item.Properties()));

    public static final RegistryObject<Item> RAW_NEILPEARTIA = ITEMS.register("raw_neilpeartia", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.4F).meat().effect(new MobEffectInstance(MobEffects.CONFUSION, 140, 2), 1F).build())));

    public static final RegistryObject<Item> SHED_LURE = ITEMS.register("shed_lure", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NEILPEARTIA_FLASK = ITEMS.register("neilpeartia_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FISH_LEATHER = ITEMS.register("fish_leather", () -> new Item(new Item.Properties()));

   public static final RegistryObject<Item> LURE_DISC = ITEMS.register("lure_disc",
            () -> new RecordItem(2, ModSounds.LURE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2600));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}