package com.serasiautoraya.slimobiledrivertracking.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.subfragment.CicoRealtimeSubFragment;
import com.serasiautoraya.slimobiledrivertracking.subfragment.CicoRequestSubFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class CicoRequestFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cico_request, container, false);
        assignView(view);

        return view;
    }

    void assignView(View view){
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_cico);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs_cico);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CicoRealtimeSubFragment(), "CiCo");
        adapter.addFragment(new CicoRequestSubFragment(), "Pengajuan");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_realtime_cico_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_request_cico_white_24dp);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
