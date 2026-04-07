package timmychips.colored_packages.forge.content.logistics.packager.repackager;

import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.InventorySummary;
import com.simibubi.create.content.logistics.packager.repackager.PackageRepackageHelper;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DyedPackageRepackageHelper extends PackageRepackageHelper {

    // Copied from Create's PackageRepackageHelper.class with tweaks for colored packages
    public List<BigItemStack> coloredRepack(int orderId, RandomSource r, Optional<DyeColor> color) {
        List<BigItemStack> exportingPackages = new ArrayList<>();
        String address = "";
        PackageOrderWithCrafts orderContext = null;
        InventorySummary summary = new InventorySummary();

        for (ItemStack box : collectedPackages.get(orderId)) {
            address = PackageItem.getAddress(box);
            if (box.hasTag()) {
                CompoundTag tag = box.getTag().getCompound("Fragment");
                if (tag.contains("OrderContext"))
                    orderContext = PackageItem.getOrderContext(box);
            }

            ItemStackHandler contents = PackageItem.getContents(box);
            for (int slot = 0; slot < contents.getSlots(); slot++)
                summary.add(contents.getStackInSlot(slot));
        }

        List<BigItemStack> orderedStacks = new ArrayList<>();
        if (orderContext != null) {
            List<BigItemStack> packagesSplitByRecipe = coloredRepackBasedOnRecipes(summary, orderContext, address, r, color);
            exportingPackages.addAll(packagesSplitByRecipe);

            if (packagesSplitByRecipe.isEmpty())
                for (BigItemStack stack : orderContext.stacks())
                    orderedStacks.add(new BigItemStack(stack.stack, stack.count));
        }

        List<BigItemStack> allItems = summary.getStacks();
        List<ItemStack> outputSlots = new ArrayList<>();

        Repack:
        while (true) {
            allItems.removeIf(e -> e.count == 0);
            if (allItems.isEmpty())
                break;

            BigItemStack targetedEntry = null;
            if (!orderedStacks.isEmpty())
                targetedEntry = orderedStacks.remove(0);

            ItemSearch:
            for (BigItemStack entry : allItems) {
                int targetAmount = entry.count;
                if (targetAmount == 0)
                    continue;
                if (targetedEntry != null) {
                    targetAmount = targetedEntry.count;
                    if (!ItemHandlerHelper.canItemStacksStack(entry.stack, targetedEntry.stack))
                        continue;
                }

                while (targetAmount > 0) {
                    int removedAmount = Math.min(Math.min(targetAmount, entry.stack.getMaxStackSize()), entry.count);
                    if (removedAmount == 0)
                        continue ItemSearch;

                    ItemStack output = ItemHandlerHelper.copyStackWithSize(entry.stack, removedAmount);
                    targetAmount -= removedAmount;
                    if (targetedEntry != null)
                        targetedEntry.count = targetAmount;
                    entry.count -= removedAmount;
                    outputSlots.add(output);
                }

                continue Repack;
            }
        }

        int currentSlot = 0;
        ItemStackHandler target = new ItemStackHandler(PackageItem.SLOTS);

        for (ItemStack item : outputSlots) {
            target.setStackInSlot(currentSlot++, item);
            if (currentSlot < PackageItem.SLOTS)
                continue;
//            exportingPackages.add(new BigItemStack(PackageItem.containing(target), 1));
            /// Colored repackage containing
            ColoredPackages.LOGGER.info("TEST 1!");
            exportingPackages.add(new BigItemStack(ColoredPackageItem.coloredContaining(target, color), 1));
            ///
            target = new ItemStackHandler(PackageItem.SLOTS);
            currentSlot = 0;
        }

        for (int slot = 0; slot < target.getSlots(); slot++)
            if (!target.getStackInSlot(slot)
                    .isEmpty()) {
//                exportingPackages.add(new BigItemStack(PackageItem.containing(target), 1));
                /// Colored repackage containing
                ColoredPackages.LOGGER.info("TEST 5!");
                exportingPackages.add(new BigItemStack(ColoredPackageItem.coloredContaining(target, color), 1));
                ///
                break;
            }

        for (BigItemStack box : exportingPackages)
            PackageItem.addAddress(box.stack, address);

        for (int i = 0; i < exportingPackages.size(); i++) {
            BigItemStack box = exportingPackages.get(i);
            boolean isfinal = i == exportingPackages.size() - 1;
            PackageOrderWithCrafts outboundOrderContext = isfinal && orderContext != null ? orderContext : null;
            if (PackageItem.getOrderId(box.stack) == -1)
                PackageItem.setOrder(box.stack, orderId, 0, true, 0, true, outboundOrderContext);
        }

        return exportingPackages;
    }

    protected List<BigItemStack> coloredRepackBasedOnRecipes(InventorySummary summary, PackageOrderWithCrafts order, String address, RandomSource r, Optional<DyeColor> color) {
        if (order.orderedCrafts().isEmpty())
            return List.of();

        List<BigItemStack> packages = new ArrayList<>();
        for (PackageOrderWithCrafts.CraftingEntry craftingEntry : order.orderedCrafts()) {
            int packagesToCreate = 0;
            Crafts: for (int i = 0; i < craftingEntry.count(); i++) {
                for (BigItemStack required : craftingEntry.pattern().stacks()) {
                    if (required.stack.isEmpty())
                        continue;
                    if (summary.getCountOf(required.stack) <= 0)
                        break Crafts;
                    summary.add(required.stack, -1);
                }
                packagesToCreate++;
            }

            ItemStackHandler target = new ItemStackHandler(PackageItem.SLOTS);
            List<BigItemStack> stacks = craftingEntry.pattern().stacks();
            for (int currentSlot = 0; currentSlot < Math.min(stacks.size(), target.getSlots()); currentSlot++)
                target.setStackInSlot(currentSlot, stacks.get(currentSlot).stack.copyWithCount(1));

            ItemStack box = ColoredPackageItem.coloredContaining(target, color);
            PackageItem.setOrder(box, r.nextInt(), 0, true, 0, true,
                    PackageOrderWithCrafts.singleRecipe(craftingEntry.pattern()
                            .stacks()));
            packages.add(new BigItemStack(box, packagesToCreate));
        }

        return packages;
    }
}
