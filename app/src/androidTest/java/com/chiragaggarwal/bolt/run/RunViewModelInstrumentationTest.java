package com.chiragaggarwal.bolt.run;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RunViewModelInstrumentationTest {
    private Resources resources;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        resources = context.getResources();
    }

    @Test
    public void testThatItKnowsHowToFormatDistanceInKilometersCorrectly() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        runViewModel.setLocation(userLocation);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        runViewModel.setLocation(secondUserLocation);
        Assert.assertEquals("0.01", runViewModel.getDistance());
    }

    @Test
    public void testThatItKnowsTheNotificationSubTextWhenElapsedTimeAndDistanceInKilometersIsPresent() {
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setElapsedTime(new ElapsedTime(123));
        runViewModel.setLocation(userLocation);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        runViewModel.setLocation(secondUserLocation);
        runViewModel.setElapsedTime(new ElapsedTime(128));
        Assert.assertEquals("Elapsed Time: 00:02:08\nDistance: 0.01", runViewModel.getNotificationSubText());
    }

    @Test
    public void testThatDistanceIsZeroKilometersWhenRunningIsStopped() {
        RunViewModel runViewModel = new RunViewModel(resources);
        UserLocation userLocation = new UserLocation(12.9611d, 77.6472d, true, 19, true, 11.98765f);
        runViewModel.setLocation(userLocation);
        UserLocation secondUserLocation = new UserLocation(12.9612d, 77.6473d, true, 19, true, 11.98765f);
        runViewModel.setLocation(secondUserLocation);
        runViewModel.setRunningAsStopped();
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }

    @Test
    public void testThatDistanceIsZeroByDefault() {
        RunViewModel runViewModel = new RunViewModel(resources);
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }

    @Test
    public void testThatVeryMinorDistancesTravelledAreNotTakenIntoConsiderationWhenShowingTotalDistance() {
        RunViewModel runViewModel = new RunViewModel(resources);
        runViewModel.setLocation(new UserLocation(-6.1805294D, 106.8088036D, true, 1, true, 10));
        runViewModel.setLocation(new UserLocation(-6.1805228D, 106.8088433D, true, 1, true, 10));
        runViewModel.setLocation(new UserLocation(-6.1805211D, 106.8088532D, true, 1, true, 10));
        Assert.assertEquals("0.00", runViewModel.getDistance());
    }
}
