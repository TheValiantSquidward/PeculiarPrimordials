package net.thevaliantsquidward.peculiarprimordials.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.BlochiusEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.NeilpeartiaEntity;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.TapejaraEntity;

@Mod.EventBusSubscriber(modid = PeculiarPrimordials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.NEILPEARTIA.get(), NeilpeartiaEntity.setAttributes());
        event.put(ModEntities.BLOCHIUS.get(), BlochiusEntity.setAttributes());
        event.put(ModEntities.TAPEJARA.get(), TapejaraEntity.setAttributes());
    }
}