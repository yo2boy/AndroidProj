package com.yo2boy.arduinoledpattern;

import java.util.ArrayList;

import com.yo2boy.arduinoledpattern.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
//import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	
	static final int check = 1111;
	int count = 0;
	// arr of switches
	private final static Integer[] ids = { R.id.switch1, R.id.switch2,
			R.id.switch3, R.id.switch4, R.id.switch5, R.id.switch6,
			R.id.switch7, R.id.switch8, R.id.switch9, R.id.switch10,
			R.id.switch11, R.id.switch12 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerLedChangeListener(ids);
	}

	// register all onCheckedChangeListener for all Switch
	private void registerLedChangeListener(Integer... ids) {
		for (int i = 0; i < ids.length; i++) {
			Integer id = ids[i];
			Switch led = (Switch) findViewById(id);
			led.setOnCheckedChangeListener(this);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Switch led;		
		if (requestCode == check && resultCode == RESULT_OK) {
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (results.get(0).equalsIgnoreCase("kriss kross"))
				sendString("0");
			else if (results.get(0).equalsIgnoreCase("random"))
				sendString("1");
			else if (results.get(0).equalsIgnoreCase("binary counter"))
				sendString("2");
			else if (results.get(0).equalsIgnoreCase("bouncing"))
				sendString("3");
			else if (results.get(0).equalsIgnoreCase("scanner"))
				sendString("4");
			else if (results.get(0).equalsIgnoreCase("knight rider"))
				sendString("5");
			else if((results.get(0).equalsIgnoreCase("stop")) || (results.get(0).equalsIgnoreCase("clear")))
				sendString("Z");
			else if (results.get(0).equalsIgnoreCase("1")){
				sendString("a");
				led = (Switch) findViewById(ids[0]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("2")){
				sendString("b");
				led = (Switch) findViewById(ids[1]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("3")){
				sendString("c");
				led = (Switch) findViewById(ids[2]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("4")){
				sendString("d");
				led = (Switch) findViewById(ids[3]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("5")){
				sendString("e");
				led = (Switch) findViewById(ids[4]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("6")){
				sendString("f");
				led = (Switch) findViewById(ids[5]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("7")){
				sendString("g");
				led = (Switch) findViewById(ids[6]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("att") || results.get(0).equalsIgnoreCase("et") || results.get(0).equalsIgnoreCase("8")){
				sendString("h");
				results.set(0, "8");
				led = (Switch) findViewById(ids[7]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("9")){
				sendString("i");
				led = (Switch) findViewById(ids[8]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("10")){
				sendString("j");
				led = (Switch) findViewById(ids[9]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("11")){
				sendString("k");
				led = (Switch) findViewById(ids[10]);
				led.setOnClickListener(null);
				led.toggle();
			}
			else if (results.get(0).equalsIgnoreCase("12")){
				sendString("l");
				led = (Switch) findViewById(ids[11]);
				led.setOnClickListener(null);
				led.toggle();
			}
			Toast.makeText(this, results.get(0), Toast.LENGTH_SHORT).show();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void loop(View v) {
		try {
			sendString(count + "");
			switch (count) {
			case 0:
				Toast.makeText(this, "Criss Cross", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(this, "Random", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(this, "Binary Counter", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(this, "Bouncing", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(this, "Scanner", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(this, "Knight Rider", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			count++;
			if (count == 6)
				count = 0;

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void stop(View v) {
		try {
			Toast.makeText(this, "Stopping", Toast.LENGTH_SHORT).show();
			sendString("Z"); // send over serial to mah beloved 'duino
			Switch led;
			for (int i = 0; i < ids.length; i++) {
				led = (Switch) findViewById(ids[i]);
				led.setOnClickListener(null);
				if (led.isChecked())
					led.toggle();
			}
			count = 0;

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void voice(View v){
		try{
			Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your wish is my command");
			startActivityForResult(i, check);
		} catch (Exception e){
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	//@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.switch1:
			if (isChecked)
				sendString("a");
			else
				sendString("A");
			return;
		case R.id.switch2:
			if (isChecked)
				sendString("b");
			else
				sendString("B");
			break;
		case R.id.switch3:
			if (isChecked)
				sendString("c");
			else
				sendString("C");
			break;
		case R.id.switch4:
			if (isChecked)
				sendString("d");
			else
				sendString("D");
			break;
		case R.id.switch5:
			if (isChecked)
				sendString("e");
			else
				sendString("E");
			break;
		case R.id.switch6:
			if (isChecked)
				sendString("f");
			else
				sendString("F");
			break;
		case R.id.switch7:
			if (isChecked)
				sendString("g");
			else
				sendString("G");
			break;
		case R.id.switch8:
			if (isChecked)
				sendString("h");
			else
				sendString("H");
			break;
		case R.id.switch9:
			if (isChecked)
				sendString("i");
			else
				sendString("I");
			break;
		case R.id.switch10:
			if (isChecked)
				sendString("j");
			else
				sendString("J");
			break;
		case R.id.switch11:
			if (isChecked)
				sendString("k");
			else
				sendString("K");
			break;
		case R.id.switch12:
			if (isChecked)
				sendString("l");
			else
				sendString("L");
			break;
		default:
			break;
		}
	}

	// Send to arduino
	private void sendString(String toSend) {
		Intent i = new Intent("primavera.arduino.intent.action.SEND_DATA");
		i.putExtra("primavera.arduino.intent.extra.DATA", toSend.getBytes());
		sendBroadcast(i);
	}

}