package com.cehome.apimanager;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * coolma 2019/9/4
 */
public class BlockedFileInputStream extends FileInputStream {

    static Set<BlockedFileInputStream> set =ConcurrentHashMap.newKeySet();
    static volatile boolean running=true;
    static Thread handler = new Thread(new Runnable() {
        @Override
        public void run() {
            while (running) {
                int count=0;
                for (BlockedFileInputStream is : set) {

                        if (is.isBlock()) {
                            synchronized (is) {
                            long len = is.file.length();
                            if (len > is.length) {
                                is.setBlock(false);
                                is.notify();

                            } else {
                                count++;
                            }
                        }
                    }
                }
                if(count>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    });

    static {
        handler.start();
    }

    private volatile boolean block;
    private File file;
    private long maxLength;
    private long length;

    private long readTimeout = 30000;

    private boolean isBlock() {
        return block;
    }


    public void setBlock(boolean block) {
        this.block = block;
    }

    public BlockedFileInputStream(File file, long maxLength,long readTimeout) throws FileNotFoundException {
        super(file);
        this.file = file;
        this.maxLength = maxLength;
        this.readTimeout=readTimeout;
        set.add(this);
    }


    public int read() throws IOException {
        int n = super.read();
        if (n == -1) {
            length = file.length();
            if (maxLength > 0 && length >= maxLength) return n;

            synchronized (this) {
                try {
                    this.setBlock(true);
                    System.out.println("wait...");

                    this.wait(readTimeout);
                     n= super.read();
                     if(n==-1) throw new IOException("wait timeout");
                     return n;

                } catch (InterruptedException e) {
                    throw new IOException(e);
                }


            }

        }
        return n;
    }

    public void close() throws IOException {
        set.remove(this);
        super.close();
    }

    public static void main(String[] args) throws  Exception {

        File file=new File("/video.mp4");
        file.delete();
        file.createNewFile();
        long max=1000;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream f=new FileOutputStream(file);
                    byte[] bs="h".getBytes();
                    long l=0;
                    while (l<max) {
                        f.write(bs,0,1);
                        f.flush();
                        l+=1;
                        if(new Random().nextInt(300)==0)  Thread.sleep(new Random().nextInt(5000));

                    }
                    System.out.println("thread-end");
                    f.close();
                } catch ( Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

        BlockedFileInputStream is=new BlockedFileInputStream(file,max,5000);
        int r;
        int c=0;
        while ((r=is.read())!=-1){
            c++;
            System.out.print(c+",");


        }
        is.close();
        System.out.println("end");

        running=false;


    }
}
