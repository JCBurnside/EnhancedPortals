package enhancedportals.registration;

import enhancedportals.block.BlockFrame;
import enhancedportals.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterItems

{
    public static Item itemWrench;
    public static Item itemNanobrush;
    public static Item itemGlasses;
    public static Item itemLocationCard;
    public static Item itemPortalModule;
    public static Item itemUpgrade;
    public static Item itemBlankPortalModule;
    public static Item itemBlankUpgrade;
    public static Item itemAddressBook;
    public static Item itemDiamondNugget;
    public static Item itemFrame;
    public static Item itemStabilizer;

    public static void preinit()
    {
        itemWrench = GameRegistry.register(new ItemWrench("wrench"));
        itemNanobrush = GameRegistry.register(new ItemNanobrush("nanobrush"));
        itemGlasses = GameRegistry.register(new ItemGlasses("glasses"));
        itemLocationCard = GameRegistry.register(new ItemLocationCard("location_card"));
        itemPortalModule = GameRegistry.register(new ItemPortalModule("portal_module"));
        itemUpgrade = GameRegistry.register(new ItemUpgrade("upgrade"));
        itemBlankPortalModule = GameRegistry.register(new ItemBlankPortalModule("blank_portal_module"));
        itemBlankUpgrade = GameRegistry.register(new ItemBlankUpgrade("blank_upgrade"));
        itemAddressBook = GameRegistry.register(new ItemAddressBook("address_book"));
        itemFrame = GameRegistry.register(new ItemPortalFrame("frame", new BlockFrame("frame")));
        itemStabilizer = GameRegistry.register(new ItemStabilizer("bridge_stabilizer"));

        //        itemDiamondNugget  = GameRegistry.register(new ItemDiamondNugget("diamondNugget));
    }


}
