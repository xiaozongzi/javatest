public class thread {
    public static void main(String[] args) {
        final Counter counter = new Counter();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }
        System.out.println(counter);
        try {
            Thread.sleep(15000);
            System.out.println(counter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //---------------------------------华丽的分割线-----------------------------
}
class Counter {
    private volatile int count = 0;

    public void inc() {
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }

    @Override
    public String toString() {
        return "[count=" + count + "]";
    }
}