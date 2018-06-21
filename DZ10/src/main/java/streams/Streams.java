package streams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;


public class Streams<T> {

    private List<? extends T> source;
    private Streams<T> first;
    private Streams<T> previous;

    private Streams(List<? extends T> source) {
        this.source = source;
        this.first = this;
    }

    private Streams(Streams<T> previous) {
        this.previous = previous;
        this.first = previous.first;

    }

    public static <T> Streams<T> of(List<? extends T> source) {
        return new Streams<>(source);
    }

    private void streamPipeline(StreamsConsumer<T> consumer) {
        Iterator<? extends T> iterator = first.source.iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    private StreamsConsumer<T> passThroughStreams(StreamsConsumer<T> consumer) {
        for (Streams stream = this; stream.previous != null; stream = stream.previous) {
            consumer = stream.handle(consumer);
        }
        return consumer;
    }

    protected StreamsConsumer<T> handle(StreamsConsumer<T> consumer) {
        throw new IllegalStateException("Handle method Must be overridden");
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        return new Streams<T>(this) {
            @Override
            protected StreamsConsumer<T> handle(StreamsConsumer<T> consumer) {
                return new StreamsConsumer<T>(consumer) {
                    @Override
                    public void accept(T el) {
                        if (predicate.test(el)) {
                            getOuterConsumer().accept(el);
                        }
                    }
                };
            }
        };
    }

    public Streams<T> transform(Function<? super T, ? extends T> transformator) {
        return new Streams<T>(this) {
            @Override
            protected StreamsConsumer<T> handle(StreamsConsumer<T> consumer) {
                return new StreamsConsumer<T>(consumer) {
                    @Override
                    public void accept(T el) {
                        getOuterConsumer().accept(transformator.apply(el));
                    }
                };
            }
        };
    }

    public <K, V> Map<K, V> toMap(Function<? super T, K> keyConstructor,
                                  Function<? super T, V> valueConstructor) {
        HashMap<K, V> map = new HashMap<>();
        streamPipeline(passThroughStreams(new StreamsConsumer<T>() {
            @Override
            public void accept(T el) {
                map.put(keyConstructor.apply(el), valueConstructor.apply(el));
            }
        }));
        return map;
    }
}