package timmychips.colored_packages.forge;

import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.content.logistics.packager.PackagerVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockEntityForge;

public class AllDyedBlockEntityTypesForge {
    public static final BlockEntityEntry<DyedPackagerBlockEntityForge> DYED_PACKAGER = ColoredPackages.REGISTRATE
            .blockEntity("dyed_packager", DyedPackagerBlockEntityForge::new)
            .visual(() -> PackagerVisual::new, true)
            .validBlocks(AllDyedBlocksForge.DYED_PACKAGER)
            .renderer(() -> PackagerRenderer::new)
            .register();

    public static void register() {
    }
}
