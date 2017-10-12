package com.wmcd.myb.util;

import android.util.Log;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by 邓志宏 on 2017/2/12.
 */
public class RxBus {
    /**
     * The constant defaultInstance.
     */
    private static volatile RxBus defaultInstance;
    /**
     * 主题
     */
    private final SerializedSubject<Object, Object> bus;

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例RxBus
     *
     * @return default
     */
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 提供了一个新的事件
     *
     * @param o the o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param <T>       the type parameter
     * @param eventType the event type
     * @return the observable
     */
    public <T> Observable<T> toObserverable(final Class<T> eventType) {
        return bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }}).cast(eventType);
    }

    /**
     * 查看是否有订阅者
     *
     * @return the boolean
     */
    public boolean hasObserver(){
        return bus.hasObservers();
    }
}
