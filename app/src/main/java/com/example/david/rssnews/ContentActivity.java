package com.example.david.rssnews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends AppCompatActivity {
	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.wv_content)
	WebView wvContent;

	String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ButterKnife.bind(this);

		Intent intent = getIntent();
		ActionBar actionBar = getSupportActionBar();
		if (intent != null) {
			if (actionBar != null) {
				actionBar.setTitle(intent.getStringExtra("title"));
				actionBar.setDisplayHomeAsUpEnabled(true);
			}
			tvContent.setText(htmlToString(intent.getStringExtra("content")));
			link = intent.getStringExtra("link");
		} else {
			Toast.makeText(ContentActivity.this, R.string.toast_intent_error, Toast.LENGTH_SHORT).show();
			finish();
		}
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, NewsPreferences.getFontSize());
		wvContent.setWebViewClient(new WebViewClient());
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
