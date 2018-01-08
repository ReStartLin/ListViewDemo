package restart.com.listviewdemo.listview;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import restart.com.listviewdemo.R;

public class AppListViewActivity extends AppCompatActivity {

    private ListView appListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list_view);

        appListView = findViewById(R.id.app_list_view);

        List<String> appNames = new ArrayList<>();

        appNames.add("QQ");
        appNames.add("微信");
        appNames.add("慕课网");

        appListView.setAdapter(new AppListAdapter(getAppInfos(),AppListViewActivity.this));
//        appListView.addHeaderView();



    }
    private List<ResolveInfo> getAppInfos(){
        /*
        * 获得系统应用的信息
        * */
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent,0);
    }


    public class AppListAdapter extends BaseAdapter{
        private List<ResolveInfo> mAppInfo;
        private ViewHolder holder;
        private Context context;

        AppListAdapter(List<ResolveInfo> mAppInfo, Context context) {
            this.mAppInfo = mAppInfo;
            this.context = context;
        }

        /*
                        * 有多少条数据
                        * */
        @Override
        public int getCount() {
            return mAppInfo.size();
        }
//        返回当前position位置的这一条
        @Override
        public Object getItem(int position) {
            return mAppInfo.get(position);
        }
//      返回当前position位置的这一条的ID
        @Override
        public long getItemId(int position) {
            return position;
        }
        /*
        * 处理 view---data 填充数据
        * */
        @SuppressLint("WrongViewCast")
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null){
                view = View.inflate(context,R.layout.item_app_list,null);
                holder = new ViewHolder();
                holder.appIconImageView = view.findViewById(R.id.icon_image_view);
                holder.appNameTextView = view.findViewById(R.id.app_name_text_view);
                view.setTag(holder);

            }else {
                holder = (ViewHolder) view.getTag();
            }
            holder.appNameTextView.setText(mAppInfo.get(position).activityInfo.loadLabel(getPackageManager()));
            holder.appIconImageView.setImageDrawable(mAppInfo.get(position).activityInfo.loadIcon(getPackageManager()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String packgeName = mAppInfo.get(position).activityInfo.packageName;

                    String classString = mAppInfo.get(position).activityInfo.name;

                    ComponentName componentName = new ComponentName(packgeName,classString);
                    final Intent intent = new Intent();
                    intent.setComponent(componentName);
                    startActivity(intent);
                }
            });
            return view;
        }
        class ViewHolder{
            private ImageView appIconImageView;
            private TextView appNameTextView;
        }
    }
}
