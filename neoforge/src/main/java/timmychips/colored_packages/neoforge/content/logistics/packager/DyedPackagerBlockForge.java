package timmychips.colored_packages.neoforge.content.logistics.packager;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import timmychips.colored_packages.neoforge.AllDyedBlockEntityTypesForge;

public class DyedPackagerBlockForge extends WrenchableDirectionalBlock implements IBE<DyedPackagerBlockEntityForge>, IWrenchable {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LINKED = BooleanProperty.create("linked");

    public DyedPackagerBlockForge(Properties properties) {
        super(properties);
        BlockState defaultBlockState = defaultBlockState();
        if (defaultBlockState.hasProperty(LINKED))
            defaultBlockState = defaultBlockState.setValue(LINKED, false);
        registerDefaultState(defaultBlockState.setValue(POWERED, false));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferredFacing = null;
        for (Direction face : context.getNearestLookingDirections()) {
            BlockEntity be = context.getLevel()
                    .getBlockEntity(context.getClickedPos()
                            .relative(face));
            if (be instanceof PackagerBlockEntity)
                continue;
            if (be != null && be.hasLevel() &&be.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, be.getBlockPos(), null) != null) {
                preferredFacing = face.getOpposite();
                break;
            }
        }

        Player player = context.getPlayer();
        if (preferredFacing == null) {
            Direction facing = context.getNearestLookingDirection();
            preferredFacing = player != null && player
                    .isShiftKeyDown() ? facing : facing.getOpposite();
        }

        if (player != null && !(player instanceof FakePlayer)) {
            if (AllBlocks.PORTABLE_STORAGE_INTERFACE.has(context.getLevel()
                    .getBlockState(context.getClickedPos()
                            .relative(preferredFacing.getOpposite())))) {
                CreateLang.translate("packager.no_portable_storage")
                        .sendStatus(player);
                return null;
            }
        }

        return super.getStateForPlacement(context).setValue(POWERED, context.getLevel()
                        .hasNeighborSignal(context.getClickedPos()))
                .setValue(FACING, preferredFacing);
    }

    public void setToDefaultPackager(BlockState state, Level worldIn, BlockPos pos, BlockEntry<?> packagerEntry) {
        // Get block state properties from Dyed Packager block
        Direction currentFacing = state.getValue(FACING);
        boolean currentPowered = state.getValue(POWERED);

        // Get Create Packager block state with current property values
        BlockState createPackagerState = packagerEntry.getDefaultState()
                        .setValue(FACING, currentFacing)
                        .setValue(POWERED, currentPowered);

        // Set and update Dyed Packager block to Create Packager block
        worldIn.setBlockAndUpdate(pos, createPackagerState);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                           BlockHitResult hit) {

        /// Dye/water
        boolean isDye = stack.is(Tags.Items.DYES);
        boolean hasWater = GenericItemEmptying.emptyItem(level, stack, true)
                .getFirst()
                .getFluid()
                .isSame(Fluids.WATER);

        if (isDye)
            return onBlockEntityUseItemOn(level, pos,
                    be ->
                            be.applyColor(DyeColor.getColor(stack)) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        if (hasWater) {
            setToDefaultPackager(state, level, pos, AllBlocks.PACKAGER);
            return ItemInteractionResult.SUCCESS;
        }
        ///

        if (AllItems.WRENCH.isIn(stack))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (AllBlocks.FACTORY_GAUGE.isIn(stack))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (AllBlocks.STOCK_LINK.isIn(stack) && !(state.hasProperty(LINKED) && state.getValue(LINKED)))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (AllBlocks.PACKAGE_FROGPORT.isIn(stack))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (onBlockEntityUseItemOn(level, pos, be -> {
            if (be.heldBox.isEmpty()) {
                if (be.animationTicks > 0)
                    return ItemInteractionResult.SUCCESS;
                if (PackageItem.isPackage(stack)) {
                    if (level.isClientSide())
                        return ItemInteractionResult.SUCCESS;
                    if (!be.unwrapBox(stack.copy(), true))
                        return ItemInteractionResult.SUCCESS;
                    be.unwrapBox(stack.copy(), false);
                    be.triggerStockCheck();
                    stack.shrink(1);
                    AllSoundEvents.DEPOT_PLOP.playOnServer(level, pos);
                    if (stack.isEmpty())
                        player.setItemInHand(handIn, ItemStack.EMPTY);
                    return ItemInteractionResult.SUCCESS;
                }
                return ItemInteractionResult.SUCCESS;
            }
            if (be.animationTicks > 0)
                return ItemInteractionResult.SUCCESS;
            if (!level.isClientSide()) {
                player.getInventory()
                        .placeItemBackInInventory(be.heldBox.copy());
                AllSoundEvents.playItemPickup(player);
                be.heldBox = ItemStack.EMPTY;
                be.notifyUpdate();
            }
            return ItemInteractionResult.SUCCESS;
        }).consumesAction())
            return ItemInteractionResult.SUCCESS;

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(new Property[]{POWERED, LINKED}));
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        if (neighbor.relative(state.getOptionalValue(FACING)
                        .orElse(Direction.UP))
                .equals(pos))
            withBlockEntityDo(level, pos, PackagerBlockEntity::triggerStockCheck);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        InvManipulationBehaviour behaviour = BlockEntityBehaviour.get(worldIn, pos, InvManipulationBehaviour.TYPE);
        if (behaviour != null)
            behaviour.onNeighborChanged(fromPos);

        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered == worldIn.hasNeighborSignal(pos))
            return;
        worldIn.setBlock(pos, state.cycle(POWERED), Block.UPDATE_CLIENTS);
        if (!previouslyPowered)
            withBlockEntityDo(worldIn, pos, PackagerBlockEntity::activate);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
    }

    @Override
    public boolean shouldCheckWeakPower(BlockState state, SignalGetter level, BlockPos pos, Direction side) {
        return false;
    }

    @Override
    public Class<DyedPackagerBlockEntityForge> getBlockEntityClass() {
        return DyedPackagerBlockEntityForge.class;
    }

    @Override
    public BlockEntityType<? extends DyedPackagerBlockEntityForge> getBlockEntityType() {
        return AllDyedBlockEntityTypesForge.DYED_PACKAGER.get();
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return getBlockEntityOptional(pLevel, pPos).map(pbe -> {
                    boolean empty = pbe.inventory.getStackInSlot(0)
                            .isEmpty();
                    if (pbe.animationTicks != 0)
                        empty = false;
                    return empty ? 0 : 15;
                })
                .orElse(0);
    }

    // Replace pick block item with Create packager
    @Override
    public ItemStack getCloneItemStack(LevelReader arg, BlockPos arg2, BlockState arg3) {
        return new ItemStack(AllBlocks.PACKAGER);
    }
}
