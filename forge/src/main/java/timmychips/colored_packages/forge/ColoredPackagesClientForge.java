package timmychips.colored_packages.forge;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.ColoredPackagesClient;

public class ColoredPackagesClientForge {
    public static void init(final FMLClientSetupEvent event) {
        ColoredPackages.LOGGER.info("Forge client bus!");
        ColoredPackagesClient.init();
    }
}
