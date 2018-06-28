package me.geeksploit.markmyword.view.listeners;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import java.util.Arrays;

import me.geeksploit.markmyword.MainActivity;
import me.geeksploit.markmyword.R;

public class SwitchListener implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private View list;
    private View cards;

    public SwitchListener(Context context, View ... switchableViews) {
        this.context = context;
        for (View view : Arrays.asList(switchableViews)) {
            if (view.getId() == R.id.srl_card_refresh) cards = view;
            if (view.getId() == R.id.srl_list_refresh) list = view;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sw_view_type) {
            switchViewMode(isChecked);
        }
    }

    private void switchViewMode(boolean isCardList){
        if (isCardList && cards.getVisibility() == View.GONE) return;
        if (!isCardList && list.getVisibility() == View.GONE) return;
        list.setVisibility(isCardList ? View.GONE : View.VISIBLE);
        cards.setVisibility(isCardList ? View.VISIBLE : View.GONE);
        MainActivity activity = (MainActivity)context;
        activity.setListViewing(!isCardList);
        activity.updateAdapters();
    }
}
