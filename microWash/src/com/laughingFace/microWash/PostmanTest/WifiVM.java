package com.laughingFace.microWash.PostmanTest;

import java.io.IOException;
import java.net.*;

/**
 * Authro: mathcoder.
 * Date: 6/05/15.
 * Note:
 * Function:
 */
public class WifiVM {
    public static void main(String[] args)
    {
        WifiVM wifi = new WifiVM(7878);
        wifi.start();
    }
    boolean isSencond = false;
    int srcport = 0;
    public WifiVM(int src)
    {
        this.srcport = src;
    }
    int currentState = 0;
    byte progress = 0;
    final byte Stand_Progress = 20;
    final byte Dy_Progress = 30;
    DatagramPacket receivePacket;
    DatagramPacket sendPacket ;
    String host = "";
    int port = 0;
    public void start()
    {
        try {
            final DatagramSocket socket = new DatagramSocket(srcport);
            byte[] data =new byte[100];

            receivePacket = new DatagramPacket(data,data.length);
            while(true)
            {
                socket.receive(receivePacket);
                host = receivePacket.getAddress().getHostName();
                port = receivePacket.getPort();
                String str = new String(data).trim();
                System.out.print("message ::::::::::::   " + str);
                if(str.contains("{W:P}"))
                {
                    String id = "{W:12435}";
                    byte[] bid = id.getBytes();
                    bid[3] = 1;
                    sendPacket = new DatagramPacket(bid,9,receivePacket.getAddress(),receivePacket.getPort());
                    socket.send(sendPacket);
                }
//                else if (str.contains("{A:P}")){
//                    sendPacket = new DatagramPacket(("{A:"+currentState+"}").getBytes(),5,receivePacket.getAddress(),receivePacket.getPort());
//                    socket.send(sendPacket);
//                }
                else if (str.length()>4 && str.getBytes()[1] == 'A')
                {
                    int tempstate = str.getBytes()[3]-'0';
                    if (tempstate == 0)
                    {
                        currentState = 0;
                        progress =0;
                    }
                    if (currentState == 0)
                    {
                        currentState = tempstate;
                        if (currentState == 3)
                        {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    progress = Stand_Progress;
                                    while(progress > -2)
                                    {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        progress--;
                                    }
                                    currentState = 0;
                                    try {
                                        sendPacket = new DatagramPacket(("{A:"+currentState+"}").getBytes(),5,InetAddress.getByName(host),port);
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        socket.send(sendPacket);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                        else if (currentState == 4)
                        {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    progress = Dy_Progress;
                                    while(progress > -2)
                                    {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        progress--;
                                    }
                                    currentState = 0;
                                    try {
                                        sendPacket = new DatagramPacket(("{A:"+currentState+"}").getBytes(),5,InetAddress.getByName(host),port);
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        socket.send(sendPacket);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                        else if (currentState == 1 || currentState == 2)
                        {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(4000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    currentState = 0;
                                    try {
                                        sendPacket = new DatagramPacket(("{A:"+currentState+"}").getBytes(),5,InetAddress.getByName(host),port);
                                    } catch (UnknownHostException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        socket.send(sendPacket);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }

                    System.out.println(currentState);
                    sendPacket = new DatagramPacket(("{A:"+currentState+"}").getBytes(),5,receivePacket.getAddress(),receivePacket.getPort());
                    socket.send(sendPacket);
                }
                else if (str.contains("{B:P"))
                {
                    if (currentState == 0)
                        continue;
                    System.out.println("current progress:"+progress);
                    byte[] progress2 = new byte[]{'{','B',':',0,progress,'}'};
                    sendPacket = new DatagramPacket(progress2,progress2.length,receivePacket.getAddress(),receivePacket.getPort());
                    socket.send(sendPacket);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
