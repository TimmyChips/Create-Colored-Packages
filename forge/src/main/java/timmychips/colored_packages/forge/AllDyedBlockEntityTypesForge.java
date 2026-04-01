package timmychips.colored_packages.forge;

import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.content.logistics.packager.PackagerVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.content.logistics.packager.DyedPackagerVisual;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockEntityForge;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerRendererForge;
import timmychips.colored_packages.forge.content.logistics.packager.DyedRepackagerBlockEntityForge;
import timmychips.colored_packages.forge.content.logistics.packager.DyedRepackagerBlockForge;

public class AllDyedBlockEntityTypesForge {
    public static final BlockEntityEntry<DyedPackagerBlockEntityForge> DYED_PACKAGER = ColoredPackages.REGISTRATE
            .blockEntity("dyed_packager", DyedPackagerBlockEntityForge::new)
            .visual(() -> DyedPackagerVisual::new, true)
            .validBlocks(AllDyedBlocksForge.DYED_PACKAGER)
            .renderer(() -> DyedPackagerRendererForge::new)
            .register();

    public static final BlockEntityEntry<DyedRepackagerBlockEntityForge> DYED_REPACKAGER = ColoredPackages.REGISTRATE
            .blockEntity("dyed_repackager", DyedRepackagerBlockEntityForge::new)
            .visual(() -> DyedPackagerVisual::new, true)
//            .validBlocks(AllDyedBlocksForge.DYED_REPACKAGER)
            .validBlocks(AllDyedBlocks.DYED_REPACKAGER_TEST)
            .renderer(() -> DyedPackagerRendererForge::new)
            .register();

    public static void register() {
    }
}
