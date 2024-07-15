package net.thevaliantsquidward.peculiarprimordials.event;

import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.custom.*;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;

@Mod.EventBusSubscriber(modid = PeculiarPrimordials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        ItemProperties.register(ModItems.GILDED_HORN.get(), new ResourceLocation("playing"), (p_234978_, p_234979_, p_234980_, p_234981_) -> p_234980_ != null && p_234980_.isUsingItem() && p_234980_.getUseItem() == p_234978_ ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.FLAMBOYANT_SAXAPHONE.get(), new ResourceLocation("playing"), (p_234978_, p_234979_, p_234980_, p_234981_) -> p_234980_ != null && p_234980_.isUsingItem() && p_234980_.getUseItem() == p_234978_ ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.ELEGANT_FLUTE.get(), new ResourceLocation("playing"), (p_234978_, p_234979_, p_234980_, p_234981_) -> p_234980_ != null && p_234980_.isUsingItem() && p_234980_.getUseItem() == p_234978_ ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.NEILPEARTIA.get(), NeilpeartiaEntity.setAttributes());
        event.put(ModEntities.BLOCHIUS.get(), BlochiusEntity.setAttributes());
        event.put(ModEntities.TAPEJARA.get(), TapejaraEntity.setAttributes());
        event.put(ModEntities.GIGANHINGA.get(), GiganhingaEntity.setAttributes());
        event.put(ModEntities.FOREYIA.get(), ForeyiaEntity.setAttributes());
    }
}