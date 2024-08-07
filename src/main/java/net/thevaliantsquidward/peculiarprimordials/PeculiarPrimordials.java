package net.thevaliantsquidward.peculiarprimordials;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thevaliantsquidward.peculiarprimordials.block.ModBlocks;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.entity.client.*;
import net.thevaliantsquidward.peculiarprimordials.item.ModCreativeModeTabs;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;
import net.thevaliantsquidward.peculiarprimordials.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;


@Mod(PeculiarPrimordials.MOD_ID)
public class PeculiarPrimordials
{
    public static final String MOD_ID = "peculiarprimordials";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PeculiarPrimordials()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModEntities.register(modEventBus);

        ModSounds.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.BLOCKS.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register
                    (ModEntities.NEILPEARTIA.get(), NeilpeartiaRenderer:: new);
            EntityRenderers.register
                    (ModEntities.BLOCHIUS.get(), BlochiusRenderer:: new);
            EntityRenderers.register
                    (ModEntities.TAPEJARA.get(), TapejaraRenderer:: new);
            EntityRenderers.register
                    (ModEntities.GIGANHINGA.get(), GiganhingaRenderer:: new);
            EntityRenderers.register
                    (ModEntities.FOREYIA.get(), ForeyiaRenderer:: new);
            EntityRenderers.register
                    (ModEntities.INFERTILE_EGG.get(), ThrownItemRenderer::new);
            EntityRenderers.register
                    (ModEntities.GOLDEN_EGG.get(), ThrownItemRenderer::new);
        }
    }
    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(PeculiarPrimordials.MOD_ID, path);
    }
}