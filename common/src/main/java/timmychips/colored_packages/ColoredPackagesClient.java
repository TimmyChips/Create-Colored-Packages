package timmychips.colored_packages;

import net.createmod.ponder.foundation.PonderIndex;
import timmychips.colored_packages.client.PackageItemModelPredicate;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderPlugin;

public class ColoredPackagesClient {
    public static void init() {
        PonderIndex.addPlugin(new ColoredPonderPlugin());
        PackageItemModelPredicate.register();
        AllPackageParticles.registerFactories();
    }
}
