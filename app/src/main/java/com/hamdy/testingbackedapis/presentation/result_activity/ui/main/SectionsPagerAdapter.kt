package com.hamdy.testingbackedapis.presentation.result_activity.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.hamdy.testingbackedapis.R
import com.hamdy.testingbackedapis.domain.model.RequestData

private val TAB_TITLES = arrayOf(
    R.string.status_code_tab,
    R.string.response_tab,
    R.string.header_tab
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val requestData: RequestData
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PlaceholderFragment.newInstance("Status Code: ${requestData.statusCode}")
            1 -> PlaceholderFragment.newInstance(requestData.response)
            else -> PlaceholderFragment.newInstance(requestData.headers)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}