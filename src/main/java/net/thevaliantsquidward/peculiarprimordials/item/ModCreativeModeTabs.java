package net.thevaliantsquidward.peculiarprimordials.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PeculiarPrimordials.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PECULIAR_PRIMORDIALS_TAB = CREATIVE_MODE_TABS.register("peculiar_primordials_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_NEILPEARTIA.get()))
                    .title(Component.translatable("creativetab.peculiar_primordials_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        //order:

                        pOutput.accept(ModItems.RAW_BLOCHIUS.get());
                        pOutput.accept(ModItems.COOKED_BLOCHIUS.get());
                        pOutput.accept(ModItems.RAW_NEILPEARTIA.get());
                        pOutput.accept(ModItems.RAW_FOREYIA.get());
                        pOutput.accept(ModItems.RAW_GIGANHINGA.get());
                        pOutput.accept(ModItems.COOKED_GIGANHINGA.get());
                        pOutput.accept(ModItems.ANHINGA_OMELETTE.get());
                        pOutput.accept(ModItems.CHARRED_STETHACANTHUS.get());

                        pOutput.accept(ModItems.BLOCHIUS_SPAWN_EGG.get());
                        pOutput.accept(ModItems.FOREYIA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.NEILPEARTIA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.TAPEJARA_SPAWN_EGG.get());
                        pOutput.accept(ModItems.GIGANHINGA_SPAWN_EGG.get());


                        pOutput.accept(ModItems.SHED_LURE.get());

                        pOutput.accept(ModItems.GILDED_CREST.get());
                        pOutput.accept(ModItems.FLAMBOYANT_CREST.get());
                        pOutput.accept(ModItems.ELEGANT_CREST.get());
                        pOutput.accept(ModItems.STURDY_SCUTE.get());
                        pOutput.accept(ModItems.CLAW_SHED.get());
                        pOutput.accept(ModItems.FLASK_OF_WHALE_OIL.get());

                        pOutput.accept(ModItems.BLOCHIUS_FLASK.get());
                        pOutput.accept(ModItems.FOREYIA_FLASK.get());
                        pOutput.accept(ModItems.NEILPEARTIA_FLASK.get());
                        pOutput.accept(ModItems.GIGANHINGA_FLASK.get());
                        pOutput.accept(ModItems.TAPEJARA_FLASK.get());

                        pOutput.accept(ModBlocks.BLOCHIUS_EGGS.get());
                        pOutput.accept(ModBlocks.FOREYIA_EGGS.get());
                        pOutput.accept(ModBlocks.NEILPEARTIA_EGGS.get());
                        pOutput.accept(ModBlocks.GIGANHINGA_EGG.get());
                        pOutput.accept(ModBlocks.TAPEJARA_EGG.get());

                        pOutput.accept(ModBlocks.STURDY_SHINGLES.get());

                        pOutput.accept(ModItems.BLOCHIUS_BUCKET.get());

                        pOutput.accept(ModItems.GILDED_HORN.get());
                        pOutput.accept(ModItems.FLAMBOYANT_SAXAPHONE.get());
                        pOutput.accept(ModItems.ELEGANT_FLUTE.get());
                        pOutput.accept(ModItems.LURE_DISC.get());
                        pOutput.accept(ModItems.GOLDEN_EGG.get());
                        pOutput.accept(ModItems.INFERTILE_EGG.get());
                        //pOutput.accept(ModItems.WHALER_PISTOL.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}