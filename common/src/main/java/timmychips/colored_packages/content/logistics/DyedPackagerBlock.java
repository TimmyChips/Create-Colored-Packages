package timmychips.colored_packages.content.logistics;

import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.util.TagUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;


// I am PISSED I cant extend the PackagerBlock class because of dumb block entity type shit
// So let me just copy and paste code instead 😤
public abstract class DyedPackagerBlock extends PackagerBlockFramework {
    public DyedPackagerBlock(Properties properties) {
        super(properties);
    }

//    @Override
//    public Class<DyedPackagerBlockEntity> getBlockEntityClass() {
//        return DyedPackagerBlockEntity.class;
//    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {

        ItemStack heldItem = player.getItemInHand(handIn);

        boolean isDye = heldItem.is(Tags.Items.DYES);
        boolean hasWater = GenericItemEmptying.emptyItem(world, heldItem, true)
                .getFirst()
                .getFluid()
                .isSame(Fluids.WATER);

        if (isDye || hasWater)
            return onBlockEntityUse(world, pos,
                    be ->
                            be.applyColor(TagUtil.getColorFromStack(heldItem)) ? InteractionResult.SUCCESS : InteractionResult.PASS);

        return super.use(state, world, pos, player, handIn, hit);
    }
}
