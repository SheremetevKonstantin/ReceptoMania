package com.sheremetev.receptomania;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>{
    private String[] captions;
    private int[] imageIds;

    private Listener listener;
    interface Listener {
        void onClick(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public CaptionedImagesAdapter(String[] captions, int[] imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        if(captions != null)
            return captions.length;

        return 0;
    }
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        try {
            ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
            Drawable drawable =
                    ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
            imageView.setImageDrawable(drawable);
            imageView.setContentDescription(captions[position]);
            TextView textView = (TextView) cardView.findViewById(R.id.info_text);
            textView.setText(captions[position]);
        }
        catch (Exception ignored){}

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}
