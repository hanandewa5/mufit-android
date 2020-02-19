package com.nostratech.mufit.consumer.muhealth.checklist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChecklistFragmentAdapter extends FragmentStateAdapter {

    public static final int ITEM_PER_PAGE = 5;

    private List<DailyQuest[]> dailyQuests;

    public ChecklistFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        dailyQuests = new ArrayList<>();
    }

    /**
     * Each fragment can only contain ITEM_PER_PAGE quests,
     * so we need to organize the models in such a way to reflect this.
     *
     * Pseudocode is as follows:
     *
     * CREATE array with a size of 5, called tempArray;
     * FOR every element in dailyQuests
     *  DO put element into tempArray at index of "iteration modulus ITEM_PER_PAGE" (0..4)
     *  IF index 5 of tempArray is reached
     *      THEN add tempArray to Collection dailyQuests
     *      AND initialize new array for tempArray
     *  END IF
     * END FOR
     *
     * @param dailyQuests - list of all daily quests
     */
    public void insertData(List<DailyQuest> dailyQuests){

        //Initialize array with a size of ITEM_PER_PAGE
        DailyQuest[] tempArray = new DailyQuest[ITEM_PER_PAGE];
        for (int i = 0; i < dailyQuests.size(); i ++){

            //Get index of item in the current array
            int index = i % ITEM_PER_PAGE;
            tempArray[index] = dailyQuests.get(i);

            //On the last index of the array, add the array into the Collection and create new array
            if(index == ITEM_PER_PAGE - 1) {
                this.dailyQuests.add(tempArray);
                tempArray = new DailyQuest[ITEM_PER_PAGE];
            }
        }

        //Add the last array
        this.dailyQuests.add(tempArray);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return DailyQuestFragment.newInstance(dailyQuests.get(position));
    }

    @Override
    public int getItemCount() {
        return dailyQuests.size();
    }
}
