package de.ubercode.teatime;

import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;

public class TeaTime extends MIDlet {
	private static final Command START_COMMAND = new Command("Start",
                                                             Command.SCREEN, 0);
	private static final Command BACK_COMMAND = new Command("Back",
                                                            Command.BACK, 1);
	private static final Command EDIT_COMMAND = new Command("Edit tea list",
                                                            Command.SCREEN, 1);
	private static final Command EXIT_COMMAND = new Command("Exit",
                                                            Command.EXIT, 2);
	private static final Tea CUSTOM_TEA = new Tea("Custom tea", 0);

	private Display display;
	private List teaList;
	private Tea selectedTea;
	private Form customTeaForm;
	private TextField drawTimeSecondsField;
	private TextField drawTimeMinutesField;
	private Form teaTimerForm;
	private StringItem teaTimerTea;
	private Timer timer;

	protected void startApp() throws MIDletStateChangeException {
		final Vector teas = new Vector();
		teas.addElement(CUSTOM_TEA);
		// TODO: Don't hardcode the teas, make the list editable.
		teas.addElement(new Tea("Green tea", 120));
		teas.addElement(new Tea("Black tea", 120));
		teas.addElement(new Tea("Herbal tea", 300));
		teas.addElement(new Tea("Fruit tea", 300));

		CommandListener cl = new CommandListener() {
                public void commandAction(Command c, Displayable d) {
                    Displayable currentDisplay = display.getCurrent();
                    String label = c.getLabel();

                    if (EXIT_COMMAND.getLabel().equals(label)) {
                        notifyDestroyed();
                        return;
                    }

                    if (teaList.equals(currentDisplay)
						&& (c == List.SELECT_COMMAND
                            || START_COMMAND.getLabel().equals(label))) {
                        String teaLabel =
                            teaList.getString(teaList.getSelectedIndex());
                        if (CUSTOM_TEA.getLabel().equals(teaLabel))
                            display.setCurrent(customTeaForm);
                        else {
                            for (int i = 0; i < teas.size(); i++) {
                                Tea tea = (Tea) teas.elementAt(i);
                                if (teaLabel.equals(tea.getLabel())) {
                                    selectedTea = tea;
                                    break;
                                }
                            }
                            startTeaTimer();
                        }
                        return;
                    }

                    if (START_COMMAND.getLabel().equals(label)) {
                        if (customTeaForm.equals(currentDisplay)) {
                            int drawTimeSeconds =
								stringToInt(drawTimeSecondsField.getString());
                            int drawTimeMinutes =
								stringToInt(drawTimeMinutesField.getString());
                            TeaTime.this.selectedTea =
                                new Tea(CUSTOM_TEA.getName(),
                                        drawTimeSeconds + drawTimeMinutes * 60);
                        }
                        startTeaTimer();
                    } else if (BACK_COMMAND.getLabel().equals(label)) {
                        if (teaTimerForm.equals(currentDisplay))
                            timer.cancel();
                        display.setCurrent(teaList);
                    } else if (EDIT_COMMAND.getLabel().equals(label)) {
                        Alert alert = new Alert("Not yet implemented",
                                                "This feature is missing.",
                                                null, AlertType.INFO);
                        alert.setTimeout(Alert.FOREVER);
                        display.setCurrent(alert);
                    }
                }
            };

		this.teaList = new List("Tea", List.IMPLICIT);
		for (int i = 0; i < teas.size(); i++)
			this.teaList.append(((Tea) teas.elementAt(i)).getLabel(), null);
		this.teaList.addCommand(START_COMMAND);
		this.teaList.addCommand(EDIT_COMMAND);
		this.teaList.addCommand(EXIT_COMMAND);
		this.teaList.setCommandListener(cl);

		this.customTeaForm = new Form("Custom tea");
		this.customTeaForm.append("Draw time:");
		this.drawTimeSecondsField = new TextField("Seconds", "", 5,
                                                  TextField.NUMERIC);
		this.customTeaForm.append(this.drawTimeSecondsField);
		this.drawTimeMinutesField = new TextField("Minutes", "", 5,
                                                  TextField.NUMERIC);
		this.customTeaForm.append(this.drawTimeMinutesField);
		this.customTeaForm.addCommand(START_COMMAND);
		this.customTeaForm.addCommand(BACK_COMMAND);
		this.customTeaForm.addCommand(EXIT_COMMAND);
		this.customTeaForm.setCommandListener(cl);

		this.teaTimerForm = new Form("Waiting for tea time ...");
		this.teaTimerTea = new StringItem("", "");
		this.teaTimerForm.append(this.teaTimerTea);
		this.teaTimerForm.addCommand(BACK_COMMAND);
		this.teaTimerForm.addCommand(EXIT_COMMAND);
		this.teaTimerForm.setCommandListener(cl);

		this.display = Display.getDisplay(this);
		this.display.setCurrent(teaList);
	}

	private int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	private void startTeaTimer() {	
		this.teaTimerTea.setLabel(selectedTea.getName());
		this.teaTimerTea.setText(" "
                                 + String.valueOf(selectedTea.getDrawTime())
                                 + " seconds");
		this.display.setCurrent(this.teaTimerForm);

		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTask() {
                private int secondsLeft = selectedTea.getDrawTime();

                public void run() {
                    teaTimerTea.setText(" " + --secondsLeft + " seconds");
                    if (secondsLeft <= 0) {
                        timer.cancel();
                        Alert alert = new Alert("Tea time", "The tea is ready.",
                                                null, AlertType.INFO);
                        alert.setTimeout(Alert.FOREVER);
                        alert.setCommandListener(new CommandListener() {
                                public void commandAction(Command c,
                                                          Displayable d) {
                                    display.setCurrent(teaList);
                                }
                            });
                        display.setCurrent(alert);
                        // TODO: Do something for phones without vibration
                        //       support.
                        try {
                            display.vibrate(1000);
                            Thread.sleep(2000);
                            display.vibrate(1000);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO: Exception handling
                        }
                    }				
                }
            }, 1000, 1000);
	}

	protected void pauseApp() {
	}

    protected void destroyApp(boolean unconditional)
        throws MIDletStateChangeException {
	}
}
