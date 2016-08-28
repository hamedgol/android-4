package com.i906.mpt.prayer;

import java.util.List;

/**
 * @author Noorzaini Ilhami
 */
public interface PrayerContext {

    Prayer getCurrentPrayer();

    Prayer getNextPrayer();

    List<Prayer> getCurrentPrayerList();

    List<Prayer> getNextPrayerList();

    String getLocationName();

    List<Integer> getHijriDate();

    ViewSettings getViewSettings();

    interface ViewSettings {
        boolean isCurrentPrayerHighlightMode();

        boolean isDhuhaEnabled();

        boolean isImsakEnabled();

        boolean isHijriDateEnabled();

        boolean isMasihiDateEnabled();
    }
}
