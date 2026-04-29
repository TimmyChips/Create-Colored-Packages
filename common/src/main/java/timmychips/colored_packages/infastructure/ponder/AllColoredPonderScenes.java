package timmychips.colored_packages.infastructure.ponder;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.AllPackageItems;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagerScenes;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagesScene;

public class AllColoredPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(new ItemProviderEntry[]{AllBlocks.PACKAGER}).addStoryBoard("high_logistics/dyed_packager", ColoredPackagerScenes::coloredPackager);
        HELPER.forComponents(new ItemProviderEntry[]{AllPackageItems.packageItemEntries.getFirst()}).addStoryBoard("high_logistics/colored_packages_ground", ColoredPackagesScene::allColoredPackages);
            // Note that for the colored packages storyboard, the schematic itself has the colored package entities but Ponder won't show them :(
    }
}
