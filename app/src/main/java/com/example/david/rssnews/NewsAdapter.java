package com.example.david.rssnews;
/*
 * Created by David on 2016-11-14.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
	private Context mContext;
	private Vector<String> mTitle;
	private Vector<String> mContent;

	NewsAdapter(Context context, Vector<String> title, Vector<String> content) {
		this.mContext = context;
		this.mTitle = title;
		this.mContent = content;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final int temp = position;
		holder.tvTitle.setText(mTitle.get(position));
		holder.cvItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, ContentActivity.class);
				intent.putExtra("title", mTitle.get(temp));
				intent.putExtra("content", mContent.get(temp));
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return mTitle.size();
	}

	void refresh(Vector<String> title, Vector<String> content) {
		this.mTitle = title;
		this.mContent = content;
		notifyDataSetChanged();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.cv_item)
		CardView cvItem;
		@BindView(R.id.tv_title)
		TextView tvTitle;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
