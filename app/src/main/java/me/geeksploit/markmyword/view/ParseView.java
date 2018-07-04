package me.geeksploit.markmyword.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.geeksploit.markmyword.model.entity.ParseProgress;

@StateStrategyType(value = SingleStateStrategy.class)
public interface ParseView extends MvpView {
    void parseStart();
    void parseProgress(ParseProgress progress);
    void parseFinish();
    void setBookTitle(ParseProgress progress);
    void setUniqueWords(ParseProgress progress);
}
