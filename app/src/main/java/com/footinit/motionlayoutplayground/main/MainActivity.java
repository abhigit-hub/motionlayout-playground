package com.footinit.motionlayoutplayground.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.footinit.motionlayoutplayground.R;
import com.footinit.motionlayoutplayground.activity.Activity_01;
import com.footinit.motionlayoutplayground.activity.Activity_03;
import com.footinit.motionlayoutplayground.activity.Activity_04;
import com.footinit.motionlayoutplayground.activity.Activity_05;
import com.footinit.motionlayoutplayground.activity.Activity_06;
import com.footinit.motionlayoutplayground.activity.Activity_07;
import com.footinit.motionlayoutplayground.model.CustomModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MainGridAdapter.Callback {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainGridAdapter adapter;

    private String[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        colors = getResources().getStringArray(R.array.colors);

        setUpToolbar();

        setUpAdapter();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setUpAdapter() {
        adapter = new MainGridAdapter(this, prepareList());
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(adapter);
    }

    private List<CustomModel> prepareList() {
        List<CustomModel> list = new ArrayList<>();
        list.add(new CustomModel("1", "Move-All-Views (ImageFilter, PropertySet, KeyFrameSet)", getRandomColor()));
        list.add(new CustomModel("2", "Move-A-View (In Progress)", getRandomColor()));
        list.add(new CustomModel("3", "Move-A-View (Constraints in two layout files)", getRandomColor()));
        list.add(new CustomModel("4", "Move-A-View (Constraints in motion-scene file)", getRandomColor()));
        list.add(new CustomModel("5", "Move-A-View (ImageFilter)", getRandomColor()));
        list.add(new CustomModel("6", "Move-A-View (Toggle Alpha using PropertySet)", getRandomColor()));
        list.add(new CustomModel("7", "Move-A-View (Arc Motion using KeyFrame)", getRandomColor()));
/*        list.add(new CustomModel("5", "E", getRandomColor()));
        list.add(new CustomModel("6", "F", getRandomColor()));
        list.add(new CustomModel("7", "G", getRandomColor()));*/

        return list;
    }

    private int getRandomColor() {
        return Color.parseColor(colors[new Random().nextInt(colors.length)]);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onItemSelected(int id, String message) {
        switch (id) {
            case 1:
                startActivity(Activity_01.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "In Progress", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                startActivity(Activity_03.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
            case 4:
                startActivity(Activity_04.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
            case 5:
                startActivity(Activity_05.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
            case 6:
                startActivity(Activity_06.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
            case 7:
                startActivity(Activity_07.getStartIntent(
                        getApplicationContext(),
                        message));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.setCallback(this);
    }

    @Override
    protected void onPause() {
        if (adapter != null)
            adapter.removeCallback();
        super.onPause();
    }
}
