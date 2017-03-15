package com.the7thcircle.fineredge.fundamentals.client.gui;

import com.the7thcircle.fineredge.fundamentals.items.FEFItems;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class FEFAchievements {	
	public static final Achievement ENDERBENDER = new Achievement("achievement.ender_bender", "ender_bender", 0, 0, FEFItems.ACTIVATED_ENDER_EYE, AchievementList.THE_END);

	public static final AchievementPage FEFACHIEVEMENTS = new AchievementPage("Finer Edge Fundamentals", ENDERBENDER);

	@SubscribeEvent
	public static void registerAchievements(RegistryEvent.Register<Item> event){
		ENDERBENDER.registerStat();
		AchievementPage.registerAchievementPage(FEFACHIEVEMENTS);
	}
	
	@SubscribeEvent
	public static void onCraftedItem(PlayerEvent.ItemCraftedEvent event){
		if(event.crafting.getItem().equals(FEFItems.ACTIVATED_ENDER_EYE)) event.player.addStat(ENDERBENDER);
	}
}
