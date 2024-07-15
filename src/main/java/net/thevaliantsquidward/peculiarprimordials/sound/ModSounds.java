package net.thevaliantsquidward.peculiarprimordials.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thevaliantsquidward.peculiarprimordials.PeculiarPrimordials;



public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PeculiarPrimordials.MOD_ID);


    public static final RegistryObject<SoundEvent> LURE = registerSoundEvents("frogsong");
    public static final RegistryObject<SoundEvent> GILDED_TOOT = registerSoundEvents("trumpet");
    public static final RegistryObject<SoundEvent> FLAMBOYANT_TOOT = registerSoundEvents("saxophone");
    public static final RegistryObject<SoundEvent> ELEGANT_TOOT = registerSoundEvents("flute");
    public static final RegistryObject<SoundEvent> SPLAT = registerSoundEvents("splat");

    public static final RegistryObject<SoundEvent> ANHINGA = registerSoundEvents("anhinga");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(PeculiarPrimordials.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) { SOUND_EVENTS.register(eventBus); }
}