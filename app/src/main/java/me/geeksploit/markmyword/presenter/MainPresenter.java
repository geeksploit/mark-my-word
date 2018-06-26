package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import me.geeksploit.markmyword.model.WordModel;
import me.geeksploit.markmyword.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Scheduler uiScheduler;
    private boolean isImageOn;
    private List<WordModel> wordsList;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        //stub collection
        this.wordsList = new ArrayList<>();
        wordsList.add(new WordModel("User", "Пользователь",
                "Всякое разное описание",
                "https://www.shareicon.net/download/2016/05/29/772558_user_512x512.png"));
        wordsList.add(new WordModel("Computer", "Компьютер",
                "Всякое разное описание",
                "https://im0-tub-ru.yandex.net/i?id=0c06c92ffc61bdee7a4cca1ec9d8c4f0&n=13"));
        wordsList.add(new WordModel("Synchronization", "Синхронизация",
                "[sɪŋkrənaɪˈzeɪʃn] сущ\n синхронизация ж, синхронность ж\n (sync, synchronism)\n\n full synchronization – полная синхронизация" +
                        "выполнение синхронизации\n\n synchronization [sɪŋkrənaɪˈzeɪʃn] прич\n синхронизированный\n (sync)" +
                        "синхронизирующий\n (synchronizing)\n\n synchronization [sɪŋkrənaɪˈzeɪʃn] прил\n  синхронизационный\n\n" +
                        "synchronisation [sɪŋkrənaɪˈzeɪʃn] сущ\n синхронизация ж, синхронность ж\n (synchronization, synchronism)" +
                        "[sɪŋkrənaɪˈzeɪʃn] сущ\n синхронизация ж, синхронность ж\n (sync, synchronism)\n\n full synchronization – полная синхронизация" +
                        "выполнение синхронизации\n\n synchronization [sɪŋkrənaɪˈzeɪʃn] прич\n синхронизированный\n (sync)" +
                        "синхронизирующий\n (synchronizing)\n\n synchronization [sɪŋkrənaɪˈzeɪʃn] прил\n  синхронизационный\n\n" +
                        "synchronisation [sɪŋkrənaɪˈzeɪʃn] сущ\n синхронизация ж, синхронность ж\n (synchronization, synchronism) EOF\n\n\n\n\n\n\n\n\n\n\n\n\n",
                "http://cs5-1.4pda.to/2444415.png"));
    }

    public void switchImageVisibility(boolean isImageOn) {
        this.isImageOn = isImageOn;
        getViewState().updateAdapters();
    }

    public void switchToCard(int wordPos) {
        getViewState().switchToCard(wordPos);
    }

    public boolean isImageOn() {
        return isImageOn;
    }

    public List<WordModel> getWords() {
        return wordsList;
    }
}
