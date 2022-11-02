package tfar.ironcheststweaks;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.AbstractTrappedIronChestBlockEntity;
import com.progwml6.ironchest.common.item.ChestUpgradeItem;
import com.progwml6.ironchest.common.item.IronChestsUpgradeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.ironcheststweaks.mixin.AbstractIronChestBlockEntityAccess;
import tfar.ironcheststweaks.mixin.ChestUpgradeItemAccess;

public class IronChestUpgradeDispenserBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource pSource, ItemStack pStack) {
        Direction direction = pSource.getBlockState().getValue(DispenserBlock.FACING);
        Level level = pSource.getLevel();
        BlockPos blockpos = pSource.getPos().relative(direction);
        BlockState blockstate = level.getBlockState(blockpos);
        BlockEntity blockEntity = level.getBlockEntity(blockpos);

        Item item = pStack.getItem();

        if (item instanceof ChestUpgradeItem chestUpgradeItem) {
            IronChestsUpgradeType ironChestsUpgradeType = ((ChestUpgradeItemAccess) item).getType();
            if (ironChestsUpgradeType.canUpgrade(IronChestsTypes.WOOD) && blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                if (upgradeNormalChest(chestBlockEntity, ironChestsUpgradeType, blockstate, level, blockpos)) {
                    pStack.shrink(1);
                }
            } else if (blockEntity instanceof AbstractIronChestBlockEntity abstractIronChestBlockEntity) {
                if (upgradeIronChest(abstractIronChestBlockEntity, ironChestsUpgradeType, blockstate, level, blockpos)) {
                    pStack.shrink(1);
                }
            }
        }
        return pStack;
    }

    protected boolean upgradeNormalChest(ChestBlockEntity chestBlockEntity, IronChestsUpgradeType ironChestsUpgradeType, BlockState oldChest, Level level, BlockPos pos) {
        BlockState state;
        boolean trapped = chestBlockEntity instanceof TrappedChestBlockEntity;
        if (trapped) {
            state = IronChestsTypes.get(ironChestsUpgradeType.target).get(1).defaultBlockState();
        } else {
            state = IronChestsTypes.get(ironChestsUpgradeType.target).get(0).defaultBlockState();
        }

            level.setBlock(pos, state.setValue(ChestBlock.FACING, oldChest.getValue(ChestBlock.FACING)), 3);
            return true;

        }

    protected boolean upgradeIronChest(AbstractIronChestBlockEntity oldChestBlockEntity, IronChestsUpgradeType ironChestsUpgradeType, BlockState oldChest, Level level, BlockPos pos) {
        BlockState state;
        boolean trapped = oldChestBlockEntity instanceof AbstractTrappedIronChestBlockEntity;

        if (trapped) {
            state = IronChestsTypes.get(ironChestsUpgradeType.target).get(1).defaultBlockState();
        } else {
            state = IronChestsTypes.get(ironChestsUpgradeType.target).get(0).defaultBlockState();
        }

        if (ironChestsUpgradeType.canUpgrade(((AbstractIronChestBlockEntityAccess)oldChestBlockEntity).getChestType())) {
            level.setBlock(pos, state.setValue(ChestBlock.FACING, oldChest.getValue(ChestBlock.FACING)), 3);
            return true;
        }
        return false;
    }
}
