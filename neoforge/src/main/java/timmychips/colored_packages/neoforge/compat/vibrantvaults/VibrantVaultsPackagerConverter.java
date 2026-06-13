package timmychips.colored_packages.neoforge.compat.vibrantvaults;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;

public class VibrantVaultsPackagerConverter extends CreatePackagerConverter {

    private static ModBlocks.VibrantVaultColor vibrantVaultColor;

    @Override
    public boolean setDyedPackager(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        if (setVibrantVaultColor(color)) this.setPackager(state, powered, level, pos, player);
        else super.setDyedPackager(state, powered, level, pos, player, color);

        return true;
    }

    public boolean setVibrantVaultColor(DyeColor color) {
        vibrantVaultColor = getVibrantVaultColor(color);
        return vibrantVaultColor != null;
    }

    // Get VibrantVaultColor enum value from DyeColor ordinal
    public static ModBlocks.VibrantVaultColor getVibrantVaultColor(DyeColor color) {
        int ordinal = color.ordinal();
        if (ordinal < 16) {
            return ModBlocks.VibrantVaultColor.values()[ordinal];
        }
        return null;
    }

    @Override
    public BlockEntry<?> getTargetEntry(BlockState state) {
        if (vibrantVaultColor != null) return ModBlocks.getVibrantPackager(vibrantVaultColor);
        return fallbackEntry(state);
    }

    // Fallback to regular Dyed Packager if dye color isn't part of VibrantVaultColor / i.e. a non-vanilla color
    private BlockEntry<?> fallbackEntry(BlockState state) {
        return AllDyedBlocks.DYED_PACKAGER;
    }
}
