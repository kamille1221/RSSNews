package com.example.david.rssnews;

import android.content.Context;
import android.content.SharedPreferences;

class NewsPreferences {
	private static final String PREFERENCE_NAME = "com.example.david.rssnews";
	private static final String NEWS_URL = "newsURL";
	private static final String FONT_SIZE = "fontSize";
	private static final String SAVE_DATA = "saveData";

	private static SharedPreferences sharedPreferences;

	static SharedPreferences getSharedPreferences(Context context) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	static String getNewsURL() {
		return sharedPreferences.getString(NEWS_URL, null);
	}

	static void setNewsURL(String newsURL) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(NEWS_URL, newsURL);
		editor.apply();
	}

	static int getFontSize() {
		return sharedPreferences.getInt(FONT_SIZE, 20);
	}

	static void setFontSize(int fontSize) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(FONT_SIZE, fontSize);
		editor.apply();
	}

	static boolean getSaveData() {
		return sharedPreferences.getBoolean(SAVE_DATA, true);
	}

	static void setSaveData(boolean saveData) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(SAVE_DATA, saveData);
		editor.apply();
	}
}
