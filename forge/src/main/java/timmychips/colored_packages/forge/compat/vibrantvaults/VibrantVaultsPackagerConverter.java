package timmychips.colored_packages.forge.compat.vibrantvaults;

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
import net.minecraftforge.registries.ForgeRegistries;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.compat.VibrantVaultsCompat;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsPackagerConverter extends CreatePackagerConverter {

    @Override
    public boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        // Set to vibrant packager if not a re-packager
        if (!isRepackager(state)) {
            // Check if this vibrant packager block is the same as the dye trying to be applied; return false item interaction if so
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());
            if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {
                if (VIBRANT_PACKAGERS_MAP.get(blockId).equals(color)) return false;
            }

            // Get vibrant packager block from dyed color input
            BlockEntry<?> entry = getVibrantPackager(color);
            // Set packager to new vibrant packager for applicable dye
            if (entry != null) {
                setPackagerBlock(entry, state, powered, level, pos, player);
                return true;
            }
        }

        // Fallback set packager to original Dyed Block Packager if packager has modded dye or is a re-packager
        return super.setColored(state, powered, level, pos, player, color);
    }

    @Override
    public BlockEntry<?> defaultEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER.has(state) || VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP.containsKey(BuiltInRegistries.BLOCK.getKey(state.getBlock())) ? AllBlocks.PACKAGER
                : AllBlocks.REPACKAGER;
    }

    @Override
    public BlockEntry<?> coloredEntry(BlockState state) {
        if (VibrantVaultsCompat.HAS_VIBRANT_VAULTS &&
                VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP.containsKey(ForgeRegistries.BLOCKS.getKey(state.getBlock()))) {
            return AllDyedBlocks.DYED_PACKAGER;
        }
        return super.coloredEntry(state);
    }

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