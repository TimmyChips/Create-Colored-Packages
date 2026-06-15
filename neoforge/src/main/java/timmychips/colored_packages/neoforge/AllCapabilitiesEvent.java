package timmychips.colored_packages.neoforge;

import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockEntityForge;
import timmychips.colored_packages.neoforge.content.logistics.packager.repackager.DyedRepackagerBlockEntityForge;

public class AllCapabilitiesEvent {
    public static void register(RegisterCapabilitiesEvent event) {
        DyedPackagerBlockEntityForge.registerDyedCapabilities(event);
        DyedRepackagerBlockEntityForge.registerDyedCapabilities(event);
    }
}
