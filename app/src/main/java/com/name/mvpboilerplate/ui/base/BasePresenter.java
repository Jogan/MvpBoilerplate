package com.name.mvpboilerplate.ui.base;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    /**
     * Encapsulate the result of an rx Observable.
     * This model is meant to be used by the children presenters to easily keep a reference
     * to the latest loaded result so that it can be easily emitted again when on configuration
     * changes.
     */
    protected static class DataResult<T> {

        private T data;
        private Throwable error;

        public DataResult(T data) {
            this.data = data;
        }

        public DataResult(Throwable error) {
            this.error = error;
        }

        public Single<T> toSingle() {
            if (error != null) {
                return Single.error(error);
            }
            return Single.just(data);
        }

        public Observable<T> toObservable() {
            if (error != null) {
                return Observable.error(error);
            }
            return Observable.just(data);
        }
    }
}

