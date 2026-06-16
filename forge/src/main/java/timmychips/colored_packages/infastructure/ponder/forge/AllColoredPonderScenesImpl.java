package timmychips.colored_packages.infastructure.ponder.forge;

import com.ninni.dye_depot.registry.DDDyes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import timmychips.colored_packages.AllPackageItems;
import timmychips.colored_packages.compat.DyeDepotCompat;
import timmychips.colored_packages.forge.infastructure.ponder.scenes.ColoredPackagesScene;
import timmychips.colored_packages.forge.infastructure.ponder.scenes.DyeDepotPackagesScene;

import static timmychips.colored_packages.AllPackageItems.packageItemEntries;

public class AllColoredPonderScenesImpl {
    public static void registerPlatform(PonderSceneRegistrationHelper<ItemProviderEntry<?>> helper) {
        for (ItemProviderEntry<?> coloredEntry : AllPackageItems.packageItemEntries) {
            helper.forComponents(new ItemProviderEntry[]{coloredEntry}).addStoryBoard("high_logistics/colored_packages_ground", ColoredPackagesScene::allColoredPackages);

            // Dye Depot packages
            if (DyeDepotCompat.HAS_DYE_DEPOT) {
                helper.forComponents(new ItemProviderEntry[]{coloredEntry}).addStoryBoard("high_logistics/colored_packages_ground", DyeDepotPackagesScene::allDyeDepotPackages);
            }
        }
    }
}