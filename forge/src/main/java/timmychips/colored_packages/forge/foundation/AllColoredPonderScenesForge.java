package timmychips.colored_packages.forge.foundation;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.forge.foundation.ponder.scenes.ColoredPackagerScenesForge;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagerScenes;

public class AllColoredPonderScenesForge {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(new ItemProviderEntry[]{AllDyedBlocks.DYED_PACKAGER}).addStoryBoard("high_logistics/dyed_packager", ColoredPackagerScenesForge::coloredPackager);
    }
}
