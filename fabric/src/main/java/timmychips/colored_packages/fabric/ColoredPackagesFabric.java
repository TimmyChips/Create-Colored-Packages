package timmychips.colored_packages.fabric;

import timmychips.colored_packages.ColoredPackages;
import net.fabricmc.api.ModInitializer;

public final class colored_packagesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        ColoredPackages.init();
    }
}
