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

import com.android.volley.RequestQueue;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralOrderSingleList;
import com.serasiautoraya.slimobiledrivertracking.adapter.GeneralSingleList;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.model.ModelActivityJourney;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.serasiautoraya.slimobiledrivertracking.subfragment.ActiveOrdersSubFragment;
import com.serasiautoraya.slimobiledrivertracking.subfragment.PlanOrdersSubFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class PlanActiveOrdersFragment extends Fragment {

    @BindView(R.id.tabs_planactive_orders) TabLayout mTabLayout;
    @BindView(R.id.viewpager_planactive_orders) ViewPager mViewPager;

    private RequestQueue mqueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planactive_orders, container, false);
        ButterKnife.bind(this, view);
        mqueue = VolleyUtil.getRequestQueue();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        refreshRecyclerView();
        return view;
    }

    public void refreshRecyclerView() {
        HelperBridge.sActiveOrdersList = new ArrayList<>();
        HelperBridge.sPlanOrdersList= new ArrayList<>();

        /*
        * TODO retrieve data order (active & outstanding), insert into Helper class, use it from active/plan subfragment
        *
        * */

        for (int i = 0; i < 12; i++) {
            ModelActivityJourney modelActivityJourneyActive = new ModelActivityJourney();
            modelActivityJourneyActive.setCode("Order Active-"+i);
            modelActivityJourneyActive.setInformation("Information summary-"+i);
            modelActivityJourneyActive.setStatus("Status-"+i);
            modelActivityJourneyActive.setId(i+"-id-active");
            GeneralSingleList activeOrder = new GeneralOrderSingleList(modelActivityJourneyActive);

            ModelActivityJourney modelActivityJourneyPlan = new ModelActivityJourney();
            modelActivityJourneyPlan.setCode("Order Plan-"+i);
            modelActivityJourneyPlan.setInformation("Information summary-"+i);
            modelActivityJourneyPlan.setId(i+"-id-plan");
            if(i%2 == 0){
                modelActivityJourneyPlan.setStatus(HelperKey.WAITING_ACK_CODE);
            }else {
                modelActivityJourneyPlan.setStatus("Status-"+i);
            }
            GeneralSingleList planOrder = new GeneralOrderSingleList(modelActivityJourneyPlan);

            HelperBridge.sActiveOrdersList.add(activeOrder);
            HelperBridge.sPlanOrdersList.add(planOrder);
        }

    }

    private void setupViewPager(final ViewPager viewPager) {
        PlanActiveOrdersFragment.ViewPagerAdapter adapter = new PlanActiveOrdersFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveOrdersSubFragment(), "Active Order");
        adapter.addFragment(new PlanOrdersSubFragment(), "Plan Order");
        viewPager.setAdapter(adapter);
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
