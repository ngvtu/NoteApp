package vietmobi.net.noteapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vietmobi.net.noteapp.fragment.AllNoteFragment;
import vietmobi.net.noteapp.fragment.FavoriteFragment;
import vietmobi.net.noteapp.fragment.FindNoteFragment;
import vietmobi.net.noteapp.fragment.FolderNoteFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllNoteFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new FolderNoteFragment();
            case 3:
                return new FindNoteFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
