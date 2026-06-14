package timmychips.colored_packages.neoforge.mixin.logistics;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import net.zlt.create_vibrant_vaults.block.VibrantPackagerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import timmychips.colored_packages.compat.VibrantVaultsCompat;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageItemForge;
import timmychips.colored_packages.neoforge.content.logistics.packager.DyedPackagerBlockEntityForge;

import java.util.Optional;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

@Mixin(PackagerBlockEntity.class)
public class PackagerBEMixinForge {
    @Redirect(
            method = "attemptToSend",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/box/PackageItem;containing(Lnet/neoforged/neoforge/items/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack coloredContaining(ItemStackHandler stacks) {

        PackagerBlockEntity self = (PackagerBlockEntity) (Object) this;

        BlockState state = self.getBlockState();
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());

        if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {
            return ColoredPackageItemForge.coloredContaining(stacks, Optional.of(
                    VIBRANT_PACKAGERS_MAP.get(blockId)));
        }
        else return PackageItem.containing(stacks);
    }
}
