package timmychips.colored_packages.forge;

import com.simibubi.create.foundation.data.BuilderTransformers;
import com.tterrag.registrate.util.entry.BlockEntry;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockForge;
import timmychips.colored_packages.forge.content.logistics.packager.DyedRepackagerBlockForge;
import timmychips.colored_packages.forge.foundation.ColoredPackagesBuilderTransformersForge;

import static timmychips.colored_packages.AllDyedBlocks.DYED_REPACKAGER_TEST;

public class AllDyedBlocksForge {
    public static final BlockEntry<DyedPackagerBlockForge> DYED_PACKAGER = ColoredPackages.REGISTRATE.block("dyed_packager", DyedPackagerBlockForge::new)
            .transform(BuilderTransformers.packager())
            .register();

//    public static final BlockEntry<DyedRepackagerBlockForge> DYED_REPACKAGER = ColoredPackages.REGISTRATE.block("dyed_repackager", DyedRepackagerBlockForge::new)
//            .transform(BuilderTransformers.packager())
//            .register();

    static {
        DYED_REPACKAGER_TEST = ColoredPackages.REGISTRATE.block("dyed_repackager", DyedRepackagerBlockForge::new)
                .transform(BuilderTransformers.packager())
                .register();
    }

    public static void register() {}
}
