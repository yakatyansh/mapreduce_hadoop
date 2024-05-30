import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BloodRequestMR {

    public static class BloodRequestMapper extends Mapper<LongWritable, Text, Text, BloodRequestWritable> {

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String[] data = value.toString().split(",");

            String requestKey = data[0];
            String city = data[1];
            String bloodType = data[2];
            String rhFactor = data[3];
            int amountMl = Integer.parseInt(data[4]);
            int year = Integer.parseInt(data[5]);

            String keyString = year + "-" + bloodType;
            BloodRequestWritable request = new BloodRequestWritable(city, rhFactor, amountMl, "min", "max");

            context.write(new Text(keyString), request);
        }
    }

    public static class BloodRequestReducer extends Reducer<Text, BloodRequestWritable, Text, Text> {

        @Override
        public void reduce(Text key, Iterable<BloodRequestWritable> values, Context context)
                throws IOException, InterruptedException {
            int minAmount = Integer.MAX_VALUE;
            int maxAmount = Integer.MIN_VALUE;
            String minCity = null, maxCity = null, minRhFactor = null, maxRhFactor = null;

            for (BloodRequestWritable request : values) {
                int amount = request.getAmountMl();
                if (amount < minAmount) {
                    minAmount = amount;
                    minCity = request.getCity();
                    minRhFactor = request.getRhFactor();
                }
                if (amount > maxAmount) {
                    maxAmount = amount;
                    maxCity = request.getCity();
                    maxRhFactor = request.getRhFactor();
                }
            }

            String output = "Year-Blood Type: " + key.toString() + "\n";
            output += "  Min Request: " + minAmount + " ml (City: " + minCity + ", Rh: " + minRhFactor + ")\n";
            output += "  Max Request: " + maxAmount + " ml (City: " + maxCity + ", Rh: " + maxRhFactor + ")\n";

            context.write(new Text(output), new Text(""));
        }
    }

    public static class BloodRequestWritable {
        private String city;
        private String rhFactor;
        private int amountMl;
        private String minFlag;
        private String maxFlag;

        public BloodRequestWritable(String city, String rhFactor, int amountMl, String minFlag, String maxFlag) {
            this.city = city;
            this.rhFactor = rhFactor;
            this.amountMl = amountMl;
            this.minFlag = minFlag;
            this.maxFlag = maxFlag;
        }

        // Getters and setters omitted for brevity

    }

    public static void main(String[] args) throws Exception {
        // Optional: Generate fake data (comment out if using existing data)
        generateFakeData(1000, 10); // Adjust numRequests and numYears as needed

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "BloodRequestMR");
        job.setJarByClass(BloodRequestMR.class);

        // Input and output paths (replace with your actual file paths)
        FileInputFormat.addInputPath(job, new Path("blood_requests.csv"));
        FileOutputFormat.setOutputPath(job, new Path("blood_request_results"));

        job.setMapperClass(BloodRequestMapper.class);
        job.setReducerClass(BloodRequestReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Blood
