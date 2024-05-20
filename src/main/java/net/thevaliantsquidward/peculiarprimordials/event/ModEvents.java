package net.thevaliantsquidward.peculiarprimordials.event;

import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.*;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;

@Mod.EventBusSubscriber(modid = PeculiarPrimordials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {


    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.NEILPEARTIA.get(), NeilpeartiaEntity.setAttributes());
        event.put(ModEntities.BLOCHIUS.get(), BlochiusEntity.setAttributes());
        event.put(ModEntities.TAPEJARA.get(), TapejaraEntity.setAttributes());
        event.put(ModEntities.GIGANHINGA.get(), GiganhingaEntity.setAttributes());
        event.put(ModEntities.FOREYIA.get(), ForeyiaEntity.setAttributes());
    }
}