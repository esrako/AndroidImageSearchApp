package com.mycompany.googleimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.googleimagesearch.R;
import com.mycompany.googleimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ekucukog on 5/12/2015.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    private class ViewHolder{
        ImageView ivImage;
        TextView tvTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //clear out the image
        viewHolder.ivImage.setImageResource(0);
        viewHolder.tvTitle.setText(Html.fromHtml(imageInfo.title));

        Picasso.with(getContext())
                .load(imageInfo.thumbUrl)
                .into(viewHolder.ivImage);

        return convertView;
    }
}
