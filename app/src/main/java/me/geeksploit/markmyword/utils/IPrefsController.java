package me.geeksploit.markmyword.utils;

import me.geeksploit.markmyword.model.entity.prefs.PrefsMarker;

public interface IPrefsController {
    PrefsMarker restoreUI();
    void saveUIState(PrefsMarker prefsEntity);
}
