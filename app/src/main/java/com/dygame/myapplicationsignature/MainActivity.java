package com.dygame.myapplicationsignature;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    protected Button quitButton ;
    protected String TAG = "MyCrashHandler" ;
    protected ListView lvDetail ;
    protected ListViewAdapter pListViewAdapter ;
    protected ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find resources
        quitButton = (Button)findViewById(R.id.button1) ;
        lvDetail = (ListView)findViewById(R.id.listView) ;
        //set OnClickListener
        quitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        //
        getAppInfo() ;
        getAppSign() ;
        checkInstalledPackege("com.dygame.mobile3") ;
        getUserEmailFetcher(this) ;
        //adapter
        pListViewAdapter = new ListViewAdapter(this , appList) ;
        //listview
        lvDetail.setAdapter(pListViewAdapter);
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.e(TAG, "ListView OnItemClickListene "+position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //get app signature
    public void getAppSign()
    {
        try
        {
            Signature[] sigs = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES).signatures;
            for (Signature sig : sigs)
            {
                Log.i(TAG, getPackageName() + " Signature hashcode : " + sig.hashCode());
            }
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            return ;
        }
    }

    public boolean checkInstalledPackege(String s)
    {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++)
        {
            PackageInfo packageInfo = packages.get(i);
            if(s.equals(packageInfo.packageName))
            {
                Log.d(TAG,s + " installed.");
                return true;
            }
        }
        Log.d(TAG,s + " uninstalled.");
        return false;
    }

    /**
     *  Inner class
     */
    public class AppInfo
    {
        protected String appName="";
        protected String packageName="";
        protected String versionName="";
        protected int versionCode=0;
        protected Drawable appIcon=null;
        public String getAppName() { return appName ; }
        public String getPackageName() { return packageName ; }
        public String getVersionName() { return versionName ; }
        public int getVersionCode() { return versionCode ; }
        public Drawable getAppIcon() { return appIcon ; }
    }

    public void getAppInfo()
    {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

        for(int i=0;i<packages.size();i++)
        {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            tmpInfo.packageName = packageInfo.packageName;
            tmpInfo.versionName = packageInfo.versionName;
            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            //Only display the non-system app info
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                //非系統應用
                appList.add(tmpInfo);//如果非系統應用，則添加至appList
                Log.e(TAG, tmpInfo.appName + " package:" + packageInfo.packageName + " version=" + packageInfo.versionName + " code=" + packageInfo.versionCode );
            }
            else
            {
                //系統應用
                Log.e(TAG, "system package :" + packageInfo.packageName + " version=" + packageInfo.versionName + " code=" + packageInfo.versionCode );
            }
        }
    }

    public void getUserEmailFetcher(Context context)
    {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");//need "android.permission.GET_ACCOUNTS" in androidManifest.xml
        Account account;
        if (accounts.length > 0)
        {
            account = accounts[0];
            Log.d(TAG, "user email :" + account.name );
        }
        else
        {
            Log.d(TAG, "user email : null" );
            return  ;
        }
    }
}
