package timmychips.colored_packages.forge.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockEntityForge;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

@Debug(export = true)
@Mixin(PackagerBlock.class)
public class PackagerBlockMixinForge {
    @Shadow
    @Final
    public static BooleanProperty POWERED;

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void coloredPackager$useDye(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (player != null) {

            ItemStack itemInHand = player.getItemInHand(handIn);

            PackagerBlock self = (PackagerBlock) (Object) this;

            /// Dye/water
            boolean isDye = itemInHand.is(Tags.Items.DYES);
            boolean hasWater = GenericItemEmptying.emptyItem(worldIn, itemInHand, true)
                    .getFirst()
                    .getFluid()
                    .isSame(Fluids.WATER);

            if (isDye) {
                // Set Packager to Dyed Packager and return newly created block entity
                BlockEntity blockEntity = coloredPackages$setToDyedPackager(state, worldIn, pos, player);

                // Apply color to dyed packager block entity
                if (blockEntity instanceof DyedPackagerBlockEntityForge dyedPackagerBE) {
                    cir.setReturnValue(dyedPackagerBE.applyColor(DyeColor.getColor(itemInHand)) ? InteractionResult.SUCCESS : InteractionResult.PASS);
                }
            }
            if (hasWater) cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Unique
    private BlockEntity coloredPackages$setToDyedPackager(BlockState state, Level worldIn, BlockPos pos, LivingEntity dyer) {
        // Get block state properties from Packager block
        PackagerBlock self = (PackagerBlock) (Object) this;

        Direction currentFacing = state.getValue(FACING);
        boolean currentPowered = state.getValue(POWERED);

        // Get the dyed packager (or re-repackager) block entry if the Create packager entry has this state (i.e. it's a packager block) or not
        BlockEntry<?> dyedPackagerEntry = AllBlocks.PACKAGER.has(state) ? AllDyedBlocks.DYED_PACKAGER
                : AllDyedBlocks.DYED_REPACKAGER;

        // Get Dyed Packager block state with current property values
        BlockState dyedPackagerState = dyedPackagerEntry.getDefaultState()
                .setValue(FACING, currentFacing)
                .setValue(POWERED, currentPowered);

        // Set and update Create Packager block to Dyed Packager block
        worldIn.setBlockAndUpdate(pos, dyedPackagerState);

        // Set advancement owner to player to properly grant advancement
        AdvancementBehaviour.setPlacedBy(worldIn, pos, dyer);

        return worldIn.getBlockEntity(pos); // Return newly created dyed packager block entity
    }
}
