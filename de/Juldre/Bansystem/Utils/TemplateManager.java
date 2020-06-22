package de.Juldre.Bansystem.Utils;

import java.util.ArrayList;
import java.util.List;

import de.Juldre.Bansystem.Utils.Objects.BanTemplateObject;
import de.Juldre.Bansystem.Utils.Objects.TimeObject;
import net.md_5.bungee.config.Configuration;

public class TemplateManager {
	List<BanTemplateObject> templateList;

	public TemplateManager() {
		templateList = new ArrayList<BanTemplateObject>();
	}

	public void loadTemplates(Configuration config) {
		if (config.getSection("Templates") == null) {
			return;
		}
		config.getSection("Templates").getKeys().forEach(key -> {
			BanTemplateObject tmp = new BanTemplateObject();
			TimeObject time = new TimeObject();
			tmp.setReason(getStr(key, "Reason",config));
			tmp.setId(Integer.parseInt(key));
			time.setMilliseconds(getInt(key, "Milliseconds",config));
			time.setSeconds(getInt(key, "Seconds",config));
			time.setMinutes(getInt(key, "Minutes",config));
			time.setHours(getInt(key, "Hours",config));
			time.setDays(getInt(key, "Days",config));
			time.setMonths(getInt(key, "Months",config));
			time.setYears(getInt(key, "Years",config));
			tmp.setTime(time);
			templateList.add(tmp);
		});
	}

	public String getStr(String key, String type,Configuration config) {
		System.out.println("Templates." + key + "." + type);
		return config.getString("Templates." + key + "." + type);
	}

	public int getInt(String key, String type,Configuration config) {
		return config.getInt("Templates." + key + ".Time." + type);
	}

	public void generateTemplates(Configuration config) {
		BanTemplateObject banTemplateObject = new BanTemplateObject();
		banTemplateObject.setReason("Hacking/Killaura");
		banTemplateObject.setId(1);
		TimeObject timeObject = new TimeObject();
		timeObject.setMonths(1);
		banTemplateObject.setTime(timeObject);

		config.set("Templates." + banTemplateObject.getId() + ".Reason", banTemplateObject.getReason());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Milliseconds",
				banTemplateObject.getTime().getMilliseconds());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Seconds",
				banTemplateObject.getTime().getSeconds());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Minutes",
				banTemplateObject.getTime().getMinutes());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Hours", banTemplateObject.getTime().getHours());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Days", banTemplateObject.getTime().getDays());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Months", banTemplateObject.getTime().getMonths());
		config.set("Templates." + banTemplateObject.getId() + ".Time.Years", banTemplateObject.getTime().getYears());
	}

	public BanTemplateObject getTemplate(String reason) {
		return templateList.stream().filter(e -> e.getReason().equalsIgnoreCase(reason)).findAny().orElse(null);
	}

	public BanTemplateObject getTemplate(int ID) {
		return templateList.stream().filter(e -> e.getId() == ID).findAny().orElse(null);
	}
}
