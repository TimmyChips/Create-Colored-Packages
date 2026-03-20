package timmychips.colored_packages;

import com.simibubi.create.foundation.data.CreateRegistrate;

public final class ColoredPackages {
    public static final String MOD_ID = "colored_packages";

    // Create Registrate for this mod
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public static void init() {
        // Write common init code here.
        AllPackageItems.register();
    }
}
