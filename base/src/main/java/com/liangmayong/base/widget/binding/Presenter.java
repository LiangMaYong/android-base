package com.liangmayong.base.widget.binding;

/**
 * Created by LiangMaYong on 2016/9/17.
 */
public abstract class Presenter<V> {
    //view
    private V view;
    //isAttached
    private boolean isAttached = false;

    /**
     * getViewInstance
     *
     * @return view
     */
    protected final V getViewInstance() {
        return view;
    }

    /**
     * isAttached
     *
     * @return isAttached
     */
    public final boolean isAttached() {
        return isAttached;
    }

    /**
     * attach
     *
     * @param view view
     */
    public void onAttach(V view) {
        this.view = view;
        isAttached = true;
    }

    /**
     * dettach
     */
    public void onDettach() {
        isAttached = false;
        view = null;
    }

    /**
     * response
     *
     * @param data data
     * @param <D>  data type
     * @return callback
     */
    protected <D> Response1<D> response(D data) {
        return new Response1<D>(data);
    }

    /**
     * response
     *
     * @return callback
     */
    protected Response response() {
        return new Response();
    }

    /**
     * Response
     */
    protected class Response {
        /**
         * back
         *
         * @param action back
         */
        public Response back(OnAction action) {
            if (isAttached() && action != null && getViewInstance() != null) {
                action.action(getViewInstance());
            }
            return this;
        }
    }

    /**
     * Response1
     *
     * @param <D> data type
     */
    protected class Response1<D> {

        private D data;

        public Response1(D data) {
            this.data = data;
        }

        public <C> Response1<C> map(OnMap<D, C> map) {
            return new Response1<C>(map.map(data));
        }

        /**
         * back
         *
         * @param callback callback
         */
        public Response1<D> back(OnCallback<D> callback) {
            if (isAttached() && callback != null && getViewInstance() != null) {
                callback.callback(getViewInstance(), data);
            }
            return this;
        }
    }

    /**
     * OnAction
     */
    protected abstract class OnAction {
        public abstract void action(V view);
    }

    /**
     * OnMap
     *
     * @param <D> data type
     * @param <C> to type
     */
    protected abstract class OnMap<D, C> {
        public abstract C map(D data);
    }

    /**
     * OnCallback
     *
     * @param <D> data type
     */
    protected abstract class OnCallback<D> {
        public abstract void callback(V view, D data);
    }
}
