package io.github.thecsdev.betterstats.client.gui_hud.widget;

import static io.github.thecsdev.tcdcommons.api.util.TextUtils.translatable;

import java.util.Objects;

import io.github.thecsdev.tcdcommons.api.client.gui.panel.TContextMenuPanel;
import io.github.thecsdev.tcdcommons.api.util.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.EntityType;

public class BSHudStatWidget_Entity extends BSHudStatWidget
{
	// ==================================================
	public final EntityType<?> entityType;
	// --------------------------------------------------
	protected LabelEntry lblStatEntry;
	protected boolean showKills = true, showDeaths = true;
	// ==================================================
	public BSHudStatWidget_Entity(int x, int y, StatsCounter statHandler, EntityType<?> entityType)
	{
		super(x, y, statHandler);
		this.entityType = Objects.requireNonNull(entityType, "entityType must not be null.");
	}
	// ==================================================
	public @Override void tick() { this.lblStatEntry.setText(createText()); }
	public @Override void onInit()
	{
		addEntityEntry(this.entityType);
		this.lblStatEntry = new LabelEntry(null);
	}
	// ==================================================
	public Component createText()
	{
		//get stats
		int kills = statHandler.getValue(Stats.ENTITY_KILLED.get(entityType));
		int deaths = statHandler.getValue(Stats.ENTITY_KILLED_BY.get(entityType));
		//construct text
		var txt_a = kills + " " + translatable("betterstats.hud.entity.kills").getString();
		var txt_b = deaths + " " + translatable("betterstats.hud.entity.deaths").getString();
		//construct string
		String txt = "";
		if(showKills) txt += txt_a;
		if(showDeaths) txt += (txt.length() > 0 ? ", " : "") + txt_b;
		//return text
		return TextUtils.literal(txt);
	}
	// ==================================================
	@Override
	protected void onContextMenu(TContextMenuPanel contextMenu)
	{
		//add kills/deaths
		contextMenu.addButton(translatable("betterstats.hud.entity.kills"), btn ->
		{
			this.showKills = !this.showKills;
			tick();
		});
		contextMenu.addButton(translatable("betterstats.hud.entity.deaths"), btn ->
		{
			this.showDeaths = !this.showDeaths;
			tick();
		});
		//add super
		super.onContextMenu(contextMenu);
	}
	// ==================================================
}