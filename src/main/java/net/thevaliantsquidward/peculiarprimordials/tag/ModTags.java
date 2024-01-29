package net.thevaliantsquidward.peculiarprimordials.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials.MOD_ID;

public interface ModTags {
    TagKey<Item> FOREYIA_FOOD = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "foreyia_food"));
}
