import java.lang.reflect.Array;
import java.util.*;
import java.lang.annotation.*;

class ProducerConsumerSimulation{
    public static void main(String[] args) throws InterruptedException {
        final SharedResource resource = new SharedResource(3);
        Thread thread1 = new Thread(new Producer(resource), "ProducerThread");
        Thread thread2 = new Thread(new Consumer(resource), "ConsumerThread");

        //Start simulation
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

//application one
class Producer implements Runnable{
    static int value=1;
    final SharedResource resource;
    Producer(SharedResource resource){
        this.resource=resource;
    }

    //make this method synchronized
    public void produce() throws InterruptedException {
       while (true){
           resource.write(value);
           value++;
           Thread.sleep(1000);
       }
    }

    @Override
    public void run() {
        try{
            produce();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

//application 2
class Consumer implements Runnable{
     final SharedResource resource;
     Consumer(SharedResource resource){
         this.resource=resource;
     }

     //make this method synchronized
     public void consume() throws InterruptedException {
         while (true){
             resource.readAndRemove();
             Thread.sleep(1000);
         }
     }

    @Override
    public void run() {
        try{
            consume();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



 class SharedResource{
    List<Integer>list;
    int capacity;

    SharedResource(int capacity){
        System.out.println("Initializing resource buffer");
        this.capacity = capacity;
        list =  new LinkedList<Integer>();
    }

    public void write(int value) throws InterruptedException {
        synchronized (this){
            while(isFull())wait();
            System.out.println("added value to list - "+value);
            list.add(value);
            if(isFull()) notify();
        }
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public boolean isFull(){
        return list.size()==capacity;
    }

    public void readAndRemove() throws InterruptedException {
        synchronized (this){
            while(isEmpty())wait();
            System.out.println("read and remove = "+list.remove(0));
            if(isFull())notify();
        }
    }
}
