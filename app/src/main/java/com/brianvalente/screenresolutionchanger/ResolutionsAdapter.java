package com.brianvalente.screenresolutionchanger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by brianvalente on 7/20/16.
 */

public class ResolutionsAdapter extends RecyclerView.Adapter<ResolutionsAdapter.ViewHolder> {
    private ArrayList<Resolution> mResolutions;
    private static MainActivity mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mResolution;
        public TextView mResolutionName;
        public ViewHolder(View v) {
            super(v);
            mResolution = (TextView) v.findViewById(R.id.resolution);
            mResolutionName = (TextView) v.findViewById(R.id.resolution_name);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Resolution resolution = (Resolution) mResolution.getTag();
                    mContext.changeResolution(resolution, true);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ResolutionsAdapter(Context context) {
        mContext = (MainActivity) context;
        mResolutions = getSupportedResolutions();
    }

    private ArrayList<Resolution> getSupportedResolutions() {
        ArrayList<Resolution> resolutions = new ArrayList<>();

        resolutions.add(new Resolution("2880x5120", ""));
        resolutions.add(new Resolution("2160x3840", "2160p"));
        resolutions.add(new Resolution("1440x2560", "1440p"));
        resolutions.add(new Resolution("1080x1920", "1080p"));
        resolutions.add(new Resolution("900x1600", ""));
        resolutions.add(new Resolution("768x1366", ""));
        resolutions.add(new Resolution("720x1280", "720p"));
        resolutions.add(new Resolution("648x1152", ""));
        resolutions.add(new Resolution("576x1024", ""));

        return resolutions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResolutionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resolutions_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Resolution resolution = mResolutions.get(position);
        String res = resolution.getResolution();
        String resName = resolution.getResolutionName();

        vh.mResolution.setTag(resolution);
        vh.mResolution.setText(res);
        vh.mResolutionName.setText(resName != null? "(" + resName + ")" : null);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mResolutions.size();
    }
}
