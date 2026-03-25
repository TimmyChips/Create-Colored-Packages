package timmychips.colored_packages.forge;

import com.simibubi.create.foundation.data.BuilderTransformers;
import com.tterrag.registrate.util.entry.BlockEntry;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockForge;
import timmychips.colored_packages.forge.foundation.ColoredPackagesBuilderTransformersForge;

public class AllDyedBlocksForge {
    public static final BlockEntry<DyedPackagerBlockForge> DYED_PACKAGER = ColoredPackages.REGISTRATE.block("dyed_packager", DyedPackagerBlockForge::new)
            .transform(BuilderTransformers.packager())
            .register();

    public static void register() {}
}
