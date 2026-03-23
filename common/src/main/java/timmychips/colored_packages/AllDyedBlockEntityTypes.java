package timmychips.colored_packages;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.content.logistics.packager.PackagerVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;

public class AllDyedBlockEntityTypes {

    // copy from Create with tweaks
    public static final BlockEntityEntry<DyedPackagerBlockEntity> DYED_PACKAGER = ColoredPackages.REGISTRATE
            .blockEntity("dyed_packager", DyedPackagerBlockEntity::new)
            .visual(() -> PackagerVisual::new, true)
            .validBlocks(AllDyedBlocks.DYED_PACKAGER)
            .renderer(() -> PackagerRenderer::new)
            .register();

    public static void register() {
    }
}
