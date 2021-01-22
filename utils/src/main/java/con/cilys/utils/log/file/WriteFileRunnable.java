package con.cilys.utils.log.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class WriteFileRunnable implements Runnable {
    private FileWriter fileWriter;

    public WriteFileRunnable(String dir, String fileName) {
        if (fileName == null) {
            throw new NullPointerException();
        }
        buff = new StringBuilder();
        String filePath = dir + File.separator + fileName;

        try {
            fileWriter = new FileWriter(filePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder buff;
    private boolean running = true;
    private Thread runningThread;

    public synchronized void addContent(String msg) {
        if (buff == null) {
            buff = new StringBuilder();
        }

        buff.append(msg);
        buff.append("\n");
    }

    @Override
    public void run() {
        if (buff == null) {
            return;
        }

        runningThread = Thread.currentThread();

        synchronized (runningThread) {
            while (running) {
                if (buff.length() > 0) {
                    //写日志到文件

                    if (fileWriter != null) {
                        try {
                            System.out.println("写文件内容：" + buff.toString());
                            fileWriter.write(buff.toString());
                            fileWriter.flush();
                            buff.delete(0, buff.length());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    //线程休眠
                    try {
                        runningThread.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void notifyRunningThread(){
        if (runningThread != null) {
            synchronized (runningThread) {
                runningThread.notify();
            }
        }
    }

    public void stopWriteFile(){
        running = false;

        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileWriter = null;
        }
    }
}
