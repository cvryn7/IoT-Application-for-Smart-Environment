package edu.rit.csci759.jsonrpc.server;


import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.GpioFactory;
import edu.rit.csci759.rspi.utils.MCP3008ADCReader;


/* Class Tipperclass
 * Author : Dler, Vaibhav and Karan
 * Description: Contains Method to 
 * 				Control LED's and
 * 				Reading Sensor Values.
 * 				Instance of this class
 * 				runs as a thread in background
 * 				reading current Temperature
 * 				and ambience values after some
 * 				seconds.
 */
public  class LedBlindSimulator implements Runnable{

	//receiving the controller instance
	static GpioController gpio = GpioFactory.getInstance();
	
	// Defining the led output pins
	static GpioPinDigitalOutput yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "yellow", PinState.LOW);
	static GpioPinDigitalOutput greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28,"green",PinState.LOW);
	static GpioPinDigitalOutput redPin =gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29,"red",PinState.LOW);
	
	//Initializing instance of TipperClass to evaluate sensor values. 
	TipperClass tipper=new TipperClass();;
		
	//Constructor
	public LedBlindSimulator(){
		MCP3008ADCReader.initSPI(gpio);
	}
	
	//Off all the LED's
	public static void led_all_off() {
		yellowPin.low();
		greenPin.low();
		redPin.low();
	}
	
	//Glow all the LED's
	public static void led_all_on() {
		yellowPin.high();
		greenPin.high();
		redPin.high();
		
	}
	
	//Blink LED's if some error occurs
	public static void led_error(int blink_count) throws InterruptedException {
		for(int i =0 ; i<blink_count; i++){
			led_all_on();
			Thread.sleep(1000);
			led_all_off();
			Thread.sleep(1000);
		}
	}

	//Glow Red LED: Indicating Blinds are closed
	public static void led_when_close() {
		yellowPin.low();
		greenPin.low();
		redPin.high();
		
	}

	//Glow Yellow LED: Indicating Blinds are Half Opened 
	public static void led_when_half() {
		yellowPin.high();
		greenPin.low();
		redPin.low();
		
	}

	//Glow Green LED: Indicating Blinds are Opened
	public static void led_when_open() {		
		yellowPin.low();
		greenPin.high();
		redPin.low();
		
	}

	//Reading and returning Ambient value from the Ambient sensor.
	public static int read_ambient_light_intensity() {
		int lightDensOrginal = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH1.ch());
		int lightDens = (int)(lightDensOrginal / 10.24); 	
		return lightDens;
	}

	//Reading and returning Temperature value form the  Temperature sensor
	public static int read_temperature() {
		int tempOrginal=MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0.ch());	
		return tempOrginal;
	}

	//Calculate Sensor readings after every 3 sec.
	// and evaluate it find current blind's state.
	public void run() {
		//Current temperature reading from sensor
		double tempOrginal;
		double temp;
		//Current light reading from sensor
		double lightOrginal;
		
		//Initialize instance of TipperClass to find 
		//state of Blind
		TipperClass tipperClass= new TipperClass();
        
		String blindPosition="";
        
		while(true){
			
			//Getting Current Value of Temperature
			tempOrginal =read_temperature();
	        temp=(tempOrginal / 10.24);
	        
	        //Getting Current Value of Light.
	       	lightOrginal=read_ambient_light_intensity() ;
	        
	       	//Getting State of Blinds based of current Temperature and Light
	        blindPosition= tipperClass.setVaribales(temp, lightOrginal);
	        
	        //Set the LED according to state of Blinds returned by TipperClass
			if (blindPosition.equals("close")){
				led_when_close();
				
			}else if (blindPosition.equals("half")){
				led_when_half();
				
			}else if (blindPosition.equals("open")){
				led_when_open();
			}			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}
