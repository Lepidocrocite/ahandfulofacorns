package com.lepidocrocite.ahandfulofacorns;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lepidocrocite.ahandfulofacorns.Ahandfulofacorns.MODID;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    //food
    public static final DeferredItem<Item> CHESTNUT = ITEMS.registerSimpleItem("chestnut", new Item.Properties());
    public static final DeferredItem<Item> ROAST_CHESTNUT = ITEMS.registerSimpleItem("roast_chestnut", new Item.Properties());
    public static final DeferredItem<Item> ACORN = ITEMS.registerSimpleItem("acorn", new Item.Properties());
    public static final DeferredItem<Item> ROAST_ACORN = ITEMS.registerSimpleItem("roast_acorn", new Item.Properties());
    public static final DeferredItem<Item> PINE_NUT = ITEMS.registerSimpleItem("pine_nut", new Item.Properties());
    public static final DeferredItem<Item> HICKORY_NUT = ITEMS.registerSimpleItem("hickory_nut", new Item.Properties());
    public static final DeferredItem<Item> ROAST_HICKORY_NUT = ITEMS.registerSimpleItem("roast_hickory_nut", new Item.Properties());

    //item
    public static final DeferredItem<Item> LONG_SECATEURS = ITEMS.register("long_secateurs" , () -> new LongSecateurs(new Item.Properties().stacksTo(1)));
}
