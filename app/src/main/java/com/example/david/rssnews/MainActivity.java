package com.example.david.rssnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.rv_news)
	RecyclerView rvNews;
	@BindView(R.id.fl_loading)
	FrameLayout flLoading;

	private static final String CHOSUN = "http://myhome.chosun.com/rss/www_section_rss.xml";
	private static final String DONGA = "http://rss.donga.com/total.xml";

	private Vector<String> title;
	private Vector<String> content;
	private Vector<String> link;
	private NewsAdapter adapter;
	private FirebaseAnalytics mFirebaseAnalytics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		NewsPreferences.getSharedPreferences(this);

		rvNews.setHasFixedSize(true);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		rvNews.setLayoutManager(layoutManager);

		getNews(NewsPreferences.getNewsURL());

		adapter = new NewsAdapter(this, title, content, link);
		rvNews.setAdapter(adapter);

		// Obtain the FirebaseAnalytics instance.
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
	}

	private void getNews(String url) {
		if (TextUtils.isEmpty(url)) {
			url = CHOSUN;
		}
		GetData getData = new GetData(flLoading);
		getData.execute(url, null, null);
		while (true) {
			if (getData.flag) {
				title = getData.titleVector;
				content = getData.contentVector;
				link = getData.linkVector;
				break;
			}
		}
		if (adapter != null) {
			adapter.refresh(title, content, link);
		}
	}

	@OnClick(R.id.fab_top)
	void onTopClick() {
		adapter.refresh(title, content, link);
		rvNews.scrollToPosition(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		String url = NewsPreferences.getNewsURL();
		if (!TextUtils.isEmpty(url)) {
			if (url.equals(CHOSUN)) {
				menu.getItem(0).setChecked(true);
			} else if (url.equals(DONGA)) {
				menu.getItem(1).setChecked(true);
			}
		}
		switch (NewsPreferences.getFontSize()) {
			case 12:
				menu.getItem(2).getSubMenu().getItem(0).setChecked(true);
				break;
			case 16:
				menu.getItem(2).getSubMenu().getItem(1).setChecked(true);
				break;
			case 20:
				menu.getItem(2).getSubMenu().getItem(2).setChecked(true);
				break;
			case 24:
				menu.getItem(2).getSubMenu().getItem(3).setChecked(true);
				break;
			case 28:
				menu.getItem(2).getSubMenu().getItem(4).setChecked(true);
				break;
		}
		if (NewsPreferences.getSaveData()) {
			menu.getItem(3).setChecked(true);
		} else {
			menu.getItem(3).setChecked(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item.setChecked(true);
		switch (item.getItemId()) {
			case R.id.select_chosun:
				getNews(CHOSUN);
				NewsPreferences.setNewsURL(CHOSUN);
				return true;
			case R.id.select_donga:
				getNews(DONGA);
				NewsPreferences.setNewsURL(DONGA);
				return true;
			case R.id.font_12:
				getNews(NewsPreferences.getNewsURL());
				NewsPreferences.setFontSize(12);
				return true;
			case R.id.font_16:
				getNews(NewsPreferences.getNewsURL());
				NewsPreferences.setFontSize(16);
				return true;
			case R.id.font_20:
				getNews(NewsPreferences.getNewsURL());
				NewsPreferences.setFontSize(20);
				return true;
			case R.id.font_24:
				getNews(NewsPreferences.getNewsURL());
				NewsPreferences.setFontSize(24);
				return true;
			case R.id.font_28:
				getNews(NewsPreferences.getNewsURL());
				NewsPreferences.setFontSize(28);
				return true;
			case R.id.save_data:
				NewsPreferences.setSaveData(!NewsPreferences.getSaveData());
				item.setChecked(NewsPreferences.getSaveData());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}