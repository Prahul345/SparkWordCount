	import org.apache.spark._
    import org.apache.spark.streaming._
    import org.apache.spark.streaming.StreamingContext._ // not necessary in Spark 1.3+
    
    object Test{
	     def main(args: Array[String]) {

	
    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent from a starvation scenario.
    val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCountExample")
    val ssc = new StreamingContext(conf, Seconds(1))
	    val lines = ssc.socketTextStream("localhost", 9999)
    // Split each line into words
    val words = lines.flatMap(_.split(" "))
	    import org.apache.spark.streaming.StreamingContext._ // not necessary in Spark 1.3+
    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
	    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate

}
	}
