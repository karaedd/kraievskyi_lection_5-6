import com.kraievskyi.lection.task1.service.FineService;
import com.kraievskyi.lection.task1.service.ReadWrite;
import com.kraievskyi.lection.task2.model.Model;
import com.kraievskyi.lection.task2.util.Loader;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Model model = Loader.loadFromProperties(Model.class,
                Path.of("src/main/resources/task2/config.properties"));
        System.out.println(model);

        ReadWrite readWrite = new ReadWrite();
        FineService fineService = new FineService();

        File[] fileArray = ReadWrite.getFiles();

        ExecutorService executor = Executors.newFixedThreadPool(8);

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

        //Execution time readFiles with one thread = 17,6 sec.
        //Execution time readFiles with two thread = 10,2 sec.
        //Execution time readFiles with four thread = 8,1 sec.
        //Execution time readFiles with eight thread = 7,6 sec.
    }
}
