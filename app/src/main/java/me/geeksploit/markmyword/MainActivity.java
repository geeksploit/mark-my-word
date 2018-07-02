package me.geeksploit.markmyword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import me.geeksploit.markmyword.model.entity.prefs.MainPrefsEntity;
import me.geeksploit.markmyword.presenter.MainPresenter;
import me.geeksploit.markmyword.utils.IPrefsController;
import me.geeksploit.markmyword.utils.PrefsFactory;
import me.geeksploit.markmyword.utils.permissions.CheckPermission;
import me.geeksploit.markmyword.view.IntentActions;
import me.geeksploit.markmyword.view.MainView;
import me.geeksploit.markmyword.view.adapters.CardRvAdapter;
import me.geeksploit.markmyword.view.adapters.ListRvAdapter;
import me.geeksploit.markmyword.view.listeners.NavigationDrawerListener;
import me.geeksploit.markmyword.view.listeners.SwipeRefreshListener;
import me.geeksploit.markmyword.view.listeners.SwitchListener;

public class MainActivity extends MvpAppCompatActivity
        implements MainView {

    private static final int FILE_CHOOSER_REQUEST = 1;
    private IPrefsController prefsController;
    private MainPrefsEntity mainPrefs;
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
        mainPrefs = new MainPrefsEntity(true, false);
        prefsController = PrefsFactory.getPrefs(this);
        initNavDrawer();
        initUi();
        initList();
        initCards();
        loadPreferences();
        CheckPermission.externalStorage(this);
    }

    private void loadPreferences() {
        mainPrefs = (MainPrefsEntity) prefsController.restoreUI();
        if (!mainPrefs.isList()) switchWordViewType.setChecked(!mainPrefs.isList());
        if (mainPrefs.isImageDisplayed()) {
            checkBoxWordImageView.setChecked(mainPrefs.isImageDisplayed());
            switchImageDisplayed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Pair<Boolean, Boolean> result = CheckPermission.onRequestRWResult(requestCode, permissions, grantResults);
        if (result.first) Toast.makeText(this, "Read granted", Toast.LENGTH_LONG).show();
        if (result.second) Toast.makeText(this, "Write granted", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @ProvidePresenter
    MainPresenter providePresenter(){
        MainPresenter presenter = new MainPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
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

    @OnClick(R.id.cb_image_switcher)
    public void OnClickSwitchMode(View view) {
                switchImageDisplayed();
    }
    @OnClick(R.id.fab)
    public void OnClickFab(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
    }

    private void switchImageDisplayed() {
        boolean isChecked = checkBoxWordImageView.isChecked();
        mainPrefs.setImageDisplayed(isChecked);
        presenter.switchImageVisibility(isChecked);
        updateAdapters();
    }

    @Override
    public void chooseBookToParse() {
        Intent fileChooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileChooserIntent.setType("*/*");
        fileChooserIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(fileChooserIntent, FILE_CHOOSER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != FILE_CHOOSER_REQUEST || resultCode != RESULT_OK) return;
        startActivity(new Intent(IntentActions.PARSE_BOOK, data.getData(), this, ParseActivity.class));
    }

    @Override
    public void updateAdapters(){
        if (mainPrefs.isList()) {
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
    protected void onPause() {
        super.onPause();
        prefsController.saveUIState(mainPrefs);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (mainPrefs.isList()) {
                swipeRefreshCard.setVisibility(View.GONE);
            } else {
                swipeRefreshList.setVisibility(View.GONE);
            }
        }
    }

    public void setListViewing(Boolean isViewing) {
        mainPrefs.setList(isViewing);
    }
}