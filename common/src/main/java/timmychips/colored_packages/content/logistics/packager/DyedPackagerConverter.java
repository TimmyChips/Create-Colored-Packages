package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.state.BlockState;
import timmychips.colored_packages.AllDyedBlocks;

public class DyedPackagerConverter extends CreatePackagerConverter {
    // Convert dyed packager to Create packager/repackager
    @Override
    public BlockEntry<?> getTargetEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER.has(state) ? AllBlocks.PACKAGER
                : AllBlocks.REPACKAGER;
    }
}
