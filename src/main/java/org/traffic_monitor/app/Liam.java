import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Liam {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            sendToElastic(
                    "of:000000000000000" + i/10,
                    (i%10)+ "",
                    3259.000000,
                    3257.000000,
                    359806.000000,
                    359003.000000,
                    0.858138,
                    0.469092,
                    0.000000,
                    0.000000,
                    1602308995925L + i*3600);
        }

    }
    public static String buildDataEntry(String switchName, String portID, double receivingPacketRate, double transmittingPacketRate,
                                         double receivingByteRate, double transmittingByteRate, double receivingErrorRate,
                                         double transmittingErrorRate,
                                         double droppedReceivingPacketsRate, double droppedTransmittingPacketsRate, long time){

        return String.format("{" +
                "\"switch\": \"%s\", " +
                "\"port\": \"%d\", " +
                "\"receivingPacketRate\": \"%s\", " +
                "\"transmittingPacketRate\": \"%s\", " +
                "\"receivingByteRate\": \"%s\", " +
                "\"transmittingErrorRate\": \"%s\", " +
                "\"droppedReceivingPacketsRate\": \"%s\", " +
                "\"droppedTransmittingPacketsRate\": \"%s\", " +
                "\"time\": \"%s\"}",
                switchName,
                portID,
                receivingPacketRate,
                transmittingPacketRate,
                receivingByteRate,
                transmittingByteRate,
                receivingErrorRate,
                transmittingErrorRate,
                droppedReceivingPacketsRate,
                droppedTransmittingPacketsRate,
                time);
    }

    public static String buildCreateEntryCommand(String indexName){
        return String.format("{\"create\": {\"_index\": \"%s\"}}", indexName);
    }

    public static void sendToElastic(String switchName, String portID, double receivingPacketRate, double transmittingPacketRate,
                                     double receivingByteRate, double transmittingByteRate, double receivingErrorRate,
                                     double transmittingErrorRate,
                                     double droppedReceivingPacketsRate, double droppedTransmittingPacketsRate, long time){
        String targetIndex = "4200";
        StringBuilder data = new StringBuilder();
            data.append(Liam.buildCreateEntryCommand(targetIndex)).append("\r\n") // don't forget the newlines
            .append(Liam.buildDataEntry(
                    switchName,
                    portID,
                    receivingPacketRate,
                    transmittingPacketRate,
                    receivingByteRate,
                    transmittingByteRate,
                    receivingErrorRate,
                    transmittingErrorRate,
                    droppedReceivingPacketsRate,
                    droppedTransmittingPacketsRate,
                    time
            )).append("\r\n"); // !!!DON'T FORGET THE LAST NEWLINE!!!!

        String dataQuery = data.toString();


        System.out.println("======== REQUEST MESSAGE ========");
        System.out.println(dataQuery);

        try {
            URL url = new URL ("http://localhost:9200/_bulk");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            // THIS IS VERY IMPORTANT
            // If you don't set accept method you will get a 400 error
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "close");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = dataQuery.getBytes();
                os.write(input, 0, input.length);
                os.flush();
            }
            System.out.println("======== SERVER RESPONSE ========");
            try(InputStream is = con.getInputStream()){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                String responseBody = baos.toString();
                System.out.println(responseBody);
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Sent to server.\n");
    }

//
//    POST /cars/transactions/_bulk
//    { "index": {}}
//    { "price" : 10000, "color" : "red", "make" : "honda", "sold" : "2014-10-28" }
//    { "index": {}}
//    { "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
//    { "index": {}}
//    { "price" : 30000, "color" : "green", "make" : "ford", "sold" : "2014-05-18" }
//    { "index": {}}
//    { "price" : 15000, "color" : "blue", "make" : "toyota", "sold" : "2014-07-02" }
//    { "index": {}}
//    { "price" : 12000, "color" : "green", "make" : "toyota", "sold" : "2014-08-19" }
//    { "index": {}}
//    { "price" : 20000, "color" : "red", "make" : "honda", "sold" : "2014-11-05" }
//    { "index": {}}
//    { "price" : 80000, "color" : "red", "make" : "bmw", "sold" : "2014-01-01" }
//    { "index": {}}
//    { "price" : 25000, "color" : "blue", "make" : "ford", "sold" : "2014-02-12" }



}


