package me.geeksploit.markmyword.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SingleStateStrategy.class)
public interface MainView extends MvpView {
    void updateAdapters();
    void switchToCard(int cardPos);
    void chooseBookToParse();
    void openBooks();
}
