package com.reactor.example;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class SequenceCreator {
    public Consumer<List<Integer>> consumer;

    public Flux<Integer> createNumberSequence() {

         return  Flux.create(
                (sink) -> SequenceCreator.this.consumer = items->{

                    items.forEach( item->{System.out.println("create item="+item); sink.next(item);});


                }

        );

//        SequenceCreator seqGen=new SequenceCreator();
//        Thread producerThread1 = new Thread(() -> seqGen.consumer.accept(sequence1));
//        seqGen.createNumberSequence().subscribe(System.out::println);
//        producerThread1.start();
//        producerThread1.join();
    }
}
