package com.kinzlstanislav.lastfmartists.viewtesting

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Looper
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kinzlstanislav.lastfmartists.viewtesting.helpers.InstrumentationTestsHelper
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.LooperMode
import org.robolectric.config.ConfigurationRegistry

/**
 * Created by Stanislav Kinzl, for easy fragment unit testing
 * experience while using Dagger 2 dependency injection framework.
 * */
@RunWith(AndroidJUnit4::class)
abstract class Robolectric4FragmentDaggerTest<FRAGMENT : Fragment> {

    private companion object {
        val TEST_ENVIRONMENT_THEME = R.style.Theme_AppCompat
    }

    private lateinit var activityScenario: ActivityScenario<FragmentDaggerTestActivity>

    private val targetContext: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    abstract var injector: (FRAGMENT) -> Unit

    abstract val subject: FRAGMENT

    /**
     * For verifying navigation, feel free to remove it if you are not using Nav. Component.
     * */
    protected var mockNavController = mockk<NavController>()

    @CallSuper
    @Before
    open fun setup() {
        Intents.init()
        InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext.setTheme(TEST_ENVIRONMENT_THEME)
    }

    @CallSuper
    @After
    open fun tearDown() {
        Intents.release()
    }

    protected fun getResString(@StringRes id: Int, args: Any? = null): String {
        return targetContext.resources.getString(id, args)
    }

    protected fun getResInteger(@IntegerRes id: Int): Int {
        return targetContext.resources.getInteger(id)
    }

    protected fun getResColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(targetContext, id)
    }

    protected fun getResDrawable(@DrawableRes id: Int): Drawable {
        return ContextCompat.getDrawable(targetContext, id)
                ?: throw KotlinNullPointerException("No such drawable found under that id")
    }

    protected fun getActivity(): FragmentActivity {
        var activity: FragmentActivity? = null
        activityScenario.onActivity { activity = it }
        if (activity == null) {
            throw KotlinNullPointerException("Activity is null, maybe you didn't call launchFragment()?")
        }
        return (activity as FragmentActivity)
    }

    protected fun launchFragment(fragment: Fragment, injector: (FRAGMENT) -> Unit) {
        activityScenario = launch(FragmentDaggerTestActivity::class.java).also {
            it.onActivity { activity ->
                (activity as FragmentDaggerTestActivity).apply {

                    setInjector(injector)
                    launchTestedFragment(fragment)

                    if (!isLooperModePaused()) {
                        initializeMockNavController()
                    } else {
                        executeAllPostedTasksOnMainLooper()
                        initializeMockNavController()
                    }
                }
            }
        }
    }

    protected fun verifyNavigation(id: Int, times: Int = 1) {
        verify(exactly = times) { mockNavController.navigate(id) }
    }

    private fun isLooperModePaused() = ConfigurationRegistry.get(LooperMode.Mode::class.java) == LooperMode.Mode.PAUSED

    private fun initializeMockNavController() {
        Navigation.setViewNavController(subject.requireView(), mockNavController)
    }

    /**
     * Executes all posted tasks on the main looper, such as animations.
     * */
    protected fun executeAllPostedTasksOnMainLooper() {
        if (isLooperModePaused()) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
        } else throw UninitializedPropertyAccessException("Idling on looper won't work if the mode in't " +
                "PAUSED (Realistic). Add @LooperMode(LooperMode.Mode.PAUSED) as an annotation to the test function.")
    }

    /**
     * Good for asserting whenever your view is in the hierarchy or not
     * */
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    protected fun printViewHierarchy() {
        InstrumentationTestsHelper.printViewHierarchy(getActivity())
    }
}