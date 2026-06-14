package timmychips.colored_packages.neoforge.compat.vibrantvaults;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsPackagerConverter extends CreatePackagerConverter {

//    private static ModBlocks.VibrantVaultColor vibrantVaultColor;

//    @Override
//    public boolean setDyedPackager(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
//        if (setVibrantVaultColor(color)) this.setPackagerOld(state, powered, level, pos, player);
//        else super.setDyedPackager(state, powered, level, pos, player, color);
//
//        return true;
//    }
//
//    public boolean setVibrantVaultColor(DyeColor color) {
//        vibrantVaultColor = getVibrantVaultColor(color);
//        return vibrantVaultColor != null;
//    }

    // Get VibrantVaultColor enum value from DyeColor ordinal
    public static ModBlocks.VibrantVaultColor getVibrantVaultColor(DyeColor color) {
        int ordinal = color.ordinal();
        if (ordinal < 16) {
            return ModBlocks.VibrantVaultColor.values()[ordinal];
        }
        return null;
    }

//    @Override
//    public BlockEntry<?> getTargetEntry(BlockState state) {
//        if (vibrantVaultColor != null) return ModBlocks.getVibrantPackager(vibrantVaultColor);
//        return fallbackEntry(state);
//    }

    // Fallback to regular Dyed Packager if dye color isn't part of VibrantVaultColor / i.e. a non-vanilla color
    private BlockEntry<?> fallbackEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER;
    }

    @Override
    public boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        // Check if this vibrant packager block is the same as the dye trying to be applied; return false if so
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {
            if (VIBRANT_PACKAGERS_MAP.get(blockId).equals(color)) return false;
        }
//        if (vibrantVaultColor == state.co)

//        if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {

        BlockEntry<?> entry = getColoredPackager(state, color);
        boolean fallback = entry == null;
        entry = fallback ? fallbackEntry(state) : entry;

        BlockEntity packagerBE = setPackagerBlock(entry, state, powered, level, pos, player);
        if (fallback) return setDyedPackagerColor(packagerBE, color);
        else return true;
    }

    @Override
    public BlockEntry<?> defaultEntry(BlockState state) {
        return AllBlocks.PACKAGER;
    }

    public BlockEntry<?> getColoredPackager(BlockState state, DyeColor color) {
        ModBlocks.VibrantVaultColor vibrantVaultColor = getVibrantVaultColor(color);

        if (vibrantVaultColor == null) return null;
        else return ModBlocks.getVibrantPackager(vibrantVaultColor);
    }
}
