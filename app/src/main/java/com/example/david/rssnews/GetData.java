package com.example.david.rssnews;

import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.crash.FirebaseCrash;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

class GetData extends AsyncTask<String, Void, Void> {
	Vector<String> titleVector = new Vector<>();
	Vector<String> contentVector = new Vector<>();
	Vector<String> linkVector = new Vector<>();
	private String tagName = "";
	private String title = "";
	private String content = "";
	private String link = "";
	private boolean[] flags = new boolean[]{true, true, true};
	Boolean flag = false;
	private FrameLayout mLoading;

	GetData(FrameLayout loading) {
		this.mLoading = loading;
	}

	@Override
	protected void onPreExecute() {
		mLoading.setVisibility(View.VISIBLE);
	}

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
					switch (tagName) {
						case "title":
							if (flags[0]) {
								flags[0] = false;
								break;
							}
							title += parser.getText();
							break;
						case "link":
							if (flags[1]) {
								flags[1] = false;
								break;
							}
							link += parser.getText();
							break;
						case "description":
							if (flags[2]) {
								flags[2] = false;
								break;
							}
							content += parser.getText();
							break;
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					tagName = parser.getName();
					if (tagName.equals("item")) {
						titleVector.add(title.trim());
						contentVector.add(content.trim());
						linkVector.add(link.trim());
						title = "";
						content = "";
						link = "";
					}
				}
				eventType = parser.next();
			}
			flag = true;
		} catch (XmlPullParserException e) {
			FirebaseCrash.log("XmlPullParserException");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			FirebaseCrash.log("MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			FirebaseCrash.log("IOException");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		mLoading.setVisibility(View.GONE);
	}
}
