package net.thevaliantsquidward.peculiarprimordials.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PeculiarPrimordials.MOD_ID);


    public static final RegistryObject<EntityType<NeilpeartiaEntity>> NEILPEARTIA =
            ENTITY_TYPES.register("neilpeartia",
                    () -> EntityType.Builder.of(NeilpeartiaEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(2.3F, 1.95F)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "neilpeartia").toString()));


    public static final RegistryObject<EntityType<BlochiusEntity>> BLOCHIUS =
            ENTITY_TYPES.register("blochius",
                    () -> EntityType.Builder.of(BlochiusEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1.1f, 0.6f)
                            .build(new ResourceLocation(PeculiarPrimordials.MOD_ID, "blochius").toString()));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}