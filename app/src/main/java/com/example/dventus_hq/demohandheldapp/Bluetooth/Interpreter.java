package com.example.dventus_hq.demohandheldapp.Bluetooth;

/**
 * Created by Bety on 8/26/2016.
 */
public class Interpreter {
    //  private byte[] message;
//  private  byte[] meterIDBytes;
    private Boolean ackAccepted;
    private  byte[] bytes;
    private byte[] acknowldge ;
    public Interpreter(byte[] bytes) {
        this.bytes=bytes;
        acknowldge = new byte[16];
        //meterIDBytes=new byte[8];
    }
    public String Check(){
        String messageID;
        if( bytes.length > 20 && bytes[20]==(byte)0xE0){
            messageID="acknowledgment";
            ackAccepted=true;
        }
//    else if(bytes.length!=22){
//      messageID="corrupt data";
//    }
        // if corrupt condition
        else{
            messageID="data";
        }
        return messageID;
    }
    public String meterID(){
        String meterID;
        StringBuilder sb = new StringBuilder();
        for(int i=12;i<20;i++){
            //meterIDBytes[i-12]=bytes[i];
            sb.append(String.format("%02X ", bytes[i]));
        }
        meterID=sb.toString();
        return meterID;
    }
    public Boolean isAckAccepted(){
        return ackAccepted;
    }

    public byte[] getAcknowldgmentByte() {

        byte initial[]={(byte)0x00,(byte)0x10,(byte)0x50,(byte)0x80,(byte)0x01,(byte)0x19};
        byte[] end={(byte)0xE5};
        System.arraycopy(initial, 0, acknowldge, 0, initial.length);
        System.arraycopy(bytes, 12, acknowldge, initial.length, 8);
        System.arraycopy(end, 0, acknowldge, acknowldge.length - 2, end.length);
        acknowldge[acknowldge.length - 1] = this.calculateChecksum(acknowldge);
        return  acknowldge;
    }

    private byte calculateChecksum(byte[] files){
        byte checksum = 0;

        for(byte b:files ){

            checksum += Integer.parseInt(String.valueOf(b), 16);
        }
        return checksum;

    }
}