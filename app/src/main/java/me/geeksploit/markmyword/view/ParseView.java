package me.geeksploit.markmyword.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SingleStateStrategy.class)
public interface ParseView extends MvpView {
    void parseStart();
    void parseProgress(int count);
    void parseFinish();
}
