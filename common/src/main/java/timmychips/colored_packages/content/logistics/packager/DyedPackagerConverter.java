package timmychips.colored_packages.content.logistics.packager;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;

public class DyedPackagerConverter extends CreatePackagerConverter {
    @Override
    public boolean setColored(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DyedPackagerBlockEntity dyedBE) {
            return dyedBE.color.map(dyeColor -> dyeColor.equals(color)).orElse(false);
        }

        BlockEntry<?> entry = coloredEntry(state);
        BlockEntity packagerBE = setPackagerBlock(entry, state, powered, level, pos, player);
        return setDyedPackagerColor(packagerBE, color);
    }
}
