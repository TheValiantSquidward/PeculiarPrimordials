package net.thevaliantsquidward.peculiarprimordials.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.*;
import net.thevaliantsquidward.peculiarprimordials.entity.projectile.GoldenGiganhingaEggEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.projectile.InfertileGiganhingaEggEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PeculiarPrimordials.MOD_ID);


    public static final RegistryObject<EntityType<NeilpeartiaEntity>> NEILPEARTIA =
            ENTITY_TYPES.register("neilpeartia",
                    () -> EntityType.Builder.of(NeilpeartiaEntity::new, MobCategory.WATER_CREATURE)
                            .sized(1.4F, 1.6F)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "neilpeartia").toString()));

    public static final RegistryObject<EntityType<ForeyiaEntity>> FOREYIA =
            ENTITY_TYPES.register("foreyia",
                    () -> EntityType.Builder.of(ForeyiaEntity::new, MobCategory.WATER_CREATURE)
                            .sized(1.5F, 1.7F)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "foreyia").toString()));

    public static final RegistryObject<EntityType<BlochiusEntity>> BLOCHIUS =
            ENTITY_TYPES.register("blochius",
                    () -> EntityType.Builder.of(BlochiusEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1.1f, 0.6f)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "blochius").toString()));

    public static final RegistryObject<EntityType<TapejaraEntity>> TAPEJARA =
            ENTITY_TYPES.register("tapejara",
                    () -> EntityType.Builder.of(TapejaraEntity::new, MobCategory.CREATURE)
                            .sized(1.6F, 1.95F)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "tapejara").toString()));

    public static final RegistryObject<EntityType<GiganhingaEntity>> GIGANHINGA =
            ENTITY_TYPES.register("giganhinga",
                    () -> EntityType.Builder.of(GiganhingaEntity::new, MobCategory.CREATURE)
                            .sized(1.0F, 1.95F)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "giganhinga").toString()));

    public static final RegistryObject<EntityType<InfertileGiganhingaEggEntity>> INFERTILE_EGG =
            ENTITY_TYPES.register("infertile_egg_projectile", () -> EntityType.Builder.<InfertileGiganhingaEggEntity>of(InfertileGiganhingaEggEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("infertile_egg_projectile"));

    public static final RegistryObject<EntityType<GoldenGiganhingaEggEntity>> GOLDEN_EGG =
            ENTITY_TYPES.register("golden_egg_projectile", () -> EntityType.Builder.<GoldenGiganhingaEggEntity>of(GoldenGiganhingaEggEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("golden_egg_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}