package timmychips.colored_packages;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timmychips.colored_packages.client.PackageItemModelPredicate;
import timmychips.colored_packages.content.logistics.box.AllPackageEntityTypes;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

public final class ColoredPackages {
    public static final String MOD_ID = "colored_packages";
    public static final Logger LOGGER = LoggerFactory.getLogger("Create Colored Packages");

    // Create Registrate for this mod
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        // Write common init code here.
        ColoredPackageStyles.initColoredPackageStyles();
        PackageItemModelPredicate.register();
        AllPackageItems.register();
        AllDyedBlocks.register();
        AllPackagerSpriteShifts.init();
        AllPackagePartialModels.init();
    }
}
