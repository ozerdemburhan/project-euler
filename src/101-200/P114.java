package com.apkbilisim.pe.p114;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P114 {

    private final int ROW_SIZE = 50;

    private long      count    = 1;

    public static void main(String[] args) {

        logger.info("started.");

        P114 problem = new P114();
        problem.start();

        logger.info("finished.");
    }

    private void start() {

        fill(0);
        logger.info("count: " + count);
    }

    private void fill(int startPos) {

        for (int block = 3; block <= ROW_SIZE; block++) {
            if (startPos + block > ROW_SIZE) {
                break;
            }

            for (int pos = startPos; pos <= ROW_SIZE - block; pos++) {
                count++;
                fill(pos + block + 1);
            }
        }
    }
}
