package com.example.david.rssnews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends AppCompatActivity {
	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.wv_content)
	WebView wvContent;

	boolean isWeb;
	String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ButterKnife.bind(this);

		Intent intent = getIntent();

		setTitle(intent.getStringExtra("title"));

		tvContent.setText(htmlToString(intent.getStringExtra("content")));
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, NewsPreferences.getFontSize());

		wvContent.setWebViewClient(new WebViewClient());
		link = intent.getStringExtra("link");
		// WebSettings webSettings = wvContent.getSettings();
		// webSettings.setJavaScriptEnabled(true);
	}

	private Spanned htmlToString(String html) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return Html.fromHtml(html);
		}
	}

	@OnClick(R.id.btn_close)
	void onCloseClick() {
		finish();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!NewsPreferences.getSaveData()) {
			getMenuInflater().inflate(R.menu.content, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.show_all:
				if (!isWeb) {
					if (!TextUtils.isEmpty(link)) {
						tvContent.setVisibility(View.GONE);
						wvContent.setVisibility(View.VISIBLE);
						wvContent.loadUrl(link);
						item.setTitle("기사 요약 보기");
						isWeb = true;
					}
				} else {
					tvContent.setVisibility(View.VISIBLE);
					wvContent.setVisibility(View.GONE);
					isWeb = false;
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
