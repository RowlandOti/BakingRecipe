package com.rowland.bakingapp;

import android.support.test.rule.ActivityTestRule;

import com.rowland.bakingapp.ui.activities.MainActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by Rowland on 7/23/2017.
 */

public class TabletCorrectLayoutTest extends ActivityTestRule<MainActivity> {


    MainActivity mActivity;

    public TabletCorrectLayoutTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        mActivity = getActivity();
        // other setup
    }

    @Test
    public void testDetailContainerIdExists() {
        assertTrue(mActivity.findViewById(R.id.detail_fragment_container) != null);
        // other test
    }
}
