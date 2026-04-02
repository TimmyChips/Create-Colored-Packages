package timmychips.colored_packages;

import net.createmod.ponder.foundation.PonderIndex;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderPlugin;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagerScenes;

public class ColoredPackagesClient {
    public static void init() {
        ColoredPackages.LOGGER.info("Registering ponder");
        PonderIndex.addPlugin(new ColoredPonderPlugin());
    }
}
