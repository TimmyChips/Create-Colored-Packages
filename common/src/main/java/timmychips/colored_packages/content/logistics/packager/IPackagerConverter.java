package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public interface IPackagerConverter {

    default BlockEntity setDefault(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player) {
        BlockEntry<?> entry = defaultEntry(state);
        return setPackagerBlock(entry, state, powered, level, pos, player);
    }

    default boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        BlockEntry<?> entry = coloredEntry(state);
        BlockEntity packagerBE = setPackagerBlock(entry, state, powered, level, pos, player);
        return setDyedPackagerColor(packagerBE, color);
    }

    default BlockEntity setPackagerOld(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player) {

        Direction currentFacing = state.getValue(FACING);
        boolean currentPowered = state.getValue(powered);

        // Get the dyed packager (or re-repackager) block entry if the Create packager entry has this state (i.e. it's a packager block) or not
        BlockEntry<?> dyedPackagerEntry = getTargetEntry(state);

        // Get Dyed Packager block state with current property values
        BlockState dyedPackagerState = dyedPackagerEntry.getDefaultState()
                .setValue(FACING, currentFacing)
                .setValue(powered, currentPowered);

        // Set and update Create Packager block to Dyed Packager block
        level.setBlockAndUpdate(pos, dyedPackagerState);

        // Set advancement owner to player to properly grant advancement
        AdvancementBehaviour.setPlacedBy(level, pos, player);

        return level.getBlockEntity(pos); // Return newly created dyed packager block entity
    }

    default BlockEntity setPackagerBlock(BlockEntry<?> blockEntry, BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player) {

        Direction currentFacing = state.getValue(FACING);
        boolean currentPowered = state.getValue(powered);

        // Get Dyed Packager block state with current property values
        BlockState dyedPackagerState = blockEntry.getDefaultState()
                .setValue(FACING, currentFacing)
                .setValue(powered, currentPowered);

        // Set and update Create Packager block to Dyed Packager block
        level.setBlockAndUpdate(pos, dyedPackagerState);

        // Set advancement owner to player to properly grant advancement
        AdvancementBehaviour.setPlacedBy(level, pos, player);

        return level.getBlockEntity(pos); // Return newly created dyed packager block entity
    }

    // Gets Dyed Packager or Dyed Repackager based on what the packager is
    default BlockEntry<?> getTargetEntry(BlockState state) {
        return AllBlocks.PACKAGER.has(state) ? AllDyedBlocks.DYED_PACKAGER
                : AllDyedBlocks.DYED_REPACKAGER;
    }

    // Gets Dyed Packager or Dyed Repackager based on what the packager is
    default BlockEntry<?> defaultEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER.has(state) ? AllBlocks.PACKAGER
                : AllBlocks.REPACKAGER;
    }

    // Gets Dyed Packager or Dyed Repackager based on what the packager is
    default BlockEntry<?> coloredEntry(BlockState state) {
        return AllBlocks.PACKAGER.has(state) ? AllDyedBlocks.DYED_PACKAGER
                : AllDyedBlocks.DYED_REPACKAGER;
    }

    default boolean setDyedPackagerColor(BlockEntity blockEntity, DyeColor color) {
        if (blockEntity instanceof DyedPackagerBlockEntity dyedPackagerBE) {
            return dyedPackagerBE.applyColor(color);
        }
        return false;
    }
}
