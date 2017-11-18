package xyz.sethy.present.dto;

public class Neuron<T> {
    private final T payload;
    private Neuron<T> next;

    public Neuron(final T payload) {
        this.payload = payload;
        this.next = null;
    }

    public T getPayload() {
        return payload;
    }

    public Neuron<T> getNext() {
        return next;
    }

    public void setNext(final Neuron<T> next) {
        this.next = next;
    }
}
