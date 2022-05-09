import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Launcher {

public static int eval(String formula){
    String[] arr = formula.split(" ");
    int sum = Integer.valueOf(arr[0]);
    for(int i = 1; i < arr.length - 1; i += 2){
        sum += Integer.valueOf(arr[i] + arr[i+1]);
    }

    return sum;
}


public class ReaderThread implements Runnable{

  public helper()

  protected BlockingQueue<String> blockingQueue = null;

  public ReaderThread(BlockingQueue<String> blockingQueue){
    this.blockingQueue = blockingQueue;     
  }

  @Override
  public void run() {
    BufferedReader br = null;
     try {
            br = new BufferedReader(new FileReader(new File("./inputFile.txt")));
            String buffer =null;
            while((buffer=br.readLine())!=null){

                buffer = buffer +"=" + eval(buffer);
                blockingQueue.put(buffer);
            }
            blockingQueue.put("EOF");  //When end of file has been reached

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch(InterruptedException e){

        }finally{
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


  }



}

public class WriterThread implements Runnable{

  protected BlockingQueue<String> blockingQueue = null;

  public WriterThread(BlockingQueue<String> blockingQueue){
    this.blockingQueue = blockingQueue;     
  }

  @Override
  public void run() {
    PrintWriter writer = null;

    try {
        writer = new PrintWriter(new File("outputFile.txt"));

        while(true){
            String buffer = blockingQueue.take();
            //Check whether end of file has been reached
            if(buffer.equals("EOF")){ 
                break;
            }
            writer.println(buffer);
        }               


    } catch (FileNotFoundException e) {

        e.printStackTrace();
    } catch(InterruptedException e){

    }finally{
        writer.close();
    } 

  }

}



  public static void main(String[] args) {

    if(args.length != 2){
    System.out.println("Your program will take two input parameters: input file and output file java XXX <input file name> <output file name>");
    System.exit(1);
    }

    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);

    ReaderThread reader = new ReaderThread(queue);
    WriterThread writer = new WriterThread(queue);

    new Thread(reader).start();
    new Thread(writer).start();

  }

 }