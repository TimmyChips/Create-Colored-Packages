package timmychips.colored_packages.forge;

import com.simibubi.create.foundation.data.BuilderTransformers;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockForge;
import timmychips.colored_packages.forge.content.logistics.packager.DyedRepackagerBlockForge;

import static timmychips.colored_packages.AllDyedBlocks.DYED_PACKAGER;
import static timmychips.colored_packages.AllDyedBlocks.DYED_REPACKAGER;

public class AllDyedBlocksImpl {
    public static void platformRegisterBlocks() {
        DYED_PACKAGER = ColoredPackages.REGISTRATE.block("dyed_packager", DyedPackagerBlockForge::new)
                .transform(BuilderTransformers.packager())
                .register();

        DYED_REPACKAGER = ColoredPackages.REGISTRATE.block("dyed_repackager", DyedRepackagerBlockForge::new)
                .transform(BuilderTransformers.packager())
                .register();
    }
}
