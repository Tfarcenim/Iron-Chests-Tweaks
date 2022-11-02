package tfar.ironcheststweaks.mixin;

import com.progwml6.ironchest.common.item.ChestUpgradeItem;
import com.progwml6.ironchest.common.item.IronChestsUpgradeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChestUpgradeItem.class)
public interface ChestUpgradeItemAccess {

    @Accessor
    IronChestsUpgradeType getType();
}
