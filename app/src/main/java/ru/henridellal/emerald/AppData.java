package ru.henridellal.emerald;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AppData extends BaseData {
	
	//Constants for parsing
	public static final String COMPONENT = "C";
	public static final String NAME = "N";
	
	private ArrayList<String> categories;
	
	@Override
	public boolean equals(Object a) {
		if (! (a instanceof AppData))
			return false;
		if (component == null) {
			return a == null || ((AppData)a).component == null;
		}
		return component.equals( ((AppData)a).component );
	}
	
	@Override
	public int hashCode() {
		if (component == null)
			return "NULL null NULL".hashCode();
		else
			return (component).hashCode();
	}
	
	public AppData() {
		super();
		categories = new ArrayList<String>();
	}
	public AppData(Cursor cursor) {
		super();
		component = cursor.getString(0);
		name = cursor.getString(1);
	}
	public AppData(String component, String name) {
		super(component, name);
		categories = new ArrayList<String>();
	}
	public void addCategory(String category) {
		categories.add(category);
	}
	private String getCategoriesString() {
		StringBuilder result = new StringBuilder();
		for (String s: categories) {
			result.append("@");
			result.append(s);
			result.append("@");
		}
		return result.toString();
	}
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put("component", component);
		values.put("name", name);
		values.put("categories", getCategoriesString());
		return values;
	}
	public void read(BufferedReader reader, String firstLineOfData){
		try {
			this.component = firstLineOfData.substring(1).trim();
			this.name = readLine(reader, NAME).substring(1).trim();
		} catch (IOException e) {
		
		}
	}
	//writes app data in given file writer
	public void write(BufferedWriter writer) throws IOException {
		writer.write(new StringBuilder(COMPONENT)
		.append(this.component)
		.append("\n")
		.append(NAME)
		.append(this.name)
		.append("\n").toString());
	}

}
