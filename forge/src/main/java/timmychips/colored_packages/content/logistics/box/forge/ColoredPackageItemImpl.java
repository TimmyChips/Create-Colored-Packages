package timmychips.colored_packages.content.logistics.box.forge;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageEntityForge;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class ColoredPackageItemImpl {
    public static Entity platformCreateEntity(Level world, Entity location, ItemStack itemstack) {
        return RedPackageEntityForge.fromDroppedItem(world, location, itemstack);
    }

    public static void platformAddEntityFromUse(UseOnContext context, Vec3 point, Level world) {
        RedPackageEntityForge packageEntity = new RedPackageEntityForge(world, point.x, point.y, point.z);;
        ItemStack itemInHand = context.getItemInHand();
        packageEntity.setBox(itemInHand.copy());
        world.addFreshEntity(packageEntity);
        itemInHand.shrink(1);
    }

    public static void platformAddEntityFromThrown(Level world, Vec3 vec, ItemStack copiedStack, Vec3 motion, Player player) {
        RedPackageEntityForge packageEntity = new RedPackageEntityForge(world, vec.x, vec.y, vec.z);
        packageEntity.setBox(copiedStack);
        packageEntity.setDeltaMovement(motion);
        packageEntity.tossedBy = new WeakReference<>(player);
        world.addFreshEntity(packageEntity);
    }

    public static ItemStack platformSerializeStacks(ItemStack box, NonNullList<ItemStack> stacks) {
        ItemStackHandler handler = new ItemStackHandler(stacks);

        CompoundTag compound = new CompoundTag();
        compound.put("Items", handler.serializeNBT());
        box.setTag(compound);
        return box;
    }
}
