package timmychips.colored_packages.neoforge;

import com.simibubi.create.foundation.data.BuilderTransformers;
import net.minecraft.world.item.CreativeModeTabs;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockForge;
import timmychips.colored_packages.neoforge.content.logistics.packager.repackager.DyedRepackagerBlockForge;

import static timmychips.colored_packages.AllDyedBlocks.DYED_PACKAGER;
import static timmychips.colored_packages.AllDyedBlocks.DYED_REPACKAGER;

public class AllDyedBlocksImpl {
    public static void platformRegisterBlocks() {
        DYED_PACKAGER = ColoredPackages.REGISTRATE.block("dyed_packager", DyedPackagerBlockForge::new)
                .transform(BuilderTransformers.packager())
                .item()
                    .removeTab(CreativeModeTabs.SEARCH) // Remove from creative menu
                    .build()
                .register();

        DYED_REPACKAGER = ColoredPackages.REGISTRATE.block("dyed_repackager", DyedRepackagerBlockForge::new)
                .transform(BuilderTransformers.packager())
                .item()
                    .removeTab(CreativeModeTabs.SEARCH) // Remove from creative menu
                    .build()
                .register();


    }
}
