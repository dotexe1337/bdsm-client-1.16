package cf.dotexe.bdsm.setting;

import cf.dotexe.bdsm.module.Module;
import java.util.ArrayList;

public class Setting {
	
	private String name;
	private String displayName;
	private Module parent;
	private String mode;
	
	public String sval;
	public ArrayList<String> options;
	
	public boolean bval;
	
	public float dval;
	public float min;
	public float max;
	

	public Setting(String name, Module parent, String sval, ArrayList<String> options){
		this(name, name, parent, sval, options);
	}
	
	public Setting(String name, Module parent, boolean bval){
		this(name, name, parent, bval);
	}
	
	public Setting(String name, Module parent, float dval, float min, float max){
		this(name, name, parent, dval, min, max);
	}
	
	public Setting(String name, String displayName, Module parent, String sval, ArrayList<String> options){
		this.name = name;
		this.displayName = displayName;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
		this.mode = "Combo";
	}
	
	public Setting(String name, String displayName, Module parent, boolean bval){
		this.name = name;
		this.displayName = displayName;
		this.parent = parent;
		this.bval = bval;
		this.mode = "Check";
	}
	
	public Setting(String name, String displayName, Module parent, float dval, float min, float max){
		this.name = name;
		this.displayName = displayName;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.mode = "Slider";
	}
	
	public String getName(){
		return name;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public Module getParentMod(){
		return parent;
	}
	
	public String getValString(){
		return this.sval;
	}
	
	public void setValString(String in){
		this.sval = in;
	}
	
	public ArrayList<String> getOptions(){
		return this.options;
	}
	
	public boolean getValBoolean(){
		return this.bval;
	}
	
	public void setValBoolean(boolean in){
		this.bval = in;
	}
	
	public float getValDouble(){
		return this.dval;
	}

	public void setValDouble(float in){
		this.dval = in;
	}
	
	public float getMin(){
		return this.min;
	}
	
	public float getMax(){
		return this.max;
	}
	
	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}
	
	public boolean isCheck(){
		return this.mode.equalsIgnoreCase("Check") ? true : false;
	}
	
	public boolean isSlider(){
		return this.mode.equalsIgnoreCase("Slider") ? true : false;
	}
}
