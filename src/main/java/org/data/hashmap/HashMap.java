package org.data.hashmap;

import java.util.LinkedList;
import java.util.Objects;

public class HashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR  = 0.75f;

    private Entry<K, V>[] buckets;
    private int size;

    public HashMap() {
        this.buckets = new Entry[INITIAL_CAPACITY];
        this.size = 0;
    }

    private int hash(K key){
        return (key == null) ? 0 : Math.abs(key.hashCode() % buckets.length);
    }

    public void put(K key, V value){
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value, null);

        if(buckets[index]==null){
            buckets[index] = newEntry;
        } else{
            Entry<K, V> current = buckets[index];
            Entry<K, V> prev = null;

            while(current!=null){
                if(Objects.equals(current.key, key)){
                    if(prev == null){
                        buckets[index] = current.next;
                    } else{
                        prev.next = current.next;
                    }
                    size--;
                    return;
                }
                prev = current;
                current = current.next;
            }
        }
    }

    public int size(){
        return size;
    }

    private void resize(){
        int newCapacity = buckets.length * 2;
        Entry<K,V>[] newBuckets = new Entry[newCapacity];

        for (Entry<K, V> entry : buckets) {
            while (entry != null) {
                int newIndex = Math.abs(entry.key.hashCode() % newCapacity);
                Entry<K, V> next = entry.next;

                entry.next = newBuckets[newIndex];
                newBuckets[newIndex] = entry;

                entry = next;
            }
        }
        buckets = newBuckets;
    }

}
