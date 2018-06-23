package me.geeksploit.markmyword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.geeksploit.markmyword.presenter.MainPresenter;
import me.geeksploit.markmyword.view.MainView;
import me.geeksploit.markmyword.view.adapters.WordCardRvAdapter;
import me.geeksploit.markmyword.view.adapters.WordListRvAdapter;

public class MainActivity extends MvpAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        SwitchCompat.OnCheckedChangeListener, MainView {

    private Boolean isList = true;
    private WordListRvAdapter listAdapter;
    private WordCardRvAdapter cardAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.sw_view_type) SwitchCompat switchWordViewType;
    @BindView(R.id.cb_image_switcher) CheckBox checkBoxWordImageView;
    @BindView(R.id.srl_card_refresh) SwipeRefreshLayout swipeRefreshCard;
    @BindView(R.id.srl_list_refresh) SwipeRefreshLayout swipeRefreshList;
    @BindView(R.id.rv_cards) RecyclerView rvWordCards;
    @BindView(R.id.rv_list) RecyclerView rvWordLists;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initUi();
        initList();
        initCards();
    }

    @ProvidePresenter
    MainPresenter providePresenter(){
        MainPresenter presenter = new MainPresenter(AndroidSchedulers.mainThread());

        return presenter;
    }

    private void initCards() {
        LinearLayoutManager cardLayoutManager = new LinearLayoutManager(this);
        cardAdapter = new WordCardRvAdapter(presenter);
        App.getInstance().getAppComponent().inject(cardAdapter);
        cardLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWordCards.setLayoutManager(cardLayoutManager);
        rvWordCards.setAdapter(cardAdapter);
    }

    private void initList() {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(this);
        listAdapter = new WordListRvAdapter(presenter);
        App.getInstance().getAppComponent().inject(listAdapter);
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWordLists.setLayoutManager(listLayoutManager);
        rvWordLists.setAdapter(listAdapter);
    }

    private void initUi() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshCard.setOnRefreshListener(this);
        swipeRefreshList.setOnRefreshListener(this);
        switchWordViewType.setOnCheckedChangeListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @OnClick({R.id.cb_image_switcher})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.cb_image_switcher:
                presenter.switchImageVisibility(checkBoxWordImageView.isChecked());
                updateAdapters();
                break;
            default:
                Toast.makeText(this, "No such button", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void updateAdapters(){
        if (isList) {
            listAdapter.notifyDataSetChanged();
        } else {
            cardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        createSearchMenu(menu);

        return true;
    }

    private void createSearchMenu(@NonNull Menu menu) {
        final MenuItem miSearch = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) miSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,
                        "Search submit: " + query, Toast.LENGTH_SHORT).show();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                miSearch.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        if (swipeRefreshCard.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "Cards refreshed", Toast.LENGTH_SHORT).show();
            swipeRefreshCard.setRefreshing(false);
        }
        if (swipeRefreshList.getVisibility() == View.VISIBLE) {

            Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show();
            swipeRefreshList.setRefreshing(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sw_view_type) {
            if (isChecked && swipeRefreshCard.getVisibility() == View.GONE) {
                swipeRefreshList.setVisibility(View.GONE);
                swipeRefreshCard.setVisibility(View.VISIBLE);
                isList = false;
                updateAdapters();
            }

            if (!isChecked && swipeRefreshList.getVisibility() == View.GONE) {
                swipeRefreshList.setVisibility(View.VISIBLE);
                swipeRefreshCard.setVisibility(View.GONE);
                isList = true;
                updateAdapters();
            }
        }
    }
}