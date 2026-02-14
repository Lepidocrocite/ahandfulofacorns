package com.lepidocrocite.ahandfulofacorns;

import net.dries007.tfc.client.overworld.SolarCalculator;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LongSecateurs extends Item {
    public LongSecateurs(Properties properties) {
        super(properties);
    }




    public static final TagKey<Block> AbleToUseSecateurs = TagKey.create(
            // 注册表键。注册表的类型必须与标签的泛型类型匹配。
            Registries.BLOCK,
            // 标签放在 `data/ahandfulofacorns/tags/block/able_to_use_secateurs.json`。
            ResourceLocation.fromNamespaceAndPath("ahandfulofacorns", "able_to_use_secateurs"));

    public boolean isValidBlock(BlockState blockstate) {
        return blockstate.is(AbleToUseSecateurs);
    }

    public boolean isValidSeason(Level level, BlockPos pos){
        Month month = Calendars.SERVER.getHemispheralCalendarMonthOfYear(SolarCalculator.getInNorthernHemisphere(pos, level));
        return (
                month == Month.SEPTEMBER ||
                month == Month.OCTOBER ||
                month == Month.NOVEMBER
                );

    };


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState Clickedblockstate = level.getBlockState(pos);
        Block block = level.getBlockState(pos).getBlock();
        Player player = context.getPlayer();
        if (isValidBlock(Clickedblockstate)) {
            if (!level.isClientSide)
                if (isValidSeason(level, pos)) {//判断季节
                    //冷却
                    player.resetCurrentImpulseContext();
                    player.getCooldowns().addCooldown(this, 20);
                    if (level.random.nextFloat() < 0.3f) {
                        player.resetCurrentImpulseContext();
                        player.getCooldowns().addCooldown(this, 70);
                        //判断叶子种类
                        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
                        String blockName = blockId.toString();
                        ItemStack dropItem = null;

                        if (blockName.contains("chestnut")) {
                            dropItem = new ItemStack(ModItem.CHESTNUT.get());
                        } else if (blockName.contains("hickory")) {
                            dropItem = new ItemStack(ModItem.HICKORY_NUT.get());
                        } else if (blockName.contains("oak")) {
                            dropItem = new ItemStack(ModItem.ACORN.get());
                        } else if (blockName.contains("pine")) {
                            Item tfcPinecone = BuiltInRegistries.ITEM.get(
                                    ResourceLocation.fromNamespaceAndPath("tfc", "groundcover/pinecone") //我服了这tfc的注册，我还得换种方法拿物品
                            );
                            if (tfcPinecone != null) {
                                dropItem = new ItemStack(tfcPinecone);
                            }
                        }
                        ItemEntity dropEntity = new ItemEntity(level, pos.getX() +0.5f , pos.getY() - 0.7f, pos.getZ() +0.5f, dropItem);
                        level.addFreshEntity(dropEntity);
                        ;
                        // 播放音效
                        level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                        return InteractionResult.SUCCESS;
                    } else {
                        // 播放失败音效
                        level.playSound(null, pos, SoundEvents.ROOTED_DIRT_BREAK, SoundSource.BLOCKS, 1.5F, 0.8F);
                        //失败提示
                        player.displayClientMessage(Component.translatable("item.ahandfulofacorns.long_secateurs.fail.slip"), true);
                        return InteractionResult.FAIL;

                    }
                } else {
                    //在聊天栏里面说现在还不是成熟时间。
                    player.displayClientMessage(Component.translatable("item.ahandfulofacorns.long_secateurs.fail.season"), true);
                    return InteractionResult.FAIL;
                }
        }
        else {
            player.displayClientMessage(Component.translatable("item.ahandfulofacorns.long_secateurs.fail.notvalid"), true);

        }
        return InteractionResult.FAIL;
    }
}

