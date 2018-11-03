package com.huawei.myvmplayer.toupin.apapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.myvmplayer.R;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by x00378851 on 2018/8/29.
 */

public class DeviceListAdapter extends ArrayAdapter<Device> {

    private LayoutInflater mInflater;

    public DeviceListAdapter(Context context) {
        super(context, 0);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.devices_items, null);

        Device item = getItem(position);
        if (item == null) {
            return convertView;
        }

        ImageView imageView = convertView.findViewById(R.id.listview_item_image);
        imageView.setBackgroundResource(R.drawable.ic_action_dock);

        TextView textView = convertView.findViewById(R.id.listview_item_line_one);
        textView.setText(item.getDetails().getFriendlyName());
        return convertView;
    }
}
