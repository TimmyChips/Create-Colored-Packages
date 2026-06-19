package timmychips.colored_packages.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import timmychips.colored_packages.AllDyedArmInteractionPointTypes;
import timmychips.colored_packages.ColoredPackages;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import timmychips.colored_packages.compat.VibrantVaultsCompat;

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
        AllDyedBlockEntityTypesForge.register();
        AllPackageEntityTypesForge.register();
        bus.addListener(AllPackageEntityTypesForge::registerEntityAttributes);
//        ModifyCreativeMenuForge.modify(bus);
        bus.addListener(ModifyCreativeMenuForge::addTaggedPackagesForge);
        // Reload listener
        bus.addListener(ColoredPackagesForge::onRegister);
        //
        bus.addListener(ColoredPackagesForge::registerMisc);
        // Client bus listener
        bus.addListener(ColoredPackagesClientForge::init);
    }

    // After registering vanilla registries, initialize modded objects (so it doesn't freeze/crash)
    public static void onRegister(RegisterEvent event) {
        AllDyedArmInteractionPointTypes.init();
    }

    public static void registerMisc(FMLCommonSetupEvent event) {
        event.enqueueWork(VibrantVaultsCompat::setVibrantPackagerSet);
    }
}
