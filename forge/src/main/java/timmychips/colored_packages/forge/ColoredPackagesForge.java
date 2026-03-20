package timmychips.colored_packages.forge;

import timmychips.colored_packages.ColoredPackages;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ColoredPackages.MOD_ID)
public final class colored_packagesForge {
    public colored_packagesForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(ColoredPackages.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        ColoredPackages.init();
    }
}
