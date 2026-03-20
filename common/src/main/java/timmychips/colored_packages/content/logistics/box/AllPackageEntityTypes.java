package timmychips.colored_packages.content.logistics.box;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class AllPackageEntityTypes {

    // Registers entity per platform
    @ExpectPlatform
    public static void registerPlatform() {}

    static {
        registerPlatform();
    }

    public static void register() {}
}
