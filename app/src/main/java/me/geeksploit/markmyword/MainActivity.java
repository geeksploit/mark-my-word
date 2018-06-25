package me.geeksploit.markmyword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.geeksploit.markmyword.presenter.MainPresenter;
import me.geeksploit.markmyword.view.MainView;
import me.geeksploit.markmyword.view.adapters.CardRvAdapter;
import me.geeksploit.markmyword.view.adapters.ListRvAdapter;
import me.geeksploit.markmyword.view.listeners.NavigationDrawerListener;
import me.geeksploit.markmyword.view.listeners.SwipeRefreshListener;
import me.geeksploit.markmyword.view.listeners.SwitchListener;

public class MainActivity extends MvpAppCompatActivity
        implements MainView {

    private Boolean isList = true;
    private ListRvAdapter listAdapter;
    private CardRvAdapter cardAdapter;
    private SnapHelper snapHelper;

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

    @Inject
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        App.getInstance().getAppComponent().inject(this);
        initNavDrawer();
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
        cardAdapter = new CardRvAdapter(presenter);
        App.getInstance().getAppComponent().inject(cardAdapter);
        cardLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvWordCards.setLayoutManager(cardLayoutManager);
        rvWordCards.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        rvWordCards.setAdapter(cardAdapter);
        snapHelper.attachToRecyclerView(rvWordCards);
    }

    private void initList() {
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(this);
        listAdapter = new ListRvAdapter(presenter);
        App.getInstance().getAppComponent().inject(listAdapter);
        listLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWordLists.setLayoutManager(listLayoutManager);
        rvWordLists.setAdapter(listAdapter);
        snapHelper.attachToRecyclerView(rvWordLists);
    }

    private void initUi() {
        snapHelper = new PagerSnapHelper();
        SwipeRefreshListener swipeRefreshListener = new SwipeRefreshListener(presenter, swipeRefreshCard, swipeRefreshList);
        SwitchListener switchListener = new SwitchListener(this, swipeRefreshCard, swipeRefreshList);
        swipeRefreshCard.setOnRefreshListener(swipeRefreshListener);
        swipeRefreshList.setOnRefreshListener(swipeRefreshListener);
        switchWordViewType.setOnCheckedChangeListener(switchListener);
    }

    private void initNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationDrawerListener(drawer, presenter));
    }

    @OnClick({R.id.cb_image_switcher, R.id.fab})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.cb_image_switcher:
                presenter.switchImageVisibility(checkBoxWordImageView.isChecked());
                updateAdapters();
                break;
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    public void switchToCard(int cardPos) {
        switchWordViewType.setChecked(true);
        rvWordCards.smoothScrollToPosition(cardPos);
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (isList) swipeRefreshCard.setVisibility(View.GONE);
        }
    }

    public void setListViewing(Boolean isViewing) {
        isList = isViewing;
    }
}