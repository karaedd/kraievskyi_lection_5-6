import com.kraievskyi.lection.task1.service.FineService;
import com.kraievskyi.lection.task1.service.ReadWrite;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();
        FineService fineService = new FineService();

        File[] fileArray = ReadWrite.getFiles();

        ExecutorService executor = Executors.newFixedThreadPool(6);

        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        final long startTime = System.currentTimeMillis();
        for (File file : fileArray) {
            completableFuture = CompletableFuture
                    .runAsync(() -> readWrite
                            .writeFileToXml(fineService
                            .convertMapToTotalFines(fineService
                                    .calculateFines(readWrite
                                            .readFiles(file)))), executor);
        }
        completableFuture.join();
        executor.shutdown();
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time readFiles: " + timeElapsed);
    }
}
