package com.example.david.rssnews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentActivity extends AppCompatActivity {
	@BindView(R.id.tv_content)
	TextView tvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		ButterKnife.bind(this);

		Intent intent = getIntent();

		setTitle(intent.getStringExtra("title"));

		tvContent.setText(htmlToString(intent.getStringExtra("content")));
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, NewsPreferences.getFontSize());
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
}
