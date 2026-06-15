package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import net.minecraft.world.level.block.state.BlockState;
import timmychips.colored_packages.AllDyedBlocks;

public class CreatePackagerConverter implements IPackagerConverter {
    // Return if packager block is default Packager or Re-Packager
    public boolean isCreatePackager(BlockState state) {
        return AllBlocks.PACKAGER.has(state) || AllBlocks.REPACKAGER.has(state);
    }

    // Return if it is a re-packager variant
    public boolean isRepackager(BlockState state) {
        return AllBlocks.REPACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
    }
}
