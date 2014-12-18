package com.evbrain.raspberrypi.gpio;

import java.util.EnumSet;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.PinImpl;

public class GpioHelper {
	public static void setPin(boolean state,int address)
	{
		final GpioController gpio = GpioFactory.getInstance();
		Pin pin = new PinImpl(RaspiGpioProvider.NAME, address,"GPIO "+ String.valueOf(address), 
                EnumSet.of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT),
                PinPullResistance.all());
		final GpioPinDigitalOutput output = gpio.provisionDigitalOutputPin(pin, "MyLED", PinState.getState(state));
	}
}
