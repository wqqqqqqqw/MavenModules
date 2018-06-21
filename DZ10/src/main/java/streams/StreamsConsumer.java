package streams;

import java.util.function.Consumer;

class StreamsConsumer<T> implements Consumer<T> {

    private StreamsConsumer<? super T> outerConsumer;

    protected StreamsConsumer() {
    }

    protected StreamsConsumer(StreamsConsumer<T> outerConsumer) {
        this.outerConsumer = outerConsumer;
    }

    protected StreamsConsumer<? super T> getOuterConsumer() {
        return outerConsumer;
    }

    public void accept(T t) {
        throw new IllegalStateException("Accept method Must be overridden");
    }
}