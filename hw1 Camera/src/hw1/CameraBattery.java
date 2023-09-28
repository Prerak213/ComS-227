package hw1;


public class CameraBattery {

    private double batteryCapacity;

    private double totalDrain;

    private double cameraConsumption=1;

    private double batteryCap;

    private int setting=0;

    private double batteryDrain=0;

    private boolean batteryInExternal;

    private boolean batteryInCamera;

    private double batteryCharge;

    private double cameraCharge;

    /**
     *  Number of external charger settings. Settings are numbered between 0 inclusive and 4 exclusive.
     */
    public static final int NUM_CHARGER_SETTINGS = 4;


    /**
     * A constant used in calculating the charge rate of the external charger.
     */
    public static final double CHARGE_RATE = 2.0;


    /**
     *  Default power consumption of the camera at the start of simulation.
     */
    public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;


    /**Constructs a new camera battery simulation. The battery should start disconnected from both the
     camera and the external charger. The starting battery charge and maximum charge capacity are
     given. If the starting charge exceeds the batteries capacity, the batteries charge is set to its
     capacity. The external charger starts at setting 0.*/

    public CameraBattery(double batteryStartingCharge,
                         double batteryCapacit){
        batteryCapacity=batteryCapacit;
        batteryCharge=Math.min(batteryStartingCharge,batteryCapacity);
        batteryCap=0;
        batteryInCamera=false;
        batteryInExternal=false;
        setting=0;
        cameraCharge=0;
        batteryDrain=0;
        totalDrain=0;
        cameraConsumption=DEFAULT_CAMERA_POWER_CONSUMPTION;

    }

    /**Indicates the user has pressed the setting button one time on the external charger. The charge
     setting increments by one or if already at the maximum setting wraps around to setting 0.*/
    public void buttonPress(){
        setting+=1;
        setting=setting%4;

    }
    /**Charges the battery connected to the camera (assuming it is connected) for a given number of
     minutes. The amount of charging in milliamp-hours (mAh) is the number minutes times the
     CHARGE_RATE constant. However, charging cannot exceed the capacity of the battery
     connected to the camera, or no charging if the battery is not connected. The method returns the
     actual amount the battery has been charged.*/
    public double cameraCharge(double minutes){
        double temp1=batteryCharge;
        double batteryCharge1=minutes*CHARGE_RATE+batteryCharge;
        double batteryCharge2=Math.min(batteryCharge1,batteryCap);
        batteryCharge=Math.max(batteryCharge,batteryCharge2);
        cameraCharge=Math.min(batteryCap,batteryCharge);
        return batteryCharge-temp1;

    }
    /**Drains the battery connected to the camera (assuming it is connected) for a given number of
     minutes. The amount of drain in milliamp-hours (mAh) is the number of minutes times the
     cameras power consumption. However, the amount cannot exceed the amount of charge
     contained in the battery assuming it is connected to the camera, or no amount drain if the battery
     is not connected. The method returns the actual amount drain from the battery.*/
    public double drain(double minutes){

        double batteryDrain1=minutes*cameraConsumption;
        double batteryDrain2=Math.min(batteryDrain1,batteryCap);
        batteryDrain=Math.min(batteryDrain2,batteryCharge);

        totalDrain+=batteryDrain;
        batteryCharge-=batteryDrain;
        cameraCharge-=batteryDrain;

        return batteryDrain;
    }
    /**Charges the battery connected to the external charger (assuming it is connected) for a given
     number of minutes. The amount of charging in milliamp-hours (mAh) is the number minutes
     times the charger setting (a number between 0 inclusive and NUM_CHARGER_SETTINGS
     exclusive) the CHARGE_RATE constant. However, charging cannot exceed the capacity of the
     battery connected to the camera, or no charging if the battery is not connected. The method
     returns the actual amount the battery has been charged.*/
    public double externalCharge(double minutes){
        double temp1=batteryCharge;
        double batteryCharge1=minutes*setting*CHARGE_RATE+batteryCharge;
        batteryCharge=Math.min(batteryCharge1,batteryCapacity);
        return batteryCharge-temp1;
    }

    /**
     * Reset the battery monitoring system by setting the total battery drain count back to 0.
     *
     */
    public void resetBatteryMonitor(){
        batteryDrain=0;
        totalDrain=0;
    }



    /**
     Get the battery's capacity.
     */

     public double getBatteryCapacity(){
     return batteryCapacity;
     }

    /**
     * Get the battery's current charge.
     */

     public double getBatteryCharge(){
     return batteryCharge;
     }


    /**
     * Get the current charge of the camera's battery.
     */


     public double getCameraCharge(){
     return cameraCharge;
     }



    /**
     *  Get the power consumption of the camera.
     */

     public double getCameraPowerConsumption(){
     return cameraConsumption;
     }


     /**
     * Get the external charger setting.
     */

     public int getChargerSetting(){
     return setting;
     }



    /**Get the total amount of power drained from the battery since the last time the battery monitor
     was started or reset.*/
    public double getTotalDrain(){
        return totalDrain;
    }


    /**
     * Move the battery to the external charger. Updates any variables as needed to represent the move.
     */

    public void moveBatteryExternal(){
        batteryInExternal=true;
        batteryCap=0;
        cameraCharge=0;
    }

    /**
     *Move the battery to the camera. Updates any variables as needed to represent the move.
     */


     public void moveBatteryCamera(){
     batteryInCamera=true;
     cameraCharge=batteryCharge;
     batteryCap=batteryCapacity;
     }


    /**Remove the battery from either the camera or external charger. Updates any variables as needed
     to represent the removal.*/

    public void removeBattery(){
        batteryInCamera=false;
        batteryInExternal=false;
        cameraCharge=0;
        batteryCap=0;
    }




    /**
     *Set the power consumption of the camera.
     */

    public void setCameraPowerConsumption(double cameraPowerConsumption){
        cameraConsumption=cameraPowerConsumption;
    }

}
