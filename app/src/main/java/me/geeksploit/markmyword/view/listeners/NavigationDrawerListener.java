package me.geeksploit.markmyword.view.listeners;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import me.geeksploit.markmyword.R;
import me.geeksploit.markmyword.presenter.MainPresenter;

public class NavigationDrawerListener implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private MainPresenter presenter;

    public NavigationDrawerListener(DrawerLayout drawer, MainPresenter presenter) {
        this.drawer = drawer;
        this.presenter = presenter;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // TODO: 24.06.2018 presenter interaction for menu items

        switch (id) {
            case R.id.nav_open_book:
                presenter.openBooks();
                break;
            case R.id.nav_parse_book:
                presenter.parseBook();
                break;
            case R.id.nav_statistics:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                throw new RuntimeException("No such menu item");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
