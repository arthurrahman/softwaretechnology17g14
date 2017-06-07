package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.Tapper;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ListViewInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void test_useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.awesomeinc.awesomeplayer", appContext.getPackageName());
    }

    @Test
    public void test_ListViewExists() throws Exception {
        assertNotEquals(null, onView(withId(R.id.lst_tracklist)) );
    }

    @Test
    public void test_ListViewScroll() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());
        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));
        onView(withId(R.id.content_main)).perform(swipeUp());

    }

    @Test
    public void test_MenuSongs() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());
        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));
    }

    @Test
    public void test_MenuPlaylist() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Playlists")).perform(click());
    }

    @Test
    public void test_ListViewSongNameClick() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.content_main)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER_LEFT, Press.FINGER));

    }

    @Test
    public void test_SettingsButton() throws Exception {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Settings")).perform(click());
    }

    @Test
    public void test_MenuBackButton() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withId(R.id.content_main)).perform(pressBack());
    }

    @Test
    public void test_MenuBackButtonFromSong() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());
        onView(withId(R.id.content_main)).perform(pressBack());
    }

    @Test
    public void test_ClickSearchBar() throws Exception {
        onView(withId(R.id.action_search)).perform(click());
    }



    @Test
    public void test_SearchBarTextSubmit() throws Exception {
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        //onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Test!"));
        onView(withText("Test!")).check(matches(isDisplayed()));
        onView(withId(R.id.action_search)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
    }

    @Test
    public void test_SearchBarText() throws Exception {
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Test!"));
        onView(withText("Test!")).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
    }

    @Test
    public void test_SearchBarClearText() throws Exception {
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Test!"));
        onView(withText("Test!")).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Test!!!"));
        onView(withText("Test!!!")).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
    }

    /*@Test
    public void test_openSongThenSong() throws Exception {

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));
    }*/

    @Test
    public void test_OpenSongThenSearch() throws Exception {

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Test!"));
        onView(withText("Test!")).check(matches(isDisplayed()));
        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        onView(withId(android.support.design.R.id.search_src_text)).perform(closeSoftKeyboard());
    }

    @Test
    public void test_OpenSongThenSearchEmptyText() throws Exception {

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(100);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        // clear search selection
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(500);
        onView(withText("Search by title")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals(""));

        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(""));

        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        onView(withId(android.support.design.R.id.search_src_text)).perform(closeSoftKeyboard());
    }

    @Test
    public void test_OpenSongThenSearchThenDelete() throws Exception {

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(200);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Aa"));
        onView(withText("Aa")).check(matches(isDisplayed()));
        onView(withId(R.id.action_search)).perform(pressKey(KeyEvent.KEYCODE_DEL));
        onView(withText("A")).check(matches(isDisplayed()));

        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        onView(withId(android.support.design.R.id.search_src_text)).perform(closeSoftKeyboard());
    }

    @Test
    public void test_OpenSongThenSearchAllThenDelete() throws Exception {

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(200);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        // clear search selection
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(500);
        onView(withText("Search by title")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals(""));

        onView(withId(R.id.action_search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Aa"));
        onView(withText("Aa")).check(matches(isDisplayed()));
        onView(withId(R.id.action_search)).perform(pressKey(KeyEvent.KEYCODE_DEL));
        onView(withText("A")).check(matches(isDisplayed()));

        onView(withId(android.support.design.R.id.search_src_text)).perform(clearText());
        onView(withId(android.support.design.R.id.search_src_text)).perform(closeSoftKeyboard());
    }

    @Test
    public void test_searchSelectionMenu() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(500);
        onView(withText("Songs")).perform(click());
        Thread.sleep(500);
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(500);
        onView(withText("Search by title")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals(""));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(100);
        onView(withText("Search by title")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(100);
        onView(withText("Search by album")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("AB"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(100);
        onView(withText("Search by album")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(100);
        onView(withText("Search by artist")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("AC"));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Thread.sleep(100);
        onView(withText("Search by artist")).perform(click());
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().getSearchSelection().equals("A"));
    }

    /*
        @Test
        public void test_BesideSettingsButton() throws Exception {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText("AwesomePlayer")).perform(click());

        }



        @Test
        public void test_SimpleBackButton() throws Exception {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText("AwesomePlayer")).perform(pressBack());
        }
    */
    public static ViewAction waitId(final Matcher<View> viewMatcher, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withId(R.id.content_main);
            }

            @Override
            public String getDescription() {
                return "wait for a specific view  during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

}
