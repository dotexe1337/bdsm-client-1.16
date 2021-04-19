package cf.dotexe.bdsm.module;

import com.darkmagician6.eventapi.EventManager;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class Module implements ClientSupport {
	
	public enum Category {
		Combat, Player, Movement, World, Render, Exploits, Misc;
	}
	
	private int bind, color = 0xffffffff, categoryColor = 0xffffffff;
	private String name = "", displayName = "", suffix = "";
	public boolean toggled;
	private boolean hidden = false;
	private Category category;
	
	public Module(int bind, String name, Category category) {
		this(bind, name, name, category);
	}
	
	public Module(int bind, String name, String displayName, Category category) {
		this.bind = bind;
		this.name = name;
		this.displayName = displayName;
		this.color = ColorUtils.generateColor();
		this.category = category;
		switch(this.category) {
		case Combat:
			this.categoryColor = 0xffe53935;
			break;
		case Player:
			this.categoryColor = 0xff64b5f6;
			break;
		case Movement:
			this.categoryColor = 0xffffb74d;
			break;
		case World:
			this.categoryColor = 0xff81c784;
			break;
		case Render:
			this.categoryColor = 0xffb39ddb;
			break;
		case Misc:
			this.categoryColor = 0xffb0bec5;
			break;
		}
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getCategoryColor() {
		return categoryColor;
	}

	public void setCategoryColor(int categoryColor) {
		this.categoryColor = categoryColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean getState() {
		return this.toggled;
	}
	
	public void setState(boolean state) {
		this.toggled = state;
		onToggle();
		if(state) {
			onEnable();
		}else {
			onDisable();
		}
	}
	
	public void toggle() {
		setState(!getState());
	}
	
	public void onEnable() { EventManager.register(this); }
	public void onDisable() { EventManager.unregister(this); }
	public void onToggle() { }
	
}