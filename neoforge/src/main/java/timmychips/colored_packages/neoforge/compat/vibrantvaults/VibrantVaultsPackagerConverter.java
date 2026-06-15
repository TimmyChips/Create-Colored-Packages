package timmychips.colored_packages.neoforge.compat.vibrantvaults;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsPackagerConverter extends CreatePackagerConverter {

    private static final BlockEntry<PackagerBlock> FALLBACK_ENTRY = AllBlocks.PACKAGER;

    @Override
    public boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        // Check if this vibrant packager block is the same as the dye trying to be applied; return false if so
        ColoredPackages.LOGGER.info("Block key: {}", BuiltInRegistries.BLOCK.getKey(state.getBlock()));

        ColoredPackages.LOGGER.info("is re-packager? {}", isRepackager(state));
        // Set to vibrant packager if not a re-packager
        if (!isRepackager(state)) {
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
            if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {
                if (VIBRANT_PACKAGERS_MAP.get(blockId).equals(color)) return false;
            }

            BlockEntry<?> entry = getVibrantPackager(color);
            if (entry != null) {
                setPackagerBlock(entry, state, powered, level, pos, player); // Set to new colored Vibrant Packager
                return true;
            }
        }

        return super.setColored(state, powered, level, pos, player, color);

//        BlockEntry<?> entry = isRepackager(state) ? coloredEntry(state) : getVibrantPackager(color);
//        boolean fallback = entry == null;
//        if (fallback) {
//            return super.setColored(state, powered, level, pos, player, color);
//        }
//        else setPackagerBlock(entry, state, powered, level, pos, player);
//
////        BlockEntity packagerBE = setPackagerBlock(entry, state, powered, level, pos, player);
////        if (fallback) return setDyedPackagerColor(packagerBE, color); // Set packager color if it's a DyedPackagerBlockEntity
////        else return true;
//        setPackagerBlock(entry, state, powered, level, pos, player);
//        return true;
    }

    // No re-packager variants, use default Packager only
//    @Override
//    public BlockEntry<?> defaultEntry(BlockState state) {
//        return AllBlocks.PACKAGER;
//    }

    // Get VibrantVaultColor enum value from DyeColor ordinal
    public static ModBlocks.VibrantVaultColor getVibrantVaultColor(DyeColor color) {
        int ordinal = color.ordinal();
        if (ordinal < 16) {
            return ModBlocks.VibrantVaultColor.values()[ordinal];
        }
        return null; // Null if color value is greater than vanilla MC color values
    }

    // Get vibrant vault packager entry from color
    public BlockEntry<?> getVibrantPackager(DyeColor color) {
        ModBlocks.VibrantVaultColor vibrantVaultColor = getVibrantVaultColor(color);

        if (vibrantVaultColor == null) return null; // Return null to use fallback entry
        else return ModBlocks.getVibrantPackager(vibrantVaultColor); // Vibrant Packager block from this color
    }
}
