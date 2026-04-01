package timmychips.colored_packages;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class AllDyedBlocks {

    public static BlockEntry<? extends WrenchableDirectionalBlock> DYED_PACKAGER;
    public static BlockEntry<? extends WrenchableDirectionalBlock> DYED_REPACKAGER;

    @ExpectPlatform
    public static void platformRegisterBlocks() {}

    public static void register() {
        platformRegisterBlocks();
    }
}
