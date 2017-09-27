package com.zhanghao.core.base.baserx;

/**
 * des:对服务器返回数据成功和失败处理
 * Created by xsf
 * on 2016.09.9:59
 */

import com.zhanghao.core.base.BaseRespose;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**************使用例子******************/


public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseRespose<T>, T> handleResult() {
        return new ObservableTransformer<BaseRespose<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseRespose<T>> upstream) {

                return upstream.flatMap(new Function<BaseRespose<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseRespose<T> tBaseRespose) throws Exception {
                        if (tBaseRespose.success()) {
                            return createData(tBaseRespose.data);
                        } else {
                            return Observable.error(new ServerException(tBaseRespose));
                        }

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });

    }
}
