package timmychips.colored_packages;

import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.tterrag.registrate.util.entry.BlockEntry;
import timmychips.colored_packages.content.logistics.DyedPackagerBlock;

public class AllDyedBlocks {

    public static final BlockEntry<DyedPackagerBlock> DYED_PACKAGER = ColoredPackages.REGISTRATE.block("dyed_packager", DyedPackagerBlock::new)
            .transform(BuilderTransformers.packager())
            .register();

    public static void register() {
    }
}
