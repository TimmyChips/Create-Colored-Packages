package timmychips.colored_packages.forge;

import com.simibubi.create.AllEntityTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import timmychips.colored_packages.AllDyedBlockEntityTypes;
import timmychips.colored_packages.ColoredPackages;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import timmychips.colored_packages.content.logistics.box.AllPackageEntityTypes;

@Mod(ColoredPackages.MOD_ID)
public final class ColoredPackagesForge {

    static IEventBus bus;

    public ColoredPackagesForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
//        EventBuses.registerModEventBus(ColoredPackages.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        //noinspection removal;
        bus = FMLJavaModLoadingContext.get().getModEventBus(); // Get event bus from language loader
        EventBuses.registerModEventBus(ColoredPackages.MOD_ID, bus); // Register Event Bus for Forge and NeoForge

        // Run our common setup.
        ColoredPackages.REGISTRATE.registerEventListeners(bus);
        ColoredPackages.init();
        AllDyedBlocksForge.register();
        AllDyedBlockEntityTypesForge.register();
        AllPackageEntityTypesForge.register();
        bus.addListener(AllPackageEntityTypesForge::registerEntityAttributes);
    }
}
