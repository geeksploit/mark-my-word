package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import me.geeksploit.markmyword.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Scheduler uiScheduler;
    private boolean isImageOn;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
    }

    public void switchImageVisibility(boolean isImageOn){
        this.isImageOn = isImageOn;
        getViewState().updateAdapters();
    }

    public boolean isImageOn() {
        return isImageOn;
    }
}
