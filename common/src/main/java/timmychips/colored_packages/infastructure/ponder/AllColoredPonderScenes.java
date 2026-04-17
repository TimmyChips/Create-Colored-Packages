package timmychips.colored_packages.infastructure.ponder;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagerScenes;

public class AllColoredPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(new ItemProviderEntry[]{AllBlocks.PACKAGER}).addStoryBoard("high_logistics/dyed_packager", ColoredPackagerScenes::coloredPackager);
    }
}
