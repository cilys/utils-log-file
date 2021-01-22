package con.cilys.utils.log.file;

public class LogFileUtils {

    private static LogFileUtils utils;

    private LogFileUtils(){

    }

    public static LogFileUtils getInstance() {
        if (utils == null) {
            synchronized (LogFileUtils.class) {
                if (utils == null) {
                    utils = new LogFileUtils();
                }
            }
        }

        return utils;
    }

    private WriteFileRunnable runnable;

    public void startWriteLog(String dir, String fileName, String msg) {
        if (runnable != null) {
            runnable.addContent(msg);

            runnable.notifyRunningThread();
        } else {
            runnable = new WriteFileRunnable(dir, fileName);

            runnable.addContent(msg);

            Thread t = new Thread(runnable);
            t.start();
        }
    }

    public void stopWriteLog(){
        if (runnable != null) {
            runnable.stopWriteFile();
        }
    }
}
