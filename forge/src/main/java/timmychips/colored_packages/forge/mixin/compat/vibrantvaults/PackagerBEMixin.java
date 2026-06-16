package timmychips.colored_packages.forge.mixin.compat.vibrantvaults;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import timmychips.colored_packages.forge.content.logistics.box.ColoredPackageItemForge;

import java.util.Optional;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

@Debug(export = true)
@Mixin(PackagerBlockEntity.class)
public class PackagerBEMixin {

    // Redirect Vibrant Vaults to call coloredContaining() method to get right colored box
    @Redirect(
            method = "attemptToSend",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/box/PackageItem;containing(Lnet/minecraftforge/items/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack coloredContaining(ItemStackHandler stacks) {

        PackagerBlockEntity self = (PackagerBlockEntity) (Object) this;

        BlockState state = self.getBlockState();
        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(state.getBlock());

        if (VIBRANT_PACKAGERS_MAP != null) {
            if (VIBRANT_PACKAGERS_MAP.containsKey(blockId)) {
                return ColoredPackageItemForge.coloredContaining(stacks, Optional.of(
                        VIBRANT_PACKAGERS_MAP.get(blockId)));
            }
        }
        return PackageItem.containing(stacks);
    }
}