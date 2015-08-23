package edu.rit.csci759.rspi;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.GpioFactory;
import edu.rit.csci759.rspi.utils.MCP3008ADCReader;

public class MyIndicator implements edu.rit.csci759.rspi.RpiIndicatorInterface {

	
	//receiving the controller instance
	static GpioController gpio = GpioFactory.getInstance();
	
	// Defining the led output pins
	GpioPinDigitalOutput yellowPin;
	GpioPinDigitalOutput greenPin;
	GpioPinDigitalOutput redPin;

		
	public MyIndicator(){
		yellowPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "yellow", PinState.LOW);
		greenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28,"green",PinState.LOW);
		redPin=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29,"red",PinState.LOW);
		MCP3008ADCReader.initSPI(gpio);
	}

	
	
	private static boolean flag=true;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		final MyIndicator myIndicator=new MyIndicator();

		//catching the SIGINT 
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{					
				System.out.println("Shutting down.");
				MyIndicator.gpio.shutdown();
				//myIndicator.led_all_off();
				flag = false;
			}
		});
		

		while(flag){

			//reflecting the light intensity
	        //int lightDens=myIndicator.read_ambient_light_intensity();
	        int tempOrginal=myIndicator.read_temperature();
	        
	        
			/*
			if (lightDens>=0 && lightDens<35){
				myIndicator.led_when_low();
			}else if (lightDens>=35 && lightDens<75){
				myIndicator.led_when_mid();
			}else if (lightDens>=75 && lightDens<=100){
				myIndicator.led_when_high();
			}else{
				myIndicator.led_error(3);
			}
			Thread.sleep(1000);
			*/
	        
	        //reflecting the temperature
	        
	        int temp=(int)(tempOrginal / 10.24);
	        System.out.println("temp=" +temp);
	        
	        float tmp36_mVolts =(float) (tempOrginal * (3300.0/1024.0));
	        System.out.println("tmp36_mVolts=" +tmp36_mVolts);

			// 10 mv per degree
	        float temp_C = (float) (((tmp36_mVolts - 100.0) / 10.0) - 40.0);
	        System.out.println("temp_C=" +temp_C);
	        
	        // convert celsius to fahrenheit
	        float temp_F = (float) ((temp_C * 9.0 / 5.0) + 32);
	        System.out.println("temp_F=" +temp_F);
	        
			if (temp>=0 && temp<35){
				myIndicator.led_when_low();
				
			}else if (temp>=35 && temp<75){
				myIndicator.led_when_mid();
				
			}else if (temp>=75 && temp<=100){
				myIndicator.led_when_high();
				
			}else{
				myIndicator.led_error(3);
			}
			Thread.sleep(1000);
			
		}
		//gpio.shutdown();
	}

	@Override
	public void led_all_off() {
		this.yellowPin.low();
		//yellowPin.setState(PinState.LOW);
		greenPin.low();
		redPin.low();
	}

	@Override
	public void led_all_on() {
		yellowPin.high();
		//yellowPin.setState(PinState.HIGH);
		greenPin.high();
		redPin.high();
		
	}

	@Override
	public void led_error(int blink_count) throws InterruptedException {
		for(int i =0 ; i<blink_count; i++){
			this.led_all_on();
			Thread.sleep(1000);
			this.led_all_off();
			Thread.sleep(1000);
		}
		
	}

	@Override
	public void led_when_low() {
		yellowPin.low();
		greenPin.low();
		redPin.high();
		
	}

	@Override
	public void led_when_mid() {
		yellowPin.high();
		greenPin.low();
		redPin.low();
		
	}

	@Override
	public void led_when_high() {		
		yellowPin.low();
		greenPin.high();
		redPin.low();
		
	}

	@Override
	public int read_ambient_light_intensity() {
		
		int lightDensOrginal = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH1.ch());
		int lightDens = (int)(lightDensOrginal / 10.24); 	
		System.out.println("light=" + lightDens);
		return lightDens;
	}

	@Override
	public int read_temperature() {
		
		int tempOrginal=MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0.ch());	
		System.out.println("tempOrginal=" +tempOrginal);
		return tempOrginal;
	}

}
