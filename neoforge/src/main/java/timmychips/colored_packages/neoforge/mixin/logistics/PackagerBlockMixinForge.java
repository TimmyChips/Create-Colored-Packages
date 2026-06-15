package timmychips.colored_packages.neoforge.mixin.logistics;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.compat.VibrantVaultsCompat;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;
import timmychips.colored_packages.content.logistics.packager.DyedPackagerConverter;
import timmychips.colored_packages.content.logistics.packager.IPackagerConverter;
import timmychips.colored_packages.neoforge.compat.vibrantvaults.VibrantVaultsPackagerConverter;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockEntityForge;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

@Mixin(PackagerBlock.class)
public class PackagerBlockMixinForge {

    @Unique
    private static CreatePackagerConverter PACKAGER_CONVERTER;

    @Shadow
    @Final
    public static BooleanProperty POWERED;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void coloredPackages$setConverter(BlockBehaviour.Properties properties, CallbackInfo ci) {
        PACKAGER_CONVERTER = (VibrantVaultsCompat.HAS_VIBRANT_VAULTS) ? new VibrantVaultsPackagerConverter() : new CreatePackagerConverter();
    }

    @Inject(method = "useItemOn", at = @At(value = "HEAD"), cancellable = true)
    public void coloredPackages$useDye(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (player != null) {

            ItemStack itemInHand = player.getItemInHand(hand);

            /// Dye/water
            boolean isDye = itemInHand.is(Tags.Items.DYES);
            boolean hasWater = GenericItemEmptying.emptyItem(level, itemInHand, true)
                    .getFirst()
                    .getFluid()
                    .isSame(Fluids.WATER);

            if (isDye) {
                // Set Packager to Dyed Packager and return interaction result
                DyeColor dyeColor = DyeColor.getColor(itemInHand);
                boolean result = PACKAGER_CONVERTER.setColored(state, POWERED, level, pos, player, dyeColor);

                cir.setReturnValue(result ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
            }
            if (hasWater) {
                // Revert vibrant colored packagers to default packager if using water
                if (PACKAGER_CONVERTER.isCreatePackager(state)) cir.setReturnValue(ItemInteractionResult.SUCCESS);
                else {
                    PACKAGER_CONVERTER.setDefault(state, POWERED, level, pos, player);
                    cir.setReturnValue(ItemInteractionResult.SUCCESS);
                }
            }
        }
    }
}
