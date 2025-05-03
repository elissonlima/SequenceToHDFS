package me.elisson;

import java.util.Collections;

public class WriteProgressPrinter {

    public static void print(long elapsedTimeInMillis,
                             long bytesWritten,
                             long totalFileSize,
                             long totalBytesWritten) {
        String rate = String.format( "%.2f", calculateProgress(elapsedTimeInMillis, bytesWritten)) + "MB/s";
        new Thread(() -> {
            updateProgressBar(
                    totalBytesWritten, totalFileSize, 50, rate,"Writing file"
            );
        }).start();
    }

    private static double calculateProgress(long elapsedTimeInMillis, long bytesWritten) {
//        double rate = ((double) elapsedTimeInMillis / 1000L) * bytesWritten;
        if (elapsedTimeInMillis == 0) {
            return 0;
        }
        double rate = bytesWritten / ((double) elapsedTimeInMillis / 1000L);
        return rate / (1024 * 1024);
    }

    public static void updateProgressBar(
            long current,
            long total,
            int barWidth,
            String rate,
            String taskName) {
        if (total == 0) {
            // Avoid division by zero
            System.out.print("\r" + taskName + ": [No items to process] 100%");
            System.out.flush();
            return;
        }

        // Ensure current does not exceed total for display purposes
        current = Math.min(current, total);

        // Calculate progress percentage and filled width
        double fraction = (double) current / total;
        int percentage = (int) (fraction * 100);
        int filledWidth = (int) (fraction * barWidth);

        // Build the progress bar string
        StringBuilder bar = new StringBuilder();
        bar.append(taskName).append(": [");
        bar.append(String.join("", Collections.nCopies(filledWidth, "="))); // Filled part
        bar.append(String.join("", Collections.nCopies(barWidth - filledWidth, " "))); // Empty part
        bar.append("] ");
        // Format percentage with consistent spacing (3 digits wide)
        bar.append(String.format("%3d%%", percentage));
        // Optionally add current/total count
        bar.append(String.format(" %s", rate));

        // Print the bar with carriage return (\r) to overwrite the line
        System.out.print("\r" + bar.toString());

        // Flush the output buffer to ensure immediate display
        System.out.flush();
    }

}
