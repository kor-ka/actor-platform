package im.actor.sdk.core.controllers.adapters;

public interface OnItemClickedListener<T> {

    void onClicked(T item);

    boolean onLongClicked(T item);
}
