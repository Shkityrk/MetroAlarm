package com.example.samsungschoolproject.view_adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.databinding.StantionItemBinding;
import com.example.samsungschoolproject.model.Station;

import java.util.ArrayList;
import java.util.List;

public class StationListAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<Station> stations= new ArrayList<>();
    private List<String> titles = new ArrayList<>();



    public StationListAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void Add(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

//
//    @NonNull
//    @Override
//    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.stantion_item,
//                parent,
//                false
//        );
//        return new StationViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final StationViewHolder holder, int position) {
//        holder.bind(stations.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return stations.size();
//    }




}