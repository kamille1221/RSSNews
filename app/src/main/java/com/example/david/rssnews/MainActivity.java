package com.example.david.rssnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.rv_news)
	RecyclerView rvNews;

	static final String CHOSUN = "http://myhome.chosun.com/rss/www_section_rss.xml";
	static final String JOINS = "http://rss.joins.com/joins_homenews_list.xml";
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

		rvNews.setHasFixedSize(true);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		rvNews.setLayoutManager(layoutManager);

		getNews(CHOSUN);

		adapter = new NewsAdapter(this, title, content);
		rvNews.setAdapter(adapter);
	}

	private void getNews(String url) {
		getData = new GetData();
		getData.execute(url, null, null);
		while (true) {
			try {
				Thread.sleep(1000);
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
		rvNews.smoothScrollToPosition(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		item.setChecked(true);
		switch (item.getItemId()) {
			case R.id.select_chosun:
				getNews(CHOSUN);
				return true;
			/*
			case R.id.select_joins:
				getNews(JOINS);
				return true;
			*/
			case R.id.select_donga:
				getNews(DONGA);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}