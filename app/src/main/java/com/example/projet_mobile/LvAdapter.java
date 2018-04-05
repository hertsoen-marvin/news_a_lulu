package com.example.projet_mobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.ArrayList;

public class LvAdapter extends ArrayAdapter<RowItems>{
    private final Context context;
    private final ArrayList<RowItems> itemsArrayList;

    public LvAdapter(Context context, ArrayList<RowItems> itemsArrayList) {

        super(context, R.layout.list_item, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        // 3. Get the two text view from the rowView
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView resume = (TextView) rowView.findViewById(R.id.resume);
        TextView author = (TextView) rowView.findViewById(R.id.author);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.illustration);

        // 4. Set the text for textView
        title.setText(itemsArrayList.get(position).getTitle());
        resume.setText(itemsArrayList.get(position).getResume());
        author.setText(itemsArrayList.get(position).getAuthor());
        new DownloadImage(imageView).execute(itemsArrayList.get(position).getImageURL());
        /*imageView.setOnClickListener(new View.OnClickListener() {
        });*/
        // 5. retrn rowView
        return rowView;
    }
}
