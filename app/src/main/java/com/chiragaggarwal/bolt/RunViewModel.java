package com.chiragaggarwal.bolt;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chiragaggarwal.bolt.location.Location;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunViewModel extends BaseObservable {
    private static final String COLON = ":";
    private static final String FORMAT_LEADING_ZERO = "%02d";
    private static final String TIME_DEFAULT = "00:00:00";

    private ElapsedTime elapsedTime;
    private boolean isRunning;
    private Resources resources;

    public RunViewModel(Resources resources) {
        this.resources = resources;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getElapsedTime() {
        if (elapsedTime == null) return TIME_DEFAULT;
        return formatWithLeadingZero(elapsedTime.hours()) + COLON + formatWithLeadingZero(elapsedTime.minutes()) + COLON + formatWithLeadingZero(elapsedTime.seconds());
    }

    public void setLocation(Location location) {

    }

    private String formatWithLeadingZero(int timeElement) {
        return String.format(FORMAT_LEADING_ZERO, timeElement);
    }

    public void setRunningAsStarted() {
        isRunning = true;
        notifyPropertyChanged(BR.toggleRunButtonText);
    }

    public void setRunningAsStopped() {
        isRunning = false;
        elapsedTime = null;
        notifyPropertyChanged(BR.toggleRunButtonText);
        notifyPropertyChanged(BR.elapsedTime);
    }

    @Bindable
    public String getToggleRunButtonText() {
        return (isRunning) ? resources.getString(R.string.stop_activity) : resources.getString(R.string.start_activity);
    }
}
