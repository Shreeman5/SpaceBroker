package Scheduler;


import Algorithm.AlgorithmCatalog;
import Device.ActiveDevices;
import Device.DeviceCatalog;
import Device.LightBulb;
import Extras.Coordinate;
import Request.LightBulbRequest;
import Request.UserRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileConfig{
    private String fileName;
    private Scanner scanner;
    private int length;
    private int width;
    private Random rand;
    private int lumenLimit;
    private ActiveDevices activeDevices;
    private UserRequest userRequest;
    private AlgorithmCatalog algorithmCatalog;

    public FileConfig(String filename, int length, int width, ActiveDevices activeDevices, UserRequest userRequest, AlgorithmCatalog algorithmCatalog) {
        this.fileName = filename;
        this.length = length;
        this.width = width;
        this.rand = new Random();
        this.lumenLimit = 2000;
        this.activeDevices = activeDevices;
        this.userRequest = userRequest;
        this.algorithmCatalog = algorithmCatalog;

        String pathname = "resources/" + filename;
        try {
            scanner = new Scanner(new File(pathname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            //System.out.println(str);
            if (str.startsWith("AD")){
                initializeActiveDevices(str);
            }
            else if (str.startsWith("UR")){
                initializeUserRequests(str);
            }
            else if (str.startsWith("AL")){
                initializeAlgorithms(str);
            }
        }
        System.exit(1);
    }

    public void initializeActiveDevices(String line) {
        System.out.println("A: "+line);
        String[] split = line.split("\\s+");
        if(split[1].equals("L")) {
            addActiveDevice(DeviceCatalog.LightBulb, Integer.parseInt(split[2]));
        }
    }

    public void initializeUserRequests(String line) {
        System.out.println("B: "+line);
        String[] split = line.split("\\s+");
        /*for (int i = 0; i < split.length; i++){
            System.out.println(split[i]);
        }*/
        if (split[1].equals("L")){
            addDeviceRequest(DeviceCatalog.LightBulb, Integer.parseInt(split[2]));
        }
    }

    public void initializeAlgorithms(String line) {
        System.out.println("C: "+line);
        String[] split = line.split("\\s+");
        //ArrayList<DeviceCatalog> deviceCatalogs = activeDevices.getAvailableDevices();
        if (split[1].equals("1")){
            getLightBulbAlgorithms(Integer.parseInt(split[1]));
        }
        else if (split[1].equals("2")){
            //
        }
        else if (split[1].equals("3")){
            //
        }
    }

    private void getLightBulbAlgorithms(int num) {
        algorithmCatalog.setSelectedLightBulbAlgorithms(num - 1);
    }


    private void addDeviceRequest(DeviceCatalog device, int numberOfRequests) {
        switch (device){
            case LightBulb:
                addLightBulbRequests(numberOfRequests);
                break;
            case AlarmClock:
                System.out.println("AlarmClock unavailable at the moment");
                System.exit(0);
                break;
            case Thermometer:
                System.out.println("Thermometer unavailable at the moment");
                System.exit(0);
                break;
            default:
                System.out.println("This device is unavailable at the moment");
                System.exit(0);
                break;

        }
    }

    private void addLightBulbRequests(int num){
        ArrayList<Integer> lengths = new ArrayList<>();
        while(lengths.size() < num) {
            int a = rand.nextInt(length);
            if(!lengths.contains(a)) {
                lengths.add(a);
            }
        }
        System.out.println("R: "+lengths);

        ArrayList<Integer> widths = new ArrayList<>();
        while(widths.size() < num) {
            int a = rand.nextInt(width);
            if(!widths.contains(a)) {
                widths.add(a);
            }
        }
        System.out.println("S: "+widths);

        ArrayList<Integer> lumens = new ArrayList<>();
        while(lumens.size() < num) {
            int a = rand.nextInt(lumenLimit);
            lumens.add(a);
        }
        System.out.println("T: "+lumens);

        for(int i = 0; i < num; i++) {
            initializeLightBulbRequests(lengths.get(i), widths.get(i), lumens.get(i));
        }

    }

    private  void initializeLightBulbRequests(int length, int width, int lumen) {
        LightBulbRequest lightBulbRequest = new LightBulbRequest(lumen, new Coordinate(length, width));
        userRequest.addDeviceRequest(DeviceCatalog.LightBulb, lightBulbRequest);
    }


    private void addActiveDevice(DeviceCatalog device, int numberOfDevices) {
        switch (device){
            case LightBulb:
                addLightBulbs(numberOfDevices);
                break;
            case AlarmClock:
                System.out.println("AlarmClock unavailable at the moment");
                System.exit(0);
                break;
            case Thermometer:
                System.out.println("Thermometer unavailable at the moment");
                System.exit(0);
                break;
            default:
                System.out.println("This device is unavailable at the moment");
                System.exit(0);
                break;
        }
    }

    private  void addLightBulbs(int num) {
        ArrayList<Integer> lengths = new ArrayList<>();
        while(lengths.size() < num) {
            int a = rand.nextInt(length);

            if(!lengths.contains(a)) {
                lengths.add(a);
            }
        }

        ArrayList<Integer> widths = new ArrayList<>();
        while(widths.size() < num) {
            int a = rand.nextInt(width);

            if(!widths.contains(a)) {
                widths.add(a);
            }
        }

        ArrayList<Integer> lumens = new ArrayList<>();
        while(lumens.size() < num) {
            int a = rand.nextInt(lumenLimit);
            lumens.add(a);
        }

        for(int i = 0; i < num; i++) {
            initializeLightBulb(lengths.get(i), widths.get(i), lumens.get(i));
        }
    }

    private  void initializeLightBulb(int length, int width, int lumen) {
        LightBulb lightbulb = new LightBulb(lumen, new Coordinate(length, width));
        activeDevices.addDevice(DeviceCatalog.LightBulb,  lightbulb);
    }

}
