package xyz.sethy.present.dto;

import java.util.*;

public class LinkedNeuron<T> implements Iterable<Neuron<T>> {
    private Neuron<T> first;
    private Neuron<T> last;
    private int place;

    public LinkedNeuron() {
        this.first = null;
        this.last = null;
        this.place = 0;
    }

    public void add(Neuron<T> data) {
        if(data == null) {
            throw new NullPointerException();
        }
        if(!isEmpty()) {
            Neuron<T> previous = this.last;
            this.last = data;
            previous.setNext(this.last);
        } else {
            this.last = data;
            this.first = last;
        }
        place++;
    }

    public Neuron<T> getLast() {
        return last;
    }

    public boolean isEmpty() {
        return place == 0;
    }

    public List<Neuron<T>> toList() {
        List<Neuron<T>> list = new ArrayList<>();
        forEach(list::add);
        return list;
    }

    public Neuron<T> getNext() {
        double completeWeight = 0.0;
        for(Neuron<T> ignored : this)
            completeWeight += 1.0;

        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (Neuron<T> neuron : this) {
            countWeight += 1.0;
            if(countWeight >= r)
                return neuron;
        }
        return null;
    }

    @Override
    public Iterator<Neuron<T>> iterator() {
        return new NeuronIterator();
    }

    private final class NeuronIterator implements Iterator<Neuron<T>> {
        private Neuron current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Neuron<T> next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Neuron<T> item = current;
            current = current.getNext();
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
