package com.kinzlstanislav.lastfmartists.viewtesting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector

/**
 * The container activity for tests using FragmentDaggerTest
 */
class FragmentDaggerTestActivity : FragmentActivity(), HasSupportFragmentInjector {

    companion object {
        const val DAGGER_TEST_FRAGMENT_TAG = "TEST FRAGMENT"
    }

    lateinit var injector: AndroidInjector<Fragment>

    override fun supportFragmentInjector() = injector

    internal fun <T> setInjector(injector: (T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        this.injector = AndroidInjector {
            injector(it as T)
        }
    }

    internal fun launchTestedFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment, DAGGER_TEST_FRAGMENT_TAG)
                .commit()
    }
}