package com.behemoth.repeat.stats.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.recyclerView.stats.ChapterStatsAdapter;

import java.util.ArrayList;

public class ChapterListFragment extends Fragment {

    private static final String CHAPTER_NUMBER = "chapter_count";

    private RecyclerView.Adapter mAdapter;
    private ArrayList<Integer> mArrayList;

    private int chapterCount;

    public ChapterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chapterCount = getArguments().getInt(CHAPTER_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_chapter_list, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.chapter_stats_recyclerview);

        LinearLayoutManager mLinearlayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearlayoutManager);

        mArrayList = new ArrayList<>();
        for(int i = 1; i <= chapterCount; i++){
            mArrayList.add(i);
        }
        mAdapter = new ChapterStatsAdapter(getContext(), mArrayList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        return rootView;
    }
}