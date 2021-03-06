package enhancedportals.inventory;

import enhancedportals.EnhancedPortals;
import enhancedportals.item.ItemAddressBook;
import enhancedportals.network.GuiHandler;
import enhancedportals.network.packet.PacketGui;
import enhancedportals.network.packet.PacketTextureData;
import enhancedportals.portal.GlyphElement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class ContainerAddressBook extends BaseContainer
{
    ItemAddressBook addressBook;
    ArrayList<String> list = new ArrayList<String>();

    public ContainerAddressBook(ItemAddressBook b, InventoryPlayer p)
    {
        super(null, p);
        addressBook = b;
        hideInventorySlots();
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (list.size() != addressBook.glyphList.size())
        {
            updateAll();
        }
        else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (!list.get(i).equals(addressBook.glyphList.get(i).name))
                {
                    updateAll();
                    break;
                }
            }
        }

        list.clear();

        for (GlyphElement e : addressBook.glyphList)
        {
            list.add(e.name);
        }
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("edit"))
        {
            int id = tag.getInteger("edit");

            if (addressBook.glyphList.size() > id)
            {
                GlyphElement e = addressBook.glyphList.get(id);
                player.openGui(EnhancedPortals.instance, GuiHandler.ADDRESS_BOOK_D, Minecraft.getMinecraft().theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
                EnhancedPortals.packetPipeline.sendTo(new PacketTextureData(e.name, e.identifier.getGlyphString(), e.texture), (EntityPlayerMP) player);
            }
        }
        else if (tag.hasKey("delete"))
        {
            int id = tag.getInteger("delete");

            if (addressBook.glyphList.size() > id)
            {
                addressBook.glyphList.remove(id);
            }

            EnhancedPortals.packetPipeline.sendTo(new PacketGui(addressBook), (EntityPlayerMP) player);
        }
    }

    void updateAll()
    {
        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting) crafters.get(i);
            EnhancedPortals.packetPipeline.sendTo(new PacketGui(addressBook), (EntityPlayerMP) icrafting);
        }
    }

}
