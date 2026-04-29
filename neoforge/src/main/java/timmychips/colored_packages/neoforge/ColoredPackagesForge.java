package timmychips.colored_packages.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;
import timmychips.colored_packages.AllDyedArmInteractionPointTypes;
import timmychips.colored_packages.ColoredPackages;

@Mod(ColoredPackages.MOD_ID)
public final class ColoredPackagesForge {
    public ColoredPackagesForge(IEventBus bus) {
        // Run our common setup.
        ColoredPackages.REGISTRATE.registerEventListeners(bus);
        ColoredPackages.init();
        AllDyedBlockEntityTypesForge.register();
        AllPackageEntityTypesForge.register();
        bus.addListener(AllPackageEntityTypesForge::registerEntityAttributes);
        bus.addListener(ModifyCreativeMenuForge::addTaggedPackagesForge);
        // Reload listener
        bus.addListener(AllCapabilitiesEvent::register);
        bus.addListener(ColoredPackagesForge::onRegister);
        // Client bus listener
        bus.addListener(ColoredPackagesClientForge::init);
    }

    // After registering vanilla registries, initialize modded objects (so it doesn't freeze/crash)
    public static void onRegister(RegisterEvent event) {
        AllDyedArmInteractionPointTypes.init();
    }
}
