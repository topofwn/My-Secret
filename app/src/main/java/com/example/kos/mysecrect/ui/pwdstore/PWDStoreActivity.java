package com.example.kos.mysecrect.ui.pwdstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.dialog.ConnectionDialog;
import com.example.kos.mysecrect.ui.dialog.ShowDataDialog;
import com.example.kos.mysecrect.ui.dialog.ShowDialogListener;
import com.example.kos.mysecrect.ui.pwdstore.adapter.PWDStoreAdapter;
import com.example.kos.mysecrect.utils.Injections;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import org.michaelbel.bottomsheet.BottomSheet;

import java.util.List;

public class PWDStoreActivity extends BaseActivity implements View.OnClickListener,ShowDialogListener{
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
                    dialog = new ShowDataDialog(PWDStoreActivity.this,mData,true);
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

                            EditData(PWDStoreActivity.this,mData);
                            break;
                        case 1:
                            new BottomDialog.Builder(PWDStoreActivity.this)
                                    .setTitle(getString(R.string.delete_automation))
                                    .setContent(getString(R.string.delete_warning))
                                    .setNegativeText(getString(R.string.no))
                                    .setPositiveText(getString(R.string.yes))
                                    .onNegative(BottomDialog::dismiss)
                                    .onPositive(bottomDialog -> {
                                        //todo remove group and show db
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
                //TODO create popup menu to choose edit or delete item

            }
        });
    }

    private void DeleteData(DataPWD mData) {

    }

    private void EditData(Context context, DataPWD mData) {
        //TODO created popup menu, handling action delete and edit
    }


    @Override
    protected void initData() {

    }


    @Override
    public void DismissDialog() {

    }

    @Override
    public void editData(DataPWD data) {

    }
}
