package timmychips.colored_packages.neoforge.content.logistics.packager.repackager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import timmychips.colored_packages.neoforge.AllDyedBlockEntityTypesForge;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockEntityForge;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockForge;

public class DyedRepackagerBlockForge extends DyedPackagerBlockForge {
    public DyedRepackagerBlockForge(Properties properties) {
        super(properties);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                           BlockHitResult hit) {
        
        boolean hasWater = GenericItemEmptying.emptyItem(level, stack, true)
                .getFirst()
                .getFluid()
                .isSame(Fluids.WATER);

        if (hasWater) {
            setToDefaultPackager(state, level, pos, AllBlocks.REPACKAGER);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, handIn, hit);
    }


    @Override
    public BlockEntityType<? extends DyedPackagerBlockEntityForge> getBlockEntityType() {
        return AllDyedBlockEntityTypesForge.DYED_REPACKAGER.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LINKED);
    }

    // Replace pick block item with Create packager
    @Override
    public ItemStack getCloneItemStack(LevelReader arg, BlockPos arg2, BlockState arg3) {
        return new ItemStack(AllBlocks.REPACKAGER);
    }
}
