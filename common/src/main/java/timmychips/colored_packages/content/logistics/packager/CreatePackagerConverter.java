package timmychips.colored_packages.content.logistics.packager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;

public class CreatePackagerConverter implements IPackagerConverter {
    public boolean setDyedPackager(BlockState state, BooleanProperty powered, Level level, BlockPos pos, LivingEntity player, DyeColor color) {
        BlockEntity packagerBE = this.setPackager(state, powered, level, pos, player);
        return setColor(packagerBE, color);
    }

    public boolean setColor(BlockEntity blockEntity, DyeColor color) {
        if (blockEntity instanceof DyedPackagerBlockEntity dyedPackagerBE) {
            return dyedPackagerBE.applyColor(color);
        }
        return false;
    }
}
