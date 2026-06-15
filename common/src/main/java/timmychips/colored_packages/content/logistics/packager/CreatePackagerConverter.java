package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import net.minecraft.world.level.block.state.BlockState;

public class CreatePackagerConverter implements IPackagerConverter {
    // Return if packager block is default Packager or Re-Packager
    public boolean isCreatePackager(BlockState state) {
        return AllBlocks.PACKAGER.has(state) || AllBlocks.REPACKAGER.has(state);
    }
}
