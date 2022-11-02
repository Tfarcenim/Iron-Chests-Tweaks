package tfar.ironcheststweaks.mixin;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractIronChestBlockEntity.class)
public interface AbstractIronChestBlockEntityAccess {

    @Accessor
    IronChestsTypes getChestType();
}
