package timmychips.colored_packages;

import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timmychips.colored_packages.client.PackageItemModelPredicate;
import timmychips.colored_packages.content.logistics.DisplayItemsGenerator;
import timmychips.colored_packages.content.logistics.box.AllPackageEntityTypes;
import timmychips.colored_packages.content.logistics.box.ColoredPackageParticle;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.content.logistics.box.util.ColorTooltipFormattingHelper;

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
        AllPackageParticles.register();
        AllPackageParticles.registerFactories();
        AllPackagerSpriteShifts.init();
        AllPackagePartialModels.init();
        ColorTooltipFormattingHelper.init();
        PackageCreativeModeTab.register();
    }
}
