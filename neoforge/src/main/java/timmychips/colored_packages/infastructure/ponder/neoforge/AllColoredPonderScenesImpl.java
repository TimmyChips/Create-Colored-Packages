package timmychips.colored_packages.infastructure.ponder.neoforge;

import com.ninni.dye_depot.registry.DDDyes;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import timmychips.colored_packages.AllPackageItems;
import timmychips.colored_packages.compat.DyeDepotCompat;
import timmychips.colored_packages.neoforge.infastructure.ponder.scenes.ColoredPackagesScene;
import timmychips.colored_packages.neoforge.infastructure.ponder.scenes.DyeDepotPackagesScene;

public class AllColoredPonderScenesImpl {
    public static void registerPlatform(PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> helper) {
        helper.forComponents(new ItemProviderEntry[]{AllPackageItems.packageItemEntries.getFirst()}).addStoryBoard("high_logistics/colored_packages_ground", ColoredPackagesScene::allColoredPackages);

        if (DyeDepotCompat.HAS_DYE_DEPOT) {
            helper.forComponents(new ItemProviderEntry[]{AllPackageItems.packageItemEntries.getFirst()}).addStoryBoard("high_logistics/colored_packages_ground", DyeDepotPackagesScene::allDyeDepotPackages);
        }
    }
}
