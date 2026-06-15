package timmychips.colored_packages.neoforge.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.computercraft.events.PackageEvent;
import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packagePort.frogport.FrogportBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerBlock;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerItemHandler;
import com.simibubi.create.content.logistics.packager.PackagingRequest;
import com.simibubi.create.content.logistics.packagerLink.PackagerLinkBlock;
import com.simibubi.create.content.logistics.packagerLink.PackagerLinkBlockEntity;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.neoforge.AllDyedBlockEntityTypesForge;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageItemForge;
import timmychips.colored_packages.neoforge.mixin.accessors.PackagerBEAdvancementAccessorForge;

import java.util.List;
import java.util.Optional;

public class DyedPackagerBlockEntityForge extends DyedPackagerBlockEntity {

    public Optional<DyeColor> color;

    public DyedPackagerBlockEntityForge(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);

        color = Optional.empty();
    }

    public static void registerDyedCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                AllDyedBlockEntityTypesForge.DYED_PACKAGER.get(),
                (be, context) -> be.inventory
        );
        PackagerBlockEntity.registerCapabilities(event);
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);

        if (color.isPresent())
            NBTHelper.writeEnum(compound, "Dye", color.get());
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);

        color = compound.contains("Dye") ? Optional.of(NBTHelper.readEnum(compound, "Dye", DyeColor.class))
                : Optional.empty();
    }

    // Apply color and return true if successful
    public boolean applyColor(DyeColor colorIn) {
        if (colorIn == null) {
            if (!color.isPresent())
                return false;
        } else if (color.isPresent() && color.get() == colorIn)
            return false;
        if (level.isClientSide())
            return true;

        this.color = Optional.ofNullable(colorIn);
        this.setChanged();
        this.sendData();

        return true;
    }

    @Override
    public void attemptToSend(List<PackagingRequest> queuedRequests) {
        if (queuedRequests == null && (!heldBox.isEmpty() || animationTicks != 0 || buttonCooldown > 0))
            return;

        IItemHandler targetInv = targetInventory.getInventory();
        if (targetInv == null || targetInv instanceof PackagerItemHandler)
            return;

        boolean anyItemPresent = false;
        ItemStackHandler extractedItems = new ItemStackHandler(PackageItem.SLOTS);
        ItemStack extractedPackageItem = ItemStack.EMPTY;
        PackagingRequest nextRequest = null;
        String fixedAddress = null;
        int fixedOrderId = 0;

        // Data written to packages for defrags
        int linkIndexInOrder = 0;
        boolean finalLinkInOrder = false;
        int packageIndexAtLink = 0;
        boolean finalPackageAtLink = false;
        PackageOrderWithCrafts orderContext = null;
        boolean requestQueue = queuedRequests != null;

        if (requestQueue && !queuedRequests.isEmpty()) {
            nextRequest = queuedRequests.get(0);
            fixedAddress = nextRequest.address();
            fixedOrderId = nextRequest.orderId();
            linkIndexInOrder = nextRequest.linkIndex();
            finalLinkInOrder = nextRequest.finalLink()
                    .booleanValue();
            packageIndexAtLink = nextRequest.packageCounter()
                    .getAndIncrement();
            orderContext = nextRequest.context();
        }

        Outer:
        for (int i = 0; i < PackageItem.SLOTS; i++) {
            boolean continuePacking = true;

            while (continuePacking) {
                continuePacking = false;

                for (int slot = 0; slot < targetInv.getSlots(); slot++) {
                    int initialCount = requestQueue ? Math.min(64, nextRequest.getCount()) : 64;
                    ItemStack extracted = targetInv.extractItem(slot, initialCount, true);
                    if (extracted.isEmpty())
                        continue;
                    if (requestQueue && !ItemStack.isSameItemSameComponents(extracted, nextRequest.item()))
                        continue;

                    boolean bulky = !extracted.getItem()
                            .canFitInsideContainerItems();
                    if (bulky && anyItemPresent)
                        continue;

                    anyItemPresent = true;
                    int leftovers = ItemHandlerHelper.insertItemStacked(extractedItems, extracted.copy(), false)
                            .getCount();
                    int transferred = extracted.getCount() - leftovers;
                    targetInv.extractItem(slot, transferred, false);

                    if (extracted.getItem() instanceof PackageItem)
                        extractedPackageItem = extracted;

                    if (!requestQueue) {
                        if (bulky)
                            break Outer;
                        continue;
                    }

                    nextRequest.subtract(transferred);

                    if (!nextRequest.isEmpty()) {
                        if (bulky)
                            break Outer;
                        continue;
                    }

                    finalPackageAtLink = true;
                    queuedRequests.remove(0);
                    if (queuedRequests.isEmpty())
                        break Outer;
                    int previousCount = nextRequest.packageCounter()
                            .intValue();
                    nextRequest = queuedRequests.get(0);
                    if (!fixedAddress.equals(nextRequest.address()))
                        break Outer;
                    if (fixedOrderId != nextRequest.orderId())
                        break Outer;

                    nextRequest.packageCounter()
                            .setValue(previousCount);
                    finalPackageAtLink = false;
                    continuePacking = true;
                    if (nextRequest.context() != null)
                        orderContext = nextRequest.context();

                    if (bulky)
                        break Outer;
                    break;
                }
            }
        }

        if (!anyItemPresent) {
            if (nextRequest != null)
                queuedRequests.remove(0);
            return;
        }

        /// Call colored box containing method instead
        ItemStack createdBox =
                extractedPackageItem.isEmpty() ? ColoredPackageItemForge.coloredContaining(extractedItems, color) : extractedPackageItem.copy();
//        if (!extractedPackageItem.isEmpty()) ColoredPackages.LOGGER.info("extracedPackageItem!");
        ///
        computerBehaviour.prepareComputerEvent(new PackageEvent(createdBox, "package_created"));
        PackageItem.clearAddress(createdBox);

        if (fixedAddress != null)
            PackageItem.addAddress(createdBox, fixedAddress);
        if (requestQueue)
            PackageItem.setOrder(createdBox, fixedOrderId, linkIndexInOrder, finalLinkInOrder, packageIndexAtLink,
                    finalPackageAtLink, orderContext);
        if (!requestQueue && !signBasedAddress.isBlank())
            PackageItem.addAddress(createdBox, signBasedAddress);

        BlockPos linkPos = getLinkPos();
        if (extractedPackageItem.isEmpty() && linkPos != null
                && level.getBlockEntity(linkPos) instanceof PackagerLinkBlockEntity plbe)
            plbe.behaviour.deductFromAccurateSummary(extractedItems);

        if (!heldBox.isEmpty() || animationTicks != 0) {
            queuedExitingPackages.add(new BigItemStack(createdBox, 1));
            return;
        }

        heldBox = createdBox;
        animationInward = false;
        animationTicks = CYCLE;

        try {
            PackagerBEAdvancementAccessorForge advancementAccessor = (PackagerBEAdvancementAccessorForge) (Object) this;
            AdvancementBehaviour advancements = advancementAccessor.coloredPackages$getPackagerAdvancements();

            advancements.awardPlayer(AllAdvancements.PACKAGER);
        } catch (ClassCastException e) {
            ColoredPackages.LOGGER.info("Unable to cast DyedPackagerBlockEntityForge: {} to {}", this, PackagerBEAdvancementAccessorForge.class);
        }

        triggerStockCheck();
        notifyUpdate();
    }

    @Override
    public void recheckIfLinksPresent() {
        if (level.isClientSide())
            return;
        BlockState blockState = getBlockState();
        if (!blockState.hasProperty(DyedPackagerBlockForge.LINKED))
            return;
        boolean shouldBeLinked = getLinkPos() != null;
        boolean isLinked = blockState.getValue(DyedPackagerBlockForge.LINKED);
        if (shouldBeLinked == isLinked)
            return;
        level.setBlockAndUpdate(worldPosition, blockState.cycle(DyedPackagerBlockForge.LINKED));
    }

    // Copied from PackagerBlockEntity since it's a private method
    private BlockPos getLinkPos() {
        for (Direction d : Iterate.directions) {
            BlockState adjacentState = level.getBlockState(worldPosition.relative(d));
            if (!AllBlocks.STOCK_LINK.has(adjacentState))
                continue;
            if (PackagerLinkBlock.getConnectedDirection(adjacentState) != d)
                continue;
            return worldPosition.relative(d);
        }
        return null;
    }
}
