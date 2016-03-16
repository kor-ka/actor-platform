package im.actor.sdk.core.controllers;

import android.app.Fragment;
import android.view.View;
import android.widget.TextView;

import im.actor.core.entity.Avatar;
import im.actor.core.viewmodel.UserVM;
import im.actor.runtime.mvvm.ValueChangedListener;
import im.actor.runtime.mvvm.ValueModel;
import im.actor.sdk.core.ActorBinder;

/**
 * Created by ex3ndr on 17.09.14.
 */
public class BinderCompatFragment extends Fragment {

    private final ActorBinder BINDER = new ActorBinder();

    public void bind(final TextView textView, ValueModel<String> value) {
        BINDER.bind(textView, value);
    }

    public <T> void bind(ValueModel<T> value, ValueChangedListener<T> listener) {
        BINDER.bind(value, listener);
    }


    public void bind(final TextView textView, final View container, final UserVM user) {
        BINDER.bind(textView, container, user);
    }

    public void bind(final TextView textView, final View container, ValueModel<String> value) {
        BINDER.bind(textView, container, value);
    }

    public void bind(final TextView textView, final View container, ValueModel<String> value, ActorBinder.OnChangedListener callback, boolean hide, String defaultValue) {
        BINDER.bind(textView, container, value, callback, hide, defaultValue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BINDER.unbindAll();
    }
}
