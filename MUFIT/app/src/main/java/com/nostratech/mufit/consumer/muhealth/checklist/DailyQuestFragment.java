package com.nostratech.mufit.consumer.muhealth.checklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nostratech.mufit.consumer.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyQuestFragment extends Fragment {

    public static final String KEY_FIRST_QUEST = "firstQuest";
    public static final String KEY_SECOND_QUEST = "secondQuest";
    public static final String KEY_THIRD_QUEST = "thirdQuest";
    public static final String KEY_FOURTH_QUEST = "fourthQuest";
    public static final String KEY_FIFTH_QUEST = "fifthQuest";

    private DailyQuest[] quests;

    private Unbinder unbinder;

    @BindView(R.id.checklist_one)
    DailyQuestView firstQuest;
    @BindView(R.id.checklist_two)
    DailyQuestView secondQuest;
    @BindView(R.id.checklist_three)
    DailyQuestView thirdQuest;
    @BindView(R.id.checklist_four)
    DailyQuestView fourthQuest;
    @BindView(R.id.checklist_five)
    DailyQuestView fifthQuest;

    public static DailyQuestFragment newInstance(DailyQuest[] dailyQuests) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_FIRST_QUEST, dailyQuests[0]);
        args.putSerializable(KEY_SECOND_QUEST, dailyQuests[1]);
        args.putSerializable(KEY_THIRD_QUEST, dailyQuests[2]);
        args.putSerializable(KEY_FOURTH_QUEST, dailyQuests[3]);
        args.putSerializable(KEY_FIFTH_QUEST, dailyQuests[4]);

        DailyQuestFragment fragment = new DailyQuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_quest, null ,false);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();

        Objects.requireNonNull(args, "Arguments passed into DailyQuestFragment is null. Please check your data source");
        quests = new DailyQuest[ChecklistFragmentAdapter.ITEM_PER_PAGE];
        quests[0] = (DailyQuest) args.getSerializable(KEY_FIRST_QUEST);
        quests[1] = (DailyQuest) args.getSerializable(KEY_SECOND_QUEST);
        quests[2] = (DailyQuest) args.getSerializable(KEY_THIRD_QUEST);
        quests[3] = (DailyQuest) args.getSerializable(KEY_FOURTH_QUEST);
        quests[4] = (DailyQuest) args.getSerializable(KEY_FIFTH_QUEST);
//
        setQuestResources(firstQuest, quests[0]);
        setQuestResources(secondQuest, quests[1]);
        setQuestResources(thirdQuest, quests[2]);
        setQuestResources(fourthQuest, quests[3]);
        setQuestResources(fifthQuest, quests[4]);

        return view;
    }

    /**
     * Apply the appropriate settings to {@link DailyQuestView}
     *
     * Note that when questModel is null, view is set to INVISIBLE not GONE,
     * due to the fact that views in DailyQuestView are laid out using ConstraintLayout using a horizontal chain
     * Setting the Views as GONE will cause them to be spread out even wider
     * @param view - the {@link DailyQuestView} object
     * @param questModel - the appropriate model for the view
     */
    private void setQuestResources(DailyQuestView view, DailyQuest questModel){
        if(questModel == null) {
            view.setVisibility(View.INVISIBLE);
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setIconImage(questModel.getImageResId());
        view.setDoneStatus(questModel.isDone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
