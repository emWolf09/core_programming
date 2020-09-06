 package code;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

 public class Leetcode {
    final static Database db = new Database();

     public static void main(String[] args) throws InterruptedException {
         Thread t1 = new Thread(new Reader(db),"reader1");
         Thread t2 = new Thread(new Reader(db),"reader2");
         Thread t3 = new Thread(new Reader(db),"reader3");

         Thread t4 = new Thread(new Writer(db),"writer1");

         t1.start();
         t4.start();
         t2.start();
         t3.start();

         t1.join();
         t2.join();
         t3.join();
         t4.join();

     }

}


class Database{
    private int []a;
    private Integer readerCount;
    private Semaphore writeSemaphore;

    Database(){
        writeSemaphore  = new Semaphore(1);
        readerCount=0;
        a = new int[]{1,2,3,4,5}; //this is data where multiple readers can read at a time .But only writer can write.
    }

    public int read(int queryIndex) throws InterruptedException {
        //only one thread should perform/change on readerCount at a time
        synchronized (readerCount) {
            readerCount++;
            System.out.println("new reader"+readerCount);
        }

        if (readerCount == 1) {
            synchronized (writeSemaphore){
                //check other writer is there or no
                writeSemaphore.acquire();
            }
        }
        int result=-1;
        try{
            Thread.sleep(1000);
            //read
            result =  queryIndex<a.length && queryIndex>=0? a[queryIndex]: -1;
        }catch (Exception e){
            System.out.println("exception occurred while reading");
            e.printStackTrace();
        }

        synchronized (readerCount){
            readerCount--;
        }
        if(readerCount==0){
            synchronized (writeSemaphore){

                writeSemaphore.release();
            }
        }
        return result;
    }

    public void write(int index,int newData) throws InterruptedException {
        synchronized (writeSemaphore) {
            writeSemaphore.acquire();
            //write
            try{
               Thread.sleep(1000);
               if(index<a.length && index>=0){
                   a[index] = newData;
                   System.out.println("inserted into db "+index+" "+newData);
                   System.out.println(Arrays.toString(a));
               }
            }catch (Exception e){
                System.out.println("exception while writing");
                e.printStackTrace();
            }
            writeSemaphore.release();
        }
    }
}


class Reader implements Runnable{
    final Database db;

    Reader(Database db){
     this.db = db;
    }

    @Override
    public void run() {
        try{
            for(int i=0;i<5;i++)read(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void read(int q) throws Exception{
        System.out.println(Thread.currentThread().getName()+"read-> "+db.read(q));
    }
}

class Writer implements Runnable{
    final Database db;

    Writer(Database db){
        this.db = db;
    }

    @Override
    public void run() {
        try{
            for(int i=0;i<5;i++) write();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void write() throws Exception{
        int ind = (int) ThreadLocalRandom.current().nextInt(0,5);
        int newData = (int) ThreadLocalRandom.current().nextInt(0,100);
        db.write(ind,newData);
    }
}
