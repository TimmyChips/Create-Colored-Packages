package timmychips.colored_packages.forge;

import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import timmychips.colored_packages.AllPackageParticles;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.ColoredPackagesClient;
import timmychips.colored_packages.forge.foundation.ColoredPonderPluginForge;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderPlugin;

public class ColoredPackagesClientForge {
    public static void init(final FMLClientSetupEvent event) {
        ColoredPackages.LOGGER.info("Forge client bus!");
        ColoredPackagesClient.init();
//        PonderIndex.addPlugin(new ColoredPonderPluginForge());
    }
}
