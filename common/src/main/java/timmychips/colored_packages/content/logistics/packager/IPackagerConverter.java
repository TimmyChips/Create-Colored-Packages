package timmychips.colored_packages.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.compat.VibrantVaultsCompat;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public interface IPackagerConverter {

    // Set current packager block to Create's default packager/re-packager
    default BlockEntity setDefault(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player) {
        BlockEntry<?> entry = defaultEntry(state);
        return setPackagerBlock(entry, state, powered, level, pos, player);
    }

    // Set current packager to dyed packager block and set color
    default boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        ColoredPackages.LOGGER.info("parent set colored");
        BlockEntity blockEntity = level.getBlockEntity(pos);
//        if (blockEntity instanceof DyedPackagerBlockEntity dyedBE) {
//            ColoredPackages.LOGGER.info("color map check: {}", dyedBE.color.map(dyeColor -> dyeColor.equals(color)).orElse(false));
//            boolean sameDye = dyedBE.color.map(dyeColor -> dyeColor.equals(color)).orElse(false);
//            if (sameDye) return false;
//        }

        BlockEntry<?> entry = coloredEntry(state);
        BlockEntity packagerBE = setPackagerBlock(entry, state, powered, level, pos, player);
        ColoredPackages.LOGGER.info("Package BE: {}", packagerBE);
        return setDyedPackagerColor(packagerBE, color);
    }

    // Set to specified packager entry (default, dyed, or vibrant packager) and return corresponding BlockEntity
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

    // Return the default Create's packager or re-packager entry
    default BlockEntry<?> defaultEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER.has(state) || VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP.containsKey(state.getBlock().arch$registryName()) ? AllBlocks.PACKAGER
                : AllBlocks.REPACKAGER;
    }

    // Returns Dyed Packager or Re-Packager entry from if it's a re-packager or not
    default BlockEntry<?> coloredEntry(BlockState state) {
        if (VibrantVaultsCompat.HAS_VIBRANT_VAULTS && VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP.containsKey(BuiltInRegistries.BLOCK.getKey(state.getBlock()))) {
            return AllDyedBlocks.DYED_PACKAGER;
        }
        ColoredPackages.LOGGER.info("setting entry to {}", AllBlocks.PACKAGER.has(state) || AllDyedBlocks.DYED_PACKAGER.has(state) ? AllDyedBlocks.DYED_PACKAGER
                : AllDyedBlocks.DYED_REPACKAGER);
        return AllBlocks.PACKAGER.has(state) || AllDyedBlocks.DYED_PACKAGER.has(state) ? AllDyedBlocks.DYED_PACKAGER
                : AllDyedBlocks.DYED_REPACKAGER;
    }

    // Set's Dyed Packager / Dyed Re-Packager's color value to applied color
    default boolean setDyedPackagerColor(BlockEntity blockEntity, DyeColor color) {
        if (blockEntity instanceof DyedPackagerBlockEntity dyedPackagerBE) {
            ColoredPackages.LOGGER.info("setting color to dyed packager");
            return dyedPackagerBE.applyColor(color);
        }
        return false;
    }
}
