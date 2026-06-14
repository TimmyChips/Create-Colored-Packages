package timmychips.colored_packages.neoforge.mixin.logistics;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PackagerBlockEntity.class)
public class PackagerBEMixinForge {
    @ModifyExpressionValue(method = "attemptToSend", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/box/PackageItem;coloredContaining(Lnet/neoforged/neoforge/items/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack coloredPackages$vibrantVaultColoredContaining(ItemStack original, ItemStackHandler handler) {

        PackagerBlockEntity be = (PackagerBlockEntity) (Object) this;

        return original;
    }
}
