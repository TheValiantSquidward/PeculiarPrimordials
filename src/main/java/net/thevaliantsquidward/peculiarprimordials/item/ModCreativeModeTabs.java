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

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PeculiarPrimordials.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PECULIAR_PRIMORDIALS_TAB = CREATIVE_MODE_TABS.register("peculiar_primordials_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_NEILPEARTIA.get()))
                    .title(Component.translatable("creativetab.peculiar_primordials_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        //order:

                        pOutput.accept(ModItems.RAW_NEILPEARTIA.get());

                        pOutput.accept(ModItems.NEILPEARTIA_SPAWN_EGG.get());

                        pOutput.accept(ModItems.SHED_LURE.get());
                        pOutput.accept(ModItems.FISH_LEATHER.get());

                        pOutput.accept(ModItems.NEILPEARTIA_FLASK.get());

                        pOutput.accept(ModItems.LURE_DISC.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}