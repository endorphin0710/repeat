package com.behemoth.repeat.stats.chapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.recyclerView.stats.RepeatDetailAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PercentageCorrectAnswersFragment extends Fragment {

    private static final String ARG_REPEATS = "repeats";

    private final ArrayList<Repeat> mArrayList;
    private RepeatDetailAdapter mAdapter;

    private boolean ascendingR = false;
    private boolean ascendingP = false;

    public PercentageCorrectAnswersFragment() {
        mArrayList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonRepeats = getArguments().getString(ARG_REPEATS);
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<Repeat>>(){}.getType();
            List<Repeat> repeats = gson.fromJson(jsonRepeats, userListType);

            for(int i = 0; i < repeats.size(); i++){
                Repeat r = repeats.get(i);
                if(!r.isFinished()) continue;

                Repeat repeat = new Repeat();
                repeat.setRepeatNumber(i+1);
                repeat.setPercent((int) Math.round((r.getScore()/(double)r.getProblemCount())*100));
                mArrayList.add(repeat);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_percentage_correct_answers, container, false);

        TextView tvRepeatNumber = rootView.findViewById(R.id.tv_question);
        tvRepeatNumber.setOnClickListener(v -> {
            reorderByRepeat(ascendingR);
            ascendingR = !ascendingR;
        });
        TextView tvPercent = rootView.findViewById(R.id.tv_mark);
        tvPercent.setOnClickListener(v -> {
            reorderByPercent(ascendingP);
            ascendingP = !ascendingP;
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.percent_recyclerview);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new RepeatDetailAdapter(getContext(), mArrayList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void reorderByRepeat(boolean ascending){
        if(ascending){
            Collections.sort(mArrayList, (a, b) -> a.getRepeatNumber() - b.getRepeatNumber());
        }else{
            Collections.sort(mArrayList, (a, b) -> b.getRepeatNumber() - a.getRepeatNumber());
        }
        mAdapter.notifyDataSetChanged();
    }

    private void reorderByPercent(boolean ascending){
        if(ascending){
            Collections.sort(mArrayList, (a, b) -> {
                if(a.getPercent() == b.getPercent()){
                    return a.getRepeatNumber() - b.getRepeatNumber();
                }else{
                    return b.getPercent() - a.getPercent();
                }
            });
        }else{
            Collections.sort(mArrayList, (a, b) -> {
                if(b.getPercent() == a.getPercent()){
                    return a.getRepeatNumber() - b.getRepeatNumber();
                }else{
                    return a.getPercent() - b.getPercent();
                }
            });
        }
        mAdapter.notifyDataSetChanged();
    }

}