package com.example.kos.mysecrect.ui.pwdstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.dialog.EditDataDialog;
import com.example.kos.mysecrect.ui.dialog.ShowDataDialog;
import com.example.kos.mysecrect.ui.pwdstore.adapter.PWDStoreAdapter;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import org.michaelbel.bottomsheet.BottomSheet;

import java.util.List;

public class PWDStoreActivity extends BaseActivity implements View.OnClickListener{
    private PWDStorePresenter mPresenter = new PWDStorePresenter();
    private ListView listData;
    private PWDStoreAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_store);
        mPresenter = new PWDStorePresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(PWDStoreActivity.this));

        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        List<DataPWD> lData = mPresenter.getListData();
        listData = findViewById(R.id.lv_data);
        adapter = new PWDStoreAdapter(getApplicationContext(),R.layout.item_data,lData);
        listData.setAdapter(adapter);
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataPWD mData = adapter.getItem(position);

                ShowDataDialog dialog = null;
                try {
                    dialog = new ShowDataDialog(PWDStoreActivity.this,mData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.show();

            }
        });
        listData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheet.Builder builder = new BottomSheet.Builder(PWDStoreActivity.this);
                builder.setItems(getResources().getStringArray(R.array.automation_action), (dialogInterface, i2) -> {
                    DataPWD mData = adapter.getItem(position);
                    switch (i2) {
                        case 0:

                            try {
                                EditData(mData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            new BottomDialog.Builder(PWDStoreActivity.this)
                                    .setTitle(getString(R.string.delete_automation))
                                    .setContent(getString(R.string.delete_warning))
                                    .setNegativeText(getString(R.string.no))
                                    .setPositiveText(getString(R.string.yes))
                                    .onNegative(BottomDialog::dismiss)
                                    .onPositive(bottomDialog -> {

                                        DeleteData(mData);
                                        bottomDialog.dismiss();

                                    })
                                    .show();
                            break;
                        default:break;

                    }
                });
                builder.setContentType(BottomSheet.LIST);
                builder.setDarkTheme(true);
                builder.show();
                return true;


            }
        });
    }

    private void DeleteData(DataPWD mData) {
        List<DataPWD> mList = mPresenter.getListData();
        for(int i=0; i<mList.size();i++){
            if(mList.get(i).getFieldName().equals(mData.getFieldName())){
                mList.remove(i);
                break;
            }
        }
        mPresenter.setListData(mList);
        UserD usr = mPresenter.getUser();
        usr.setListData(mList);
        FirebaseUtils.addNewField(usr);
    }

    private void EditData( DataPWD mData) throws Exception {
        //TODO created popup menu, handling action delete and edit
        EditDataDialog dialog = new EditDataDialog(PWDStoreActivity.this,mData, (DataPWD newData) ->{
            List<DataPWD> mList = mPresenter.getListData();
            for (int i = 0; i<mList.size();i++){
                if(mList.get(i).getMyKeySpec().equals(newData.getMyKeySpec())){
                    mList.get(i).setEncrytKey(newData.getEncrytKey());
                    mList.get(i).setFieldName(newData.getFieldName());
                    break;
                }
            }
            mPresenter.setListData(mList);
            UserD usr = mPresenter.getUser();
            usr.setListData(mList);
            FirebaseUtils.addNewField(usr);
        });
        dialog.show();
    }


    @Override
    protected void initData() {

    }


    @Override
    public void DismissDialog() {

    }


}
