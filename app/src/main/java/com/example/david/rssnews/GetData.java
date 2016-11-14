package com.example.david.rssnews;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;

class GetData extends AsyncTask<String, Void, Void> {
	Vector<String> titleVector = new Vector<>();
	Vector<String> contentVector = new Vector<>();
	private String tagName = "";
	private String title = "";
	private String desc = "";
	Boolean flag = false;

	@Override
	protected Void doInBackground(String... strings) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();

			URL url = new URL(strings[0]);
			InputStream inputStream = url.openStream();
			parser.setInput(inputStream, "utf-8");

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					tagName = parser.getName();
				} else if (eventType == XmlPullParser.TEXT) {
					if (tagName.equals("title")) {
						title += parser.getText();
					} else if (tagName.equals("description")) {
						desc += parser.getText();
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					tagName = parser.getName();
					if (tagName.equals("item")) {
						titleVector.add(title.trim());
						contentVector.add(desc.trim());
						title = "";
						desc = "";
					}
				}
				eventType = parser.next();
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
