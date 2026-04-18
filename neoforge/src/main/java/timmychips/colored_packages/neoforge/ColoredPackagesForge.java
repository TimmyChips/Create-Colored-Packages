package timmychips.colored_packages.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import timmychips.colored_packages.ColoredPackages;

@Mod(ColoredPackages.MOD_ID)
public final class ColoredPackagesForge {
    public ColoredPackagesForge(IEventBus bus) {
        // Submit our event bus to let Architectury API register our content on the right time.
//        EventBuses.registerModEventBus(ColoredPackages.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        //noinspection removal;
//        EventBuses.registerModEventBus(ColoredPackages.MOD_ID, bus); // Register Event Bus for Forge and NeoForge

        // Run our common setup.
        ColoredPackages.REGISTRATE.registerEventListeners(bus);
        ColoredPackages.init();
        AllDyedBlockEntityTypesForge.register();
        AllPackageEntityTypesForge.register();
        bus.addListener(AllPackageEntityTypesForge::registerEntityAttributes);
//        ModifyCreativeMenuForge.modify(bus);
        bus.addListener(ModifyCreativeMenuForge::addTaggedPackagesForge);
        // Client bus listener
        bus.addListener(ColoredPackagesClientForge::init);
        bus.addListener(AllCapabilitiesEvent::register);
    }
}
