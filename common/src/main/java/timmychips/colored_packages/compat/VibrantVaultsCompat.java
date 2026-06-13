package timmychips.colored_packages.compat;

import dev.architectury.platform.Platform;

public class VibrantVaultsCompat {
    public static final String VIBRANT_VAULTS_ID = "create_vibrant_vaults";
    public static boolean HAS_VIBRANT_VAULTS;

    static {
        HAS_VIBRANT_VAULTS = Platform.isModLoaded(VIBRANT_VAULTS_ID);
    }
}
