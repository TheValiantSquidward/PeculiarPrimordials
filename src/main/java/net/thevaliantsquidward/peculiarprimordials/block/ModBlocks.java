package net.thevaliantsquidward.peculiarprimordials.block;

import com.peeko32213.unusualprehistory.common.block.BlockDinosaurLandEggs;
import com.peeko32213.unusualprehistory.common.block.BlockDinosaurWaterEggs;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;
import net.thevaliantsquidward.peculiarprimordials.entity.ModEntities;
import net.thevaliantsquidward.peculiarprimordials.item.ModItems;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            PeculiarPrimordials.MOD_ID);



    public static final Supplier<Block> NEILPEARTIA_EGGS = create("neilpeartia_eggs",
            () -> new BlockDinosaurWaterEggs(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    ModEntities.NEILPEARTIA,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> BLOCHIUS_EGGS = create("blochius_eggs",
            () -> new BlockDinosaurWaterEggs(BlockBehaviour.Properties.copy(Blocks.FROGSPAWN).instabreak().noOcclusion().noCollission().randomTicks(),
                    ModEntities.BLOCHIUS,
                    false
            ),
            entry -> new PlaceOnWaterBlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> GIGANHINGA_EGG = create("giganhinga_eggs",
            () -> new BlockDinosaurLandEggs(
                    BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    ModEntities.GIGANHINGA, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(3.0D, 0.0D, 3.0D, 15.0D, 7.0D, 15.0D)
            ),
            entry -> new BlockItem(entry.get(), new Item.Properties()));

    public static final Supplier<Block> TAPEJARA_EGG = create("tapejara_eggs",
            () -> new BlockDinosaurLandEggs(
                    BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion(),
                    ModEntities.TAPEJARA, 3,
                    Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D),
                    Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D)
            ),
            entry -> new BlockItem(entry.get(), new Item.Properties()));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        ModItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties()));
    }



    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }

}
