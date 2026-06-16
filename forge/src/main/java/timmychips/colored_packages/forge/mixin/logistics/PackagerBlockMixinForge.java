package timmychips.colored_packages.forge.mixin.logistics;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.compat.VibrantVaultsCompat;
import timmychips.colored_packages.content.logistics.packager.CreatePackagerConverter;
import timmychips.colored_packages.forge.compat.vibrantvaults.VibrantVaultsPackagerConverter;
import timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockEntityForge;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;
import static timmychips.colored_packages.forge.content.logistics.packager.DyedPackagerBlockForge.PACKAGER_CONVERTER;

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
                // Set Packager to Dyed Packager and return interaction result
                DyeColor dyeColor = DyeColor.getColor(itemInHand);
                boolean result = PACKAGER_CONVERTER.setColored(state, POWERED, worldIn, pos, player, dyeColor);

                cir.setReturnValue(result ? InteractionResult.SUCCESS : InteractionResult.PASS);
            }
            if (hasWater) {
                // Revert vibrant colored packagers to default packager if using water
                if (PACKAGER_CONVERTER.isCreatePackager(state)) cir.setReturnValue(InteractionResult.SUCCESS);
                else {
                    PACKAGER_CONVERTER.setDefault(state, POWERED, worldIn, pos, player);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }
}
