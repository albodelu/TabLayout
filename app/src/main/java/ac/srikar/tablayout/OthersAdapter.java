package ac.srikar.tablayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OthersAdapter extends RecyclerView.Adapter<OthersAdapter.OthersViewHolder> {

    /**
     * Log cat tag
     */
    private static final String LOG_TAG = OthersAdapter.class.getSimpleName();

    private final Context context;
    private final ArrayList<LocalDealsDataFields> othersDataArray;
    private LayoutInflater layoutInflater;

    public OthersAdapter(Context context, ArrayList<LocalDealsDataFields> othersDataArray) {
        this.context = context;
        this.othersDataArray = othersDataArray;
        if (this.context != null) {
            layoutInflater = LayoutInflater.from(this.context);
        } else {
            Log.i(LOG_TAG, "Context is null");
        }

        getLogDetails();
    }

    /**
     * Method shows received data array in the log cat
     */
    private void getLogDetails() {
        Log.i(LOG_TAG, "Others Data Array Size: " + othersDataArray.size());
        for (int j = 0; j < othersDataArray.size(); j++) {
            Log.i(LOG_TAG, "othersDataArray of item " + j + ", Other Deal Id: " +
                    othersDataArray.get(j).getLocalDealId());
            Log.i(LOG_TAG, "othersDataArray of item " + j + ", Other Deal Second Title: " +
                    othersDataArray.get(j).getLocalDealSecondTitle());
            Log.i(LOG_TAG, "othersDataArray of item " + j + ", Other Deal Image Link: " +
                    othersDataArray.get(j).getLocalDealImage());
        }
    }

    class OthersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView othersSmallTitleTextView;
        ImageView othersImageView;

        OthersViewHolder(View itemView) {
            super(itemView);
            othersSmallTitleTextView = (TextView) itemView.findViewById(R.id.others_small_title);
            othersImageView = (ImageView) itemView.findViewById(R.id.others_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Open Details Activity
        }
    }

    @Override
    public OthersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.others_items, parent, false);
        return new OthersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OthersViewHolder holder, int position) {
        Log.i(LOG_TAG, "onBindViewHolder: Item count: " + getItemCount());
        Log.i(LOG_TAG, "onBindViewHolder: position: " + position);
        getLogDetails();
        String lfImage = othersDataArray.get(position).getLocalDealImage();
        Log.i(LOG_TAG, "Local Deal Image: " + lfImage);
        String lfCategoryName = othersDataArray.get(position).getLocalDealSecondTitle();
        Log.i(LOG_TAG, "Local Deal Category Name: " + lfCategoryName);
        if (lfCategoryName != null) {
            // Set the second title
            holder.othersSmallTitleTextView.setText(lfCategoryName);
        }
        if (lfImage != null) {
            if (!lfImage.isEmpty()) {
                // Get the Uri
                Uri lfUriImage = Uri.parse(context.getString(R.string.server_image_assets) + lfImage);
                // Load the Image
                Picasso.with(context).load(lfUriImage).into(holder.othersImageView);
                //holder.fabDealsImageView.setImageURI(lfUriImage);
                Log.i(LOG_TAG, "Local Deal Image: " + lfUriImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return othersDataArray.size();
    }
}