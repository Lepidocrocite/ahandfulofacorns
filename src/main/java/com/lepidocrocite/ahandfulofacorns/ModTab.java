package com.lepidocrocite.ahandfulofacorns;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.lepidocrocite.ahandfulofacorns.Ahandfulofacorns.MODID;
import static com.lepidocrocite.ahandfulofacorns.ModItem.*;

public class ModTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> AHOATAB = TABS.register
            ("ahandfulofacorns_tab",
                    ()-> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.ahandfulofacorns"))
                            .icon(()-> ACORN.get().getDefaultInstance())
                            .displayItems((parameters, output) ->
                            {
                                output.accept(ACORN.get());
                                output.accept(CHESTNUT.get());
                                output.accept(ROAST_CHESTNUT.get());
                                output.accept(ROAST_ACORN.get());
                                output.accept(PINE_NUT.get());
                                output.accept(HICKORY_NUT.get());
                                output.accept(ROAST_HICKORY_NUT.get());
                                output.accept(LONG_SECATEURS.get());
                            })
                            .build()

            );
    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
