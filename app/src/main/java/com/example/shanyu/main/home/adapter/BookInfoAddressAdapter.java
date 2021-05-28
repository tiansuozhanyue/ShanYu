package com.example.shanyu.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.main.mine.bean.AddressMode;

import java.util.List;

public class BookInfoAddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddressMode> addressModes;
    private AddressOnClick onClick;

    public BookInfoAddressAdapter(Context mContext, List<AddressMode> actionModes, AddressOnClick onClick) {
        this.mContext = mContext;
        this.addressModes = actionModes;
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return addressModes.size();
    }

    @Override
    public Object getItem(int position) {
        return addressModes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressMode mode = addressModes.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_info_address_item, parent, false);
        ImageView selectView = view.findViewById(R.id.select);
        TextView address = view.findViewById(R.id.address);
        TextView name = view.findViewById(R.id.name);
        TextView phone = view.findViewById(R.id.phone);
        LinearLayout set_layout = view.findViewById(R.id.set_layout);
        Switch radioButton = view.findViewById(R.id.radioButton);

        selectView.setVisibility(mode.getIsselected() == 1 ? View.VISIBLE : View.GONE);
        radioButton.setChecked(mode.getIsselected() == 1);
        address.setText(mode.getAreaname() + mode.getAddress());
        name.setText(mode.getName());
        phone.setText(mode.getPhone());

        set_layout.setVisibility(View.GONE);


        if (onClick != null) {
            view.setOnClickListener(v -> onClick.onAddressSelet(mode));
        }

        return view;
    }

    public interface AddressOnClick {

        void onAddressSelet(AddressMode mode);

    }

}
