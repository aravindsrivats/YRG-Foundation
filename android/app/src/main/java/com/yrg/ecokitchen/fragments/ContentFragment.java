package com.yrg.ecokitchen.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yrg.ecokitchen.R;

import java.util.zip.Inflater;

public class ContentFragment extends Fragment {

    private String item;

    public ContentFragment() {

    }

    public static ContentFragment newInstance(String item) {
        ContentFragment cf = new ContentFragment();
        Bundle b = new Bundle();
        b.putString("type", item);
        cf.setArguments(b);
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            item = args.getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View end = inflater.inflate(R.layout.text_content, container, false);
        TextView contentText = (TextView) end.findViewById(R.id.textView);
        if (item.equals("About Us")) {
            contentText.setText(R.string.about);
        }
        else {
            contentText.setText(R.string.contact);
        }
        return end;
    }
}
