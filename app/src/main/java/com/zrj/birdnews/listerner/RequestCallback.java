package com.zrj.birdnews.listerner;

/**
 * Created by a on 2018/3/9.
 */

public interface RequestCallback<T> {

    void onSuccess(T data);

    void onFailure(String msg);

    void onError(String msg);

}
