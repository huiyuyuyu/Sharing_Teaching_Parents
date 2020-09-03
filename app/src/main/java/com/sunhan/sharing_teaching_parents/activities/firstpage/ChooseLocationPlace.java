package com.sunhan.sharing_teaching_parents.activities.firstpage;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.sunhan.sharing_teaching_parents.R;
import com.sunhan.sharing_teaching_parents.beans.CityEntity;
import com.sunhan.sharing_teaching_parents.sqlite.RecordSQLiteOpenHelper;
import com.sunhan.sharing_teaching_parents.ui.LetterListView;
import com.sunhan.sharing_teaching_parents.ui.binding.Bind;
import com.sunhan.sharing_teaching_parents.ui.binding.ViewBinder;
import com.sunhan.sharing_teaching_parents.util.JsonReadUtil;
import com.sunhan.sharing_teaching_parents.util.StatusBarUtil;
import com.sunhan.sharing_teaching_parents.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 孙汉 on 2019-07-13/09/42
 */
public class ChooseLocationPlace extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener {

    //文件名称
    private final static String CityFileName = "allcity.json";

    private ImageView back;
    private TextView locationCity;
    private TextView noSearchDataTv;
    private EditText searchContentEt;
    private ListView totalCityLv;
    private ListView searchCityLv;
    private View location_type_1;
    private View location_type_2;
    private View location_type_3;
    private LetterListView lettersLv;
    private RelativeLayout mToolbar;
    private TextView location_quxiao;
    private View img_lishisousuo_clear;
    private View img_lishidizhi_clear;
    private ListView listview_lishisousuo;
    private ListView listview_lishidizhi;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private RecordSQLiteOpenHelper helper_dizhi = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db_lishisousuo;
    private SQLiteDatabase db_lishidizhi;
    private BaseAdapter adapter_lishisousuo;
    private BaseAdapter adapter_lishidizhi;

    private boolean isScroll = false;
    private boolean mReady = false;
    private TextView overlay;//对话框首字母TextView
    private OverlayThread overlayThread; //显示首字母对话框
    private Handler handler;

    public int locationCurrentType = 1;
    public final static int LOCATION_TYPE1 = 1;
    public final static int LOCATION_TYPE2 = 2;

    private HashMap<String, Integer> alphaIndexer;//存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> hotCityList = new ArrayList<>();
    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;

    private String locationCity_string, curSelCity_string;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselocationplace);
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setStatusBarBlack(true,this);
        //默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //setSystemBarTransparent();
        initView();
        initData();
        initListener();

        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
        Date date = new Date();
        long time = date.getTime();
        insertData("Leo" + time);
        insertData_dizhi("Leo" + time);

        // 第一次进入查询所有的历史记录
        queryData();
        queryData_dizhi();
    }

    private void initView() {
        mToolbar = findViewById(R.id.tool_bar_rl);
        searchCityLv = findViewById(R.id.search_city_lv);
        noSearchDataTv = findViewById(R.id.no_search_result_tv);
        ViewBinder.bind(this);

        /**
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
         int top = ScreenUtils.getSystemBarHeight();
         mToolbar.setPadding(0, top, 0, 0);
         }*/

        handler = new Handler();
        overlayThread = new OverlayThread();
        searchCityListAdapter = new SearchCityListAdapter(this, searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
        locationCity_string = "上海";
        curSelCity_string = locationCity_string;
    }

    private void initData() {
        initTotalCityList();
        back = findViewById(R.id.location_back_service);
        back.setOnClickListener(this);
        location_quxiao = findViewById(R.id.location_quxiao);
        location_quxiao.setOnClickListener(this);
        locationCity = findViewById(R.id.locationCity);
        locationCity.setOnClickListener(this);
        img_lishisousuo_clear = findViewById(R.id.img_lishisousuo_clear);
        img_lishisousuo_clear.setOnClickListener(this);
        img_lishidizhi_clear = findViewById(R.id.img_lishidizhi_clear);
        img_lishidizhi_clear.setOnClickListener(this);
        listview_lishisousuo = findViewById(R.id.listview_lishisousuo);
        listview_lishidizhi = findViewById(R.id.listview_lishidizhi);
        location_type_1 = findViewById(R.id.location_type_1);
        location_type_2 = findViewById(R.id.location_type_2);
        location_type_3 = findViewById(R.id.location_type_3);
        totalCityLv = findViewById(R.id.total_city_lv);

        cityListAdapter = new CityListAdapter(this, totalCityList, hotCityList);
        totalCityLv.setAdapter(cityListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    CityEntity cityEntity = totalCityList.get(position);
                    showSetCityDialog(cityEntity.getName(),cityEntity.getCityCode());
                }
            }
        });
        lettersLv = findViewById(R.id.total_city_letters_lv);
        lettersLv.setOnTouchingLetterChangedListener(new LetterListViewListener());
        initOverlay();
    }

    /**
     * 初始化全部城市列表
     */
    private void initTotalCityList() {
        hotCityList.clear();
        totalCityList.clear();
        curCityList.clear();

        String cityListJson = JsonReadUtil.getJsonStr(this, CityFileName);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(cityListJson);
            JSONArray array = jsonObject.getJSONArray("City");
            for (int i=0; i<array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String key = object.getString("key");
                String pinyin = object.getString("full");
                String first = object.getString("first");
                String cityCode = object.getString("code");

                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(name);
                cityEntity.setKey(key);
                cityEntity.setPinyin(pinyin);
                cityEntity.setFirst(first);
                cityEntity.setCityCode(cityCode);

                if (key.equals("热门")) {
                    hotCityList.add(cityEntity);
                } else {
                    if (!cityEntity.getKey().equals("0") && !cityEntity.getKey().equals("1")) {
                        curCityList.add(cityEntity);
                    }
                    totalCityList.add(cityEntity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_back_service:
                finish();
                break;
            case R.id.locationCity:
                setExchange();
                break;
            case R.id.location_quxiao:
                hideSoftInput(searchContentEt.getWindowToken());
                location_quxiao.setVisibility(View.GONE);
                searchContentEt.setCursorVisible(false);
                searchCityLv.setVisibility(View.GONE);
                noSearchDataTv.setVisibility(View.GONE);
                location_type_3.setVisibility(View.GONE);
                location_type_2.setVisibility(View.GONE);
                location_type_1.setVisibility(View.VISIBLE);
                searchContentEt.setText("");
                break;
            case R.id.search_locate_content_et:
                location_quxiao.setVisibility(View.VISIBLE);
                searchContentEt.setCursorVisible(true);
                location_type_3.setVisibility(View.VISIBLE);
                queryData();
                //location_type_1.setVisibility(View.GONE);
                //location_type_2.setVisibility(View.GONE);
                break;
            case R.id.img_lishisousuo_clear:
                deleteData();
                queryData();
            case R.id.img_lishidizhi_clear:
                deleteData_dizhi();
                queryData_dizhi();
        }
    }

    /**
     * 模糊查询数据
     */
    private void queryData() {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records", null);
        // 创建adapter适配器对象
        adapter_lishisousuo = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listview_lishisousuo.setAdapter(adapter_lishisousuo);
        adapter_lishisousuo.notifyDataSetChanged();
    }

    /**
     * 模糊查询数据（历史地址）
     */
    private void queryData_dizhi() {
        Cursor cursor = helper_dizhi.getReadableDatabase().rawQuery(
                "select id as _id,name from records_dizhi", null);
        // 创建adapter适配器对象
        adapter_lishidizhi = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listview_lishidizhi.setAdapter(adapter_lishidizhi);
        adapter_lishidizhi.notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db_lishisousuo = helper.getWritableDatabase();
        db_lishisousuo.execSQL("delete from records");
        db_lishisousuo.close();
    }

    /**
     * 清空数据（历史地址）
     */
    private void deleteData_dizhi() {
        db_lishidizhi = helper_dizhi.getWritableDatabase();
        db_lishidizhi.execSQL("delete from records_dizhi");
        db_lishidizhi.close();
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db_lishisousuo = helper.getWritableDatabase();
        db_lishisousuo.execSQL("insert into records(name) values('" + tempName + "')");
        db_lishisousuo.close();
    }

    /**
     * 插入数据（历史地址）
     */
    private void insertData_dizhi(String tempName) {
        db_lishidizhi = helper_dizhi.getWritableDatabase();
        db_lishidizhi.execSQL("insert into records_dizhi(name) values('" + tempName + "')");
        db_lishidizhi.close();
    }

    private void initListener() {
        searchContentEt = findViewById(R.id.search_locate_content_et);
        searchContentEt.setOnClickListener(this);
        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = searchCityList.get(position);
                String lishidizhi = cityEntity.getName();
                String lishisousuo = searchContentEt.getText().toString().trim();
                //按完搜索键后将当前查询的关键字保存起来，如果该关键字已经已经存在就不执行保存
                boolean hasData = hasData(searchContentEt.getText().toString().trim());
                if (!hasData) {
                    insertData(lishisousuo);
                }
                insertData_dizhi(lishidizhi);
                showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
            }
        });
        searchContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = searchContentEt.getText().toString().trim().toLowerCase();
                setSearchCityList(content);
            }
        });

        searchContentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput(searchContentEt.getWindowToken());
                    String content = searchContentEt.getText().toString().trim().toLowerCase();
                    setSearchCityList(content);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 检查数据库中是否已经有该条记录
     * @param tempName
     * @return
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 设置搜索数据展示
     * @param content
     */
    private void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            location_type_2.setVisibility(View.GONE);
            location_type_3.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchDataTv.setVisibility(View.GONE);
        } else {
            location_type_1.setVisibility(View.GONE);
            location_type_2.setVisibility(View.GONE);
            location_type_3.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                CityEntity cityEntity = curCityList.get(i);
                if (cityEntity.getName().contains(content) || cityEntity.getPinyin().contains(content)
                        || cityEntity.getFirst().contains(content)) {
                    searchCityList.add(cityEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchDataTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchDataTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }

    private void setExchange() {
        switch (locationCurrentType) {
            case LOCATION_TYPE1:
                location_type_1.setVisibility(View.GONE);
                location_type_2.setVisibility(View.VISIBLE);
                location_type_3.setVisibility(View.GONE);
                locationCurrentType = 2;
                hideSoftInput(searchContentEt.getWindowToken());
                searchContentEt.setCursorVisible(false);
                //searchContentEt.setFocusable(false);
                searchContentEt.clearFocus();
                break;
            case LOCATION_TYPE2:
                location_type_1.setVisibility(View.VISIBLE);
                location_type_2.setVisibility(View.GONE);
                location_type_3.setVisibility(View.GONE);
                locationCurrentType = 1;
                //searchContentEt.setFocusable(true);
                searchContentEt.requestFocus();
                break;
        }
    }

    /**
     * 设置沉浸式状态栏
     */
    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0 LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
            overlay.setText(key);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟让overlay为不可见
            handler.postDelayed(overlayThread, 700);
        }
    }

    /**
     * 总城市适配器
     */
    private class CityListAdapter extends BaseAdapter {

        private Context context;

        private List<CityEntity> totalCityList;
        private List<CityEntity> hotCityList;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        CityListAdapter(Context context,
                        List<CityEntity> totalCityList,
                        List<CityEntity> hotCityList) {
            this.context = context;
            this.totalCityList = totalCityList;
            this.hotCityList = hotCityList;
            inflater = LayoutInflater.from(context);

            alphaIndexer = new HashMap<>();

            for (int i = 0; i < totalCityList.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = totalCityList.get(i).getKey();

                String previewStr = (i - 1) >= 0 ? totalCityList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return totalCityList == null ? 0 : totalCityList.size();
        }

        @Override
        public Object getItem(int position) {
            return totalCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView curCityNameTv;
            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (viewType == 0) {//定位
                convertView = inflater.inflate(R.layout.location_select_city_location_item, null);

            } else if (viewType == 1){ //热门城市
                convertView = inflater.inflate(R.layout.location_recent_city_item, null);

            } else {
                if (null == convertView) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.location_city_list_item_layout, null);
                    ViewBinder.bind(holder, convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                CityEntity cityEntity = totalCityList.get(position);
                holder.cityKeyTv.setVisibility(View.VISIBLE);
                holder.cityKeyTv.setText(getAlpha(cityEntity.getKey()));
                holder.cityNameTv.setText(cityEntity.getName());

                if (position >= 1) {
                    CityEntity preCity = totalCityList.get(position - 1);
                    if (preCity.getKey().equals(cityEntity.getKey())) {
                        holder.cityKeyTv.setVisibility(View.GONE);
                    } else {
                        holder.cityKeyTv.setVisibility(View.VISIBLE);
                    }
                }
            }
            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    /**
     * 获得首字母
     * @param key
     * @return
     */
    private String getAlpha(String key) {
        if (key.equals("0")) {
            return "定位";
        } else if (key.equals("1")) {
            return "热门";
        } else {
            return key;
        }
    }

    private void showSetCityDialog(final String curCity, final String cityCode) {
        if (curCity.equals(curSelCity_string)) {
            ToastUtils.show("当前定位城市" + curCity);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否设置" + curCity + "为您的当前城市"); //设置内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //选中之后做你的方法
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 隐藏软件盘
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化汉语拼音首字母弹出提示框
     */
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {
        @Override
        public void onTouchingLetterChanged(String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                totalCityLv.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                //延迟让overlay为不可见
                handler.postDelayed(overlayThread, 700);
            }
        }
    }

    /**
     * 设置overlay不可见
     */
    private class OverlayThread implements Runnable{
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    private class SearchCityListAdapter extends BaseAdapter{
        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        SearchCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.location_city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = cityEntities.get(position);
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }


        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }
}
