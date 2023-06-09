package com.reactor.example;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class AssertSubscriber {

    public static<T> Subscriber  create(T param){
        return new Subscriber<T>(){
            Subscription subscription=null;
            volatile int i=0;
            private void request(){
                subscription.request(1);
            }
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe,"+Thread.currentThread());
                this.subscription=subscription;
                request();
            }

            @Override
            public void onNext(T s) {
                request();
            }

            @Override
            public void onError(Throwable throwable) {
               // System.out.println("onerror,error="+throwable.getMessage()+","+Thread.currentThread());
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                //System.out.println("onComplete"+","+Thread.currentThread());
                subscription.cancel();
            }
        };
    }
}
