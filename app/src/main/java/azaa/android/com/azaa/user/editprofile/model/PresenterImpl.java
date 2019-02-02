package azaa.android.com.azaa.user.editprofile.model;

import android.content.Intent;

import azaa.android.com.azaa.user.editprofile.presenter.Presenter;
import azaa.android.com.azaa.user.editprofile.view.profileView;

public class PresenterImpl implements Presenter {
    private profileView view;

    public PresenterImpl(profileView view) {
        this.view = view;
    }


    @Override
    public void navigatEdit(Intent intent) {
        view.startNewIntent();
    }
}
