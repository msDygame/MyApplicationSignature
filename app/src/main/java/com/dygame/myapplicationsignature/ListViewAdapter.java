package com.dygame.myapplicationsignature;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/6.
 */
public class ListViewAdapter extends BaseAdapter
{
    protected String TAG = "" ;
    protected LayoutInflater inflater;
    protected ArrayList<MainActivity.AppInfo> arrayAppinfo;
    protected Context mContext ;
    public ListViewAdapter(Context context ,  ArrayList<MainActivity.AppInfo> array)
    {
        mContext = context ;
        inflater = LayoutInflater.from(context);
        arrayAppinfo = array;
    }

    public void setTag(String sTag) { TAG = sTag ; }

    @Override
    public int getCount() { return arrayAppinfo.size(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
        	convertView = inflater.inflate(R.layout.listview_item, null);
	    }	    
        
        Log.i(TAG, "ListViewAdapter getView() convertView = null , position = " + position);
        convertView = inflater.inflate(R.layout.listview_item, null);

        TextView tvApplitionName = (TextView)convertView.findViewById(R.id.textViewAppName);
        TextView tvPackageName = (TextView)convertView.findViewById(R.id.textViewPackageName);
        TextView tvVersionName = (TextView)convertView.findViewById(R.id.textViewVersionName);
        TextView tvVersionCode =  (TextView)convertView.findViewById(R.id.textViewVersionCode);
        ImageView ivAppIcon = (ImageView)convertView.findViewById(R.id.imageViewLogo);
        ProgressBar pb = (ProgressBar)convertView.findViewById(R.id.progressBar);

        tvApplitionName.setText((String) arrayAppinfo.get(position).getAppName());
        tvVersionName.setText((String) arrayAppinfo.get(position).getPackageName());
        tvPackageName.setText((String) arrayAppinfo.get(position).getVersionName());
        tvVersionCode.setText("" + (int) arrayAppinfo.get(position).getVersionCode());
        ivAppIcon.setImageDrawable(arrayAppinfo.get(position).getAppIcon());
        int iProgress = (int) arrayAppinfo.get(position).getVersionCode() ;
        if (iProgress > 100) iProgress = 100 ;
        pb.setProgress(iProgress) ;

                ivAppIcon.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Log.i(TAG, "ListViewAdapter getView() AppIcon = click ");
                        Toast.makeText(mContext, "ListViewAdapter onClick ", Toast.LENGTH_LONG).show();
                    }
                });
        return convertView;
    }

    @Override
    public Object getItem(int position)
    {
        return arrayAppinfo.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }
}
