package com.example.david.rssnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.rv_news)
	RecyclerView rvNews;
	@BindView(R.id.fl_loading)
	FrameLayout flLoading;

	static final String CHOSUN = "http://myhome.chosun.com/rss/www_section_rss.xml";
	// 중앙일보는 내용이 너무 부실해서 그냥 제외..
	// static final String JOINS = "http://rss.joins.com/joins_homenews_list.xml";
	static final String DONGA = "http://rss.donga.com/total.xml";

	GetData getData;
	Vector<String> title;
	Vector<String> content;
	NewsAdapter adapter;

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

		adapter = new NewsAdapter(this, title, content);
		rvNews.setAdapter(adapter);
	}

	private void getNews(String url) {
		if (TextUtils.isEmpty(url)) {
			url = CHOSUN;
		}
		getData = new GetData(flLoading);
		getData.execute(url, null, null);
		while (true) {
			try {
				if (getData.flag) {
					title = getData.titleVector;
					content = getData.contentVector;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (adapter != null) {
			adapter.refresh(title, content);
		}
	}

	@OnClick(R.id.fab_top)
	void onTopClick() {
		adapter.refresh(title, content);
		rvNews.scrollToPosition(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		String url = NewsPreferences.getNewsURL();
		if (!TextUtils.isEmpty(url)) {
			if (url.equals(CHOSUN)) {
				menu.getItem(0).setChecked(true);
//		    } else if (url.equals(JOINS)) {
//				menu.getItem(1).setChecked(true);
			} else if (url.equals(DONGA)) {
//				menu.getItem(2).setChecked(true);
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
			/*
			case R.id.select_joins:
				getNews(JOINS);
				NewsPreferences.setNewsURL(JOINS);
				return true;
			*/
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
		}
		return super.onOptionsItemSelected(item);
	}
}