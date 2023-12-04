package net.thevaliantsquidward.peculiarprimordials.item;

import com.peeko32213.unusualprehistory.common.item.ItemModFishBucket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.item.custom.ItemFishBucket;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PeculiarPrimordials.MOD_ID);

    public static final RegistryObject<Item> NEILPEARTIA_SPAWN_EGG = ITEMS.register("neilpeartia_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.NEILPEARTIA, 0x876e36, 0x4e442c, new Item.Properties()));

    public static final RegistryObject<Item> BLOCHIUS_SPAWN_EGG = ITEMS.register("blochius_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLOCHIUS, 0x94b8de, 0xe0e0e0, new Item.Properties()));

    public static final RegistryObject<Item> TAPEJARA_SPAWN_EGG = ITEMS.register("tapejara_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TAPEJARA, 0x1b1b1b, 0xefc15b, new Item.Properties()));

    public static final RegistryObject<Item> GIGANHINGA_SPAWN_EGG = ITEMS.register("giganhinga_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GIGANHINGA, 0x2f3c42, 0x643d3c, new Item.Properties()));


    public static final RegistryObject<Item> RAW_NEILPEARTIA = ITEMS.register("raw_neilpeartia", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.4F).meat().effect(new MobEffectInstance(MobEffects.CONFUSION, 140, 2), 1F).build())));

    public static final RegistryObject<Item> RAW_BLOCHIUS = ITEMS.register("raw_blochius", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.4F).meat().build())));

    public static final RegistryObject<Item> RAW_GIGANHINGA = ITEMS.register("raw_giganhinga", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).meat().effect(new MobEffectInstance(MobEffects.HUNGER, 140, 1), 0.5F).build())));

    public static final RegistryObject<Item> COOKED_GIGANHINGA = ITEMS.register("roast_giganhinga", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(5F).meat().build())));

    public static final RegistryObject<Item> SHED_LURE = ITEMS.register("shed_lure", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NEILPEARTIA_FLASK = ITEMS.register("neilpeartia_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLOCHIUS_FLASK = ITEMS.register("blochius_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TAPEJARA_FLASK = ITEMS.register("tapejara_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GIGANHINGA_FLASK = ITEMS.register("giganhinga_flask", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FISH_LEATHER = ITEMS.register("fish_leather", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLOCHIUS_BUCKET = ITEMS.register("blochius_bucket", () -> new ItemFishBucket(ModEntities.BLOCHIUS, Fluids.WATER, new Item.Properties()));

    public static final RegistryObject<Item> LURE_DISC = ITEMS.register("lure_disc",
            () -> new RecordItem(2, ModSounds.LURE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 2600));

    public static final RegistryObject<Item> GOLDEN_EGG = ITEMS.register("golden_egg", () -> new ExperienceBottleItem(new Item.Properties()));

    public static final RegistryObject<Item> INFERTILE_EGG = ITEMS.register("infertile_egg", () -> new SnowballItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}