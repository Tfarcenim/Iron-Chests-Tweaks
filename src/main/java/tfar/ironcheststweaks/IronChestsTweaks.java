package tfar.ironcheststweaks;

import com.progwml6.ironchest.common.item.ChestUpgradeItem;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.item.IronChestsUpgradeType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import tfar.ironcheststweaks.mixin.ItemAccess;

import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ironcheststweaks")
public class IronChestsTweaks
{
    // Directly reference a slf4j logger

    public IronChestsTweaks()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        for(Map.Entry<IronChestsUpgradeType, RegistryObject<ChestUpgradeItem>> entry : IronChestsItems.UPGRADES.entrySet()) {
            dispenseAndFullStack(entry.getValue().get());
        }
    }

    private void dispenseAndFullStack(Item item) {
        DispenserBlock.registerBehavior(item,new IronChestUpgradeDispenserBehavior());
        fullStack(item);
    }

    private void fullStack(Item item) {
        ((ItemAccess)item).setMaxStackSize(64);
    }
}
