package timmychips.colored_packages.forge.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlock;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.forge.AllDyedBlockEntityTypesForge;

public class DyedRepackagerBlockForge extends DyedPackagerBlockForge {
    public DyedRepackagerBlockForge(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemInHand = player.getItemInHand(handIn);

        boolean hasWater = GenericItemEmptying.emptyItem(worldIn, itemInHand, true)
                .getFirst()
                .getFluid()
                .isSame(Fluids.WATER);

        if (hasWater) {
            setToDefaultPackager(state, worldIn, pos, AllBlocks.REPACKAGER);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, worldIn, pos, player, handIn, hit);
    }


    @Override
    public BlockEntityType<? extends DyedPackagerBlockEntityForge> getBlockEntityType() {
        return AllDyedBlockEntityTypesForge.DYED_REPACKAGER.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LINKED);
    }
}
