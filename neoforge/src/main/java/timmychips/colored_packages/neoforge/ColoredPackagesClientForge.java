package timmychips.colored_packages.neoforge;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import timmychips.colored_packages.ColoredPackagesClient;

public class ColoredPackagesClientForge {
    public static void init(final FMLClientSetupEvent event) {
        ColoredPackagesClient.init();
    }
}
